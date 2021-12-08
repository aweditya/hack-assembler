import java.io.*;

public class HackAssembler {
    private final Parser parser;
    private final Code encoder;
    private final String path;

    private String convertToBinary(String symbol) {
        int address = Integer.parseInt(symbol);
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

    public HackAssembler(String path) throws FileNotFoundException {
        this.path = path.substring(0, path.lastIndexOf('/') + 1) + ".hack";
        File assemblyCode = new File(path);
        parser = new Parser(assemblyCode); // Create a Parser object
        encoder = new Code(); // Create a Code object
    }

    public String translateAInstruction() {
        // Assuming no symbols in program (only numbers)
        String symbol = parser.symbol();
        return convertToBinary(symbol);
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

    public void assemble() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        while (parser.hasMoreCommands()) {
            parser.advance();
            switch (parser.commandType()) {
                case "A_COMMAND":
                    // System.out.println(translateAInstruction());
                    writer.write(translateAInstruction() + "\n");
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
        HackAssembler assembler = new HackAssembler("../pong/PongL.asm");
        assembler.assemble();
    }
}
