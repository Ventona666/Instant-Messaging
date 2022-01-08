package client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import object.Thread;
import object.User;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class ThreadMemberGUI extends JFrame {

    private Thread thread;

    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JTable groupScroll;
    private JPanel topPanel;
    private JPanel centerPanel;

    public ThreadMemberGUI(Thread thread) {
        this.thread = thread;
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
        User[] listUser = new User[thread.getParticipantsSet().size()];
        listUser = thread.getParticipantsSet().toArray(listUser);
        JList<User> userJList = new JList<>(listUser);
        JScrollPane scrollPane = new JScrollPane(userJList);
        centerPanel.add(scrollPane);
        mainPane.add(centerPanel, BorderLayout.CENTER);
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}
