
/**
 * Created by Kevin L. Wong on 11/28/2023
 * JackAnalyzer processes Jack programming language files to generate XML output
 * conforming to the Jack language's syntax and structure.
 * 
 * It serves as the main entry point for compiling individual .jack files or
 * entire directories containing .jack files into corresponding XML representations.
 * 
 * For single .jack file processing:
 * - It checks and compiles the individual Jack file provided as input.
 * 
 * For directory processing:
 * - It identifies all .jack files within the directory and compiles each file into
 *   a separate XML file, preserving their structure and content.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JackAnalyzer {

    public static void main(String[] args) {
        // input is the jack file
        File jackFileDir = new File(args[0]);
        File fileOut;
        String fileNameOut = "";
        ArrayList<File> files = new ArrayList<>();
        if (jackFileDir.isFile() && args[0].endsWith(".jack")) {
            files.add(jackFileDir);
            fileNameOut = args[0].substring(0, args[0].length() - 5);

        }

        else if (jackFileDir.isDirectory()) {
            files = getJackFiles(jackFileDir);
            fileNameOut = args[0];

        }
        // output xml file
        System.out.println(fileNameOut);
        fileNameOut = fileNameOut + ".xml";

        fileOut = new File(fileNameOut);
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File file : files) {
            String fileOutName = file.toString().substring(0, file.toString().length() - 5) + "KW.xml";
            File fileOutFile = new File(fileOutName);
            // compile the files
            CompilationEngine compilationEngine = new CompilationEngine(file, fileOutFile);
            compilationEngine.compileClass();

        }

        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // obtain all jack files in a given directory
    public static ArrayList<File> getJackFiles(File jackFileDir) {
        File[] files = jackFileDir.listFiles();
        ArrayList<File> fResults = new ArrayList<>();
        if (files != null)
            for (File file : files) {
                if (file.getName().endsWith(".jack"))
                    fResults.add(file);
            }
        return fResults;
    }

}