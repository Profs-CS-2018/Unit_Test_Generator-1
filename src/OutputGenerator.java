import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OutputGenerator {

    private ArrayList<File> outputFiles;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;
    private ArrayList<File> files;
    private ParseCPP parserCPP;
    private ParseInclude parserInclude;
    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public OutputGenerator(ArrayList<File> files) {
        this.files = files;
        filesCPP = new ArrayList<>();
        filesH = new ArrayList<>();
        parserInclude = new ParseInclude(files.get(0).getAbsolutePath(), files);
        parserCPP = new ParseCPP(files.get(0).getAbsolutePath(), files);
        outputFiles = new ArrayList<>();
        initializeFileLists();
    }

    public void writeMakeFile() {
        ArrayList<String> objectList = new ArrayList<>();
        File makefile = new File(files.get(0).getParent()+ "\\" + "makefile");
        //file = new File(getDirectoryName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(makefile))) {
            writer.write("all: executable");
            writer.write("\n\nOBJS =");

            for (File object : filesCPP) {
                String oFile = object.getName().split("\\.")[0] + ".o";
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
                ArrayList<String> dependencies = parserInclude.parse(input);

                for (String dependency : dependencies) {
                    writer.write(dependency);
                }
                writer.write("\n\t$(CC) $(CFLAGS) " + input.getName());
            }
            writer.write("\n\nclean :");
            writer.write("\n\t-rm *.o $(OBJS) executable");

            writer.flush();
            writer.close();

            outputFiles.add(makefile);
        } catch (Exception e) {
            System.out.println("Error generating makefile.");
        }
    }

    public void writeTestFixtures() {
        for (File input : filesCPP) {
            ArrayList<String> dependencies = parserInclude.parse(input);
            String className = input.getName().split("\\.")[0];

            /**
             * TODO: getPath() requires dynamic capability in the case where two files
             * are selected from multiple locations.
             */
            File testFixture = new File(files.get(0).getParent()+ "\\" + className + "Fixture.h");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFixture))) {
                writer.write("#include \"TestHarness.h\"\n");

                for (String header : dependencies) {
                    writer.write("#include " + '"' + header + '"' + "\n");
                }

                String testName = input.getName().split("\\.")[0] + "Fixture()";
                //String obj = " object";
                writer.write("\nstruct " + className + "Fixture:testing::Test");
                writer.write("\n{");
                writer.write("\n\t" + className + " *comp;");
                writer.write("\n\n\t" + testName);
                writer.write("\n\t{");
                writer.write("\n\t\tcomp = new " + className + "();");
                writer.write("\n\t}");
                writer.write("\n\n\t~" + testName);
                writer.write("\n\t{");
                writer.write("\n\t\tdelete comp;");
                writer.write("\n\t}");
                writer.write("\n}");

                writer.flush();
                writer.close();

                outputFiles.add(testFixture);
                //testFixtures.add(file);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error generating test fixture for " + className);
            }
        }
    }

    public void writeUnitTests() {
        for (File input : filesCPP) {
            String className = input.getName().split("\\.")[0];
            String fixtureName = className + "Fixture";

            File unitTestFile = new File(files.get(0).getParent() + "\\" + className + "Test.cpp");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(unitTestFile))) {
                ArrayList<String> methodList = parserCPP.parse(input);

                writer.write("#include \"TestHarness.h\"");
                writer.write("\n#include \"" + fixtureName + ".h\"");

                for (String method : methodList) {
                    writer.write("\n\nTEST( " + className + "Tests, " + method + "_Test )");
                    writer.write("\n{");
                    writer.write("\n\n\t" + fixtureName + " f;");
                    writer.write("\n\t//Test Logic goes here");
                    writer.write("\n\n}");
                }

                writer.flush();
                writer.close();

                outputFiles.add(unitTestFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getDirectoryName() {
        String s = "";
        String filePath = files.get(0).getParent();
        String directoryName = filePath.replace("\\", "\\\\");
        return directoryName;
    }

    public ArrayList getOutputFiles() {
        return outputFiles;
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

    public ArrayList moveOutputFiles(){
        for (File file : outputFiles) {
            System.out.println("LOOK AT ME: " + file.getParent() + "\\" + file.getName());
        }

        return outputFiles;
    }

    public int getFilesCPPSize() {
        return filesCPP.size();
    }
}