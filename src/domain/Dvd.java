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

    private String title;

    private int durationMinutes;

    private String ageRating;

    private List<String> categories;

    /**
     * Constructs a Dvd with full metadata.
     *
     * @param title the DVD title; must not be blank
     * @param durationMinutes runtime in minutes; must not be negative
     * @param ageRating age rating label; must not be blank
     * @param categories
     */
    public Dvd(String title, int durationMinutes, String ageRating, List<String> categories) {
        super();
        setTitle(title);
        setDurationMinutes(durationMinutes);
        setAgeRating(ageRating);
        if  (categories != null) this.categories.addAll(categories);
    }

}
