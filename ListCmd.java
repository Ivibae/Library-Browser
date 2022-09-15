import java.util.List;
import java.util.Objects;

/**
 * List command used to print the list of current loaded books in different formats.
 */
public class ListCmd extends LibraryCommand {

    /** String that stores the parsed argument that follows the LIST command.*/
    private String parsedArgument;
    /** String that is used to check if the parsed argument is short.*/
    private final static String STRING_SHORT = "short";
    /** String that is used to check if the parsed argument is long.*/
    private final static String STRING_LONG = "long";
    /** String that represents the default parsed argument, that is, if no arguments are given.*/
    private final static String STRING_DEFAULT = "";

    /** Constructor of the class ListCmd. It is used to create a list command.
     * @param argumentInput argument input that should be either {@value #STRING_LONG}, {@value #STRING_SHORT}
     * or {@value #STRING_DEFAULT};
     * @throws IllegalArgumentException if the given argument input does not satisfy the conditions in parseArguments.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public ListCmd(String argumentInput){
        super(CommandType.LIST, argumentInput);
    }

    /** Method that parses the argument in order for its later use in the execute method.
     * @param argumentInput argument input following the LIST command.
     * @return true if the argument input is either {@value #STRING_LONG}, {@value #STRING_SHORT} or {@value #STRING_DEFAULT};
     * @throws NullPointerException if argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, Utils.ARGUMENT_INPUT_NULL_MESSAGE);

        argumentInput = argumentInput.strip();
        switch (argumentInput) {
            case STRING_SHORT:
                parsedArgument = STRING_SHORT;
                return true;
            case STRING_LONG:
                parsedArgument = STRING_LONG;
                return true;
            case STRING_DEFAULT:
                parsedArgument = STRING_DEFAULT;
                return true;
        }

        return false;
    }

    /** Method that executes the list command. It prints the current number of loaded books and either prints the list of
     *  titles if the argument is {@value #STRING_SHORT} or {@value #STRING_DEFAULT}, or prints all the information of
     *  each book if the argument is {@value #STRING_LONG}.
     * @param data {@link LibraryData} to be considered for command execution.
     * @throws NullPointerException if the given {@link LibraryData} data is null.
     * @throws NullPointerException if the given {@link #parsedArgument} is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.DATA_NULL_MESSAGE);
        Objects.requireNonNull(parsedArgument, Utils.PARSED_ARGUMENT_NULL_MESSAGE);

        printHeaderMessage(data);

        printBooks(data);
    }

    /** Helper method print the header of the execute method, indicating how many books are in the library.
     * @param data {@link LibraryData} which contains the list of the books.
     */
    public void printHeaderMessage(LibraryData data){
        List<BookEntry> books = data.getBookData();
        if (books.isEmpty()){
            System.out.println(Utils.EMPTY_LIBRARY_MESSAGE);
        }
        else{
            System.out.println(books.size() + " books in library:");
        }
    }

    /** Helper method that prints the list of titles if the argument is {@value #STRING_SHORT} or {@value #STRING_DEFAULT},
     *  or prints all the information of each book if the argument is {@value #STRING_LONG}.
     * @param data {@link LibraryData} which contains the list of the books.
     */
    public void printBooks (LibraryData data){
        List<BookEntry> books = data.getBookData();
        if (parsedArgument.equals(STRING_DEFAULT) || parsedArgument.equals(STRING_SHORT)){
            for (BookEntry book : books){
                System.out.println(book.getTitle());
            }
        }
        else{
            for (BookEntry book : books){
                System.out.println(book);
            }
        }
    }
}
