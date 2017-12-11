import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PreferencesFrame extends JFrame {

    private Container container;
    private JComboBox<String> directoryInput;
    private JTextField makeNameInput;
    private String[] userDirectories;
    private ArrayList<String> filePaths;
    private ArrayList<String> storedDirectories;

    public PreferencesFrame() {
        super("Preferences");
        container = getContentPane();
        filePaths = new ArrayList<>();
        loadDirectoryPreferences();
        createFrame();
        buildApplication();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        //setVisible(true);
    }

    private void createFrame() {
        setPreferredSize(new Dimension(800, 400));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void buildApplication() {
        JPanel pane = new JPanel(new GridBagLayout());
        pane.setPreferredSize(new Dimension(700, 500));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel directoryLabel = new JLabel("Set a default directory:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        pane.add(directoryLabel, constraints);

        directoryInput = new JComboBox<>(userDirectories);
        directoryInput.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        directoryInput.setEditable(true);
        pane.add(directoryInput, constraints);

        JButton applyDirectory = new JButton("Apply");
        constraints.gridx = 3;
        constraints.gridy = 1;
        pane.add(applyDirectory, constraints);

        JLabel makeFileLabel = new JLabel("Set a name for the makefile executable:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(20, 0, -20, 0);
        pane.add(makeFileLabel, constraints);

        makeNameInput = new JTextField();
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        makeNameInput.setEditable(true);
        pane.add(makeNameInput, constraints);

        JButton applyMakeName = new JButton("Apply");
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.weighty = 1;
        pane.add(applyMakeName, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setSize(new Dimension(600, 300));

        JButton save = new JButton("Save");
        JButton saveAndClose = new JButton("Save and Close");
        buttonPanel.add(save);
        buttonPanel.add(saveAndClose);

        container.add(pane);
        container.add(buttonPanel, BorderLayout.SOUTH);

        applyDirectory.addActionListener(e -> setDirectory());
        applyMakeName.addActionListener(e -> setMakeName());
        save.addActionListener(e -> saveDirectoryPreferences());
        saveAndClose.addActionListener(e -> saveAndClose());

        setVisible(true);
    }

    private void setDirectory() {
        if (directoryInput.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please enter a file path.");
        } else {
            String filePath = (String) directoryInput.getSelectedItem();
            if (Files.exists(Paths.get(filePath))) {
                if (!storedDirectories.contains(filePath) && (!filePaths.contains(filePath))) {
                    directoryInput.addItem(filePath);
                    filePaths.add(filePath);
                }
                Frame.userDirectory = filePath;
            } else {
                JOptionPane.showMessageDialog(null, "The directory you entered does not exist.");
            }
        }
    }

    private void setMakeName() {
        if (makeNameInput.getText() == null) {
            JOptionPane.showMessageDialog(null, "Please enter a replacement makefile executable name.");
        } else {
            Frame.makeExecutableName = makeNameInput.getText();
            System.out.println(makeNameInput.getText());
        }
    }

    private void saveDirectoryPreferences() {
        if (filePaths.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no new file paths to be saved.");
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("preferences/directories.txt", true))) {
                for (String directory : filePaths) {
                    if (!storedDirectories.contains(directory)) {
                        bw.write(directory);
                        bw.write("\n");
                    } else {
                        JOptionPane.showMessageDialog(null, "Warning: " + directory + " is already a saved directory.");
                    }
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDirectoryPreferences() {
        File f = new File("preferences/directories.txt");
        String line;
        storedDirectories = new ArrayList<>();

        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                while ((line = br.readLine()) != null) {
                    storedDirectories.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userDirectories = storedDirectories.toArray(new String[0]);
    }

    private void saveAndClose() {
        saveDirectoryPreferences();
        dispose();
    }
}
