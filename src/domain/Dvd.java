package domain;

import java.util.List;

/**
 * Concrete media type representing a DVD in the Library's collection.
 * <p>
 *     Extends {@link MediaItem} with film-specific metadata such as
 *     title, directors, age rating, and categories.
 * </p>
 */
public class Dvd extends MediaItem{

    /** Title of the DVD. */
    private String title;

    /** Duration of the DVD in minutes. */
    private int durationMinutes;

    /** Age rating of the DVD. */
    private String ageRating;

    /** Category labels (e.g. 'Fiction', 'History'). */
    private List<String> categories;

    /**
     * Constructs a Dvd with full metadata.
     *
     * @param title DVD title
     * @param durationMinutes runtime in minutes
     * @param ageRating age rating label
     * @param categories list of categories
     */
    public Dvd(String title, int durationMinutes, String ageRating, List<String> categories) {
        super();
        setTitle(title);
        setDurationMinutes(durationMinutes);
        setAgeRating(ageRating);
        if  (categories != null) this.categories.addAll(categories);
    }

    /** @return the DVDs title */
    public String getTitle() {
        return title;
    }

    /** @param title new title; cannot be null/blank */
    public void setTitle(String title) {
        if  (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    /** @return the runtime in minutes */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /** @param durationMinutes new runtime in minutes; cannot be null/blank */
    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        this.durationMinutes = durationMinutes;
    }

    /** @return the age rating label */
    public String getAgeRating() {
        return ageRating;
    }

    /** @param ageRating new age rating label; cannot be null/blank */
    public void setAgeRating(String ageRating) {
        if (ageRating == null || ageRating.isBlank()) {
            throw new IllegalArgumentException("AgeRating cannot be null or blank");
        }
        this.ageRating = ageRating;
    }

    /** @return a copy of the list of categories */
    public List<String> getCategories() {
        return List.copyOf(categories);
    }

    /** @param categories new list of category labels; cannot be null/empty */
    public void setCategories(List<String> categories) {
        if  (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty");
        }
        this.categories.addAll(categories);
    }

    /** @return a formatted string representing the DVD, including title, duration and age rating */
    @Override
    public String toString() {
        return "DVD: \n" +
                "Title: " + title + " \n" +
                "Duration: " + durationMinutes + " \n" +
                "AgeRating: " + ageRating + " \n" +
                "Categories: " + getCategories() + "\n" +
                "Status: " + getStatus();
    }

}
