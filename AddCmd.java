import java.nio.file.Path;
import java.util.Objects;

/**
 * Add command that allows the user to add additional books to the library from a {@value #FILE_NAME_EXTENSION} file .
 */
public class AddCmd extends LibraryCommand {

    /** Private instance field that is used to store the path of the file*/
    private Path filePath;
    /** Private instance field that indicates the extension of the file from which to add the books*/
    private static final String FILE_NAME_EXTENSION = ".csv";

    /** Constructor of the class AddCmd. It is used to create an Add command.
     * @param argumentInput string that should represent a path ending with the valid file name extension ({@value #FILE_NAME_EXTENSION}).
     * @throws IllegalArgumentException if the given argument input does not satisfy the conditions in parseArguments.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public AddCmd(String argumentInput){
        super(CommandType.ADD, argumentInput);
    }

    /** Method that parses the arguments in order to store the path if it ends in a valid {@value #FILE_NAME_EXTENSION} extension.
     * @param argumentInput argument input following the ADD command.
     * @return true if it is a path ending with the valid extension ({@value #FILE_NAME_EXTENSION}). False otherwise.
     * @throws NullPointerException if the given argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, Utils.ARGUMENT_INPUT_NULL_MESSAGE);

        argumentInput = argumentInput.strip();
        if (argumentInput.endsWith(FILE_NAME_EXTENSION)){
            filePath = Path.of(argumentInput);
            return true;
        }
        return false;
    }

    /** Method that is responsible for the execution of the Add command. It uses the stored Path from the parseArguments method
     * and uses the method loadData to add the list of books.
     * @param data {@link LibraryData} which contains the list of  the books.
     * @throws NullPointerException if the {@link LibraryData} data is null.
     * @throws NullPointerException if {@link #filePath} is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.DATA_NULL_MESSAGE);
        Objects.requireNonNull(filePath, "Given file path must not be null");

        data.loadData(filePath);
    }
}
