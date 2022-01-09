package serveurGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import object.CampusUser;
import object.Group;
import object.Message;
import object.Thread;
import object.User;
import server.Server;

public class ServeurGestionGroupes {
    private Server server;
    private Group currentGroup = null;

    // Components
    private JFrame frame;
    private JSplitPane splitPane;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel topLeftPanel;
    private JPanel iconPanel;
    private JScrollPane scrollPane;
    private JTree groupTree;
    private JLabel groupListLabel;
    private JPanel topRightPanel;
    private JComboBox comboBox;

    public ServeurGestionGroupes() {
        setTest();
        build();
    }

    private void buildComponents() {
        buildTopLeftPanel();
        buildTree();
        buildScrollPane();
        buildLeftPanel();

        buildRightPanel();
        buildTopRightPanel();
        buildBottomRightPanel();
        buildSplitPane();
    }

    private void setMainFrame() {
        frame = new JFrame("Gestion des groupes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        buildMainPanel();
        frame.setContentPane(mainPanel);
    }

    private void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout());
        buildComponents();
    }

    private void buildSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setDividerLocation(350);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPanel.add(splitPane, BorderLayout.CENTER);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(topLeftPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

    }

    private void buildScrollPane() {
        scrollPane = new JScrollPane(groupTree);
    }

    private void buildTree() {
        DefaultMutableTreeNode groups = new DefaultMutableTreeNode("Groupes");
        /*
         * for (Group group : server.getAllGroup()) { DefaultMutableTreeNode groupTemp =
         * new DefaultMutableTreeNode(group.getName()); groups.add(groupTemp); }
         */
        groupTree = new JTree(groups);

        // TODO selection listener
    }

    private void buildTopLeftPanel() {
        topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        groupListLabel = new JLabel("Liste des groupes");
        topLeftPanel.add(groupListLabel);
        iconPanel = new JPanel();
        topLeftPanel.add(iconPanel);
    }

    private void buildTopRightPanel() {
        topRightPanel = new JPanel();
        JLabel groupName = new JLabel(currentGroup.getName());

        topRightPanel.setLayout(new BorderLayout(0, 0));
        groupName.setHorizontalAlignment(SwingConstants.CENTER);
        topRightPanel.add(groupName, BorderLayout.NORTH);
        rightPanel.add(topRightPanel, BorderLayout.NORTH);

    }

    private void buildBottomRightPanel() {
        JPanel bottomRightPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(bottomRightPanel);
        JLabel ajoutLabel = new JLabel("Ajouter un utilisateur dans ce groupe");
        ajoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel supprimerUserLabel = new JLabel("Supprimer un membre de ce groupe");
        JLabel selectionLabel = new JLabel("Veuillez sélectionner un membre ");
        JButton choisirUserButton = new JButton("Choisir un utilisateur");
        choisirUserButton.setHorizontalTextPosition(SwingConstants.CENTER);

        setComboBox();
        // JButton supprimerGroupeButton = new JButton("Supprimer ce groupe");
        JButton supprimerMembreButton = new JButton("Supprimer ce membre");

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
                        .createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(
                                        supprimerMembreButton, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
                        .addGap(121))
                .addGroup(Alignment.LEADING,
                        groupLayout.createSequentialGroup().addContainerGap().addComponent(selectionLabel).addGap(18)
                                .addComponent(comboBox, 0, 290, Short.MAX_VALUE).addGap(121))
                .addGroup(Alignment.LEADING,
                        groupLayout.createSequentialGroup()
                                .addComponent(ajoutLabel, GroupLayout.PREFERRED_SIZE, 511, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(90, Short.MAX_VALUE))
                .addGroup(Alignment.LEADING,
                        groupLayout.createSequentialGroup().addGap(189)
                                .addComponent(choisirUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addGap(281))
                .addGroup(Alignment.LEADING,
                        groupLayout
                                .createSequentialGroup().addGap(163).addComponent(supprimerUserLabel,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(262)));

        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addGap(24).addComponent(ajoutLabel).addGap(29)
                        .addComponent(choisirUserButton).addGap(47)
                        .addComponent(supprimerUserLabel, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE).addGap(31)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(selectionLabel))
                        .addGap(48).addComponent(supprimerMembreButton).addGap(120)));

        bottomRightPanel.setLayout(groupLayout);
        rightPanel.add(bottomRightPanel, BorderLayout.CENTER);
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

    private void setComboBox() {
        int size = currentGroup.getUserSet().size();
        User[] listeMembre = new User[size];
        listeMembre = currentGroup.getUserSet().toArray(listeMembre);
        comboBox = new JComboBox<>(listeMembre);

    }

    private void setActionListeners() {
        comboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void setTest() {
        CampusUser user = new CampusUser(1, "Sabrina", "Sikder", "caca");
        CampusUser user2 = new CampusUser(2, "Hugo", "Deleye", "zizi");
        Group student = new Group(0, "Étudiant");
        Group grp1 = new Group(1, "TDA1");
        Group grp2 = new Group(2, "TDA2");
        Group grp3 = new Group(3, "TDA3");
        Group grp4 = new Group(4, "TDA4");
        Group grp5 = new Group(5, "TDA5");
        user.addGroup(grp1);
        grp1.addUser(user);
        grp1.addUser(user2);
        user2.addGroup(grp1);
        user.addGroup(student);
        student.addUser(user);
        user2.addGroup(grp2);
        grp2.addUser(user2);
        user2.addGroup(student);
        student.addUser(user2);
        Thread th1 = new Thread(1, "J'ai des soucis avec Christine Sénac", user, grp4);
        grp4.addThread(th1);
        user.addThread(th1);
        th1.addMessage(new Message(1, new Date(), user, "Christine arrête pas de m'embeter", th1));
        Thread th2 = new Thread(2, "caca", user, student);
        student.addThread(th2);

        th2.addMessage(new Message(2, new Date(), user, "J'ai fait caca sur une des tables de l'U3-03 !", th2));

        Thread th3 = new Thread(3, "title title title title title title title title title", user2, grp1);
        grp1.addThread(th3);
        th3.addMessage(new Message(3, new Date(), user2,
                "Je ne savais pas quoi mettre comme titre du coup j'ai mis ça mais je suis pas sûr du titre", th3));

        currentGroup = grp1;
    }

    public static void main(String[] args) {
        ServeurGestionGroupes s = new ServeurGestionGroupes();

    }

}
