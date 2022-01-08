package client;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import object.Group;
import object.User;

public class UserInfoGUI {

    private User user;

    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JPanel bottomPanel;
    private JScrollPane groupScrollPane;

    public UserInfoGUI(User user) {
        this.user = user;
    }

    private void buildComponents() {
        buildGroupScrollPane();
        buildCenterPanel();
    }

    private void setMainFrame() {
        frame = new JFrame("Mes informations");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 700, 450);
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

    private void buildGroupScrollPane() {
        Group[] listGroup = new Group[user.getGroupSet().size()];
        listGroup = user.getGroupSet().toArray(listGroup);
        JList<Group> groupJList = new JList<>(listGroup);
        groupScrollPane = new JScrollPane(groupJList);
    }

    private void buildCenterPanel() {

        bottomPanel = new JPanel();
        bottomPanel.setForeground(Color.BLUE);
        bottomPanel.setOpaque(false);

        JLabel lastNameLabel = new JLabel("Nom");

        JLabel firstNameLabel = new JLabel("Prénom");

        JLabel lastNameUserLabel = new JLabel(user.getLastName());
        lastNameUserLabel.setOpaque(true);
        lastNameUserLabel.setBackground(Color.WHITE);

        JLabel firstNameUserLabel = new JLabel(user.getFirstName());
        firstNameUserLabel.setOpaque(true);
        firstNameUserLabel.setBackground(Color.WHITE);

        JLabel myGroupsLabel = new JLabel("Groupes auxquels j'appartiens");

        GroupLayout groupLayoutBottomPanel = new GroupLayout(bottomPanel);
        groupLayoutBottomPanel.setHorizontalGroup(groupLayoutBottomPanel.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayoutBottomPanel.createSequentialGroup()
                        .addGroup(groupLayoutBottomPanel.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayoutBottomPanel.createSequentialGroup()
                                        .addGap(232)
                                        .addGroup(groupLayoutBottomPanel
                                                .createParallelGroup(
                                                        Alignment.TRAILING)
                                                .addComponent(firstNameLabel)
                                                .addComponent(lastNameLabel))
                                        .addGap(28)
                                        .addGroup(groupLayoutBottomPanel
                                                .createParallelGroup(
                                                        Alignment.LEADING)
                                                .addComponent(lastNameUserLabel,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        98,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(firstNameUserLabel)))
                                .addGroup(groupLayoutBottomPanel.createSequentialGroup()
                                        .addGap(148)
                                        .addComponent(myGroupsLabel)))
                        .addContainerGap(252, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING,
                        groupLayoutBottomPanel.createSequentialGroup().addContainerGap(249,
                                        Short.MAX_VALUE)
                                .addComponent(groupScrollPane,
                                        GroupLayout.PREFERRED_SIZE, 168,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(229)));
        groupLayoutBottomPanel.setVerticalGroup(
                groupLayoutBottomPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayoutBottomPanel.createSequentialGroup().addGap(44)
                                .addGroup(groupLayoutBottomPanel
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lastNameLabel)
                                        .addComponent(lastNameUserLabel))
                                .addGap(18)
                                .addGroup(groupLayoutBottomPanel
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(firstNameLabel)
                                        .addComponent(firstNameUserLabel))
                                .addGap(65).addComponent(myGroupsLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(groupScrollPane, GroupLayout.DEFAULT_SIZE,
                                        159, Short.MAX_VALUE)
                                .addGap(18)));
        bottomPanel.setLayout(groupLayoutBottomPanel);
        mainPane.add(bottomPanel, BorderLayout.CENTER);

    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}