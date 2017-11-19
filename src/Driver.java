/**
 *
 */
//package com.java.gui2;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author T. McClintock
 */
public class Driver {

    public static void main(String[] args) {
        String test_generator = "Unit Test Generator";
        Frame frame = new Frame(test_generator);
        /**
         * Changes the default theme of JFileChooser
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
