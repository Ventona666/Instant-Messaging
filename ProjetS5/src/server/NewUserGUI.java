package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import object.CampusUser;
import object.Group;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class NewUserGUI extends JFrame {
    private Server server;

    public NewUserGUI(Server server){
        this.server = server;
    }

    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JTextField firstNameField;
    private JLabel lastNameLabel;
    private JTextField lastNameField;
    private JLabel pwdLabel;
    private JTextField pwdField;
    private JLabel typeLabel;
    private JComboBox<String> typecomboBox;
    private JLabel groupLabel;
    private JScrollPane scrollPane;
    private JButton createButton;

    private void buildComponents() {
        buildFirstName();
        buildLastName();
        buildGroup();
        buildPwd();
        buildType();
        buildCreateButton();
    }

    private void setMainFrame() {
        frame = new JFrame("Nouvel utilisateur");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 450, 500);
        frame.setResizable(false);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gblmainPane = new GridBagLayout();
        gblmainPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gblmainPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gblmainPane.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gblmainPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        mainPane.setLayout(gblmainPane);
        buildComponents();
    }

    private void buildFirstName() {
        JLabel firstNameLabel = new JLabel("Prénom");
        GridBagConstraints gbcfirstNameLabel = new GridBagConstraints();
        gbcfirstNameLabel.insets = new Insets(0, 0, 5, 5);
        gbcfirstNameLabel.gridx = 1;
        gbcfirstNameLabel.gridy = 1;
        mainPane.add(firstNameLabel, gbcfirstNameLabel);

        firstNameField = new JTextField();
        GridBagConstraints gbcfirstNameField = new GridBagConstraints();
        gbcfirstNameField.insets = new Insets(0, 0, 5, 5);
        gbcfirstNameField.fill = GridBagConstraints.HORIZONTAL;
        gbcfirstNameField.gridx = 4;
        gbcfirstNameField.gridy = 1;
        mainPane.add(firstNameField, gbcfirstNameField);
        firstNameField.setColumns(5);
    }

    private void buildLastName() {
        lastNameLabel = new JLabel("Nom");
        GridBagConstraints gbclastNameLabel = new GridBagConstraints();
        gbclastNameLabel.insets = new Insets(0, 0, 5, 5);
        gbclastNameLabel.gridx = 1;
        gbclastNameLabel.gridy = 3;
        mainPane.add(lastNameLabel, gbclastNameLabel);

        lastNameField = new JTextField();
        GridBagConstraints gbclastNameField = new GridBagConstraints();
        gbclastNameField.insets = new Insets(0, 0, 5, 5);
        gbclastNameField.fill = GridBagConstraints.HORIZONTAL;
        gbclastNameField.gridx = 4;
        gbclastNameField.gridy = 3;
        mainPane.add(lastNameField, gbclastNameField);
        lastNameField.setColumns(10);
    }

    private void buildPwd() {
        pwdLabel = new JLabel("Mot de passe");
        GridBagConstraints gbcpwdLabel = new GridBagConstraints();
        gbcpwdLabel.insets = new Insets(0, 0, 5, 5);
        gbcpwdLabel.gridx = 1;
        gbcpwdLabel.gridy = 5;
        mainPane.add(pwdLabel, gbcpwdLabel);

        pwdField = new JTextField();
        GridBagConstraints gbcpwdField = new GridBagConstraints();
        gbcpwdField.insets = new Insets(0, 0, 5, 5);
        gbcpwdField.fill = GridBagConstraints.HORIZONTAL;
        gbcpwdField.gridx = 4;
        gbcpwdField.gridy = 5;
        mainPane.add(pwdField, gbcpwdField);
        pwdField.setColumns(10);
    }

    private void buildType() {
        typeLabel = new JLabel("Type");
        GridBagConstraints gbctypeLabel = new GridBagConstraints();
        gbctypeLabel.insets = new Insets(0, 0, 5, 5);
        gbctypeLabel.gridx = 1;
        gbctypeLabel.gridy = 7;
        mainPane.add(typeLabel, gbctypeLabel);

        String[] typeList = new String[2];
        typeList[0] = "Campus User";
        typeList[1] = "Staff User";
        typecomboBox = new JComboBox<>(typeList);
        GridBagConstraints gbctypecomboBox = new GridBagConstraints();
        gbctypecomboBox.insets = new Insets(0, 0, 5, 5);
        gbctypecomboBox.fill = GridBagConstraints.HORIZONTAL;
        gbctypecomboBox.gridx = 4;
        gbctypecomboBox.gridy = 7;
        mainPane.add(typecomboBox, gbctypecomboBox);
    }

    private void buildGroup() {
        groupLabel = new JLabel("Groupe");
        GridBagConstraints gbcgroupLabel = new GridBagConstraints();
        gbcgroupLabel.insets = new Insets(0, 0, 5, 5);
        gbcgroupLabel.gridx = 1;
        gbcgroupLabel.gridy = 9;
        mainPane.add(groupLabel, gbcgroupLabel);

        scrollPane = new JScrollPane();
        GridBagConstraints gbcscrollPane = new GridBagConstraints();
        gbcscrollPane.insets = new Insets(0, 0, 5, 5);
        gbcscrollPane.fill = GridBagConstraints.BOTH;
        gbcscrollPane.gridx = 4;
        gbcscrollPane.gridy = 9;
        mainPane.add(scrollPane, gbcscrollPane);
    }

    private void buildCreateButton() {
        createButton = new JButton("Créer");
        GridBagConstraints gbccreateButton = new GridBagConstraints();
        gbccreateButton.insets = new Insets(0, 0, 0, 5);
        gbccreateButton.gridx = 4;
        gbccreateButton.gridy = 11;
        mainPane.add(createButton, gbccreateButton);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username;
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String password = pwdLabel.getText();
                String userType = typeLabel.getText();
                if(userType.equals("Campus User")){
                    username = server.createAccount(firstName, lastName, password, false);
                }
                else{
                    username = server.createAccount(firstName, lastName, password, true);
                }
                // TODO afficher un message pour donner le username + confirmer la creation du compte
            }
        });

    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }
}
