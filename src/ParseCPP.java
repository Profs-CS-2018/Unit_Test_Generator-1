import java.io.*;
import java.util.ArrayList;

/**
 * The ParseCPP class adds the Parser functionality for parsing/searching a selected C++ class
 * for its method names; needed for generating Unit Test files.
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 */
public class ParseCPP extends Parser {

    /**
     * Constructor for the ParseCPP class.
     *
     */
    public ParseCPP() {

    }

    /**
     * The parse method of the ParseCPP class takes a C++ class as input, and parses through it in search of
     * its methods. This is done by searching for the '::' character, and then splitting the text
     * accordingly. Each found method name is added to an ArrayList, and then returned for use in the
     * OutputGenerator class.
     * @param input A C++ class (File object) to search through.
     * @return An ArrayList of String objects, representing the names of each method found within a C++ class.
     */
    public ArrayList parse(File input) {
        ArrayList<String> methodList = new ArrayList<>();
        String line;
        String className = input.getName().split("\\.")[0];

        try {
            bufferedReader = new BufferedReader(new FileReader(input));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(className) && line.contains("::")) {
                    if (!line.split("::")[1].contains(className)) {
                        line = line.split("::")[1];
                        line = line.split("\\(")[0];
                        methodList.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return methodList;
    }
}
