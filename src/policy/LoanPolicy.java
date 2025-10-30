package policy;

import java.time.LocalDate;

/**
 * Interface class for calculating a loans due date.
 */
public interface LoanPolicy {

    /**
     * Calculates a due date for a loan that begins on {@code loanDate}.
     *
     * @param loanDate the start date of the loan
     * @return the calculated due date of the loan
     */
    LocalDate calculateDueDate(LocalDate loanDate);
}
