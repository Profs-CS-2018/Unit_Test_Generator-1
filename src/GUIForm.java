package com.java.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GUIForm extends JFrame {

    private Container pane;
    private JList listFiles = null;
    private JTabbedPane TestFiles;
    private JTabbedPane TestFixtures;
    private JTabbedPane Makefiles;
    private JFileChooser fc;
    private JFileChooser fc1;
    private JTextField textFieldSave;
    private JLabel fixedLabel;

    public GUIForm(String title) {
        super(title);
        createFrame();
        createContentPanel();
        createMenuBar();
        createButtonControls();
        /**
         * Changes the default theme of JFileChooser
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        setVisible(true);
    }

    private void createFrame() {
        int width = 700;
        int height = 500;

        setPreferredSize(new Dimension(width, height));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //this should center the app

    }

    private void createContentPanel() {
        pane = getContentPane();

        DefaultListModel dm = new DefaultListModel();
        fixedLabel = new JLabel("Output Save Destination");
        fixedLabel.setLabelFor(textFieldSave);

    /*	JTextField textField = new JTextField("C://UnitTest/");
    	textField.setToolTipText("Modify selection paths.");
    	textField.setEditable(true);
    	*/

        JList<?> pathList = new JList();

        JRadioButton preview = new JRadioButton("Preview");
        JRadioButton cppview = new JRadioButton("CPP Files");
        JRadioButton hview = new JRadioButton("H Files");

        JButton browse = new JButton("Browse");
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> close());

        JPanel layer1 = new JPanel();
        JPanel layer2 = new JPanel();
        JPanel layer3 = new JPanel();
        JPanel layer4 = new JPanel();
        JPanel layer5 = new JPanel();
        JPanel layer6 = new JPanel();
        JPanel layer7 = new JPanel();
        JPanel layer8 = new JPanel();
        JPanel layer9 = new JPanel();
        JPanel toggle = new JPanel();

        toggle.add(preview);
        toggle.add(cppview);
        toggle.add(hview);

        layer1.setLayout(new BorderLayout(7, 7));
        layer2.setLayout(new GridLayout());
        layer3.setLayout(new GridLayout(0, 2));
        layer4.setLayout(new FlowLayout());
        layer5.setLayout(new FlowLayout());
        layer6.setLayout(new BorderLayout());
        toggle.setLayout(new GridLayout());
        layer8.setLayout(new FlowLayout());
        layer9.setLayout(new FlowLayout());

        //layer1.add(textField);
        layer1.add(pathList);
        layer2.add(browse);
        layer3.add(submit);
        layer3.add(cancel);
        layer4.add(toggle);

        layer5.add(layer3);
        layer6.add(layer3);
        layer7.add(layer2);
        layer8.add(layer5);
        layer8.add(layer6);

        pane.add(layer1, BorderLayout.CENTER);
        pane.add(layer7, BorderLayout.EAST);
        pane.add(layer8, BorderLayout.SOUTH);
        pane.add(layer4, BorderLayout.NORTH);
        pane.add(layer9, BorderLayout.WEST);


        //Adding Listeners
        browse.addActionListener(e -> browse());

    }

    public void createMenuBar() {
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

    public void browse() {
        JFileChooser fc = new JFileChooser();

        /**
         * Enables multiple selection.
         */
        fc.setMultiSelectionEnabled(true);
        /**
         *The following line of code can be used to open the file search in a particular directory
         * */
        fc.setCurrentDirectory(new File("C:\\Users\\T. McClintock\\workspace"));

        DefaultListModel dm = new DefaultListModel();
        /**
         * Enables multiple selection.
         */
        fc.setMultiSelectionEnabled(true);
        /**
         *The following line of code can be used to open the file search in a particular directory
         * */
        fc.setCurrentDirectory(new File("C:\\Users\\aanch\\Desktop\\Fall 2017"));
        /**
         * The following code adds filter to the file extensions.
         */

        fc.setFileFilter(new FileNameExtensionFilter("Text Files(.txt)", "txt"));
        fc.setFileFilter(new FileNameExtensionFilter("Java(.java)", "java"));
        fc.setFileFilter(new FileNameExtensionFilter("C++(.cpp)", "cpp"));
        /**
         * The following code checks if the action of clicking the button takes place
         * if it does then the user sees the textArea populated with the selected file
         * names
         */
        int returnVal = fc.showOpenDialog(this.pane);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            if (files.length > 1) {
                for (int i = 0; i < files.length; i++) {
                    dm.addElement(files[i].getAbsolutePath());
                }
            } else {
                dm.addElement(files[0].getAbsolutePath());
                //listFiles.setModel(dm);
            }
            //dm.addElement(fileNames);
            listFiles.setModel(dm);
        } else {
            JOptionPane.showMessageDialog(this.pane, "Oops! Operation was cancelled.");
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


    public void close() {
        dispose();
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
}