package domain;

/**
 * Represents the state of a Reservation within the library system.
 * <p>
 *     Reservations progress through a simple lifecycle:
 *     ACTIVE -> FULFILLED or CANCELLED.
 * </p>
 */
public enum ReservationStatus {
    ACTIVE,     // Reservation is active and awaiting availability
    FULFILLED,  // Reservation has been fulfilled and is ready or collected
    CANCELLED   // Reservation was cancelled by member or librarian
}
