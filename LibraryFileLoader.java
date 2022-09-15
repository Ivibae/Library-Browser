import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /** String that separates the data values in the file*/
    private static final String DATA_VALUES_SEPARATOR = ",";
    /** String that separates the authors in the file*/
    private static final String AUTHOR_SEPARATOR = "-";
    /** The index of the title in the BookData file*/
    private static final int TITLE_INDEX = 0;
    /** The index of the authors in the BookData file*/
    private static final int AUTHORS_INDEX = 1;
    /** The index of the rating in the BookData file*/
    private static final int RATING_INDEX = 2;
    /** The index of the ISBN in the BookData file*/
    private static final int ISBN_INDEX = 3;
    /** The index of the pages in the BookData file*/
    private static final int PAGES_INDEX = 4;



    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }


    /** Parse file content loaded previously with the loadFileContent method.
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        ArrayList<BookEntry> result = new ArrayList<>();
        if (!contentLoaded()){
            System.err.println("ERROR: No content loaded before parsing.");
            return result;
        }

        for (int entry = 1; entry < fileContent.size(); entry++) {
            String thisBook = fileContent.get(entry);
            String[] bookData = thisBook.split(DATA_VALUES_SEPARATOR);

            String title = bookData[TITLE_INDEX];
            String[] authors = bookData[AUTHORS_INDEX].split(AUTHOR_SEPARATOR);
            float rating = Float.parseFloat(bookData[RATING_INDEX]);
            String ISBN = bookData[ISBN_INDEX];
            int pages = Integer.parseInt(bookData[PAGES_INDEX]);
            BookEntry book = new BookEntry(title, authors, rating, ISBN, pages);

            result.add(book);
        }

        return result;
    }
}
