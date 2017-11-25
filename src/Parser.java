import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Parser implements ParserI {

    //private ArrayList<String> lineList;
    protected BufferedReader bufferedReader;
    protected FileReader fileReader;
    private ArrayList<File> filesCPP;
    private ArrayList<File> filesH;
    private String path = "";

    private static final Logger LOGGER = Logger.getLogger(OutputGenerator.class.getName());

    public Parser(String path){
        this.path = path;
    }



}