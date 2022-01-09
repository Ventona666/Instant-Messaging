package serveurGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.NavigableSet;

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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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

    public ServeurGestionGroupes(Server server) {
        this.server = server;
        setTest();
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

        try{
            NavigableSet<Group> groupNavigableSet = server.getAllGroup();
            for (Group group : groupNavigableSet) {
                DefaultMutableTreeNode groupTemp =  new DefaultMutableTreeNode(group);
                groups.add(groupTemp);
                currentGroup = group;
            }

            groupTree = new JTree(groups);

            groupTree.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) groupTree.getLastSelectedPathComponent();
                    if (node == null || !node.isLeaf())
                        return;
                    currentGroup = (Group) node.getUserObject();
                    buildRightPanel();
                    buildBottomRightPanel();
                    buildTopRightPanel();
                    splitPane.setRightComponent(rightPanel);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


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


        choisirUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServeurListUser serveurListUser = new ServeurListUser(server, currentGroup);
                serveurListUser.buildFrame();
            }
        });
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
    }
}
