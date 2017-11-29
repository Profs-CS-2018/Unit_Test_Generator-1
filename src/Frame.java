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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;

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

    //File Choosers
    private JFileChooser fc;
    private JFileChooser fc1;

    //Text Areas
    private JTextField textFieldSave;
    private JTextField addSaveTo;
    private JTextField addFileInput;
    private JTextField textField;
    private JTextArea failure;

    //Content Panels
    private JPanel outputPanel;

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
    private JList<?> listFiles;
    private JList<File> generatedFiles;

    //Progress bar
    private JProgressBar progressBar;

    //File List for storing the files
    private ArrayList<File> selectedFiles;

    //Path name variable for storing the Path name.
    private String path;

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
        createButtonControls();

        //	java.net.URL url = ClassLoader.getSystemResource("src/asrc.gif");
        /**
         * Changes the default theme of JFileChooser
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        setVisible(true);
    } /* End of Constructor */

    /**
     * Creates the Frame Dimensions and Defaults.
     */
    private void createFrame() {
        int width = 1000;
        int height = 500;

        //Set the Frame Dimensions
        setPreferredSize(new Dimension(width, height));
        pack();

        //Set the Default Close Operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Center the Window Frame
        setLocationRelativeTo(null);

    }/* End of createFrame()*/

    /**
     *
     */
    private void createContent() {
        //Pane instantiation
        pane = getContentPane();

        //Default Model List instantiation
        dm = new DefaultListModel();

        //JList instantiation
        listFiles = new JList<DefaultListModel>();

        //String instantiation
        path = "";

        //Array List instantiation
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
        //JButton findFile = new JButton("Search");
        JButton saveTo = new JButton("Save To");

        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Close");

        tabbedPane = new JTabbedPane();

        JPanel filePanel = new JPanel();
        JPanel previewPanel = new JPanel();
        JPanel errorPanel = new JPanel();
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
        outputPanel = new JPanel();
        JPanel imagePanel = new JPanel();

        //addSaveTo = new JTextField("Type Save To Location Here");
        addFileInput = new JTextField("Type Filename Here");
        JTextField addDirectoryInput = new JTextField("Type Search Directory Here");

        //Border Layout Display Panels
        filePanel.setLayout(new BorderLayout(7, 7));
        previewPanel.setLayout(new BorderLayout(7, 7));
        errorPanel.setLayout(new BorderLayout(7, 7));
        outputPanel.setLayout(new BorderLayout(7, 7));

        //Grid Layout Button Panels
        sideBtnPanel.setLayout(new GridLayout(0, 2));
        btmBtnPanel.setLayout(new GridLayout(0, 2));
        fileInputPanel.setLayout(new GridLayout(0, 2));
        togglePanel.setLayout(new GridLayout(4, 1));


        //Flow Layout Output Selection Toggles
        northContainer.setLayout(new FlowLayout());
        submitBtnPartition.setLayout(new FlowLayout());
        cancelBtnPartition.setLayout(new BorderLayout());
        togglePanel.setLayout(new GridLayout());
        eastContainer.setLayout(new BorderLayout());
        southContainer.setLayout(new FlowLayout());
        westContainer.setLayout(new FlowLayout());

        togglePanel.setName("Select the type(s) of files to generate:");
        togglePanel.setVisible(true);
        togglePanel.add(allFilesBox);
        togglePanel.add(makeFileBox);
        togglePanel.add(testFixtureBox);
        togglePanel.add(unitTestBox);

        //layer1.add(textField);
        filePanel.add(listFiles);
        //scrollPane.add(filePanel);
        tabbedPane.addTab("Input Files", new JScrollPane(filePanel));
        tabbedPane.addTab("Preview", previewPanel);
        tabbedPane.addTab("Generated Files", new JScrollPane(outputPanel));
        tabbedPane.addTab("Errors", errorPanel);
        //sideBtnPanel.add(browse);
        //sideBtnPanel.add(preview);

        //fileInputPanel.add(addSaveTo);
        //fileInputPanel.add(saveTo);
        fileInputPanel.add(addFileInput);
        fileInputPanel.add(addFile);
        fileInputPanel.add(addDirectoryInput);
        fileInputPanel.add(findFile);
        fileInputPanel.add(browse);
        fileInputPanel.add(remove);
        fileInputPanel.add(preview);

        btmBtnPanel.add(submit);
        btmBtnPanel.add(cancel);
        //northContainer.add(togglePanel);

        submitBtnPartition.add(btmBtnPanel);
        cancelBtnPartition.add(btmBtnPanel);
        //eastContainer.add(sideBtnPanel, BorderLayout.CENTER);
        eastContainer.add(fileInputPanel, BorderLayout.SOUTH);
        eastContainer.add(togglePanel, BorderLayout.EAST);
        southContainer.add(submitBtnPartition);
        southContainer.add(cancelBtnPartition);

        pane.add(tabbedPane, BorderLayout.CENTER);
        pane.add(eastContainer, BorderLayout.EAST);
        pane.add(southContainer, BorderLayout.SOUTH);
        pane.add(northContainer, BorderLayout.NORTH);
        pane.add(westContainer, BorderLayout.WEST);

        //Adding Button Listeners
        browse.addActionListener(e -> browse());
        preview.addActionListener(e -> preview());
        cancel.addActionListener(e -> close());
        submit.addActionListener(e -> submit());
        remove.addActionListener(e -> remove());
        addFile.addActionListener(e -> addFiles());
        //saveTo.addActionListener(e -> saveTo());

        //Adding Check Box Listeners
        allFilesBox.addActionListener(e -> allFilesChecked());
        makeFileBox.addActionListener(e -> makeFilesChecked());
        testFixtureBox.addActionListener(e -> testFixtureFilesChecked());
        unitTestBox.addActionListener(e -> unitTestFilesChecked());
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
                    selectedFiles.add(files[i]);
                }
            } else {
                dm.addElement(files[0].getAbsolutePath());
                listFiles.setModel(dm);
            }
            //dm.addElement(fileNames);
            listFiles.setModel(dm);
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

    private void saveTo() {
        path = addSaveTo.getText();
        addSaveTo.setEnabled(false);
        addSaveTo.setEditable(false);
    }

    public void saveToField() {
        path = addSaveTo.getText();
        addSaveTo.setEnabled(false);
    }

    public void addFiles() {
        String fileNm = addFileInput.getText();
        String dirNm = addFileInput.getText();
        String directoryName = dirNm;
        File directory = new File(directoryName);

        /**
         * Checks if directory does not exists or the directory text field is empty
         */
        if (dirNm.isEmpty() || !directory.exists()) {
            JOptionPane.showMessageDialog(null, "Please enter valid directory name");
        }
        /**
         * In case directory entered exists user can either
         * 1. Enter a file name
         * 2. Not enter a file name
         *
         */
        else {
            String absolutePath = dirNm + "\\" + fileNm;
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
                    } else {
                        System.out.println(dm.contains(fc.getSelectedFile().getAbsolutePath()));
                        if (dm.contains(fc.getSelectedFile().getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "Duplicate exists");
                            listFiles.setSelectedIndex(dm.indexOf(fc.getSelectedFile().getAbsolutePath()));
                        } else {
                            dm.addElement(fc.getSelectedFile().getAbsolutePath());
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
                    } else {
                        System.out.println(dm.contains(fc.getSelectedFile().getAbsolutePath()));
                        if (dm.contains(fc.getSelectedFile().getAbsolutePath())) {
                            JOptionPane.showMessageDialog(null, "Duplicate exists");
                            listFiles.setSelectedIndex(dm.indexOf(fc.getSelectedFile().getAbsolutePath()));
                        } else {
                            dm.addElement(fc.getSelectedFile().getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    private void submit() {
        OutputGenerator outputGen = new OutputGenerator(selectedFiles);

        try {
            if (makeFileBox.isSelected() && selectedFiles != null) {
                outputGen.writeMakeFile();
                System.out.println("Generating makefile...");
            }

            if (testFixtureBox.isSelected() && selectedFiles != null) {
                outputGen.writeTestFixtures();
                System.out.println("Generating testfixture(s)...");
            }

            if (unitTestBox.isSelected() && selectedFiles != null) {
                outputGen.writeUnitTests();
                System.out.println("Generating Unit Test file(s)...");
            }

            if (!makeFileBox.isSelected() && !testFixtureBox.isSelected() && !unitTestBox.isSelected()) {
                JOptionPane.showMessageDialog(pane, "No output options selected.");
            }

            ArrayList<File> outputFiles = outputGen.getOutputFiles();
            generatedFiles = new JList<>(outputFiles.toArray(new File[0]));
            outputPanel.add(generatedFiles);
        } catch (Exception e1) //catch any error which happens to have resulted in generation failure
        {
            JOptionPane.showMessageDialog(null, "ERROR: See Error Information Tab " +
                    "for details: \nfile already exists.");
            e1.printStackTrace();
            //clicking OK redirects you to a tabbed pane labeled ERRORS
            String errors = "Errors";
            LOGGER.log(Level.FINE, "ERROR:  {0} file already exists.", errors);
            //check if ERRORS tab exists. Create if it does not.
            if (tabbedPane.indexOfTab(errors) == -1) {
                tabbedPane.add(errors, new JScrollPane(failure));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            failure.setText("(" + sdf.format(new java.util.Date()) + ") Action failed.");

            //populate the list of errors. make the text red?

            failure.setEditable(false);
            //set ERROR tab as currently selected tab
            tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(errors));
        }
    }

    public void search() {

    }

    private void preview() {

        /**
         * The following code checks if the action of clicking the button takes place
         * if it does then the user sees the textArea on the Preview tab changed to
         * have the preview of the output file in it.
         */

        Object selectedFile = generatedFiles.getSelectedValue();
        if (selectedFile != null) {
            /**
             * Selected file has its preview generated and a copy placed into
             * the textArea on the Preview tab.
             */
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
        JOptionPane.showMessageDialog(this.pane, listFiles.getSelectedValue());
        System.out.println(listFiles.getSelectedValue().toString());
        try {
            FileReader fr = new FileReader(listFiles.getSelectedValue().toString());
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
        try {
            //DefaultListModel model = (DefaultListModel) listFiles.getModel();
            int selectedIndex = listFiles.getSelectedIndex();

            if (selectedIndex != -1) {
                listFiles.remove(selectedIndex);
                selectedFiles.remove(selectedIndex);
            }

            tabbedPane.validate();
            tabbedPane.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pane, "No file selected to remove.");
        }
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}