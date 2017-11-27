import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private FileWriter log;

    public Logger() {


    }

    public void createLog(String logMessage) {
        try {
            log = new FileWriter("debug.log");
            log.write(logMessage + " \n");
        } catch (IOException e) {

        }

    }

    public void closeLog() {


    }
}