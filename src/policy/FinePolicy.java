package policy;

import java.time.LocalDate;

/**
 * Interface class for calculating overdue fines.
 */
public interface FinePolicy {

    /**
     * Calculates the fine in pence for an item returned on {@code returnDate}
     * when the due date was {@code dueDate}.
     *
     * @param dueDate the due date of the loan
     * @param returnDate the actual return date of the loan
     * @return non-negative fine amount in pence
     */
    int calculateFine(LocalDate dueDate, LocalDate returnDate);
}
