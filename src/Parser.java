import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Parser implements ParserI {

    private ArrayList<String> lineList;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;

    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public Parser() {

    }

    public ArrayList parseMake(File inputFile) {
        ArrayList<String> dependencyList = new ArrayList<>();
        lineList = new ArrayList<>();
        String line;
        int num = 0;

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("#include") && !line.contains("<")) {
                    line = line.replace("#include", "");
                    line = line.replaceAll("\"", "");
                    lineList.add("Line: " + (num + 1) + ".) " + line);
                    //System.out.println("File Dependency: " + line);
                    dependencyList.add(line);
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dependencyList;
    }

    @Override
    public void parseCPP() {

        // TODO Auto-generated method stub

    }

    @Override
    public void parseH() {

        // TODO Auto-generated method stub

    }
}