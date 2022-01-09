package serveurGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import object.Group;
import object.User;
import server.Server;

public class AddUsersToGroup {

    private Server server;
    private Group group;
    // Components
    private JFrame frame = new JFrame("Ajouter les utlisateurs dans le nouveau groupe");
    private JScrollPane scrollPane;
    private JButton validerButton;
    private NavigableSet<User> listSelectedUser = new TreeSet<>();

    public AddUsersToGroup(Server server){
        this.server = server;
    }

    public void buildFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        setFrame();
        frame.setVisible(true);
    }

    public NavigableSet<User> getListSelectedUser() {
        return listSelectedUser;
    }

    public JFrame getFrame() {
        return frame;
    }

    private void setFrame() {
        JCheckBox temp;
        Map<User, JCheckBox> jCheckBoxUserMap = new TreeMap<>();
        Box box = Box.createVerticalBox();

        try{
            NavigableSet<User> userSet = server.getAllUser();
            for(User user : userSet){
                temp = new JCheckBox(user.toString());
                jCheckBoxUserMap.put(user, temp);
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
                for(User user : jCheckBoxUserMap.keySet()){
                    if(jCheckBoxUserMap.get(user).isSelected()){
                        listSelectedUser.add(user);
                    }
                }
                frame.dispose();
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(validerButton, BorderLayout.SOUTH);

    }
}