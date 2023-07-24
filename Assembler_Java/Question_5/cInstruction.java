import java.io.*;
import java.util.*;
public class cInstruction {
    
    public static String cInstructionToBinary(String instruction) {
        Map<String, String> destTable = new HashMap<>();
        destTable.put("", "000");
        destTable.put("M", "001");
        destTable.put("D", "010");
        destTable.put("MD", "011");
        destTable.put("A", "100");
        destTable.put("AM", "101");
        destTable.put("AD", "110");
        destTable.put("AMD", "111");
        
        Map<String, String> compTable = new HashMap<>();
        compTable.put("0", "0101010");
        compTable.put("1", "0111111");
        compTable.put("-1", "0111010");
        compTable.put("D", "0001100");
        compTable.put("A", "0110000");
        compTable.put("!D", "0001101");
        compTable.put("!A", "0110001");
        compTable.put("-D", "0001111");
        compTable.put("-A", "0110011");
        compTable.put("D+1", "0011111");
        compTable.put("A+1", "0110111");
        compTable.put("D-1", "0001110");
        compTable.put("A-1", "0110010");
        compTable.put("D+A", "0000010");
        compTable.put("D-A", "0010011");
        compTable.put("A-D", "0000111");
        compTable.put("D&A", "0000000");
        compTable.put("D|A", "0010101");
        compTable.put("M", "1110000");
        compTable.put("!M", "1110001");
        compTable.put("-M", "1110011");
        compTable.put("M+1", "1110111");
        compTable.put("M-1", "1110010");
        compTable.put("D+M", "1000010");
        compTable.put("M+D", "1000010");
        compTable.put("D-M", "1010011");
        compTable.put("M-D", "1000111");
        compTable.put("D&M", "1000000");
        compTable.put("D|M", "1010101");
        
        Map<String, String> jumpTable = new HashMap<>();
        jumpTable.put("", "000");
        jumpTable.put("JGT", "001");
        jumpTable.put("JEQ", "010");
        jumpTable.put("JGE", "011");
        jumpTable.put("JLT", "100");
        jumpTable.put("JNE", "101");
        jumpTable.put("JLE", "110");
        jumpTable.put("JMP", "111");
        
        String[] parts = instruction.split(";");
        String dest = "";
        String comp;
        String jump = "";
        
        if (parts[0].contains("=")) {
            String[] destComp = parts[0].split("=");
            dest = destComp[0];
            comp = destComp[1];
        } else {
            comp = parts[0];
        }
        
        if (parts.length == 2) {
            jump = parts[1];
        }
        
        String destBits = destTable.get(dest);
        String compBits = compTable.get(comp);
        String jumpBits = jumpTable.get(jump);
        
        String binary = "111" + compBits + destBits + jumpBits;
        return binary;
    }
    
    public static void assembleFile(String inputFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter("c_instruction.hack"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("@") && !line.startsWith("(")) {
                    String binary = cInstructionToBinary(line);
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
            System.out.print("Enter the asm file name with extension: ");
            String fileName = reader.readLine();
            
            assembleFile(fileName);
            
            System.out.println("Assembly complete. Output written to c_instruction.hack");
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}
