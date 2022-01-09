package serveurGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import object.CampusUser;
import object.Group;
import object.User;
import server.Server;

public class AddUsersToGroup {

    private Server server;
    private NewGroupGUI newGroupGUI;
    private Group group;
    // Components
    private JFrame frame;
    private JScrollPane scrollPane;
    private JButton validerButton;
    private User[] listSelectedUser;

    public AddUsersToGroup(Server server, NewGroupGUI newGroupGUI){
        this.server = server;
        this.newGroupGUI = newGroupGUI;
    }

    public void buildFrame() {
        frame = new JFrame("Ajouter les utlisateurs dans le nouveau groupe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        setFrame();
        frame.setVisible(true);
    }

    public User[] getListSelectedUser() {
        return listSelectedUser;
    }

    private void setFrame() {
        JCheckBox temp;
        User[] listUser;

        Map<User, JCheckBox> jCheckBoxUserMap = new TreeMap<>();
        int size = 0;
        Box box = Box.createVerticalBox();
        try{
            size = server.getAllUser().size();
            listUser = new User[size];
            listSelectedUser = new User[size];
            listUser = server.getAllUser().toArray(listUser);
            for (int i = 0; i < size; i++) {
                temp = new JCheckBox(listUser[i].toString());
                jCheckBoxUserMap.put(listUser[i], temp);
                box.add(temp);
            }



        } catch (Exception e){
            e.printStackTrace();
        }



        scrollPane = new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        validerButton = new JButton("Ajouter");

        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                for(User user : jCheckBoxUserMap.keySet()){
                    if(jCheckBoxUserMap.get(user).isSelected()){
                        listSelectedUser[i] = user;
                        i++;
                    }
                }
                Sys.out
                newGroupGUI.setListSelectedUser(listSelectedUser);
                frame.dispose();
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(validerButton, BorderLayout.SOUTH);

    }
}