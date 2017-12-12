package TestGenerator;

import java.io.BufferedReader;

/**
 * The Parser Class is the superclass for the ParseCPP and ParseInclude classes, and is designed to provide child
 * classes that can Parse a range of unique file types.
 *
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 * @version 2017.12.12
 */
public class Parser implements ParserI {

    protected BufferedReader bufferedReader;

    /**
     * Constructor for the Parser class.
     */
    public Parser() {

    }

    /**
     * The Parser class' parse method.
     */
    public void parse() {

    }
}
