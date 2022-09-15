import java.util.List;
import java.util.Objects;

/**
 * Search command that allows the user to print book titles that contain the search term.
 */
public class SearchCmd extends LibraryCommand {

    /** Helper string generated to store the parsed argument.*/
    private String parsedArgument;
    /** String that represents the character that separates different words.*/
    private static final String CHARACTER_BETWEEN_WORDS = " ";


    /** Constructor of the class SearchCmd. It is used to create a search command.
     * @param argumentInput a string that should be a non-empty single word.
     * @throws IllegalArgumentException if the given argument input does not satisfy the conditions in parseArguments.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public SearchCmd(String argumentInput){
        super(CommandType.SEARCH, argumentInput);
    }

    /** Method that parses the arguments in order to store the search term if it is a non-empty single word.
     * @param argumentInput argument input following the SEARCH command.
     * @return true if the argument input is a valid non-empty word. False otherwise.
     * @throws NullPointerException if the given argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, Utils.ARGUMENT_INPUT_NULL_MESSAGE);
        argumentInput = argumentInput.strip();

        if(!argumentInput.contains(CHARACTER_BETWEEN_WORDS) && !argumentInput.isEmpty()){
            parsedArgument = argumentInput;
            return true;
        }
        return false;
    }

    /** Method that is responsible for the execution of the search command. It uses the stored search term from the
     *  parsed Argument and prints the list of titles that contain the search term. If no titles are found, it prints
     *  a message indicating no books have been found matching the search term.
     * @param data {@link LibraryData} which contains the list of the books.
     * @throws NullPointerException if the {@link LibraryData} data is null.
     * @throws NullPointerException if {@link #parsedArgument} is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.DATA_NULL_MESSAGE);
        Objects.requireNonNull(parsedArgument, Utils.PARSED_ARGUMENT_NULL_MESSAGE);

        List<BookEntry> books = data.getBookData();
        int numberOfPrintedBooks = 0;
        for (BookEntry book : books){
            if (book.getTitle().toLowerCase().contains(parsedArgument.toLowerCase())){
                System.out.println(book.getTitle());
                numberOfPrintedBooks++;
            }
        }
        if (numberOfPrintedBooks == 0){
            System.out.println("No hits found for search term: " + parsedArgument);
        }
    }
}
