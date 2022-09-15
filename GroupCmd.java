import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

/**
 * Group command that allows the user to group books by introducing the desired parameter (title or author)
 * to group by.
 */
public class GroupCmd extends LibraryCommand {

    /** Helper string that is used to store the parameter of grouping, either {@value #TITLE_STRING} or {@value #AUTHOR_STRING}*/
    private String parsedArgument;
    /** String that represents the title parameter.*/
    private final static String TITLE_STRING = "TITLE";
    /** String that represents the author parameter.*/
    private final static String AUTHOR_STRING = "AUTHOR";
    /** String that represents the prefix that is printed before each group name.*/
    private final static String GROUP_PREFIX = "## ";
    /** String that represents the prefix for book titles that start with a single digit.*/
    private final static String SINGLE_DIGIT_GROUP = "[0-9]";
    /** String that represents the prefix that is printed before each book title.*/
    private final static String GROUP_ELEMENT_PREFIX = "\t";

    /** Constructor of the class GroupCmd. It is used to create a group command.
     * @param argumentInput a string that should be the parameter to group, either {@value #TITLE_STRING} or {@value #AUTHOR_STRING}.
     * @throws IllegalArgumentException if the given argument input does not satisfy the conditions in parseArguments.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public GroupCmd(String argumentInput){
        super(CommandType.GROUP, argumentInput);
    }

    /** Method that parses the arguments in order to store the desired parameter of grouping.
     * @param argumentInput argument input following the GROUP command.
     * @return true if the given argument input is either {@value #TITLE_STRING} or {@value #AUTHOR_STRING}.
     * @throws NullPointerException if the given argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, Utils.ARGUMENT_INPUT_NULL_MESSAGE);

        argumentInput = argumentInput.strip();
        switch (argumentInput){
            case TITLE_STRING:
                parsedArgument = TITLE_STRING;
                return true;
            case AUTHOR_STRING:
                parsedArgument = AUTHOR_STRING;
                return true;
        }
        return false;
    }

    /** Method that is responsible for the execution of the group command. It uses the stored parameter from the parsed
     *  argument to group the books by {@value #TITLE_STRING} or {@value #AUTHOR_STRING}.
     * @param data {@link LibraryData} which contains the list of the books.
     * @throws NullPointerException if the {@link LibraryData} data is null.
     * @throws NullPointerException if the {@link #parsedArgument} is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, Utils.DATA_NULL_MESSAGE);
        Objects.requireNonNull(parsedArgument, Utils.PARSED_ARGUMENT_NULL_MESSAGE);

        if (data.getBookData().isEmpty()){
            System.out.println(Utils.EMPTY_LIBRARY_MESSAGE);
        }
        else {
            System.out.println("Grouped data by " + parsedArgument);
            if (parsedArgument.equals(TITLE_STRING)){
                groupByTitle(data);
            }
            else if (parsedArgument.equals(AUTHOR_STRING)){
                groupByAuthor(data);
            }
        }
    }

    /** Helper function of the execute method that prints the titles alphabetically grouped by their initial letter or by
     * {@value #SINGLE_DIGIT_GROUP} if the title starts with a number.
     * @param data {@link LibraryData} which contains the list of the books
     */
    private void groupByTitle(LibraryData data){
        List<BookEntry> books = data.getBookData();
        HashMap<String , ArrayList<String>> index = new HashMap<>();

        for (BookEntry book : books){
            String initial = setInitial(book);
            addToHashMap(index, book, initial);
        }
        printAlphabetically(index);
    }

    /** Helper function of the groupByTitle method that sets the group name initial in its correct form: if the title begins
     *  with a letter, then it sets the initial to that letter uppercase, or if it starts with a number, it sets the
     *  group name initial to {@value #SINGLE_DIGIT_GROUP}.
     * @param book BookEntry whose title we want to set the initial in its correct form.
     * @return the group name initial in its correct form.
     */
    private String setInitial(BookEntry book) {
        String initial = book.getTitle().charAt(0) + "";
        if (isInteger(initial)){
            initial = SINGLE_DIGIT_GROUP;
        }
        else{
            initial = initial.toUpperCase();
        }
        return initial;
    }

    /** Helper function that determines whether a string is an integer or not.
     * @param possibleNumber the string we want to analyze.
     * @return true if the string is an integer, false otherwise.
     */
    private boolean isInteger(String possibleNumber){
        try {
            Integer.parseInt(possibleNumber);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }

    /** Helper function that adds the title of the book that is being considered to a HashMap, according of whether it
     * is being classified by initial letters or by authors.
     * @param index HashMap whose keys are either the initial of the title or the author, and the values are
     *              the titles that correspond to that key.
     * @param book The book that is going to be added to the HashMap.
     * @param header The string that consists of a key of the HashMap. If the books are being grouped by initials,
     *               the header will be the corresponding initial, and if it is being grouped by authors, the header
     *               will be the corresponding author.
     */
    private void addToHashMap(HashMap<String, ArrayList<String>> index, BookEntry book, String header) {
        if (index.get(header) == null) {
            ArrayList<String> list = new ArrayList<>();
            list.add(book.getTitle());
            index.put(header, list);
        } else {
            ArrayList<String> list = index.get(header);
            list.add(book.getTitle());
        }
    }

    /** Helper method that, given a HashMap<String , ArrayList<String>>, it prints the grouped books ordered
     * alphabetically (it orders the strings alphabetically and for each string it prints the arrayList<String>.
     * @param index the HashMap we want to print ordered alphabetically.
     */
    private void printAlphabetically (HashMap<String , ArrayList<String>> index){
        ArrayList<String> orderedArray = new ArrayList<>(index.keySet());

        Collections.sort(orderedArray);

        for (String entry : orderedArray) {
            System.out.println(GROUP_PREFIX + entry);
            ArrayList<String> titles = index.get(entry);
            for (String title : titles){
                System.out.println(GROUP_ELEMENT_PREFIX + title);
            }
        }
    }

    /** Helper function of the execute method that prints the titles grouped by their author.
     * @param data {@link LibraryData} which contains the list of the books
     */
    private void groupByAuthor(LibraryData data){
        List<BookEntry> books = data.getBookData();
        HashMap<String , ArrayList<String>> index = new HashMap<>();
        for (BookEntry book : books){
            for (String author : book.getAuthors()) {
                addToHashMap(index, book, author);
            }
        }
        printAlphabetically(index);
    }
}
