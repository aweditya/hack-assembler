import java.io.File;

/**
 * Encapsulates access to the input code. Reads an assembly language command, parses it,
 * and provides convenient access to the command’s components (fields and symbols). In
 * addition, removes all white space and comments.
 */
public class Parser {
    public enum commandTypes {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    public Parser(File assemblyCode) {

    }

    public boolean hasMoreCommands() {
        return true;
    }

    public commandTypes commandType() {
        return commandTypes.A_COMMAND;
    }

    public String symbol() {
        return "Symbol";
    }

    public String dest() {
        return "Destination";
    }

    public String comp() {
        return "Computation";
    }

    public String jump() {
        return "Jump";
    }

}

