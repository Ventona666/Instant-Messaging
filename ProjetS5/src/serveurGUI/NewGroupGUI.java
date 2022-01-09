package serveurGUI;

import object.Group;
import object.User;
import server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NavigableSet;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class NewGroupGUI {
    private Server server;

    // Components
    private JFrame frame = new JFrame("Gestion des groupe");
    private JPanel mainPanel;
    private JPanel centerPanel;
    private JLabel userSelectedLabel;
    JList<User> selectedUser;
    JScrollPane scrollPane = new JScrollPane();
    private NavigableSet<User> listSelectedUser;

    public NewGroupGUI(Server server) {
        this.server = server;
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

    private void setMainFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(526, 529);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        buildMainPanel();
        frame.setContentPane(mainPanel);
    }

    public JFrame getFrame() {
        return frame;
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();

        JLabel groupNameLabel = new JLabel("Nom du groupe");

        JTextField groupNameTextField = new JTextField();
        groupNameTextField.setColumns(10);

        JLabel ajoutUserLabel = new JLabel("Ajouter les utilisateurs de ce groupe");
        ajoutUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton choisirUserButton = new JButton("Choisir les utilisateurs");

        userSelectedLabel = new JLabel("Utilisateurs s\u00E9lectionn\u00E9s");

        JButton validerButton = new JButton("Valider");

        GroupLayout groupLayout = new GroupLayout(centerPanel);
        groupLayout
                .setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup().addGap(22).addComponent(userSelectedLabel)
                                .addContainerGap(378, Short.MAX_VALUE))
                        .addGroup(
                                groupLayout.createSequentialGroup().addContainerGap(180, Short.MAX_VALUE)
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 185,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGap(152))
                        .addGroup(groupLayout.createSequentialGroup().addGap(50)
                                .addComponent(groupNameLabel, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(groupNameTextField, GroupLayout.PREFERRED_SIZE, 206,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(ajoutUserLabel, GroupLayout.PREFERRED_SIZE, 213,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(choisirUserButton, GroupLayout.PREFERRED_SIZE, 205,
                                                        GroupLayout.PREFERRED_SIZE)))
                                .addGap(140))
                        .addGroup(groupLayout.createSequentialGroup().addContainerGap(391, Short.MAX_VALUE)
                                .addComponent(validerButton, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addGap(27)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addGap(35)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(groupNameTextField, GroupLayout.PREFERRED_SIZE, 22,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(groupNameLabel))
                        .addGap(39).addComponent(ajoutUserLabel).addGap(18).addComponent(choisirUserButton).addGap(33)
                        .addComponent(userSelectedLabel).addGap(18)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(validerButton)
                        .addContainerGap(38, Short.MAX_VALUE)));
        centerPanel.setLayout(groupLayout);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = groupNameTextField.getText();
                Group group = new Group(name);
                for(User user : listSelectedUser){
                    group.addUser(user);
                }
                try{
                    server.createGroup(group);
                    System.err.println("Groupe ajouté avec succès dans la base de donnée");
                    JFrame jFrameConfirmation = new JFrame();
                    JOptionPane.showMessageDialog(jFrameConfirmation, "Le groupe " + name + " à bien été créé.");
                } catch (Exception exception){
                    System.err.println("Erreur lors de l'ajout dans la base de donnée : " + exception);
                    exception.printStackTrace();
                }
                frame.dispose();
            }
        });

        choisirUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddUsersToGroup addUsersToGroup = new AddUsersToGroup(server);
                addUsersToGroup.getFrame().addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent ev) {
                        listSelectedUser = addUsersToGroup.getListSelectedUser();
                        User[] userList = new User[listSelectedUser.size()];

                        int i = 0;
                        for(User user : listSelectedUser){
                            userList[i] = user;
                            i++;
                        }

                        selectedUser = new JList<>(userList);
                        JScrollPane scrollPaneTemp = new JScrollPane(selectedUser);
                        groupLayout.replace(scrollPane, scrollPaneTemp);
                        scrollPane = scrollPaneTemp;
                    }
                });

                addUsersToGroup.buildFrame();
            }
        });

    }

    private void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout());
        buildCenterPanel();
        JLabel titleLabel = new JLabel("Créer un nouveau groupe");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
    }
}

