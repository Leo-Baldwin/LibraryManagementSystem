package domain;

import java.util.ArrayList;
import java.util.List;

/** Concrete media type representing a book in the Library's collection.
 * <p>
 *     Extends the abstract {@link MediaItem} and adds metadata such as
 *     title, authors, year of publication, and categories/genres.
 * </p>
 */
public class Book extends MediaItem{

    private String title;

    private List<String> authors = new ArrayList<>();

    private int yearOfPublish;

    private List<String> categories = new ArrayList<>();

    public Book(String title, List<String> authors, int yearOfPublish,  List<String> categories) {
        super();
        setTitle(title);
        if (authors != null) this.authors.addAll(authors);
        this.yearOfPublish = yearOfPublish;
        if  (categories != null) this.categories.addAll(categories);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if ((title == null) || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    public List<String> getAuthors() {
        return List.copyOf(authors);
    }

    public void addAuthor(String author) {
        if  ((author == null) || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        authors.add(author.trim());
    }

    public boolean removeAuthor(String author) {
        if  ((author == null) || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        return authors.remove(author.trim());
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public List<String> getCategories() {
        return List.copyOf(categories);
    }

    public void addCategory(String category) {
        if  ((category == null) || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
        categories.add(category.trim());
    }

    public boolean removeCategory(String category) {
        if  ((category == null) || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
        return categories.remove(category.trim());
    }

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
