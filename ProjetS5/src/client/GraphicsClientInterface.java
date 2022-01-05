package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import object.Group;
import object.Message;
import object.Thread;
import object.User;
import object.CampusUser;

public class GraphicsClientInterface {

    private User user;
    private String name;

    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JTree threadTree;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomRightPanel;
    private JPanel topPanel;
    private JScrollPane scrollPane;

    // private Color blue = new Color(16, 79, 85, 255);

    public GraphicsClientInterface(User user) {
        this.user = user;
        name = user.getFirstName() + " " + user.getLastName();
        build();
    }

    private void buildComponents() {
        buildTopPanel();
        buildTree();
        buildScrollPane();
        buildLeftPanel();
        buildBottomRightPanel();
        buildRightPanel();
        buildSplitPane();
    }

    private void setMainFrame() {
        frame = new JFrame("Poucavor 2000");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPane.setLayout(new BorderLayout());
        buildComponents();
    }

    private void buildSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setDividerLocation(350);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPane.add(splitPane, BorderLayout.CENTER);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(bottomRightPanel, BorderLayout.SOUTH);
        JTable table = new JTable();
        rightPanel.add(table, BorderLayout.NORTH);
    }

    private void buildBottomRightPanel() {
        bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        bottomRightPanel.add(textArea, BorderLayout.CENTER);
        JButton btnNewButton = new JButton("Envoyer");
        bottomRightPanel.add(btnNewButton, BorderLayout.EAST);
        JSeparator separator = new JSeparator();
        bottomRightPanel.add(separator, BorderLayout.NORTH);

    }

    private void buildTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel firstName = new JLabel(user.getFirstName());
        topPanel.add(firstName);
        JLabel lastName = new JLabel(user.getLastName());
        topPanel.add(lastName);
        JPanel imgUserInfo = new JPanel();
        topPanel.add(imgUserInfo);
        JPanel imgNewThread = new JPanel();
        topPanel.add(imgNewThread);
        JPanel imgSignOut = new JPanel();
        topPanel.add(imgSignOut);
    }

    private void buildScrollPane() {
        scrollPane = new JScrollPane(threadTree);
    }

    private void buildTree() {
        DefaultMutableTreeNode groups = new DefaultMutableTreeNode("Groupes");
        TreeMap<Group, TreeSet<Thread>> threadList = user.getAllThread();
        for (Group group : threadList.keySet()) {
            DefaultMutableTreeNode groupTemp = new DefaultMutableTreeNode(group.getName());
            groups.add(groupTemp);
            for (Thread thread : threadList.get(group)) {
                DefaultMutableTreeNode threadTemp = new DefaultMutableTreeNode(thread.getTitle());
                groupTemp.add(threadTemp);
            }
        }
        threadTree = new JTree(groups);
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CampusUser user = new CampusUser(1, "Sabrina", "Sikder");
        CampusUser user2 = new CampusUser(2, "Hugo", "Deleye");
        Group student = new Group(0, "Étudiant");
        Group grp1 = new Group(1, "TDA1");
        Group grp2 = new Group(2, "TDA2");
        Group grp3 = new Group(3, "TDA3");
        Group grp4 = new Group(4, "TDA4");
        Group grp5 = new Group(5, "TDA5");
        user.addGroup(grp1);
        grp1.addUser(user);
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
        new GraphicsClientInterface(user);
    }
}