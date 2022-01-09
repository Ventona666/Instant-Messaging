package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import object.User;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class UserGUI extends JFrame {

    private User currentUser;

    private JFrame frame;
    private JPanel mainPane;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTree userTree;
    private JPanel rightPanel;
    private JPanel leftTopPanel;

    private void buildComponents() {
        buildTreePanel();
        buildLeftTopPanel();
        buildLeftPanel();
        buildRightPanel();
        buildFirstName();
        buildLastName();
        buildGroup();
        buildType();
        buildUpdateButton();
        buildSplitPanel();
    }

    private void setMainFrame() {
        frame = new JFrame("Nouvel utilisateur");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 650, 460);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPane.setLayout(new BorderLayout(0, 0));
        buildComponents();
    }

    private void buildSplitPanel() {
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPane.add(splitPane, BorderLayout.CENTER);
    }

    private void buildTreePanel() {
        userTree = new JTree();
    }

    private void buildLeftTopPanel() {
        leftTopPanel = new JPanel();
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(0, 0));
        leftPanel.add(userTree, BorderLayout.CENTER);
        leftPanel.add(leftTopPanel, BorderLayout.NORTH);
    }

    private void buildRightPanel() {
        rightPanel = new JPanel();
        GridBagLayout gblrightPanel = new GridBagLayout();
        gblrightPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gblrightPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gblrightPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gblrightPanel.rowWeights = new double[] { 1., 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
        rightPanel.setLayout(gblrightPanel);
    }

    private void buildFirstName() {
        JLabel firstNameLabel = new JLabel("Prénom");
        GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
        gbc_firstNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstNameLabel.gridx = 1;
        gbc_firstNameLabel.gridy = 1;
        rightPanel.add(firstNameLabel, gbc_firstNameLabel);

        firstNameField = new JTextField();
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.insets = new Insets(0, 0, 5, 0);
        gbc_firstNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstNameField.gridx = 3;
        gbc_firstNameField.gridy = 1;
        rightPanel.add(firstNameField, gbc_firstNameField);
        firstNameField.setColumns(10);
    }

    public void buildLastName() {
        JLabel lastNameLabel = new JLabel("Nom");
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lastNameLabel.gridx = 1;
        gbc_lastNameLabel.gridy = 3;
        rightPanel.add(lastNameLabel, gbc_lastNameLabel);

        lastNameField = new JTextField();
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = new Insets(0, 0, 5, 0);
        gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_lastNameField.gridx = 3;
        gbc_lastNameField.gridy = 3;
        rightPanel.add(lastNameField, gbc_lastNameField);
        lastNameField.setColumns(10);
    }

    private void buildType() {
        JLabel typeLabel = new JLabel("Type");
        GridBagConstraints gbc_typeLabel = new GridBagConstraints();
        gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
        gbc_typeLabel.gridx = 1;
        gbc_typeLabel.gridy = 5;
        rightPanel.add(typeLabel, gbc_typeLabel);

        JComboBox<String> typeComboBox = new JComboBox();
        GridBagConstraints gbc_typeComboBox = new GridBagConstraints();
        gbc_typeComboBox.insets = new Insets(0, 0, 5, 0);
        gbc_typeComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_typeComboBox.gridx = 3;
        gbc_typeComboBox.gridy = 5;
        rightPanel.add(typeComboBox, gbc_typeComboBox);
    }

    public void buildGroup() {
        JLabel groupLabel = new JLabel("Groupe");
        GridBagConstraints gbc_groupLabel = new GridBagConstraints();
        gbc_groupLabel.insets = new Insets(0, 0, 5, 5);
        gbc_groupLabel.gridx = 1;
        gbc_groupLabel.gridy = 7;
        rightPanel.add(groupLabel, gbc_groupLabel);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 3;
        gbc_scrollPane.gridy = 7;
        rightPanel.add(scrollPane, gbc_scrollPane);
    }

    private void buildUpdateButton() {
        JButton updateButton = new JButton("Modifier");
        GridBagConstraints gbc_updateButton = new GridBagConstraints();
        gbc_updateButton.gridx = 3;
        gbc_updateButton.gridy = 8;
        rightPanel.add(updateButton, gbc_updateButton);
    }

}
