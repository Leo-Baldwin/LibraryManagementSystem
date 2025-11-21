import domain.model.Loan;
import domain.model.LoanStatus;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Unit tests for the Loan class.
 */
public class LoanTest {

    public static void main(String[] args) {
        LoanTest test = new LoanTest();
        test.testMarkReturnedSetsStatusAndDate();
        test.testMarkReturnedCannotBeCalledTwice();
        test.testIsOverdueFalseBeforeDueDate();
        test.testIsOverdueTrueAfterDueDate();
        test.testIsOverdueRejectsNullCurrentDate();
        test.testSetFineAccruedRejectsNegative();
        test.testSetFineAccruedStoresPositive();
    }

    private Loan testLoan(LocalDate loanDate, LocalDate dueDate) {
        return new Loan(
                UUID.randomUUID(),
                UUID.randomUUID(),
                loanDate,
                dueDate
        );
    }

    private void testMarkReturnedSetsStatusAndDate() {
        Loan loan = testLoan(LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 25));

        LocalDate returnDate = LocalDate.of(2025, 11, 20);
        loan.markReturned(returnDate);

        boolean statusOk = loan.getStatus() == LoanStatus.RETURNED;
        boolean dateOk   = returnDate.equals(loan.getReturnDate());

        if (statusOk && dateOk) {
            System.out.println("L1 - PASS");
        } else {
            System.out.println("L1 - FAIL " +
                    "(status=" + loan.getStatus() +
                    ", returnDate=" + loan.getReturnDate() + ")");
        }
    }

    private void testMarkReturnedCannotBeCalledTwice() {
        Loan loan = testLoan(LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 10));

        loan.markReturned(LocalDate.of(2025, 11, 20));
        try {
            loan.markReturned(LocalDate.of(2025, 11, 21));
            System.out.println("L2 - FAIL (no exception thrown)");
        } catch (IllegalStateException e) {
            System.out.println("L2 - PASS (exception: " + e.getMessage() + ")");
        }
    }

    private void testIsOverdueFalseBeforeDueDate() {
        LocalDate loanDate = LocalDate.of(2025, 11, 10);
        LocalDate dueDate  = LocalDate.of(2025, 11, 20);
        Loan loan = testLoan(loanDate, dueDate);

        LocalDate currentDate = LocalDate.of(2025, 11, 15);
        boolean overdue = loan.isOverdue(currentDate);

        if (!overdue) {
            System.out.println("L3 - PASS");
        } else {
            System.out.println("L3 - FAIL (expected false, got true)");
        }
    }

    private void testIsOverdueTrueAfterDueDate() {
        LocalDate loanDate = LocalDate.of(2025, 11, 10);
        LocalDate dueDate  = LocalDate.of(2025, 11, 20);
        Loan loan = testLoan(loanDate, dueDate);

        LocalDate currentDate = LocalDate.of(2025, 11, 25);
        boolean overdue = loan.isOverdue(currentDate);

        if (overdue) {
            System.out.println("L4 - PASS");
        } else {
            System.out.println("L4 - FAIL (expected true, got false)");
        }
    }

    private void testIsOverdueRejectsNullCurrentDate() {
        Loan loan = testLoan(LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 20));

        try {
            loan.isOverdue(null);
            System.out.println("L5 - FAIL (no exception thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("L5 - PASS (exception: " + e.getMessage() + ")");
        }
    }

    private void testSetFineAccruedRejectsNegative() {
        Loan loan = testLoan(LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 20));

        try {
            loan.setFineAccrued(-1);
            System.out.println("L6 - FAIL (no exception thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("L6 - PASS (exception: " + e.getMessage() + ")");
        }
    }

    private void testSetFineAccruedStoresPositive() {
        Loan loan = testLoan(LocalDate.of(2025, 11, 10),
                LocalDate.of(2025, 11, 20));

        loan.setFineAccrued(250);
        if (loan.getFineAccrued() == 250) {
            System.out.println("L7 - PASS");
        } else {
            System.out.println("L7 - FAIL (expected 250, got " +
                    loan.getFineAccrued() + ")");
        }
    }

}
