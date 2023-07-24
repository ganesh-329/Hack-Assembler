
import java.util.*;
import java.io.*;
public class assembler {
    public static void assembleFile(List<String> inputLines, Map<String, Integer> symbolTable){
            
        StringBuilder processedLines = new StringBuilder();

        for(String line: inputLines){
            line = line.trim();
            if(!line.isEmpty() && !line.startsWith("(")){
                if(line.startsWith("@")){
                    String Binary = aInstruction.aInstructionToBinary(line, symbolTable);
                    processedLines.append(Binary).append("\n");
                }
                else{
                    String Binary = cInstruction.cInstructionToBinary(line);
                    processedLines.append(Binary).append("\n");
                }

            }
        }
        String outputFileName = "output.hack";
        try(FileWriter outputFile = new FileWriter(outputFileName)){
            outputFile.write(processedLines.toString());
        } catch(IOException e){
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the asm file name with extension: ");
            String fileName = reader.readLine();
            StringBuilder asmCodeBuilder = new StringBuilder();
            try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    asmCodeBuilder.append(line).append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.println("Error reading input file: " + e.getMessage());
                return; 
            }
            String asmCode = asmCodeBuilder.toString();
            Map<String, Integer> symbolTable = SymbolTable.createSymbolTable(asmCode);
            
            List<String> processedLines = WhitespaceRemoval.removeWhitespace(fileName);
            assembleFile(processedLines, symbolTable);
            System.out.println("output.hack file created successfully.");
        } catch(IOException e){
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}