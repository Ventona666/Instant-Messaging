package serveurGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
    private Group group;
    // Components
    private JFrame frame;
    private JScrollPane scrollPane;
    private JButton validerButton;

    private void buildFrame() {
        frame = new JFrame("Ajouter les utlisateurs dans le nouveau groupe");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 500));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        setFrame();
        frame.setVisible(true);

    }

    private void setFrame() {
        User[] listUser = new User[2];
        Box box = Box.createVerticalBox();
        // int size = server.getAllUser().size();
        // User[] listUser = new User[server.getAllUser().size()];
        // listUser = server.getAllUser().toArray(listUser);
        for (int i = 0; i < size; i++) {
            JCheckBox temp = new JCheckBox(listUser[i].toString());
            box.add(temp);
        }
        listUser[0] = new CampusUser(1, "Sabrina", "Sikder", "caca"); // a enlever
        listUser[1] = new CampusUser(2, "Hugo", "Deleye", "zizi"); // a enlever

        JCheckBox a = new JCheckBox(listUser[0].toString()); // a enlever
        box.add(a); // a enlever
        scrollPane = new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        validerButton = new JButton("Ajouter");

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(validerButton, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        AddUsersToGroup l = new AddUsersToGroup();
        l.buildFrame();

    }
}