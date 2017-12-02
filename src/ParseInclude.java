import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ParseInclude extends Parser{

    private static final Logger LOGGER = Logger.getLogger(ParseInclude.class.getName());
    public ParseInclude(String path, ArrayList<File> files)
    {
        super(path);
    }


    public ArrayList parse(File inputFile) {
        ArrayList<String> parseList = new ArrayList<>();
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
                    System.out.println("File Dependency: " + line);
                    parseList.add(line);
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseList;
    }


}
