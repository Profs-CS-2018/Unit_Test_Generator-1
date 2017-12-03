import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Set;
import java.util.HashSet;
import java.nio.file.PathMatcher;
import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import javax.swing.border.TitledBorder;

/**
 * Frame Class extends JFrame
 */
public class Frame extends JFrame {

    /**
     * Private Variable Declarations
     */
    //Defined immutable variable for Logging
    private static final long serialVersionUID = 1L;

    //Container for the Pane
    private Container pane;

    //Tab Pane
    private JTabbedPane tabbedPane;
    private JPanel outputPanel;
    private JPanel filePanel;

    //File Choosers
    private JFileChooser fc;
    private JFileChooser fc1;

    //Text Areas
    private JTextField textFieldSave;
    private JTextField addFileInput;
    private JTextField textField;
    private JTextArea failure;

    //Check Boxes
    private JCheckBox allFilesBox;
    private JCheckBox makeFileBox;
    private JCheckBox testFixtureBox;
    private JCheckBox unitTestBox;

    //Labels
    private JLabel fixedLabel;
    private JLabel iconNameLabel;
    private JLabel iconLabel;

    //Default List Model for content
    private DefaultListModel dm;

    //List that takes and stores path names
    private JList<?> inputFiles;
    private JList<File> generatedFiles;

    //Progress bar
    private JProgressBar progressBar;

    //File List for storing the files
    private ArrayList<File> selectedFiles;

    //Path name variable for storing the Path name.
    private String path;

    //Declare the Output Generator Object
    OutputGenerator outputGen;

    //Call upon the logging capabilities
    private static final Logger LOGGER = Logger.getLogger(Frame.class.getName());

    /**
     * @param title
     */
    public Frame(String title) {
        /**
         * Name the Frame
         */
        super(title);
        createFrame();
        createContent();
        createMenuBar();

        //	java.net.URL url = ClassLoader.getSystemResource("src/asrc.gif");
        /**
         * Changes the default theme of JFileChooser
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setVisible(true);
    } /* End of Constructor */

    /**
     *
     */
    private void createFrame() {
        int width = 1000;
        int height = 600;

        setPreferredSize(new Dimension(width, height));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //this should center the app
    }/* End of Frame */

    /**
     *
     */
    private void createContent() {
        pane = getContentPane();

        dm = new DefaultListModel();

        inputFiles = new JList<DefaultListModel>();

        progressBar = new JProgressBar();

        path = "";

        selectedFiles = new ArrayList<>();

        fixedLabel = new JLabel("Output Save Destination");
        fixedLabel.setLabelFor(textFieldSave);

        textField = new JTextField("C://UnitTest/");
        textField.setToolTipText("Modify selection paths.");
        textField.setEditable(true);

        failure = new JTextArea();

        buildApplication();
    }

    private void buildApplication() {
        allFilesBox = new JCheckBox("All Files");
        allFilesBox.setSelected(true);
        makeFileBox = new JCheckBox("Make File");
        makeFileBox.setSelected(true);
        testFixtureBox = new JCheckBox("Test Fixture");
        testFixtureBox.setSelected(true);
        unitTestBox = new JCheckBox("Unit Test");
        unitTestBox.setSelected(true);

        JButton browse = new JButton("Browse");
        JButton preview = new JButton("Preview");
        JButton remove = new JButton("Remove");

        JButton addFile = new JButton("Add File");

        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Close");

        tabbedPane = new JTabbedPane();

        filePanel = new JPanel();
        JPanel previewPanel = new JPanel();
        JPanel errorPanel = new JPanel();
        outputPanel = new JPanel();
        JPanel sideBtnPanel = new JPanel();
        JPanel btmBtnPanel = new JPanel();
        JPanel northContainer = new JPanel();
        JPanel submitBtnPartition = new JPanel();
        JPanel cancelBtnPartition = new JPanel();
        JPanel eastContainer = new JPanel();
        JPanel southContainer = new JPanel();
        JPanel westContainer = new JPanel();
        JPanel togglePanel = new JPanel();
        JPanel fileInputPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        addFileInput = new JTextField("Search for File");

        //Border Layout Display Panels
        filePanel.setLayout(new BorderLayout(7, 7));
        previewPanel.setLayout(new BorderLayout(7, 7));
        errorPanel.setLayout(new BorderLayout(7, 7));
        outputPanel.setLayout(new BorderLayout(7, 7));

        //Grid Layout Button Panels
        sideBtnPanel.setLayout(new GridLayout(0, 2));
        btmBtnPanel.setLayout(new GridLayout(0, 2));
        fileInputPanel.setLayout(new GridLayout(0, 2));
        togglePanel.setLayout(new GridLayout(1, 4));

        //Flow Layout Output Selection Toggles
        northContainer.setLayout(new FlowLayout());
        submitBtnPartition.setLayout(new FlowLayout());
        cancelBtnPartition.setLayout(new BorderLayout());
        eastContainer.setLayout(new BorderLayout());
        southContainer.setLayout(new FlowLayout());
        westContainer.setLayout(new FlowLayout());


        TitledBorder border = new TitledBorder("Select the type(s) of files to generate:");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        JPanel panel = new JPanel();
        togglePanel.setBorder(border);
        togglePanel.setVisible(true);
        togglePanel.add(allFilesBox);
        togglePanel.add(makeFileBox);
        togglePanel.add(testFixtureBox);
        togglePanel.add(unitTestBox);

        filePanel.add(inputFiles);
        tabbedPane.addTab("Input Files", new JScrollPane(filePanel));
        tabbedPane.addTab("Preview", previewPanel);
        tabbedPane.addTab("Generated Files", new JScrollPane(outputPanel));
        tabbedPane.addTab("Errors", errorPanel);

        fileInputPanel.add(addFileInput);
        fileInputPanel.add(addFile);
        fileInputPanel.add(browse);
        fileInputPanel.add(remove);
        fileInputPanel.add(preview);


        btmBtnPanel.add(submit);
        btmBtnPanel.add(cancel);

        submitBtnPartition.add(btmBtnPanel);
        cancelBtnPartition.add(btmBtnPanel);
        eastContainer.add(fileInputPanel, BorderLayout.SOUTH);
        eastContainer.add(togglePanel, BorderLayout.NORTH);
        eastContainer.add(imagePanel, BorderLayout.CENTER);
        southContainer.add(submitBtnPartition);
        southContainer.add(cancelBtnPartition);

        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(eastContainer, BorderLayout.EAST);
        pane.add(southContainer, BorderLayout.SOUTH);
        pane.add(northContainer, BorderLayout.NORTH);
        pane.add(westContainer, BorderLayout.WEST);

        //filePanel.setPreferredSize(new Dimension(300, 300));
        previewPanel.setPreferredSize(new Dimension(100, 100));
        errorPanel.setPreferredSize(new Dimension(100, 100));
        outputPanel.setPreferredSize(new Dimension(100, 100));
        //sideBtnPanel.setPreferredSize(new Dimension(100, 100));
        //btmBtnPanel.setPreferredSize(new Dimension(100, 100));
        //northContainer.setPreferredSize(new Dimension(100, 100));
        //submitBtnPartition.setPreferredSize(new Dimension(100, 100));
        //cancelBtnPartition.setPreferredSize(new Dimension(100, 100));
        //eastContainer.setPreferredSize(new Dimension(100, 100));
        // southContainer.setPreferredSize(new Dimension(100, 100));
        //westContainer.setPreferredSize(new Dimension(100, 100));
        togglePanel.setPreferredSize(new Dimension(500, 100));
        fileInputPanel.setPreferredSize(new Dimension(300, 100));
        imagePanel.setPreferredSize(new Dimension(100, 100));

        //Adding Button Listeners
        browse.addActionListener(e -> browse());
        preview.addActionListener(e -> preview());
        cancel.addActionListener(e -> close());
        submit.addActionListener(e -> submit());
        remove.addActionListener(e -> remove());
        addFile.addActionListener(e -> addFiles());

        //Adding Check Box Listeners
        allFilesBox.addActionListener(e -> allFilesChecked());
        makeFileBox.addActionListener(e -> makeFilesChecked());
        testFixtureBox.addActionListener(e -> testFixtureFilesChecked());
        unitTestBox.addActionListener(e -> unitTestFilesChecked());
        makeFileBox.addActionListener(e -> otherFilesSelected());
        testFixtureBox.addActionListener(e -> otherFilesSelected());
        unitTestBox.addActionListener(e -> otherFilesSelected());
    }

    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu FILE = new JMenu("File");
        menubar.add(FILE);

        JMenuItem BROWSE = new JMenuItem("Browse");
        JMenuItem SAVE = new JMenuItem("Save");
        JMenuItem EXIT = new JMenuItem("Exit");
        FILE.add(BROWSE);
        FILE.add(SAVE);
        FILE.add(EXIT);
        BROWSE.addActionListener(e -> browse());
        SAVE.addActionListener(e -> browse());
        EXIT.addActionListener(e -> close());

        JMenu EDIT = new JMenu("Edit");
        menubar.add(EDIT);
        JMenuItem CUT = new JMenuItem("Cut");
        JMenuItem COPY = new JMenuItem("Copy");
        JMenuItem PASTE = new JMenuItem("Paste");
        EDIT.add(CUT);
        EDIT.add(COPY);
        EDIT.add(PASTE);

        JMenu VIEW = new JMenu("View");
        menubar.add(VIEW);
        JMenuItem PREVIEW = new JMenuItem("Preview");
        JMenuItem TEST_FILES = new JMenuItem("Test Files");
        JMenuItem TEST_FIXTURES = new JMenuItem("Test Fixtures");
        JMenuItem MAKEFILES = new JMenuItem("Makefiles");
        VIEW.add(PREVIEW);
        VIEW.add(TEST_FILES);
        VIEW.add(TEST_FIXTURES);
        VIEW.add(MAKEFILES);

        JMenu PREFERENCES = new JMenu("Preferences");
        menubar.add(PREFERENCES);
        JMenuItem STYLE = new JMenuItem("New Style");
        PREFERENCES.add(STYLE);
    }

    private void browse() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        /**
         *The following line of code can be used to open the file search in a particular directory
         * */
        fc.setCurrentDirectory(new File(System.getProperty("user.name")));
        /**
         * The following code adds filter to the file extensions.
         */

        //for searching the files, filters which files appear in the box
        fc.setFileFilter(new FileNameExtensionFilter("Text Files(.txt)", "txt"));
        fc.setFileFilter(new FileNameExtensionFilter("Java(.java)", "java"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp)", "cpp"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp, .h)", "cpp", "h"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp, .h) and Text Files(.txt)", "cpp", "txt", "h"));

        /**
         * The following code checks if the action of clicking the button takes place
         * if it does then the user sees the textArea populated with the selected file
         * names
         */
        int returnVal = fc.showOpenDialog(pane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles(); // the selected files from browse
            if (files.length >= 1) {
                for (int i = 0; i < files.length; i++) {
                    dm.addElement(files[i].getAbsolutePath()); //add path into the JPanel

                    ArrayList<String> holder = new ArrayList<>();
                    for(int x=0; x<files.length; x++){
                        holder.add(files[x].getAbsolutePath());
                        System.out.print(files[x].getAbsolutePath());
                    }

                    Set<String> set = new HashSet<String>(holder);
                    if(set.size() < holder.size()) {
                        System.out.println("ERROR");
                    }
                    else{
                        selectedFiles.add(files[i]);
                    }
                }
            } else {
                dm.addElement(files[0].getAbsolutePath());
                inputFiles.setModel(dm);
            }
            //dm.addElement(fileNames);
            inputFiles.setModel(dm);
        } else {
            JOptionPane.showMessageDialog(pane, "Oops! Operation was cancelled.");
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        System.out.println(getClass().getResource(path));
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void addFiles1() {
        /**
         * @Aanchal Chaturvedi
         * The add file button works based on the two text fields
         *  1. File name
         *  2. Directory name
         * Entering a directory name is MUST. (otherwise it would be like creating something similar to Browse)
         * If user does not add directory name or adds an incorrect directory name Error pops up saying they have to enter a valid directory name.
         * Once the user enters a valid directory name, they can either enter a file name or not
         * Case 1: user enters a file name
         * File chooser opens with the selected file in the open field.
         * Upon hitting approve the code checks of the duplicate of file selected already exists in Jfile.
         * Yes duplicate exists: error pops up saying “duplicate file exits”
         * Duplicate in the Jlist gets highlighted
         * Case 2: user does not enter a file name
         * File chooser opens with selected directory. User chooses a file from the directory.
         * Upon hitting open the code checks for duplicate and does the same as previously mentioned
         */

        String fileNm = addFileInput.getText();
        String dirNm = addFileInput.getText();
        String directoryName = dirNm.replace("\\", "\\\\");
        File directory = new File(directoryName);
        /**
         * Checks if directory does not exists or the directory text field is empty
         */
        if(dirNm.isEmpty() || !directory.exists())
        {
            JOptionPane.showMessageDialog(null, "Please enter valid directory name");
        }
        /**
         * In case directory entered exists user can either
         * 1. Enter a file name
         * 2. Not enter a file name
         *
         */
        else {
            String absolutePath = dirNm + "\\\\" + fileNm;
            /**
             * Case 1: user does not enter file name
             *
             */
            if (fileNm.isEmpty()) {
                fc.setCurrentDirectory(new File(directoryName));
                System.out.println(directoryName);
                int returnVal = fc.showOpenDialog(pane);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    if (dm.isEmpty()) {
                        dm.addElement(fc.getSelectedFile().getAbsolutePath());
                        inputFiles.setModel(dm);
                    } else {
                        System.out.println(dm.contains(fc.getSelectedFile().getAbsolutePath()));
                        if (dm.contains(fc.getSelectedFile().getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "Duplicate exists");
                            inputFiles.setSelectedIndex(dm.indexOf(fc.getSelectedFile().getAbsolutePath()));
                        } else {
                            dm.addElement(fc.getSelectedFile().getAbsolutePath());
                            inputFiles.setModel(dm);
                        }
                    }

                }
            }
            /**
             * Case 2: user enters file name
             */
            else {
                fc.setSelectedFile(new File(absolutePath));
                int returnVal1 = fc.showOpenDialog(pane);
                if (returnVal1 == JFileChooser.APPROVE_OPTION) {
                    if (dm.isEmpty()) {
                        dm.addElement(fc.getSelectedFile().getAbsolutePath());
                        inputFiles.setModel(dm);
                    } else {
                        System.out.println(dm.contains(fc.getSelectedFile().getAbsolutePath()));
                        if (dm.contains(fc.getSelectedFile().getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "Duplicate exists");
                            inputFiles.setSelectedIndex(dm.indexOf(fc.getSelectedFile().getAbsolutePath()));
                        } else {
                            dm.addElement(fc.getSelectedFile().getAbsolutePath());
                            inputFiles.setModel(dm);
                        }
                    }
                }
            }
        }
    }


    private void addFiles() {
        String filePath = "";
        filePath = addFileInput.getText();
        File file = new File(filePath);
        /*PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + fileNm);
        System.out.println(matcher.toString());
        File file = new File(matcher.toString());
        Path pathNm = getPaths(matcher);
        System.out.println(pathNm.toString());*/
        /**
         * Validates that the Default Model contains the selection.
         */
        if(!dm.contains(file.getAbsolutePath())) {
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "Please enter valid directory name");
            } else {
                if (dm.contains(file.getAbsolutePath())) {
                    JOptionPane.showMessageDialog(null, "Duplicate exists");
                    inputFiles.setSelectedIndex(dm.indexOf(file.getAbsolutePath()));
                } else {
                    dm.addElement(file);
                    selectedFiles.add(file);
                    inputFiles.setModel(dm);
                    filePanel.repaint();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Duplicate exists");
        }
             /*if(matcher.matches(pathNm))
            {
                System.out.println(pathNm);
            }
            else
            {

            }*/
    }


    public Path getPaths(PathMatcher matcher)
    {
        String path_name = matcher.toString();
        Path pathNm = FileSystems.getDefault().getPath(path_name);
        return pathNm;
    }

    private void submit() {
        outputGen = new OutputGenerator(selectedFiles);
        System.out.println(selectedFiles);
        int filesCPPSize = outputGen.getFilesCPPSize();
        //JList.setListData(new String[0]);

        try {
            if (selectedFiles.size() > 0 && filesCPPSize > 0) {
                if (makeFileBox.isSelected()) {
                    outputGen.writeMakeFile();
                    Logs.userLog("makefile");
                }

                if (testFixtureBox.isSelected()) {
                    outputGen.writeTestFixtures();
                    Logs.userLog("testfixture");
                }

                if (unitTestBox.isSelected()) {
                    outputGen.writeUnitTests();
                    Logs.userLog("Unit Test File");
                }

                ArrayList<File> outputFiles = outputGen.getOutputFiles();
                generatedFiles = new JList<>(outputFiles.toArray(new File[0]));
                outputPanel.add(new JScrollPane(generatedFiles));

            } else {
                JOptionPane.showMessageDialog(pane, "No C++ classes were selected.");
            }

            if (!makeFileBox.isSelected() && !testFixtureBox.isSelected() && !unitTestBox.isSelected()) {
                JOptionPane.showMessageDialog(pane, "No output options selected.");
            }
        } catch (Exception e1) {        //catch any error which happens to have resulted in generation failure
            JOptionPane.showMessageDialog(null, "ERROR: See Error Information Tab " +
                    "for details: \nfile already exists.");
            e1.printStackTrace();
            LOGGER.log(Level.FINE, "ERROR:  {0} file already exists.");


            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        }
    }

    private void preview() {

        /**
         * The following code checks if the action of clicking the button takes place
         * if it does then the user sees the textArea on the Preview tab changed to
         * have the preview of the output file in it.
         */
        int selectedIndex = generatedFiles.getSelectedIndex();

        if (selectedIndex != -1) {

            try {
                BufferedReader br = new BufferedReader(new FileReader(generatedFiles.getSelectedValue().toString()));
                String sCurrentLine;
                String preview = "Preview";
                /**
                 * trying to create a new Panel here.
                 */
                JScrollPane scrollPane = new JScrollPane();
                JPanel fileContent = new JPanel();
                fileContent.setLayout(new BorderLayout());
                JTextArea textArea = new JTextArea();

                /**
                 * Lines below again for testing
                 */
                if (tabbedPane.indexOfTab(preview) == 1) {
                    while ((sCurrentLine = br.readLine()) != null) {
                        textArea.append(sCurrentLine + "\n");
                    }

                    textArea.setEditable(false);
                    fileContent.add(textArea);

                    tabbedPane.setComponentAt(1, new JScrollPane(fileContent));
                    tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(preview));
                }
                //JOptionPane.showMessageDialog(pane, "See Preview Tab.");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(pane, "No files selected for preview.");
        }
    }

    private void createButtonControls() {

        // TODO Auto-generated method stub

    }

    public void save() {
        fc1.setCurrentDirectory(fc.getCurrentDirectory());

        int returnVal = fc1.showSaveDialog(pane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc1.getSelectedFile();
            textFieldSave.setText(file.getAbsolutePath());
            try {
                FileWriter newFile = new FileWriter(file.getPath());
                //fw.write(content);
                //fw.flush();
                //fw.close();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }
            /**
             * if the user wants to display only file name then the following line of code can be used
             * textField.setText(file.getName());
             */
        }
    }

    private void close() {
        outputGen.moveOutputFiles();
        dispose();
    }

    private void allFilesChecked() {
        // Turn on all checkboxes if Select All is on
        if (allFilesBox.isSelected()) {
            makeFileBox.setSelected(true);
            testFixtureBox.setSelected(true);
            unitTestBox.setSelected(true);
        }

        // If Select All is turned off, turn off all other checkboxes
        else {
            makeFileBox.setSelected(false);
            testFixtureBox.setSelected(false);
            unitTestBox.setSelected(false);
        }
    }

    private void otherFilesSelected() {
        // if all boxes are selected automatically select the "All Files" box
        boolean makeFile = makeFileBox.isSelected();
        boolean testFixture = testFixtureBox.isSelected();
        boolean unitTest = unitTestBox.isSelected();
        if (makeFile && testFixture && unitTest)
        {
            allFilesBox.setSelected(true);
        }
    }

    private void makeFilesChecked() {
        if (!makeFileBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    private void testFixtureFilesChecked() {
        if (!testFixtureBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    private void unitTestFilesChecked() {
        if (!unitTestBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    public void listFiles() {
        /**
         * JOptionPane implemented below is just for testing purposes.
         */
        JOptionPane.showMessageDialog(this.pane, inputFiles.getSelectedValue());
        System.out.println(inputFiles.getSelectedValue().toString());
        try {
            FileReader fr = new FileReader(inputFiles.getSelectedValue().toString());
            BufferedReader br = new BufferedReader(fr);
            String sCurrentLine;
            /**
             * trying to create a new Panel here.
             */
            JPanel fileContent = new JPanel();
            JTextArea textArea = new JTextArea();
            fileContent.add(textArea);
            /**
             * Lines below again for testing
             */
            textArea.setText("Hello!!!!");
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }
    }

    private void remove() {
        int selectedIndex = inputFiles.getSelectedIndex();

        if (selectedIndex != -1) {
            dm.remove(selectedIndex);

            tabbedPane.validate();
            tabbedPane.repaint();
        }
        else {
            JOptionPane.showMessageDialog(pane, "No file selected to remove.");
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
