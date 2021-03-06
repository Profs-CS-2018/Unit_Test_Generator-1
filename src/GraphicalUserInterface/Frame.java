package GraphicalUserInterface;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultEditorKit;
import java.util.Iterator;
import TestGenerator.OutputGenerator;
import Records.Logs;

/**
 * The GraphicalUserInterface.Frame class creates the Front End/GUI application for the Unit Test Generator Tool.
 *
 * @author Aanchal Chaturvedi, Gianluca Solari, Thomas Soistmann Jr., Timothy McClintock
 * @version 2017.12.12
 */
public class Frame extends JFrame {

    //Defined immutable variable for Logging
    private static final long serialVersionUID = 1L;

    //Container for the Pane
    private Container pane;

    //Tab Pane
    private JTabbedPane tabbedPane;
    private JPanel outputPanel;
    private JPanel filePanel;
    private JPanel previewPanel;
    private JPanel userPanel;

    //Text Areas
    private JTextField textFieldSave;
    private JTextField addFileInput;
    private JTextField textField;

    //Check Boxes
    private JCheckBox allFilesBox;
    private JCheckBox makeFileBox;
    private JCheckBox testFixtureBox;
    private JCheckBox unitTestBox;

    //Labels
    private JLabel fixedLabel;

    //Default List Model for content
    private DefaultListModel dm;

    //List that takes and stores path names
    private JList<?> inputFiles;
    private JList<File> generatedFiles;

    //File List for storing the files
    private ArrayList<File> selectedFiles;

    //Declare the Output Generator Object
    private OutputGenerator outputGen;

    //Call upon the logging capabilities
    private static final Logger LOGGER = Logger.getLogger(Frame.class.getName());
    private boolean initialFolder = true;

    public static String userDirectory;
    public static String makeExecutableName;

    /**
     * The constructor for the Frame class. Calls the methods responsible for populating the GUI
     * with all of its content and sets it to be visible on screen.
     * @param title The title of the created GUI.
     */
    public Frame(String title) {
        super(title);
        createFrame();
        createContent();
        createMenuBar();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setVisible(true);
    }

    /**
     * The createFrame method initializes the GUI itself, setting its dimensions, location, and close operation.
     */
    private void createFrame() {
        int width = 1000;
        int height = 600;

        setPreferredSize(new Dimension(width, height));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //this should center the app
    }

    /**
     * The createContent method initializes the different collections/data structures used throughout
     * the lifetime of the program.
     */
    private void createContent() {
        pane = getContentPane();
        dm = new DefaultListModel();

        inputFiles = new JList<DefaultListModel>();
        selectedFiles = new ArrayList<>();

        fixedLabel = new JLabel("Output Save Destination");
        fixedLabel.setLabelFor(textFieldSave);

        textField = new JTextField("C://UnitTest/");
        textField.setToolTipText("Modify selection paths.");
        textField.setEditable(true);

        userDirectory = "";

        buildApplication();
    }

    /**
     * The buildApplication populates the GUI with the majority of its content, including buttons, check boxes,
     * text fields, tabbed panes, etc. This method also adds action listeners to the appropriate components.
     */
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
        JButton reset = new JButton("Reset");
        JButton addFile = new JButton("Add File");
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Close");

        tabbedPane = new JTabbedPane();

        filePanel = new JPanel();
        previewPanel = new JPanel();
        outputPanel = new JPanel();
        userPanel = new JPanel();
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

        char uniChar = '\u00A9';

        TitledBorder copyright = new TitledBorder("Powered By Rowan University SWE, " + uniChar +  " 2017");
        copyright.setTitleJustification(TitledBorder.CENTER);
        copyright.setTitlePosition(TitledBorder.TOP);

        ImageIcon icon = new ImageIcon(this.getClass().getResource("ASRC%20Federal.jpg"));
        JLabel img = new JLabel(icon);
        imagePanel.setBorder(copyright);
        imagePanel.setVisible(true);
        imagePanel.add(img);
        addFileInput = new JTextField("Search for File");

        //Border Layout Display Panels
        filePanel.setLayout(new BorderLayout(7, 7));
        previewPanel.setLayout(new BorderLayout(7, 7));
        outputPanel.setLayout(new BorderLayout(7, 7));
        userPanel.setLayout(new BorderLayout(7,7));

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

        togglePanel.setBorder(border);
        togglePanel.setVisible(true);
        togglePanel.add(allFilesBox);
        togglePanel.add(makeFileBox);
        togglePanel.add(testFixtureBox);
        togglePanel.add(unitTestBox);

        filePanel.add(inputFiles);
        inputFiles.setModel(dm);
        tabbedPane.addTab("Input Files", new JScrollPane(filePanel));
        tabbedPane.addTab("Preview", previewPanel);
        tabbedPane.addTab("Generated Files", new JScrollPane(outputPanel));
        tabbedPane.addTab("Logs", userPanel);

        fileInputPanel.add(addFileInput);
        fileInputPanel.add(addFile);
        fileInputPanel.add(browse);
        fileInputPanel.add(remove);
        fileInputPanel.add(preview);
        fileInputPanel.add(reset);

        btmBtnPanel.add(submit);
        btmBtnPanel.add(cancel);

        submitBtnPartition.add(btmBtnPanel);
        cancelBtnPartition.add(btmBtnPanel);
        eastContainer.add(fileInputPanel, BorderLayout.SOUTH);
        eastContainer.add(togglePanel, BorderLayout.CENTER);
        eastContainer.add(imagePanel, BorderLayout.NORTH);
        southContainer.add(submitBtnPartition);
        southContainer.add(cancelBtnPartition);

        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(eastContainer, BorderLayout.EAST);
        pane.add(southContainer, BorderLayout.SOUTH);
        pane.add(northContainer, BorderLayout.NORTH);
        pane.add(westContainer, BorderLayout.WEST);

        previewPanel.setPreferredSize(new Dimension(100, 100));
        outputPanel.setPreferredSize(new Dimension(100, 100));
        userPanel.setPreferredSize(new Dimension(100,100));
        togglePanel.setPreferredSize(new Dimension(100, 100));
        fileInputPanel.setPreferredSize(new Dimension(300, 100));
        imagePanel.setPreferredSize(new Dimension(500, 300));

        preview.setEnabled(false);

        //Adding Button Listeners
        browse.addActionListener(e -> browse());
        preview.addActionListener(e -> preview());
        cancel.addActionListener(e -> close());
        submit.addActionListener(e -> submit(preview));
        remove.addActionListener(e -> remove());
        addFile.addActionListener(e -> addFiles());
        reset.addActionListener(e -> reset());

        if (initialFolder) {
            createUserDevLogFolder();
        }

        Logs.userLog("0");
        Logs.userLog("1");
        showLogs();
        tabbedPane.setSelectedIndex(0);

        //Adding Check Box Listeners
        allFilesBox.addActionListener(e -> allFilesChecked());
        makeFileBox.addActionListener(e -> makeFilesChecked());
        testFixtureBox.addActionListener(e -> testFixtureFilesChecked());
        unitTestBox.addActionListener(e -> unitTestFilesChecked());
        makeFileBox.addActionListener(e -> otherFilesSelected());
        testFixtureBox.addActionListener(e -> otherFilesSelected());
        unitTestBox.addActionListener(e -> otherFilesSelected());
    }

    /**
     * This method creates the menu bar found along the top of the GUI and adds action listeners
     * to each of the menu's components.
     */
    private void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu FILE = new JMenu("File");
        menubar.add(FILE);

        JMenuItem BROWSE = new JMenuItem("Browse");
        JMenuItem EXIT = new JMenuItem("Exit");
        FILE.add(BROWSE);
        FILE.add(EXIT);
        BROWSE.addActionListener(e -> browse());
        EXIT.addActionListener(e -> close());

        JMenu EDIT = new JMenu("Edit");
        menubar.add(EDIT);
        Action CUT = new DefaultEditorKit.CutAction();
        CUT.putValue(Action.NAME, "Cut");
        Action COPY = new DefaultEditorKit.CopyAction();
        COPY.putValue(Action.NAME, "Copy");
        Action PASTE = new DefaultEditorKit.PasteAction();
        PASTE.putValue(Action.NAME, "Paste");
        EDIT.add(CUT);
        EDIT.add(COPY);
        EDIT.add(PASTE);

        JMenu PREFERENCES = new JMenu("Preferences");
        menubar.add(PREFERENCES);
        JMenuItem EDITOR = new JMenuItem("Edit Preferences");
        PREFERENCES.add(EDITOR);
        EDITOR.addActionListener(e -> createPreferencesFrame());
    }

    /**
     * The browse method is used when a user selects the 'Browse' button within the GUI.
     * This method opens a File Chooser window, where the user can search their file system for one
     * or multiple files.
     * This method also contains file filtering, duplicate file checking with the ability to make decisions.
     */
    private void browse() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);

        if (userDirectory.equals("")) {
            fc.setCurrentDirectory(new File(System.getProperty("user.name")));
        } else {
            fc.setCurrentDirectory(new File(userDirectory));
        }

        // The following code adds file extension filters to the File Chooser.
        fc.setFileFilter(new FileNameExtensionFilter("Text Files(.txt)", "txt"));
        fc.setFileFilter(new FileNameExtensionFilter("Java(.java)", "java"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp)", "cpp"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp, .h)", "cpp", "h"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp, .h) and Text Files(.txt)", "cpp", "txt", "h"));

        int returnVal = fc.showOpenDialog(pane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();      // the selected files from browse

            if (files.length >= 1) {
                for (int i = 0; i < files.length; i++) {
                    if (dm.contains(files[i].getAbsolutePath())) {
                        int reply = JOptionPane.showConfirmDialog(null, "Duplicate found: " + files[i].getName() +
                                        "\nWould you like to add this file anyway?", "Warning: Duplicate(s) exist.",
                                JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            dm.addElement(files[i].getAbsolutePath());
                            selectedFiles.add(files[i]);
                        }
                    } else {
                        dm.addElement(files[i].getAbsolutePath());
                        selectedFiles.add(files[i]);
                    }
                }
            } else {
                dm.addElement(files[0].getAbsolutePath());
            }
        }
    }

    /**
     * As an alternative to browse, this method gives users the functionality to type in a file (along with its
     * directory) to add a file to the GUI.
     * The addFiles method also includes duplicate checking, similarly to browse.
     */
    private void addFiles() {
        String filePath;
        filePath = addFileInput.getText();
        File file = new File(filePath);

        if (dm.contains(file.getAbsolutePath())) {
            int reply = JOptionPane.showConfirmDialog(null, "Duplicate found: " + file.getName() +
                            "\nWould you like to add this file anyway?", "Warning: Duplicate(s) exist.",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                dm.addElement(file.getAbsolutePath());
                selectedFiles.add(file);
            }
        } else {
            if (file.exists()) {
                dm.addElement(file.getAbsolutePath());
                selectedFiles.add(file);
                filePanel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(null, "Please enter a valid file path.");
            }
        }
    }

    /**
     * The submit method passes each of the C++ source files selected by the user over to the OutputGenerator
     * class to that corresponding test files can be generated for each one.
     * This method also checks to see which checkboxes are on, so that only desired test files are generated.
     * Finally, this method also received a collection of files which are generated and displays them
     * in the 'Generated Files' tab.
     * @param preview Automatically passes the 'preview' JButton, so that it becomes visible for use
     *                after a submit.
     */
    private void submit(JButton preview) {
        try {
            if (selectedFiles.size() > 0) {

                outputGen = new OutputGenerator(selectedFiles);
                int filesCPPSize = outputGen.getFilesCPPSize();

                if (filesCPPSize > 0) {

                    //This bypasses the Log bug
                    Logs.userLog("0");

                    if (makeFileBox.isSelected()) {
                        outputGen.writeMakeFile(makeExecutableName);
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

                    outputPanel.removeAll();
                    outputPanel.add(new JScrollPane(generatedFiles));

                    outputPanel.repaint();
                    outputPanel.revalidate();

                    preview.setEnabled(true);

                    String longDir;
                    ArrayList<String> fileNames = new ArrayList<>();
                    for (File file : outputFiles) {
                        longDir = file.getAbsolutePath();
                        String[] parts = longDir.split("/");
                        int size = parts.length;
                        fileNames.add(parts[size-1]);
                    }

                    Logs.generatedFiles(fileNames);
                    showLogs();
                    tabbedPane.setSelectedIndex(2);

                } else {
                    JOptionPane.showMessageDialog(pane, "No C++ classes (.cpp files) have been selected.");
                }
            } else {
                JOptionPane.showMessageDialog(pane, "No files have been selected.");
            }

            if (!makeFileBox.isSelected() && !testFixtureBox.isSelected() && !unitTestBox.isSelected()) {
                JOptionPane.showMessageDialog(pane, "No output options selected.");
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "ERROR: See Error Information Tab " +
                    "for details: \nfile already exists.");
            e1.printStackTrace();
            LOGGER.log(Level.FINE, "ERROR:  {0} file already exists.");
        }
    }

    /**
     * The showLogs method loads the contents of the USer.logs file into the 'Logs' tab of the GUI.
     */
    private void showLogs() {
        try {
            //FileReader fileReader = ;
            BufferedReader br = new BufferedReader (new FileReader(System.getProperty("user.dir")+"/logs/User.log"));
            String sCurrentLine;
            JPanel fileContent = new JPanel();
            fileContent.setLayout(new BorderLayout());
            JTextArea textArea = new JTextArea();

            if (tabbedPane.indexOfTab("Logs") == 3) {
                while ((sCurrentLine=br.readLine()) != null) {
                    textArea.append(sCurrentLine + "\n");
                }

                textArea.setEditable(false);
                fileContent.add(textArea);

                tabbedPane.setComponentAt(3, new JScrollPane(fileContent));
                tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab("Logs"));
            }
        }
        catch(Exception e){
            LOGGER.log(Level.SEVERE, "ERROR: Can't display User Logs");
        }
    }

    /**
     * Creates the 'logs' directory in which to store both the User and Developer logs.
     */
    private void createUserDevLogFolder() {
        new File(System.getProperty("user.dir")+"/logs").mkdir();
        initialFolder = false;
    }

    /**
     * The preview method displays the contents of a selected file from the 'Generated Files' tab
     * in the Preview tab, upon the press of the 'Preview' button.
     */
    private void preview() {
        if (generatedFiles != null) {
            int selectedIndex = generatedFiles.getSelectedIndex();

            if (selectedIndex != -1) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(generatedFiles.getSelectedValue().toString()));
                    String sCurrentLine;
                    String previewStr = "Preview";
                    JPanel fileContent = new JPanel();
                    fileContent.setLayout(new BorderLayout());
                    JTextArea textArea = new JTextArea();

                    if (tabbedPane.indexOfTab(previewStr) == 1) {
                        while ((sCurrentLine = br.readLine()) != null) {
                            textArea.append(sCurrentLine + "\n");
                        }

                        textArea.setEditable(false);
                        fileContent.add(textArea);

                        tabbedPane.setComponentAt(1, new JScrollPane(fileContent));
                        tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(previewStr));
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(pane, "No files selected for preview.");
            }
        } else {
            JOptionPane.showMessageDialog(pane, "No files generated to preview.");
        }
    }

    /**
     * This method closes the Java application.
     */
    private void close() {
        dispose();
    }

    /**
     * This method adds the functionality for when the user either selects or deselects the All Files
     * checkbox, each of the other three checkboxes are selected or deselected accordingly.
     */
    private void allFilesChecked() {
        // Turn on all checkboxes if All Files is on
        if (allFilesBox.isSelected()) {
            makeFileBox.setSelected(true);
            testFixtureBox.setSelected(true);
            unitTestBox.setSelected(true);
        }

        // If All Files is turned off, turn off all other checkboxes
        else {
            makeFileBox.setSelected(false);
            testFixtureBox.setSelected(false);
            unitTestBox.setSelected(false);
        }
    }

    /**
     * This method will automatically turn on the All Files checkbox when all of the other three have been turned on.
     */
    private void otherFilesSelected() {
        boolean makeFile = makeFileBox.isSelected();
        boolean testFixture = testFixtureBox.isSelected();
        boolean unitTest = unitTestBox.isSelected();
        if (makeFile && testFixture && unitTest) {
            allFilesBox.setSelected(true);
        }
    }

    /**
     * This method checks to see if the 'Make File' checkbox is or isn't selected.
     * In the case that it is not selected (has been un-checked by the user), this method deselects
     * the 'All Files' checkbox correspondingly.
     */
    private void makeFilesChecked() {
        if (!makeFileBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    /**
     * This method checks to see if the 'Test Fixture' checkbox is or isn't selected.
     * In the case that it is not selected (has been un-checked by the user), this method deselects
     * the 'All Files' checkbox correspondingly.
     */
    private void testFixtureFilesChecked() {
        if (!testFixtureBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    /**
     * This method checks to see if the 'Unit Test' checkbox is or isn't selected.
     * In the case that it is not selected (has been un-checked by the user), this method deselects
     * the 'All Files' checkbox correspondingly.
     */
    private void unitTestFilesChecked() {
        if (!unitTestBox.isSelected()) {
            allFilesBox.setSelected(false);
        }
    }

    /**
     * This method removes a user-selected file from both the JList of files selected from the File Chooser,
     * and the ArrayList of files (C++ classes) to eventually be submitted for test suite generation.
     */
    private void remove() {
        int selectedIndex = inputFiles.getSelectedIndex();

        if (selectedIndex != -1) {
            dm.remove(selectedIndex);
            selectedFiles.remove(selectedIndex);

            tabbedPane.validate();
            tabbedPane.repaint();
        }
        else {
            JOptionPane.showMessageDialog(pane, "No file selected to remove.");
        }
    }

    /**
     * This method is responsible for resetting the GUI and all accompanying data structures, so that the user can
     * re-run the application without having to close entirely and restart.
     */
    private void reset() {
        if (inputFiles != null) {
            dm.removeAllElements();
            inputFiles.removeAll();
            resetFilesList();
        }

        if (generatedFiles != null) {
            generatedFiles.removeAll();
            outputPanel.removeAll();
            outputGen.resetFilesList();
            outputGen.resetFilesCPPList();
            outputGen.resetFilesHList();
            outputGen.resetOutputFilesList();
        }

        previewPanel.removeAll();
        repaint();
        revalidate();
    }

    /**
     * Clears the selectedFiles collection of File objects; used during reset().
     * Done via iteration to avoid ConcurrentModificationException.
     */
    private void resetFilesList() {
        Iterator<File> iterator = selectedFiles.iterator();

        while (iterator.hasNext()) {
            File file = iterator.next();
            iterator.remove();
        }
    }

    /**
     * This method creates and displays the Preferences Menu upon the press of the 'Edit Preferences'
     * menu option.
     */
    private void createPreferencesFrame() {
        PreferencesFrame preferencesFrame = new PreferencesFrame();
    }
}
