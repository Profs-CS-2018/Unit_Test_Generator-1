import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.*;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.*;

public class Logs {

    private final static Logger userLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static Logger devLogr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static boolean initialUserLog = true;
    static boolean initialDevLog = true;

    public Logs() {

    }

    public static void setupDevLogger() {
        try {
            FileHandler devFH = new FileHandler("Dev.log", true);
            devFH.setLevel(Level.INFO);
            devLogr.addHandler(devFH);
        } catch (IOException e) {
            devLogr.log(Level.SEVERE, "ERROR: Dev Logger setup not working \n");
        }
    }

    public static void setupUserLogger(){
        try{
            FileHandler userFH = new FileHandler("User.log", true);
            userFH.setLevel(Level.INFO);
            userFH.setFormatter(new SimpleFormatter());
            userLogr.addHandler(userFH);
        }
        catch(IOException e){
            userLogr.log(Level.SEVERE, "ERROR: User Logger setup not working \n");
        }
    }

    public static void userLog(String file){
        try{
            if(initialUserLog){
                setupUserLogger();
                setupDevLogger();
                initialUserLog = false;
            }
            else{
                userLogr.info("Generating "+file+"(s)... \n");
            }
        }
        catch(Exception e){
            userLogr.log(Level.SEVERE, "ERROR: File not working");
        }
    }

    public static void generatedFiles(String file){
        userLogr.info("File generated: "+file+"\n");
    }

    public static void devLog(String file){
        try{
            if(initialDevLog){
                setupDevLogger();
                initialDevLog = false;
            }
            else{
                devLogr.info("Generating "+file+"(s)... \n");
            }
        }
        catch(Exception e){
            devLogr.log(Level.SEVERE, "ERROR: File not working \n");
        }
    }
}