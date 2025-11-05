package domain;

import policy.FinePolicy;
import policy.LoanPolicy;

import java.time.LocalDate;
import java.util.*;

/**
 * Aggregate root class that coordinates all the behaviour for domain entities within the
 * library system.
 * <p>
 *     Stores the collections of {@link MediaItem}s, {@link Loan}s, {@link Member}s, and
 *     {@link Reservation}s, and performs behaviours such as adding/removing items,
 *     loaning/returning items, and managing reservations.
 * </p>
 */
public class Library {

    /**
     * All media items by ID.
     */
    private final Map<UUID, MediaItem> items = new HashMap<>();

    /**
     * All loans by ID.
     */
    private final Map<UUID, Loan> loans = new HashMap<>();

    /**
     * All members by ID.
     */
    private final Map<UUID, Member> members = new HashMap<>();

    /**
     * All reservations by ID.
     */
    private final Map<UUID, Deque<Reservation>> reservationsByMediaItem = new HashMap<>();

    /**
     * Policy for calculating due dates.
     */
    private final LoanPolicy loanPolicy;

    /**
     * Policy for calculating fines.
     */
    private final FinePolicy finePolicy;

    /**
     * Constructs a Library aggregate with configured loan and fine policies.
     *
     * @param loanPolicy policy for calculating due dates; must not be null
     * @param finePolicy policy for calculating fines; must not be null
     */
    public Library(LoanPolicy loanPolicy, FinePolicy finePolicy) {
        if (loanPolicy == null || finePolicy == null) {
            throw new IllegalArgumentException("Policies cannot be null");
        }
        this.loanPolicy = loanPolicy;
        this.finePolicy = finePolicy;
    }

    // ---------------------------------------- Items ----------------------------------------

    /**
     * Adds a media item to the items Map, with its UUID as the key.
     *
     * @param item a non null {@link MediaItem}
     * @return the item being added
     */
    public MediaItem addItem(MediaItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        items.put(item.getMediaId(), item);
        return item;
    }

    /**
     * Removes a media item from the items Map.
     *
     * @param mediaId the ID of the item to remove
     */
    public void removeItem(UUID mediaId) {
        // Retrieves the item from items Map by its ID
        MediaItem item = items.get(mediaId);
        // Checks if item is currently on loan or reserved
        if (!item.isAvailable()) {
            throw new IllegalArgumentException("Cannot remove: item is not available");
        } else if (hasActiveReservation()) {
            throw new IllegalArgumentException("Cannot remove: item has active reservation");
        }
        items.remove(mediaId);
    }

    // ---------------------------------------- Members --------------------------------------

    /**
     * Adds a member to the members Map, with its UUID as the key
     *
     * @param member a non null {@link Member}
     * @return the member being added
     */
    public Member addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.put(member.getId(), member);
        return member;
    }

    /**
     * Removes a member from the members Map.
     *
     * @param memberId the ID of the member to remove
     */
    public void removeMember(UUID memberId) {
        // Retrieves the member from members Map by their ID
        Member member = members.get(memberId);

        if (member.hasOverdueLoans()) {
            throw new IllegalArgumentException("Cannot remove: member has overdue loans");
        }
        members.remove(memberId);
    }

    // ---------------------------------------- Loans ----------------------------------------

    /**
     * Create a new loan on an available item for member.
     * <p>
     * Invariants:
     *     <ul>
     *         <li>Member must be active.</li>
     *         <li>Member must not have overdue loans.</li>
     *         <li>Item must be AVAILABLE.</li>
     *     </ul>
     * </p>
     *
     * @param memberId the ID of the member borrowing item
     * @param mediaId  the ID of the item being borrowed
     * @return the created {@link Loan}
     */
    public Loan loanItem(UUID memberId, UUID mediaId) {
        Member member = members.get(memberId);
        MediaItem item = items.get(mediaId);

        if (!member.isActiveMember()) {
            throw new IllegalStateException("Cannot loan item while inactive member");
        } else if (member.hasOverdueLoans()) {
            throw new IllegalArgumentException("Cannot loan item with overdue loans");
        } else if (!item.isAvailable()) {
            throw new IllegalArgumentException("Item is not currently available");
        }

        LocalDate loanDate = LocalDate.now();
        LocalDate dueDate = loanPolicy.calculateDueDate(loanDate);

        // Create new loan object and add to loans Map
        Loan loan = new Loan(member.getId(), item.getMediaId(), loanDate, dueDate);
        loans.put(loan.getLoanId(), loan);

        // Change item state
        item.setStatus(AvailabilityStatus.ON_LOAN);

        return loan;
    }

    /**
     * Return an item and close a loan for member.
     *
     * @param mediaId the ID of the item being returned
     */
    public void returnItem(UUID mediaId) {
        MediaItem item = items.get(mediaId);
        Loan loan = findOpenLoanByMediaId(mediaId);

        // Get current date and calculate any fine accrued
        LocalDate returnDate = LocalDate.now();
        int fine = finePolicy.calculateFine(loan.getDueDate(), returnDate);

        // Record fine
        loan.setFineAccrued(fine);

        // Change loan status to RETURNED and record return date
        loan.markReturned(returnDate);

        // Update item status to RESERVED if it has a reservation; else AVAILABLE
        if (hasActiveReservation(mediaId)) {
            item.setStatus(AvailabilityStatus.RESERVED);
        } else {
            item.setStatus(AvailabilityStatus.AVAILABLE);
        }
    }

    // ---------------------------------------- Reservations ----------------------------------

    public Reservation placeReservation(UUID memberId, UUID mediaId) {
        Member member = members.get(memberId);

        if (!member.isActiveMember()) {
            throw new IllegalArgumentException("Inactive members cannot reserve items.");
        }

        Deque<Reservation> reservations = reservationsByMediaItem.get(mediaId);
        Reservation r = new Reservation(memberId, mediaId, LocalDate.now());
        reservations.addLast(r);
        return r;
    }

    public void fulfillReservation(UUID mediaId) {
        MediaItem item = items.get(mediaId);

        if(fulfillNextReservation(mediaId)) {
            item.setStatus(AvailabilityStatus.RESERVED);
        }
    }


    // ---------------------------------------- Helpers ---------------------------------------

    public boolean hasActiveReservation(UUID mediaId) {
        MediaItem item = items.get(mediaId);

        Deque<Reservation> reservations = reservationsByMediaItem.get(mediaId);
        if (reservations != null && !reservations.isEmpty()) {
            Reservation nextReservation = reservations.pop(); // gets the next reservation from the queue
            if (nextReservation.getStatus() == ReservationStatus.ACTIVE) {
                item.setStatus(AvailabilityStatus.RESERVED);
            } else {
                item.setStatus(AvailabilityStatus.AVAILABLE);
            }
        } else {
            item.setStatus(AvailabilityStatus.AVAILABLE);
        }
    }

    public Loan findOpenLoanByMediaId(UUID mediaId) {
        for (Loan loan : loans.values()) {
            if (loan.getMediaId().equals(mediaId) && loan.getStatus() == LoanStatus.OUTSTANDING) {
                return loan;
            }
        }
        throw new NoSuchElementException("Cannot find open loan for loan with id " + mediaId);
    }

    public boolean fulfillNextReservation(UUID mediaId) {
        Deque<Reservation> reservations = reservationsByMediaItem.get(mediaId);

        if (reservations == null) return false;

        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.ACTIVE) {
                reservation.fulfil();
                return true;
            }
        }
        return false;
    }
}
