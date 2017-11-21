import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OutputGenerator {

    private ArrayList<File> files;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;
    private Parser parser;
    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public OutputGenerator(ArrayList<File> files) {
        this.files = files;
        parser = new Parser();
        filesCPP = new ArrayList<>();
        filesH = new ArrayList<>();
        initializeFileLists();
    }

    public void writeMakeFile() {
        ArrayList<String> objectList = new ArrayList<>();
        //file = new File(getDirectoryName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("makefile"))) {
            writer.write("all: executable");
            writer.write("\n\nOBJS =");

            for (File object : filesCPP) {
                String oFile = object.getName().split("\\.")[0] + ".o";
                //String fileNm = object.getName();
                //System.out.println("Hea:" + fileNm);
                objectList.add(oFile);
                writer.write(" " + oFile);
            }

            writer.write("\nCC = g++");
            writer.write("\nDEBUG = -g");
            writer.write("\nCFLAGS = -Wall -c $(DEBUG)");
            writer.write("\nLFLAGS = -Wall $(DEBUG)");
            writer.write("\n\nexecutable : $(OBJS)");
            writer.write("\n\t$(CC) $(LFLAGS) $(OBJS) -o executable");

            for (File input : filesCPP) {
                String oFile = input.getName().split("\\.")[0] + ".o";
                writer.write("\n\n" + oFile + " : ");
                ArrayList<String> dependencies = parser.parseMake(input);

                for (String dependency : dependencies) {
                    writer.write(dependency);
                }
                writer.write("\n\t$(CC) $(CFLAGS) " + input.getName());
            }
            writer.write("\n\nclean :");
            writer.write("\n\t-rm *.o $(OBJS) executable");
            writer.close();

        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public void writeTestFixture() {
        Parser parser = new Parser();
        ArrayList<String> objectList = new ArrayList<>();
        //file = new File(getDirectoryName());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("testFixture"))) {

            for (File input : filesCPP) {
                writer.write("\n");
                String className = input.getName().split("\\.")[0];
                //System.out.println(className);
                String testName = input.getName().split("\\.")[0] + "Test()";
                String obj = " object";
                writer.write("struct " + className + "Test:testing::Test");
                writer.write("{");
                writer.write("\n\t" + className + "*" + obj + ";");
                writer.write("\n\t" + testName);
                writer.write("\n\t{");
                writer.write("\n\t\t" + obj + " = new " + className + "();");
                writer.write("\n\t}");
                writer.write("\n\t" + testName);
                writer.write("\n\t{");
                writer.write("\n\t\tdelete" + obj + ";");
                writer.write("\n\t}");
                writer.write("\n};");

                /**
                 writer.write("\n{");

                 //ArrayList<String> classNames = parser.parseForTestFixture(input);

                 writer.write("\n\t");
                 writer.write(input.getName() + "* " + obj);
                 writer.write("\n" + testName);
                 writer.write("\n" + obj + " = new " + testName);
                 writer.write("\n\t}");
                 writer.write("~" + testName);
                 writer.write("\n\tdelete" + obj);
                 writer.write("\n\t}");
                 writer.write("\n}");
                 */
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    public String getDirectoryName() {
        String s = "";
        String filePath = files.get(0).getParent();
        String directoryName = filePath.replace("\\", "\\\\");
        return directoryName;
    }

    public void initializeFileLists() {
        for (File file : files) {
            String fileName = file.getName();
            String fileExtension = getFileExtension(fileName);
            if (fileExtension.equals("cpp")) {
                filesCPP.add(file);
            } else if (fileExtension.equals("h")) {
                filesH.add(file);
            }
        }
    }

    private String getFileExtension(String fileName) {
        String extension = "NoExtension";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}