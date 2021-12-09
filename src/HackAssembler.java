import java.io.*;

public class HackAssembler {
    private final Parser firstParser, parser;
    private final Code encoder;
    private final SymbolTable symbolTable;
    private final String path;

    private String convertToBinary(int address) {
        StringBuilder fixedWidthBinaryRepresentation = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (address != 0) {
                int remainder = address % 2;
                if (remainder == 1) {
                    fixedWidthBinaryRepresentation.insert(0, "1");
                } else {
                    fixedWidthBinaryRepresentation.insert(0, "0");
                }
                address /= 2;
            } else {
                fixedWidthBinaryRepresentation.insert(0, "0");
            }
        }
        return fixedWidthBinaryRepresentation.toString();
    }

    private boolean checkSymbolIsNumber(String symbol) {
        // A user-defined symbol cannot begin with a number
        return Character.isDigit(symbol.charAt(0));
    }

    public HackAssembler(String path) throws FileNotFoundException {
        this.path = path.substring(0, path.lastIndexOf('.')) + ".hack";
        File assemblyCode = new File(path);
        firstParser = new Parser(assemblyCode); // Parser object to build the symbol table
        parser = new Parser(assemblyCode); // Parser object to assemble
        encoder = new Code(); // Create a Code object
        symbolTable = new SymbolTable(); // Create a Symbol Table object
    }

    public String translateAInstruction(int address) {
        return convertToBinary(address);
    }

    public String translateCInstruction() {
        String c = parser.computation();
        String d = parser.destination();
        String j = parser.jump();

        String cc = encoder.comp(c);
        String dd = encoder.dest(d);
        String jj = encoder.jump(j);

        return "111" + cc + dd + jj;
    }

    public void buildSymbolTable() {
        int ROMAddress = 0;
        while (firstParser.hasMoreCommands()) {
            firstParser.advance();
            switch (firstParser.commandType()) {
                case "A_COMMAND":
                case "C_COMMAND":
                    ROMAddress++;
                    break;
                case "L_COMMAND":
                    symbolTable.addEntry(firstParser.symbol(), ROMAddress);
                    break;
                default:
                    break;
            }
        }
    }

    public void assemble() throws IOException {
        buildSymbolTable();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        int userRegister = 16;
        while (parser.hasMoreCommands()) {
            parser.advance();
            switch (parser.commandType()) {
                case "A_COMMAND":
                    // System.out.println(translateAInstruction());
                    int address;
                    String symbol = parser.symbol();
                    if (!checkSymbolIsNumber(symbol)) {
                        boolean symbolTableContainsSymbol = symbolTable.contains(symbol);
                        if (symbolTableContainsSymbol) {
                            address = symbolTable.GetAddress(parser.symbol());
                        } else {
                            symbolTable.addEntry(parser.symbol(), userRegister);
                            address = userRegister;
                            userRegister++;
                        }
                    } else {
                        address = Integer.parseInt(symbol);
                    }
                    writer.write(translateAInstruction(address) + "\n");
                    break;
                case "C_COMMAND":
                    // System.out.println(translateCInstruction());
                    writer.write(translateCInstruction() + "\n");
                    break;
                default:
                    break;
            }
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        HackAssembler assembler = new HackAssembler("../pong/Pong.asm");
        assembler.assemble();
    }
}
