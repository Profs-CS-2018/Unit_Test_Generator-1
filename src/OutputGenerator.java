import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OutputGenerator {

    private ArrayList<File> files;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;
    static ArrayList<File> outputFiles;
    private Parser parser;
    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public OutputGenerator(ArrayList<File> files) {
        this.files = files;
        parser = new Parser();
        filesCPP = new ArrayList<>();
        filesH = new ArrayList<>();
        outputFiles = new ArrayList<>();
        initializeFileLists();
    }

    public void writeMakeFile() {
        if (!filesCPP.isEmpty()) {
            ArrayList<String> objectList = new ArrayList<>();
            File makeFile = new File("makefile");
            //file = new File(getDirectoryName());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(makeFile))) {
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
                    ArrayList<String> dependencies = parser.searchForIncludes(input);

                    for (String dependency : dependencies) {
                        writer.write(dependency + " ");
                    }
                    writer.write("\n\t$(CC) $(CFLAGS) " + input.getName());
                }
                writer.write("\n\nclean :");
                writer.write("\n\t-rm *.o $(OBJS) executable");

                writer.flush();
                writer.close();

                outputFiles.add(makeFile);
                //makeFile.renameTo(new File("/Users/gianlucasolari/Desktop/makefile"));
            } catch (Exception e) {
                Logs.userLog("Error generating test fixture for " + "makefile");
            }
        }
    }

    public void writeTestFixtures() {
        for (File input : filesCPP) {
            ArrayList<String> dependencies = parser.searchForIncludes(input);
            String className = input.getName().split("\\.")[0];

            File testFixture = new File(className + "Fixture.h");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFixture))) {
                writer.write("#include \"TestHarness.h\"\n");

                for (String header : dependencies) {
                    //System.out.println(header);
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
                Logs.userLog("Error generating test fixture for " + className);
            }
        }
    }

    public void writeUnitTests() {
        for (File input : filesCPP) {
            String className = input.getName().split("\\.")[0];
            String fixtureName = className + "Fixture";

            File unitTestFile = new File(className + "Test.cpp");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(unitTestFile))) {
                ArrayList<String> methodList = parser.searchForMethods(input);

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
                Logs.userLog("Error generating Unit Test for " + className);
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

    public ArrayList getOutputFiles() {
        //System.out.println();
        for (File file : outputFiles) {
            Logs.generatedFiles(file.getName());
        }

        return outputFiles;
    }

    static ArrayList moveOutputFiles(){
        for (File file : outputFiles) {
            //file.renameTo(new File("/Users/gianlucasolari/Desktop/"+file.getName()));
            System.out.println("LOOK AT ME: "+file.getParent()+" "+file.getName());
            //file.renameTo(new File("/Users/gianlucasolari/Desktop/"+file.getName()));
        }

        return outputFiles;
    }

    public int getFilesCPPSize() {
        return filesCPP.size();
    }
}