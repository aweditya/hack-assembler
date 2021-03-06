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
    public String commandType() {
        if (currentCommand.isEmpty()) {
            // After trimming, any blank line is reduced to an empty string
            return "COMMENT_WHITESPACE";
        } else {
            char firstChar = currentCommand.charAt(0);
            if (firstChar == '@') {
                return "A_COMMAND";
            } else if (firstChar == '(') {
                return "L_COMMAND";
            } else if (firstChar == '/') {
                return "COMMENT_WHITESPACE";
            } else {
                return "C_COMMAND";
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
        if (commandType().equals("A_COMMAND")) {
            // Handling inline comments
            if (currentCommand.indexOf('/') == -1) {
                return currentCommand.substring(1);
            } else {
                return currentCommand.substring(1, currentCommand.indexOf('/')).trim();
            }
        } else if (commandType().equals("L_COMMAND")) {
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
        int equalSignPosition = currentCommand.indexOf('=');
        if (equalSignPosition != -1) {
            destinationMnemonic = currentCommand.substring(0, equalSignPosition);
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
        String computationMnemonic;
        boolean hasEqualSign = true, hasSemiColon = true;

        // Check if the command has a destination or jump using '=' and ';' signs
        if (currentCommand.indexOf('=') == -1) {
            hasEqualSign = false;
        }
        if (currentCommand.indexOf(';') == -1) {
            hasSemiColon = false;
        }

        if (hasEqualSign && hasSemiColon) {
            computationMnemonic = currentCommand.substring(currentCommand.indexOf('=') + 1, currentCommand.indexOf(';'));
            computationMnemonic = computationMnemonic.trim();
        } else if (hasEqualSign) {
            // Handling inline comments
            if (currentCommand.indexOf('/') == -1) {
                computationMnemonic = currentCommand.substring(currentCommand.indexOf('=') + 1);
            } else {
                computationMnemonic = currentCommand.substring(currentCommand.indexOf('=') + 1, currentCommand.indexOf('/'));
                computationMnemonic = computationMnemonic.trim();
            }
        } else if (hasSemiColon) {
            computationMnemonic = currentCommand.substring(0, currentCommand.indexOf(';'));
            computationMnemonic = computationMnemonic.trim();
        } else {
            // Handling inline comments
            if (currentCommand.indexOf('/') == -1) {
                computationMnemonic = currentCommand;
            } else {
                computationMnemonic = currentCommand.substring(0, currentCommand.indexOf('/'));
                computationMnemonic = computationMnemonic.trim();
            }
        }
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
        String jumpMnemonic = "null";
        int semiColonPosition = currentCommand.indexOf(';');
        if (semiColonPosition != -1) {
            jumpMnemonic = currentCommand.substring(semiColonPosition + 1);
            jumpMnemonic = jumpMnemonic.trim();
            if (jumpMnemonic.indexOf('/') != -1) {
                jumpMnemonic = jumpMnemonic.substring(0, jumpMnemonic.indexOf('/')).trim();
            }
        }
        return jumpMnemonic;
    }
}

