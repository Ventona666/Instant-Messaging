package client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import object.Group;
import object.User;

public class NewThreadGUI {

    private User user;

    // Components

    private JPanel mainPane;
    private JFrame frame;
    private JLabel threadLabel;
    private JTextField threadField;
    private JLabel groupLabel;
    private JComboBox<Group> groupComboBox;
    private JLabel messageLabel;
    private JTextArea messageTextArea;
    private JScrollPane messagePane;
    private JButton newButton;
    private JButton cancelButton;
    private JPanel centerPanel;

    public NewThreadGUI(User user) {
        this.user = user;
    }

    private void buildComponents() {
        buildFields();
        buildButtons();
        buildCenterPanel();
    }

    private void setMainFrame() {
        frame = new JFrame("Nouvelle discussion");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 550, 515);
        frame.setResizable(false);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPane.setLayout(new BorderLayout(0, 0));
        buildComponents();
    }

    private void buildFields() {
        threadLabel = new JLabel("Sujet");
        threadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupLabel = new JLabel("Groupe");
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel = new JLabel("Message");
        threadField = new JTextField();
        threadField.setColumns(20);
        // TODO : LISTE DES GROUPES DANS LA COMBO BOX
        groupComboBox = new JComboBox<>();
        messageTextArea = new JTextArea();
        messageTextArea.setLineWrap(true);
        messagePane = new JScrollPane(messageTextArea);
    }

    private void buildButtons() {
        newButton = new JButton("Envoyer");
        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO NEW THREAD
            }
        });
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();
        GroupLayout groupLayoutCenter = new GroupLayout(centerPanel);
        groupLayoutCenter.setHorizontalGroup(
                groupLayoutCenter.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayoutCenter.createSequentialGroup()
                                .addGroup(groupLayoutCenter.createParallelGroup(Alignment.LEADING)
                                        .addGroup(Alignment.TRAILING, groupLayoutCenter.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(newButton)
                                                .addGap(29)
                                                .addComponent(cancelButton)
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addGroup(groupLayoutCenter.createSequentialGroup()
                                                .addGap(64)
                                                .addGroup(groupLayoutCenter.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(messagePane, 0, 0, Short.MAX_VALUE)
                                                        .addGroup(groupLayoutCenter.createSequentialGroup()
                                                                .addGroup(
                                                                        groupLayoutCenter
                                                                                .createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(messageLabel)
                                                                                .addComponent(threadLabel)
                                                                                .addComponent(groupLabel))
                                                                .addGap(18)
                                                                .addGroup(groupLayoutCenter
                                                                        .createParallelGroup(Alignment.LEADING, false)
                                                                        .addComponent(groupComboBox, 0,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(threadField,
                                                                                GroupLayout.DEFAULT_SIZE, 252,
                                                                                Short.MAX_VALUE))))))
                                .addContainerGap(87, Short.MAX_VALUE)));
        groupLayoutCenter.setVerticalGroup(
                groupLayoutCenter.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayoutCenter.createSequentialGroup()
                                .addGap(28)
                                .addGroup(groupLayoutCenter.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(threadLabel)
                                        .addComponent(threadField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayoutCenter.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(groupLabel)
                                        .addComponent(groupComboBox, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(messageLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(messagePane, GroupLayout.PREFERRED_SIZE, 263,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(groupLayoutCenter.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton)
                                        .addComponent(newButton))
                                .addContainerGap()));
        centerPanel.setLayout(groupLayoutCenter);
        mainPane.add(centerPanel, BorderLayout.CENTER);
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}
