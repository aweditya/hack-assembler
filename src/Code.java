/**
 * Translates Hack assembly language mnemonics into binary codes.
 */
public class Code {
    /**
     * Returns the binary code of the dest mnemonic.
     *
     * @param mnemonic
     * @return
     */
    public String dest(String mnemonic) {
        return switch (mnemonic) {
            case "null" -> "000";
            case "M" -> "001";
            case "D" -> "010";
            case "MD" -> "011";
            case "A" -> "100";
            case "AM" -> "101";
            case "AD" -> "110";
            case "AMD" -> "111";
            default -> "222";
        };
    }

    /**
     * Returns the binary code of the comp mnemonic.
     *
     * @param mnemonic
     * @return
     */
    public String comp(String mnemonic) {
        return "comp";
    }

    /**
     * Returns the binary code of the jump mnemonic.
     *
     * @param mnemonic
     * @return
     */
    public String jump(String mnemonic) {
        return switch (mnemonic) {
            case "null" -> "000";
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> "222";
        };
    }
}
