package domain;

import java.util.ArrayList;
import java.util.List;

/** Concrete media type representing a book in the Library's collection.
 * <p>
 *     Extends {@link MediaItem} and adds metadata such as
 *     title, authors, year of publication, and categories.
 * </p>
 */
public class Book extends MediaItem{

    /** The book's display title. */
    private String title;

    /** List of author names. */
    private String author;

    /** Year the book was published. */
    private int yearOfPublish;

    /** Categories/genres (e.g. 'Fiction', 'History'). */
    private List<String> categories = new ArrayList<>();

    /**
     * Constructs a Book with full metadata.
     *
     * @param title
     * @param author
     * @param yearOfPublish
     * @param categories
     */
    public Book(String title, String author, int yearOfPublish,  List<String> categories) {
        super();
        setTitle(title);
        setAuthor(author);
        setYearOfPublish(yearOfPublish);
        if  (categories != null) this.categories.addAll(categories);
    }

    /**
     * Returns the book's title.
     *
     * @return title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the books title.
     *
     * @param title new title, cannot be null or blank
     * @throws IllegalArgumentException if {@code title} is null or blank
     */
    public void setTitle(String title) {
        if ((title == null) || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    /**
     * Returns the book's author.
     *
     * @return author string
     */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if ((author == null) || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        this.author = author;
    }

    /**
     * Returns the year of publication.
     *
     * @return publication year
     */
    public int getYearOfPublish() {
        return yearOfPublish;
    }

    /**
     * Sets the year of publication, use 0 if unknown.
     *
     * @param yearOfPublish publication year or 0 if unknown
     */
    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    /**
     * Returns the categories list
     *
     * @return a copy of the list of catgories
     */
    public List<String> getCategories() {
        return List.copyOf(categories);
    }

    /**
     * Adds a category label to the list.
     *
     * @param category non-null or blank category name
     * @throws IllegalArgumentException if {@code category} is null or blank
     */
    public void addCategory(String category) {
        if  ((category == null) || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
        categories.add(category.trim());
    }

    /**
     * Removes the first instance of a category label (if present).
     *
     * @param category not null/blank category label to remove from list
     * @return {@code true} if removed successfully, {@code false} if category label not present
     * @throws IllegalArgumentException if {@code category} is null or blank
     */
    public boolean removeCategory(String category) {
        if  ((category == null) || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
        return categories.remove(category.trim());
    }

    /**
     * Returns a human-readable string representing this book.
     *
     * @return formatted string including title, authors, and year of publish
     */
    @Override
    public String toString() {
        return "Book: \n" +
                "Title: " + title + "\n" +
                "Authors: " + getAuthors() + "\n" +
                "Year of publish: " + yearOfPublish + "\n" +
                "Categories: " + getCategories() + "\n" +
                "Status: " + getStatus();
    }
}
