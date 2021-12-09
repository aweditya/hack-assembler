import java.util.Hashtable;

/**
 * Keeps a correspondence between symbolic labels and numeric addresses
 */
public class SymbolTable {
    Hashtable<String, Integer> symbolTable;

    /**
     * Creates a new empty symbol table.
     */
    public SymbolTable() {
        symbolTable = new Hashtable<>();

        /**
         * Add predefined symbols
         * SP: 0
         * LCL: 1
         * ARG: 2
         * THIS: 3
         * THAT: 4
         * R0-R15: 0-15
         * SCREEN: 16384
         * KBD: 24576
         */
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }

    /**
     * Adds the pair (symbol, address) to the table.
     *
     * @param symbol
     * @param address
     */
    public void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
    }

    /**
     * Does the symbol table contain the given symbol?
     *
     * @param symbol
     * @return
     */
    public boolean contains(String symbol) {
        return symbolTable.contains(symbol);
    }

    /**
     * Returns the address associated with the symbol.
     *
     * @param symbol
     * @return
     */
    public int GetAddress(String symbol) {
        return symbolTable.get(symbol);
    }
}
