import java.io.*;
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
        String[] lines = asmCode.split("\\n");
        int currentAddress = 0;
        for (String line : lines) {
            line = removeComments(line).trim();
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
            line = removeComments(line).trim();
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

    private static String removeComments(String line) {
        int index = line.indexOf("//");
        if (index != -1) {
            line = line.substring(0, index);
        }
        return line;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the asm file name including the extension: ");
        String asmCodeFileName = scanner.nextLine();
        scanner.close();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(asmCodeFileName))) {
            StringBuilder asmCodeBuilder = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                asmCodeBuilder.append(line).append(System.lineSeparator());
            }
            String asmCode = asmCodeBuilder.toString();
            Map<String, Integer> symbolTable = createSymbolTable(asmCode);
            String outputFileName = "symboltable.asm";

            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFileName))) {
                for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
                    fileWriter.write(entry.getKey() + "\t" + entry.getValue() + "\t\n");
                }
            } catch (IOException e) {
                System.err.println("Error writing to the output file: " + e.getMessage());
            }

            System.out.println("Symbol table has been created and saved to symboltable.asm.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
