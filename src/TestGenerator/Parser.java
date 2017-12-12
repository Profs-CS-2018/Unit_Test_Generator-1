package TestGenerator;

import java.io.BufferedReader;

/**
 * The Parser Class is the superclass for the ParseCPP and ParseInclude classes, and is designed to provide child
 * classes that can Parse a range of unique filetypes.
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 */
public class Parser implements ParserI {

    protected BufferedReader bufferedReader;

    public Parser() {

    }

    public void parse() {

    }
}
