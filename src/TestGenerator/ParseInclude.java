package TestGenerator;

import java.io.*;
import java.util.ArrayList;

/**
 * The TestGenerator.ParseInclude class adds the TestGenerator.Parser functionality for parsing/searching a selected C++ class
 * to determine its dependencies; needed to correctly write makefiles and test fixtures.
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 */
public class ParseInclude extends Parser {

    /**
     * The constructor for the TestGenerator.ParseInclude class.
     */
    public ParseInclude() {

    }

    /**
     * The parse method in the TestGenerator.ParseInclude class takes a C++ class and parses through it in search
     * of '#include' statements, to determine what a class' dependencies may be. Any found dependencies
     * are stored into an ArrayList, and then returned for use in the TestGenerator.OutputGenerator class.
     * @param inputFile The C++ source file to search through for dependencies
     * @return parseList: The collection of file dependencies found while parsing.
     */
    public ArrayList parse(File inputFile) {
        ArrayList<String> parseList = new ArrayList<>();
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("#include") && !line.contains("<")) {
                    line = line.replace("#include ", "");
                    line = line.replaceAll("\"", "");
                    parseList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseList;
    }
}
