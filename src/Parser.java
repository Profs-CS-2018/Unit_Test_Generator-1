import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Parser {

    //private ArrayList<String> lineList;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;

    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public Parser() {

    }

    public ArrayList searchForIncludes(File inputFile) {
        ArrayList<String> dependencyList = new ArrayList<>();
        ArrayList lineList = new ArrayList<>();
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
                    line = line.replace("#include ", "");
                    line = line.replaceAll("\"", "");
                    lineList.add("Line: " + (num + 1) + ".) " + line);
                    dependencyList.add(line);
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dependencyList;
    }

    public ArrayList searchForMethods(File input) {
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
                        lineList.add("Line: " + (num + 1) + ".) " + line);
                        //System.out.println("Method for " + className + ": " + line);
                        methodList.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return methodList;
    }

    public void parseCPP() {

        // TODO Auto-generated method stub

    }

    public void parseH() {

        // TODO Auto-generated method stub

    }
}