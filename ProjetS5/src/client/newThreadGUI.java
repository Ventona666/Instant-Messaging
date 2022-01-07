package client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import object.Group;
import object.User;

public class newThreadGUI {

    private User user;

    // Components

    private JPanel mainPane;
    private JFrame frame;
    private JLabel threadLabel;
    private JTextField threadField;
    private JLabel groupLabel;
    private JComboBox<Group> groupComboBox;
    private JLabel messageLabel;
    private JTextArea messageTextArea;
    private JScrollPane messagePane;
    private JButton newButton;
    private JButton cancelButton;

    public newThreadGUI(User user) {
        this.user = user;
    }

    private void buildComponents() {
        buildFields();
        buildButtons();
        buildCenterPanel();
    }

    private void setMainFrame() {
        frame = new JFrame("Nouvelle discussion");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 100, 550, 515);
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

    private void buildFields() {
        threadLabel = new JLabel("Sujet");
        threadLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupLabel = new JLabel("Groupe");
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel = new JLabel("Message");
        threadField = new JTextField();
        threadField.setColumns(20);
        // TODO : LISTE DES GROUPES DANS LA COMBO BOX
        groupComboBox = new JComboBox<>();
        messageTextArea = new JTextArea();
        messageTextArea.setLineWrap(true);
        messagePane = new JScrollPane(messageTextArea);
    }

    private void buildButtons() {
        newButton = new JButton("Envoyer");
        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO NEW THREAD
            }
        });
    }

    private void buildCenterPanel() {

    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}
