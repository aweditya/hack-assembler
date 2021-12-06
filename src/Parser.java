import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Encapsulates access to the input code. Reads an assembly language command, parses it,
 * and provides convenient access to the command’s components (fields and symbols). In
 * addition, removes all white space and comments.
 */
public class Parser {
    private final Scanner scanner;
    private String currentCommand;

    /**
     * A_COMMAND for @XXX where XXX is either a symbol or a decimal number
     * C_COMMAND for dest=comp;jump
     * L_COMMAND (actually, pseudo-command) for (XXX) where XXX is a symbol.
     */
    public enum commandTypes {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    /**
     * Opens the input file/stream and gets ready to parse it.
     *
     * @param assemblyCode
     * @throws FileNotFoundException
     */
    public Parser(File assemblyCode) throws FileNotFoundException {
        scanner = new Scanner(assemblyCode);
        currentCommand = "";
    }

    /**
     * Are there more commands in the input?
     *
     * @return
     */
    public boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    /**
     * Reads the next command from the input and makes it the current command.
     * Should be called only if hasMoreCommands() is true. Initially there is
     * no current command.
     */
    public void advance() {
        currentCommand = scanner.nextLine().trim();
        System.out.println(currentCommand);
    }

    /**
     * Returns the type of the current command
     *
     * @return
     */
    public commandTypes commandType() {
        return commandTypes.A_COMMAND;
    }

    /**
     * Returns the symbol or decimal XXX of the current command @XXX or (XXX).
     * Should be called only when commandType() is A_COMMAND or L_COMMAND.
     *
     * @return
     */
    public String symbol() {
        return "Symbol";
    }

    /**
     * Returns the dest mnemonic in the current C-command (8 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String dest() {
        return "Destination";
    }

    /**
     * Returns the comp mnemonic in the current C-command (28 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String comp() {
        return "Computation";
    }

    /**
     * Returns the jump mnemonic in the current C-command (8 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String jump() {
        return "Jump";
    }

    public void printFile() {
        while (hasMoreCommands()) {
            advance();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File assemblyCode = new File("../add/Add1.asm");
        Parser parser = new Parser(assemblyCode);
        parser.printFile();
    }
}

