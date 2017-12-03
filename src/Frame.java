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
import javax.swing.text.DefaultEditorKit;

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
        errorPanel.setPreferredSize(new Dimension(100, 100));
        outputPanel.setPreferredSize(new Dimension(100, 100));
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
        JMenuItem PREVIEW = new JMenuItem("Preview");
        JMenuItem TEST_FILES = new JMenuItem("Test Files");
        JMenuItem TEST_FIXTURES = new JMenuItem("Test Fixtures");
        JMenuItem MAKEFILES = new JMenuItem("Makefiles");
        PREFERENCES.add(PREVIEW);
        PREFERENCES.add(TEST_FILES);
        PREFERENCES.add(TEST_FIXTURES);
        PREFERENCES.add(MAKEFILES);

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

    private void addFiles() {

        String filePath = "";
        filePath = addFileInput.getText();
        File file = new File(filePath);

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
    }

    private void search() {

        String filePath = JOptionPane.showInputDialog(this,
                "Search for a file", null);
        filePath = addFileInput.getText();
        File file = new File(filePath);

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
    }

    private void submit(JButton preview) {
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
        preview.setEnabled(true);
        outputGen.moveOutputFiles();
    }

    private void preview() {

        /**
         * The following code checks if the action of clicking the button takes place
         * if it does then the user sees the textArea on the Preview tab changed to
         * have the preview of the output file in it.
         */
        if(generatedFiles != null) {
            int selectedIndex = generatedFiles.getSelectedIndex();

            if (selectedIndex != -1) {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(generatedFiles.getSelectedValue().toString()));
                    String sCurrentLine;
                    String previewStr = "Preview";
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
                    if (tabbedPane.indexOfTab(previewStr) == 1) {
                        while ((sCurrentLine = br.readLine()) != null) {
                            textArea.append(sCurrentLine + "\n");
                        }

                        textArea.setEditable(false);
                        fileContent.add(textArea);

                        tabbedPane.setComponentAt(1, new JScrollPane(fileContent));
                        tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(previewStr));
                    }
                    //JOptionPane.showMessageDialog(pane, "See Preview Tab.");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(pane, "No files selected for preview.");
            }
        }else
        {
            JOptionPane.showMessageDialog(pane, "No files generated to preview.");
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
