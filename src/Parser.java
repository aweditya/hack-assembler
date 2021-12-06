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

    public enum commandTypes {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    public Parser(File assemblyCode) throws FileNotFoundException {
        scanner = new Scanner(assemblyCode);
    }

    public boolean hasMoreCommands() {
        return scanner.hasNextLine();
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

    public void printFile() {
        while (hasMoreCommands()) {
            System.out.println(scanner.nextLine());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File assemblyCode = new File("../add/Add.asm");
        Parser parser = new Parser(assemblyCode);
        parser.printFile();
    }
}

