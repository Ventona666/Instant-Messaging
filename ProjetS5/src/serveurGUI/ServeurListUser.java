package serveurGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NavigableSet;
import java.util.TreeSet;

import javax.swing.*;

import object.CampusUser;
import object.Group;
import object.User;
import server.Server;

public class ServeurListUser {
    private Group group;
    private Server server;
    // Components
    private JFrame frame;
    private JScrollPane scrollPane;
    private JButton ajouterButton;

    public ServeurListUser(Server server, Group group){
        this.server = server;
        this.group = group;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void buildFrame() {
        frame = new JFrame("Ajouter un utilisateur dans le groupe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        setframe();
        frame.setVisible(true);

    }

    private void setframe() {
        int i = 0;
        try{
            NavigableSet<User> userTreeSet = server.getAllUser();
            User[] listUser = new User[userTreeSet.size()];

            for(User user : userTreeSet){
                listUser[i] = user;
                i++;
            }

            JList<User> userJList = new JList<>(listUser);
            scrollPane = new JScrollPane(userJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            ajouterButton = new JButton("Ajouter");
            ajouterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    User userSelected = userJList.getSelectedValue();
                    try{
                        server.addToGroup(userSelected, group);
                        System.err.println("L'utilisateur a bien été ajouté au groupe");
                    } catch (Exception exception){
                        System.err.println("Erreur lors de l'ajout de l'utilisateur : " + exception);
                        exception.printStackTrace();
                    }

                }
            });

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(ajouterButton, BorderLayout.SOUTH);

        } catch (Exception e){
            System.err.println("Erreur lors de la récupération des utilisateurs dans la base de donnée : " + e);
            e.printStackTrace();
        }
    }

}

