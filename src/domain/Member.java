package domain;

/**
 * Represents a library member who can borrow and reserve media items.
 * <p>
 *     Extends the abstract {@link Person} class by adding member specific
 *     attributes and methods. Each member is uniquely identified within the
 *     library system by their inherited {@code id}.
 * </p>
 */
public class Member extends Person {

    /** Indicates whether the person is currenctly an active member. */
    private boolean activeMember;

    /**
     * Constructs a new active Member with name and email inherited from superclass.
     *
     * @param name the member's full name
     * @param email the member's email address
     */
    public Member(String name, String email) {
        super(name, email);
        this.activeMember = true; // default to active
    }

    /**
     * Constructs a Member with explicit activation state.
     *
     * @param name the member's full name
     * @param email the member's email address
     * @param activeMember {@code true} if the member is currently active
     */
    public Member(String name, String email, boolean activeMember) {
        super(name, email);
        this.activeMember = activeMember;
    }

    /** Returns whether the member's account is active.
     *
     * @return {@code true} if the member is active.
     */
    public boolean isActiveMember() {
        return activeMember;
    }

    /**
     * Sets the members activity status.
     *
     * @param activeMember {@code true} to activate membership, {@code false} to deactivate
     */
    public void setActiveMember(boolean activeMember) {
        this.activeMember = activeMember;
    }

    /**
     * Determines whether the member can borrow items.
     * <p>
     *     The member must have an active membership and no overdue loans.
     * </p>
     *
     * @return {@code true} if the member can borrow; otherwise {@code false}
     */
    public boolean canBorrow() {
        return activeMember && !hasOverdueLoans();
    }

    /**
     * Determines whether the member has any overdue loans.
     *
     * @return {@code true} if the member has overdue loans; otherwise {@code false}
     */
    public boolean hasOverdueLoans() {
        return false; // Placeholder: replace with full functionality once all classes are built
    }

    /**
     * Returns a human-readable string representing this member.
     *
     * @return formatted string with name, email, and activity status
     */
    @Override
    public String toString() {
        return "Name: " + getName() + ", \n" +
                "Email: " + getEmail() + ", \n" +
                "Active: " + activeMember;
    }
}
