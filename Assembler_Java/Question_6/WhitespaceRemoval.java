
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WhitespaceRemoval{

    public static List<String> removeWhitespace(String fileName) {
        List<String> processedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pattern whitespacePattern = Pattern.compile("\\s");
            Pattern commentPattern = Pattern.compile("//.*");

            while ((line = reader.readLine()) != null) {
                line = whitespacePattern.matcher(line).replaceAll(""); // Remove whitespace
                line = commentPattern.matcher(line).replaceAll(""); // Remove comments
                if (!line.isEmpty()) {
                    processedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }

        return processedLines;
    }



    
    public static void writeLinesToFile(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
        }
    }
}

