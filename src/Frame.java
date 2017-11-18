package com.java.gui2;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Frame extends JFrame{

    
	private Container pane;
	private JTabbedPane tabbedPane;
	private JTabbedPane TestFiles;
    private JTabbedPane TestFixtures;
    private JTabbedPane Makefiles;
    private JFileChooser fc;
    private JFileChooser fc1;
    private JTextField textFieldSave;
    private JLabel fixedLabel;
    private JTextField textField;
    private DefaultListModel dm;
    private JList<?> listFiles;
    
   
	/**
	 * 
	 * @param title
	 */
    public Frame(String title)
    {
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
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    	setVisible(true);
    } /* End of Constructor */

    /**
     * 
     */
	private void createFrame()
    {
    	int width = 700;
    	int height = 500;
    	
    	setPreferredSize(new Dimension(width, height));
    	pack();
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null); //this should center the app
    
    	
    }/* End of Frame */
    
	/**
	 * 
	 */
    private void createContentPanel()
    {
    	pane = getContentPane();
   
    	dm = new DefaultListModel();
    	
    	listFiles = new JList<DefaultListModel>();
    	
    fixedLabel = new JLabel("Output Save Destination");
    fixedLabel.setLabelFor(textFieldSave);
    	
    textField = new JTextField("C://UnitTest/");
    	textField.setToolTipText("Modify selection paths.");
    	textField.setEditable(true);
    	
    	buildPanels();
    }
    	
    	public void buildPanels()
    	{   
        	JCheckBox allFiles = new JCheckBox("All Files");
        	JCheckBox makeFile	= new JCheckBox("Make File");
        	JCheckBox testFixture	= new JCheckBox("Test Fixture");
        	JCheckBox unitTest	= new JCheckBox("Unit Test");
        	
        	JButton browse = new JButton("Browse");
        	JButton preview	= new JButton("Preview");
        	
        	JButton addFile = new JButton("Add File");
        	JButton findFile = new JButton("Search");
        	
        	JButton submit = new JButton("Submit");
        	JButton cancel	= new JButton("Cancel");
        	
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
        	
        	JTextField addFileInput = new JTextField("Type Filename Here");
        	JTextField addDirectoryInput = new JTextField("Type Directory Here");
        	
        	JScrollPane scrollPane = new JScrollPane();
        	
        	
        	
        	//Border Layout Display Panels
        	filePanel.setLayout(new BorderLayout(7, 7));
        	previewPanel.setLayout(new BorderLayout(7, 7));
        	errorPanel.setLayout(new BorderLayout(7, 7));
        	
        	//Grid Layout Button Panels
        	sideBtnPanel.setLayout(new GridLayout(0, 2));
        	btmBtnPanel.setLayout(new GridLayout(0, 2));
        	fileInputPanel.setLayout(new GridLayout(0, 2));
        	togglePanel.setLayout(new GridLayout());
        	
        	//Flow Layout Output Selection Toggles
        	northContainer.setLayout(new FlowLayout());
        	submitBtnPartition.setLayout(new FlowLayout());
        	cancelBtnPartition.setLayout(new BorderLayout());
        	togglePanel.setLayout(new GridLayout());
        	eastContainer.setLayout(new BorderLayout());
        	southContainer.setLayout(new FlowLayout());
        	westContainer.setLayout(new FlowLayout());
        	
        	togglePanel.setName("Output Parameter Selection");
        	togglePanel.setVisible(true);
        	togglePanel.add(allFiles);
        	togglePanel.add(makeFile);
        togglePanel.add(testFixture);
        	togglePanel.add(unitTest);
        	
        	
        	//layer1.add(textField);
        	filePanel.add(listFiles);
        //scrollPane.add(filePanel);
        tabbedPane.addTab("Input Files",new JScrollPane( filePanel));
        tabbedPane.addTab("Preview", previewPanel);
        tabbedPane.addTab("Errors", errorPanel);
        //sideBtnPanel.add(browse);
        //sideBtnPanel.add(preview);
        
        
        
        fileInputPanel.add(addFileInput);
        fileInputPanel.add(addFile);
        fileInputPanel.add(addDirectoryInput);
        fileInputPanel.add(findFile);
        fileInputPanel.add(browse);
        fileInputPanel.add(preview);
        
        
        btmBtnPanel.add(submit);
        btmBtnPanel.add(cancel);
        //northContainer.add(togglePanel);
     
        	
        
        
        submitBtnPartition.add(btmBtnPanel);
        cancelBtnPartition.add(btmBtnPanel);
        //eastContainer.add(sideBtnPanel, BorderLayout.CENTER);
        eastContainer.add(fileInputPanel, BorderLayout.SOUTH);
        eastContainer.add(togglePanel, BorderLayout.NORTH);
        	southContainer.add(submitBtnPartition);
        	southContainer.add(cancelBtnPartition);
            
        	pane.add(tabbedPane, BorderLayout.CENTER);
        	pane.add(eastContainer, BorderLayout.EAST);
        	pane.add(southContainer, BorderLayout.SOUTH);
        	pane.add(northContainer, BorderLayout.NORTH);
        	pane.add(westContainer, BorderLayout.WEST);
        	
    	//Adding Listeners
    	browse.addActionListener(e -> browse());
    	preview.addActionListener(e -> preview());
    	cancel.addActionListener(e -> close());
    }
    
    public void createMenuBar()
    {
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
    
    public void browse()
    {
    	JFileChooser fc = new JFileChooser();
    	 fc.setMultiSelectionEnabled(true);
         /**
          *The following line of code can be used to open the file search in a particular directory
          * */
         fc.setCurrentDirectory(new File("C:\\Users\\timmcclintock\\Documents\\workspace\\TestGT2"));
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
                 if (files.length > 1) {
                     for (int i = 0; i < files.length; i++) {
                         dm.addElement(files[i].getAbsolutePath()); //add path into the JPanel

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
    
    public void submit()
    {
    	 try { //begin the middle end attempt to parse and make files
             throw new Exception();
     }
     catch (Exception e1) //catch any error which happens to have resulted in generation failure
     {
         JOptionPane.showMessageDialog(null, "ERROR: See Error Information Tab " +
                 "for details");
         //clicking OK redirects you to a tabbed pane labelled ERRORS
         String errors = "Errors";

         //check if ERRORS tab exists. Create if it does not.
         if(tabbedPane.indexOfTab(errors) == -1) {
        	 tabbedPane.addTab(errors, new JScrollPane(new JList<>()));
         }

             //populate the list of errors. make the text red?

             //set ERROR tab as currently selected tab
         tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(errors));
    	
    }
    }
    
    public void search()
    {
    	
    }
    
    public void preview()
    {

    	            /**
    	             * The following code checks if the action of clicking the button takes place
    	             * if it does then the user sees the textArea on the Preview tab changed to
    	             * have the preview of the output file in it.
    	             */

    	                Object returnVal = listFiles.getSelectedValue();
    	                if (returnVal != null) {
    	                    /**
    	                     * Selected file has its preview generated and a copy placed into
    	                     * the textArea on the Preview tab.
    	                     */
    	                    try {
    	                        FileReader fr = new FileReader(listFiles.getSelectedValue().toString());
    	                        BufferedReader br = new BufferedReader(fr);
    	                        String sCurrentLine;
    	                        String  preview = "Preview";
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
    	                        if(tabbedPane.indexOfTab(preview) == 1) {
    	                        while((sCurrentLine = br.readLine())!= null){
    	                        textArea.append(sCurrentLine + "\n");
    	                        }
                 
                    			textArea.setEditable(false);
    	                        fileContent.add(textArea);
    	                      
    	                        tabbedPane.setComponentAt(1,
    	                                new JScrollPane(fileContent));
    	                        tabbedPane.getModel().setSelectedIndex(tabbedPane.indexOfTab(preview));
    	                        }else 
    	                        {
    	                        	
    	                        }
    	                    	JOptionPane.showMessageDialog(pane, "See Preview Tab.");
    	                    }
    	                    catch (Exception e1)
    	                    {
    	                        JOptionPane.showMessageDialog(null, e1.getMessage());
    	                    }
    	                

    	                } else {
    	                    JOptionPane.showMessageDialog(pane, "No files selected for preview.");
    	                }
    }
    
  
    
    private void createButtonControls ()
 	{

 		// TODO Auto-generated method stub
 		
 	}
    public void save()
    {
    	fc1.setCurrentDirectory(fc.getCurrentDirectory());

            int returnVal = fc1.showSaveDialog(pane);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                File file = fc1.getSelectedFile();
                textFieldSave.setText(file.getAbsolutePath());
                try{
                    FileWriter newFile = new FileWriter(file.getPath());
                    //fw.write(content);
                    //fw.flush();
                    //fw.close();
                }
                catch(Exception e1)
                {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
                /**
                 * if the user wants to display only file name then the following line of code can be used
                 * textField.setText(file.getName());
                 */
            }
        }

    
    public void close()
    {
    		dispose();
    }
    
    public void listFiles()
    {
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
            while((sCurrentLine = br.readLine())!= null){
                System.out.println(sCurrentLine);
            }

        }
        catch (Exception e1)
        {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }
    }
   

}
