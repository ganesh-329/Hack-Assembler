import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
public class IdentifySymbols {
    public static void main(String[] args) {
        // Get the input file name from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the asm file name with extension: ");
        String fileName = scanner.nextLine();
        scanner.close();

        // Identify symbols in the file
        identifySymbols(fileName);
        System.out.println("Symbols generated in symbolsandvariables.asm file");  
    }

    public static void identifySymbols(String fileName) {
        List<String> symbolList = new ArrayList<>();
        Set<String> predefinedSymbols = new HashSet<>();
        predefinedSymbols.add("SP");
        predefinedSymbols.add("LCL");
        predefinedSymbols.add("ARG");
        predefinedSymbols.add("THIS");
        predefinedSymbols.add("THAT");
        predefinedSymbols.add("SCREEN");
        predefinedSymbols.add("KBD");
        for (int i = 0; i < 16; i++) {
            predefinedSymbols.add("R" + i);
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null){
                Pattern labelPattern = Pattern.compile("\\(([^)]+)\\)");
                Matcher labelMatcher = labelPattern.matcher(line);
                while(labelMatcher.find()){
                    String symbol = labelMatcher.group(1);
                    if(!symbolList.contains(symbol)){
                        symbolList.add(symbol);
                    }
                }
                Pattern variablPattern = Pattern.compile("@([a-zA-Z0-9_.$:]+)");
                Matcher variablMatcher = variablPattern.matcher(line);
                while(variablMatcher.find()){
                    String symbol = variablMatcher.group(1);
                    if(!predefinedSymbols.contains(symbol) && !symbolList.contains(symbol)){
                        symbolList.add(symbol);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
        String outputFileName = "symbolsandvariables.asm";
        writeSymbolsToFile(outputFileName, symbolList);
        }

        public static void writeSymbolsToFile(String fileName, List<String> symbols){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
                for (String symbol : symbols) {
                    writer.write(symbol);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to the output file: " + e.getMessage());
            }
        }
}
