package clientGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Client;
import object.Group;
import object.Thread;
import object.User;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class ThreadMemberGUI extends JFrame {

    private Thread thread;
    private Client client;
    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JPanel topPanel;
    private JPanel centerPanel;

    public ThreadMemberGUI(Thread thread, Client client) {
        this.thread = thread;
        this.client = client;
    }

    private void buildComponents() {
        buildTopPanel();
        buildCenterPanel();
    }

    private void setMainFrame() {
        frame = new JFrame("Participants de la discussion");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 500, 350);
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

    private void buildTopPanel() {
        topPanel = new JPanel();
        JLabel groupLabel = new JLabel("Participants :");
        topPanel.add(groupLabel);
        mainPane.add(topPanel, BorderLayout.NORTH);
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel();
        long idGroup = thread.getIdGroup();
        try{
            Group group = client.getUser().getStubServer().getGroup(idGroup);
            System.err.println("Groupe obtenu avec succès");

            User[] listUser = new User[group.getNumberOfMember() + 1];
            group.getUserSet().add(client.getUser());
            listUser = group.getUserSet().toArray(listUser);
            JList<User> userJList = new JList<>(listUser);
            JScrollPane scrollPane = new JScrollPane(userJList);
            centerPanel.add(scrollPane);
            mainPane.add(centerPanel, BorderLayout.CENTER);
        } catch (Exception e){
            System.err.println("Erreur lors de l'obtention du groupe : " + e);
            e.printStackTrace();
        }
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}