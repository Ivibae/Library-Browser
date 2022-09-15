import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Remove command that allows the user to remove matching books by introducing the desired parameter (title or author)
 * of removal and a non-empty removal term.
 */
public class RemoveCmd extends LibraryCommand {

    /** Helper string that is used to store the parameter of removal, either {@value #TITLE_STRING} or {@value #AUTHOR_STRING}*/
    private String parameterOfRemoval;
    /** Helper string used to store the name of the title or the author that is going to be removed.*/
    private String nameOfRemoval;

    /** String that represents the title parameter.*/
    private final static String TITLE_STRING = "TITLE";
    /** String that represents the author parameter.*/
    private final static String AUTHOR_STRING = "AUTHOR";
    /**  String that represents an entirely blank string.*/
    private static final String EMPTY_STRING = "";
    /** Regex that represents one or more blank spaces.*/
    private static final String REGEX_BLANK = "\\s+";
    /** String that represents the character that separates different words.*/
    private static final String CHARACTER_BETWEEN_ARGUMENTS = " ";
    /** String that represents the minimum number of words accepted, the parameter and the removal value*/
    private static final int MIN_WORDS = 2;


    /** Constructor of the class RemoveCmd. It is used to create a remove command.
     * @param argumentInput a string that should be the parameter of removal followed by the desired term to be removed.
     * @throws IllegalArgumentException if the given argument input does not satisfy the conditions in parseArguments.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public RemoveCmd(String argumentInput){
        super(CommandType.REMOVE, argumentInput);
    }


    /** Method that parses the arguments in order to store the desired parameter of removal and the term to be removed.
     * @param argumentInput argument input following the REMOVE command.
     * @return true if the first word is a correct parameter and it is followed by a non-empty value.
     * @throws NullPointerException if the given argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, Utils.ARGUMENT_INPUT_NULL_MESSAGE);

        argumentInput = argumentInput.strip();

        if (argumentInput.isEmpty()){
            return false;
        }
        if (isCorrectBeginning(argumentInput)){
            return isCorrectNameOfRemoval(argumentInput);
        }
        return false;
    }


    /** Helper method of parseArguments, which checks if the first word is a valid parameter , either
     * {@value #TITLE_STRING} or {@value #AUTHOR_STRING}, and in that case it stores that parameter in
     * {@link #parameterOfRemoval}. It also checks if after the parameter there is no name of removal.
     * @param argumentInput argument input for this command.
     * @return true if the first word is a valid parameter and it is followed by a non-empty string, returns false otherwise.
     */
    private boolean isCorrectBeginning(String argumentInput){
        String[] words = argumentInput.split(REGEX_BLANK);
        if (words.length < MIN_WORDS){
            return false;
        }
        if (words[0].equals(TITLE_STRING)){
            parameterOfRemoval = TITLE_STRING;
            return true;
        }
        else if (words[0].equals(AUTHOR_STRING)){
            parameterOfRemoval = AUTHOR_STRING;
            return true;
        }
        return false;
    }

    /** Helper method of parseArguments. It checks that the text after the parameter of removal is not empty. If it is,
     * it returns false and it does not store the name of the removal. If it is not empty, it stores the name of the
     * removal and returns true.
     * @param argumentInput String that has the parameter of the removal followed by the name of the removal.
     * @return true if the name of the removal is not empty. False otherwise.
     */
    private boolean isCorrectNameOfRemoval(String argumentInput) {
        String nameOfRemoval = argumentInput.replaceFirst(parameterOfRemoval + CHARACTER_BETWEEN_ARGUMENTS, EMPTY_STRING);
        nameOfRemoval = nameOfRemoval.strip();
        if (!nameOfRemoval.isEmpty()){
            this.nameOfRemoval = nameOfRemoval;
            return true;
        }
        return false;
    }


    /** Method that is responsible for the execution of the remove command. It uses the stored parameter {@link #parameterOfRemoval}
     *  and the stored term of the book to remove {@link #nameOfRemoval}in order to actually remove the book.
     *  It prints afterwards an informative message about the books removed.
     * @param data {@link LibraryData} which contains the list of the books.
     * @throws NullPointerException if the {@link LibraryData} data is null.
     * @throws NullPointerException if {@link #parameterOfRemoval} is null.
     * @throws NullPointerException if {@link #nameOfRemoval} is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.DATA_NULL_MESSAGE);
        Objects.requireNonNull(parameterOfRemoval, "Given parameter of removal must not be null.");
        Objects.requireNonNull(nameOfRemoval, "Given name of removal must not be null.");

        switch (parameterOfRemoval){
            case TITLE_STRING:
                removeTitles(data);
                break;
            case AUTHOR_STRING:
                removeAuthors(data);
                break;
        }
    }

    /** Helper method of the execute method that removes all books whose title coincides with {@link #nameOfRemoval},
     * and is responsible for printing the necessary message afterwards.
     * @param data {@link LibraryData} which contains the list of the books.
     */
    private void removeTitles(LibraryData data){
        int numberOfBooksRemoved = 0;
        List<BookEntry> books = data.getBookData();
        for (BookEntry book : books){
            if ((book.getTitle()).equals(nameOfRemoval)){
                books.remove(book);
                numberOfBooksRemoved ++;
                break;
            }
        }
        if (numberOfBooksRemoved != 0){
            System.out.println(nameOfRemoval + ": removed successfully.");
        }
        else{
            System.out.println(nameOfRemoval + ": not found.");
        }
    }

    /** Helper method of the execute method that removes all books whose author coincides with {@link #nameOfRemoval},
     * and is responsible for printing the necessary message afterwards.
     * @param data {@link LibraryData} which contains the list of the books.
     */
    private void removeAuthors(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        ArrayList<BookEntry> booksToRemove = new ArrayList<>();

        for (BookEntry book : books) {
            for (String author : book.getAuthors()) {
                if (author.equals(nameOfRemoval)) {
                    booksToRemove.add(book);
                }
            }
        }
        for (BookEntry bookToRemove : booksToRemove) {
            books.remove(bookToRemove);
        }

        System.out.println(booksToRemove.size() + " books removed for author: " + nameOfRemoval);

    }

}
