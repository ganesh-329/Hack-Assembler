
import java.io.*;
import java.util.*;
public class aInstruction
 {
    public static Map<String, Integer> readSymbolTable(String symbolTableFileName) {
        Map<String, Integer> symbolTable = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(symbolTableFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String symbol = parts[0];
                int address = Integer.parseInt(parts[1]);
                symbolTable.put(symbol, address);
            }
        } catch (IOException e) {
            System.err.println("Error reading the symbol table file: " + e.getMessage());
        }
        return symbolTable;
    }
    
    public static String aInstructionToBinary(String instruction, Map<String, Integer> symbolTable) {
        String symbol = instruction.substring(1);
        int address;
        if (symbol.matches("\\d+")) {
            address = Integer.parseInt(symbol);
        } else if (symbolTable.containsKey(symbol)) {
            address = symbolTable.get(symbol);
        } else {
            address = symbolTable.size() + 16;
            symbolTable.put(symbol, address);
        }
        String binary = "0" + String.format("%015d", Integer.parseInt(Integer.toBinaryString(address)));
        return binary;
    }
    
}

