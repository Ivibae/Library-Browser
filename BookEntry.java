import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public final class BookEntry {

    /** Private instance field that represents the title of the book.*/
    private final String title;
    /** Private instance field that represents the array of all the authors of the book.*/
    private final String[] authors;
    /** Private instance field that represents the rating of the book.*/
    private final float rating;
    /** Private instance field that represents the ISBN of the book.*/
    private final String ISBN;
    /** Private instance field that represents the number of pages of the book.*/
    private final int pages;

    /** Minimum rating a given book can have*/
    private static final int MIN_RATING = 0;
    /** Maximum rating a given book can have*/
    private static final int MAX_RATING = 5;
    /** Minimum number of pages the book can have*/
    private static final int MIN_PAGES = 0;
    /** String that is used to separate the authors in a book in the toString method*/
    private static final String AUTHORS_SEPARATOR = ", ";
    /** String that is used to format the number of decimal places of the float rating*/
    private static final String RATING_FORMAT = "%.2f";



    /** Constructor of the BookEntry class. Takes five parameters and initialises the corresponding fields.
     * @param title String that represents the name of the title of a given book
     * @param authors Array of strings which represents all the authors of a given book
     * @param rating Float between {@value #MIN_RATING} and {@value #MAX_RATING} that represents the rating the book has.
     * @param ISBN String that represents the specific ISBN of a given book
     * @param pages Integer larger or equal than {@value #MIN_PAGES} that represents the number of pages a given book has.
     *
     * @throws NullPointerException if the parameter title is null.
     * @throws NullPointerException if the parameter authors is null.
     * @throws NullPointerException if an author inside the array authors is null.
     * @throws NullPointerException if the parameter ISBN is null.
     * @throws IllegalArgumentException if the parameter rating is smaller than {@value #MIN_RATING} or larger than
     * {@value #MAX_RATING}.
     * @throws IllegalArgumentException if the number of pages is smaller than {@value #MIN_PAGES}.
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages){
        Objects.requireNonNull(title, "Given title must not be null");
        Objects.requireNonNull(authors, "Given authors array must not be null");
        for (String author : authors){
            Objects.requireNonNull(author, "An author of the book must not be null");
        }
        Objects.requireNonNull(ISBN, "Given ISBN must not be null");

        if (rating < MIN_RATING || rating > MAX_RATING){
            throw new IllegalArgumentException("Given rating must be between " + MIN_RATING + " and " + MAX_RATING +
                    ", but it is: " + rating);
        }

        if (pages < MIN_PAGES){
            throw new IllegalArgumentException("Given number of pages must not be less than " + MIN_PAGES
                    + ", but it is: " + pages);
        }

        this.title = title;
        this.authors = authors.clone();
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /** Getter for the instance field title.
     * @return a string that represents the title of the book
     */
    public String getTitle() {
        return title;
    }

    /** Getter for the instance field authors.
     * @return a clone of the array of Strings that represents the authors of the book
     */
    public String[] getAuthors() {
        return authors.clone();
    }

    /** Getter for the instance field rating.
     * @return a float that represents the rating of the book
     */
    public float getRating() {
        return rating;
    }

    /** Getter for the instance field ISBN.
     * @return a string that represents the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /** Getter for the instance field pages.
     * @return an integer that represents the number of pages of the book
     */
    public int getPages() {
        return pages;
    }

    /** Method that converts BookEntry into String format.
     * @return a string that represents a book entry.
     */
    @Override
    public String toString() {
        return  title + "\n" +
                "by " + stringAuthors() + "\n" +
                "Rating: " + String.format(RATING_FORMAT, rating) + "\n" +
                "ISBN: " + ISBN + "\n" +
                pages + " pages\n" ;
    }

    /** Tests equality of the BookEntry with respect to the given object
     * @param that the instance we want to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object that){
        if (this == that){
            return true;
        }
        if (that == null || getClass() != that.getClass()){
            return false;
        }
        BookEntry bookEntry = (BookEntry) that;
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**Generates a hash code based on the current state of the given book
     * @return this book's hashcode
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

    /** Helper method of toString() that helps to print the authors of the book.
     * @return the authors of the book separated by comas.
     */
    private String stringAuthors(){
        StringBuilder authorBuilder = new StringBuilder(authors[0]);
        if (authors.length > 1) {
            for (int i = 1; i < authors.length; i++) {
                authorBuilder.append(AUTHORS_SEPARATOR).append(authors[i]);
            }
        }
        return authorBuilder.toString();
}

}
