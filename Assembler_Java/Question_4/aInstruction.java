kfkfpackage AInstruction;

import java.io.*;
import java.util.*;

public class aInstruction {
    
    public static String aInstructionToBinary(String instruction) {
        int address = Integer.parseInt(instruction.substring(1));
        String binary = "0" + String.format("%015d", Integer.parseInt(Integer.toBinaryString(address)));
        return binary;
    }
    
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
    
    public static void assembleFile(String inputFileName, Map<String, Integer> symbolTable) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter("a_instruction.hack"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.startsWith("@")) {
                    String symbol = line.substring(1);
                    int address;
                    if (symbol.matches("\\d+")) {
                        address = Integer.parseInt(symbol);
                    } else if (symbolTable.containsKey(symbol)) {
                        address = symbolTable.get(symbol);
                    } else {
                        address = symbolTable.size() + 16;
                        symbolTable.put(symbol, address);
                    }
                    String binary = aInstructionToBinary("@" + address);
                    writer.write(binary);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error assembling the file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter the file name with extension: ");
            String fileName = reader.readLine();
            System.out.print("Enter the symbol table file name with extension: ");
            String symbolTableFileName = reader.readLine();
            
            Map<String, Integer> symbolTable = readSymbolTable(symbolTableFileName);
            assembleFile(fileName, symbolTable);
            
            System.out.println("Assembly complete. Output written to a_instruction.hack");
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}
