package domain.policy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StandardFinePolicy implements FinePolicy {

    /** Fine to be applied for each overdue day, in pence. */
    private final int pencePerDay;

    /**
     * Creates a policy with the specified daily overdue charge.
     *
     * @param pencePerDay fine per day in pence
     */
    public StandardFinePolicy(int pencePerDay) {
        this.pencePerDay = pencePerDay;
    }

    /** @return fine value in pence */
    public int getPencePerDay() {
        return pencePerDay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateFine(LocalDate dueDate, LocalDate returnDate) {
        // Ensures that the difference between the two dates cannot be negative
        long daysLate = Math.max(0, ChronoUnit.DAYS.between(dueDate, returnDate));
        long total = daysLate * pencePerDay;
        // Returns calculated fine cast to int
        return (int) total;
    }
}
