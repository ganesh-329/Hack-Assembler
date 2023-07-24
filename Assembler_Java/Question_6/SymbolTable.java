
import java.util.*;
public class SymbolTable {

    public static Map<String, Integer> createSymbolTable(String asmCode) {
        Map<String, Integer> symbolTable = new LinkedHashMap<>();

        int variableAddress = 16;

        // Predefined symbols
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        for (int i = 0; i < 16; i++) {
            symbolTable.put("R" + i, i);
        }

        // First pass
        List<String> lines = WhitespaceRemoval.removeWhitespace(asmCode);
        int currentAddress = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("(")) {
                String label = line.substring(1, line.length() - 1);
                symbolTable.put(label, currentAddress);
            } else {
                currentAddress++;
            }
        }

        // Second pass
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("@")) {
                String symbol = line.substring(1);
                if (!symbolTable.containsKey(symbol)) {
                    symbolTable.put(symbol, variableAddress);
                    variableAddress++;
                }
            }
        }

        return symbolTable;
    }
}