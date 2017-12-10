import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.logging.Logger;

/**
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 */
public class Logs {

    private final static Logger userLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static Logger devLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static boolean initialUserLog = true;
    private static boolean initialDevLog = true;

    public Logs() {

    }

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

    public static void userLog(String file) {
        try {
            if (initialUserLog) {
                setupUserLogger();
                setupDevLogger();
                initialUserLog = false;
            } else if(file.equals("0")) {
                //
            }
            else {
                userLogr.info("Generating " + file + "(s)... \n");
            }
        } catch (Exception e) {
            userLogr.log(Level.SEVERE, "ERROR: File not working");
        }
    }

    public static void generatedFiles(ArrayList<String> fileNames) {
        for(String file : fileNames) {
            userLogr.info("File generated: " + file+ "\n");
        }
    }

    public static void devLog(String file) {
        try {
            if (initialDevLog) {
                setupDevLogger();
                initialDevLog = false;
            } else {
                devLogr.info("Generating " + file + "(s)... \n");
            }
        } catch (Exception e) {
            devLogr.log(Level.SEVERE, "ERROR: File not working \n");
        }
    }

    public static void moveLogsToFolder(){
        //System.out.println(devLogr.getParent());
    }
}
