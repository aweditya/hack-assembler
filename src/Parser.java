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
        L_COMMAND,
        COMMENT_WHITESPACE
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
        // Trimming the string to remove spaces in the start and end
        currentCommand = scanner.nextLine().trim();
    }

    /**
     * Returns the type of the current command
     *
     * @return
     */
    public commandTypes commandType() {
        if (currentCommand.isEmpty()) {
            // After trimming, any blank line is reduced to an empty string
            return commandTypes.COMMENT_WHITESPACE;
        } else {
            char firstChar = currentCommand.charAt(0);
            if (firstChar == '@') {
                return commandTypes.A_COMMAND;
            } else if (firstChar == '(') {
                return commandTypes.L_COMMAND;
            } else if (firstChar == '/') {
                return commandTypes.COMMENT_WHITESPACE;
            } else {
                return commandTypes.C_COMMAND;
            }
        }
    }

    /**
     * Returns the symbol or decimal XXX of the current command @XXX or (XXX).
     * Should be called only when commandType() is A_COMMAND or L_COMMAND.
     *
     * @return
     */
    public String symbol() {
        if (commandType() == commandTypes.A_COMMAND) {
            return currentCommand.substring(1);
        } else if (commandType() == commandTypes.L_COMMAND) {
            return currentCommand.substring(currentCommand.indexOf('(') + 1, currentCommand.indexOf(')'));
        } else {
            return "Wrong Command Type";
        }
    }

    /**
     * Returns the dest mnemonic in the current C-command (8 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String destination() {
        /*
        dest is the first part of a C-instruction till the = sign
        The extracted string is trimmed to remove spaces
         */
        String destinationMnemonic = "null";
        int equalsPosition = currentCommand.indexOf('=');
        if (equalsPosition != -1) {
            destinationMnemonic = currentCommand.substring(0, currentCommand.indexOf('='));
            destinationMnemonic = destinationMnemonic.trim();
        }
        return destinationMnemonic;
    }

    /**
     * Returns the comp mnemonic in the current C-command (28 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String computation() {
        /*
        comp is the second part of a C-instruction from the = sign to
        the ; sign. All whitespaces are removed
         */
        String computationMnemonic = currentCommand.substring(currentCommand.indexOf('='), currentCommand.indexOf(';'));
        computationMnemonic = computationMnemonic.replaceAll(" ", "");
        return computationMnemonic;
    }

    /**
     * Returns the jump mnemonic in the current C-command (8 possibilities).
     * Should be called only when commandType() is C_COMMAND.
     *
     * @return
     */
    public String jump() {
        /*
        jump is the last part of a C-instruction from the ; sign to
        the end of the string
         */
        String jumpMnemonic = currentCommand.substring(currentCommand.indexOf(';'));
        jumpMnemonic = jumpMnemonic.trim();
        return jumpMnemonic;
    }

    public void printFile() {
        while (hasMoreCommands()) {
            advance();
            System.out.println(currentCommand);
        }
    }

    public void printCommandType() {
        while (hasMoreCommands()) {
            advance();
            if (commandType() == commandTypes.A_COMMAND) {
                String symbol = symbol();
                System.out.println("A_COMMAND: " + symbol);
            } else if (commandType() == commandTypes.C_COMMAND) {
                String dest = destination();
                String comp = "computation()";
                String jump = "jump()";
                System.out.println("C_COMMAND: " + dest + "\t" + comp + "\t" + jump);
            } else if (commandType() == commandTypes.L_COMMAND) {
                String symbol = symbol();
                System.out.println("L_COMMAND: " + symbol);
            } else {
                System.out.println("COMMENT_WHITESPACE");
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File assemblyCode = new File("../rect/Rect.asm");
        Parser parser = new Parser(assemblyCode);
        // parser.printFile();
        parser.printCommandType();
    }
}

