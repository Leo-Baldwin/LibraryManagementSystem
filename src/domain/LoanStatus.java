package domain;

/**
 * Represents the lifecycle state of a Loan within the library system.
 * <p>
 *     A Loan begins as OUTSTANDING when it is created and is set to RETURNED
 *     when the media item has been returned by the Member.
 * </p>
 */
public enum LoanStatus {
    OUTSTANDING,    // Loan is currently active
    RETURNED        // MediaItem has been returned and Loan has been closed
}
