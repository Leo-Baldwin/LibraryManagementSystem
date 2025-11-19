import domain.policy.StandardFinePolicy;

import java.time.LocalDate;

/**
 * Unit testing class for StandardFinePolicy.
 */
public class StandardFinePolicyTest {

    public static void main(String[] args) {
        StandardFinePolicyTest test = new StandardFinePolicyTest();

        test.testReturnBeforeDueDate();
        test.testReturnOnDueDate();
        test.testReturnOneDayLate();
        test.testReturnFiveDaysLate();
        test.testNegativePencePerDay();

    }

    private void testReturnBeforeDueDate() {
        StandardFinePolicy policy = new StandardFinePolicy(50);
        LocalDate due = LocalDate.of(2025, 11, 15);
        LocalDate returnDate = LocalDate.of(2025, 11, 13);

        int fine = policy.calculateFine(due, returnDate);
        if (fine == 0) {
            System.out.println("Test 1 - return before due date - PASS");
        }  else {
            System.out.println("Test 1 - return before due date - FAIL");
        }
    }

    private void testReturnOnDueDate() {
        StandardFinePolicy policy = new StandardFinePolicy(50);
        LocalDate due = LocalDate.of(2025, 11, 15);
        LocalDate returnDate = LocalDate.of(2025, 11, 15);

        int fine = policy.calculateFine(due, returnDate);
        if (fine == 0) {
            System.out.println("Test 2 - return on due date - PASS");
        }  else {
            System.out.println("Test 2 - return on due date - FAIL");
        }
    }

    private void testReturnOneDayLate() {
        StandardFinePolicy policy = new StandardFinePolicy(50);
        LocalDate due = LocalDate.of(2025, 11, 15);
        LocalDate returnDate = LocalDate.of(2025, 11, 16);

        int fine = policy.calculateFine(due, returnDate);
        if (fine == 50) {
            System.out.println("Test 3 - return 1 day late - PASS");
        }  else {
            System.out.println("Test 3 - return 1 day late - FAIL");
        }
    }

    private void testReturnFiveDaysLate() {
        StandardFinePolicy policy = new StandardFinePolicy(50);
        LocalDate due = LocalDate.of(2025, 11, 15);
        LocalDate returnDate = LocalDate.of(2025, 11, 20);

        int fine = policy.calculateFine(due, returnDate);
        if (fine == 250) {
            System.out.println("Test 4 - return 5 days late - PASS");
        }  else {
            System.out.println("Test 4 - return 5 days late - FAIL");
        }
    }

    private void testNegativePencePerDay() {
        try {
            StandardFinePolicy policy = new StandardFinePolicy(-50);
            System.out.println("Test 5 - negative pencePerDay - FAIL (no exception thrown)");
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 - negative pencePerDay - PASS (exception thrown: " + e.getMessage() + ")");
        }
    }

}
