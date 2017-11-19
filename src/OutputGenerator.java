//package com.java.gui2;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OutputGenerator {

    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public OutputGenerator(ArrayList<File> files, String path) {
        writeMakeFile(files, path);
    }

    public void writeMakeFile(ArrayList<File> classes, String path) {
        //Parser parser = new Parser();
        ArrayList<String> objectList = new ArrayList<>();
        path = "makefile";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("OBJS =");

            for (File object : classes) {
                String oFile = object.toString().split("\\.")[0] + ".o";
                objectList.add(oFile);
                writer.write(" " + oFile);
            }

            writer.write("\nCC = g++");
            writer.write("\nDEBUG = -g");
            writer.write("\nCFLAGS = -Wall -c $(DEBUG)");
            writer.write("\nLFLAGS = -Wall $(DEBUG)");
            writer.write("\n\nexecutable : $(OBJS)");
            writer.write("\n\t$(CC) $(LFLAGS) $(OBJS) -o executable");

            for (File input : classes) {
                String oFile = input.toString().split("\\.")[0] + ".o";
                writer.write("\n\n" + oFile + " : ");
                //ArrayList<String> dependencies = parser.parseMake(input);

                /**for (String dependency : dependencies) {
                    writer.write(dependency);
                } */

                writer.write("\n\t$(CC) $(CFLAGS) " + input.toString());
            }

            writer.write("\n\nclean :");
            writer.write("\n\t-rm *.o $(OBJS) executable");
            writer.close();

        } catch (Exception e) {
            System.out.println("Error!");
        }
    }
}
