import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 */
public class ParseCPP extends Parser{

    private static final Logger LOGGER = Logger.getLogger(ParseCPP.class.getName());
    public ParseCPP(String path, ArrayList<File> files)
    {
        super(path);
    }

    public ArrayList parse(File input) {
        ArrayList<String> methodList = new ArrayList<>();
        ArrayList lineList = new ArrayList<>();
        String line;
        String[] methodTypes = new String[] {"void", "int", "string", "bool", "long"};
        String className = input.getName().split("\\.")[0];
        int num = 0;

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
