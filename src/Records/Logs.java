package Records;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.logging.Logger;

/**
 * The TestGenerator.Logs class is responsible for generating records for both the User and Developer. Record Logs include important
 * information regarding the proper execution of the program as well as error reporting and run time metrics.
 *
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 * @version 2017.12.12
 */
public class Logs {

     //Creates User and Developer Logs with a global reference name
    private final static Logger userLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static Logger devLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    //Boolean variables determine if the Logs were initialized
    private static boolean initialUserLog = true;
    private static boolean initialDevLog = true;

    /**
     * Constructor for the Logs Class
     */
    public Logs() {

    }

    /**
     * Initializes the Developer Log & generates it in the logs folder
     */
    public static void setupDevLogger() {
        try {
            String name = System.getProperty("user.dir");
            FileHandler devFH = new FileHandler(name+"/logs/Dev.log", true);
            devFH.setLevel(Level.INFO);
            devLogr.addHandler(devFH);
        } catch (IOException e) {
            devLogr.log(Level.SEVERE, "ERROR: Dev Logger setup not working \n");
        }
    }

    /**
     * Initializes the User Logs & formats it in the SimpleFormatter for more readability
     */
    public static void setupUserLogger() {
        try {
            String name = System.getProperty("user.dir");
            FileHandler userFH = new FileHandler(name+"/logs/User.log", true);
            userFH.setLevel(Level.INFO);
            userFH.setFormatter(new SimpleFormatter());
            userLogr.addHandler(userFH);
        } catch (IOException e) {
            userLogr.log(Level.SEVERE, "ERROR: User Logger setup not working \n");
        }
    }

    /**
     * The userLog method will initialize the User & Developer Logs, & will take an input of type String
     * It updates the User and Developer Logs with each call
     * @param input of type String, where it would determine if it is either a 0, 1 or other
     *             0 is for bypassing the Log bug, 1 is for the initialization of the GUI
     *             if it is a file name, it will print the generating file line
     */
    public static void userLog(String input) {
        try {
            if (initialUserLog) {
                setupUserLogger();
                setupDevLogger();
                initialUserLog = false;
            } else if(input.equals("0")) {
                //Bypasses Log bug
            }
            else if(input.equals("1")){
                userLogr.info("GUI Initialized \n");
            }
            else {
                userLogr.info("Generating " + input + "(s)... \n");
            }
        } catch (Exception e) {
            userLogr.log(Level.SEVERE, "ERROR: File not working");
        }
    }

    /**
     * The generatedFiles method goes through an input ArrayList and prints out the files being generated
     * @param fileNames An ArrayList collection of the fileNames of type String that are being generated
     */
    public static void generatedFiles(ArrayList<String> fileNames) {
        for(String file : fileNames) {
            userLogr.info("File generated: " + file+ "\n");
        }
    }
}
