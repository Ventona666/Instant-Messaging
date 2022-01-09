package clientGUI;

import client.Client;
import object.User;
import server.ConnexionRefusedException;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class LogInGUI {
    private Client client;
    // Components

    JFrame frame;
    JPanel mainPane;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel pwdLabel;
    JPasswordField pwdField;
    JButton connectButton;

    public LogInGUI(Client client){
        this.client = client;
    }

    private void buildComponents() {
        buildButton();
        buildUsername();
        buildPwd();
    }

    private void setMainFrame() {
        frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBounds(100, 100, 506, 317);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gBMainPane = new GridBagLayout();
        gBMainPane.columnWidths = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20 };
        gBMainPane.rowHeights = new int[] { 20, 20, 20, 20, 20, 20, 20, 20, 20 };
        gBMainPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0,
                Double.MIN_VALUE };
        gBMainPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        mainPane.setLayout(gBMainPane);
        buildComponents();
    }

    private void buildUsername() {
        usernameLabel = new JLabel("Identifiant :");
        GridBagConstraints gbcIdLabel = new GridBagConstraints();
        gbcIdLabel.insets = new Insets(0, 0, 5, 5);
        gbcIdLabel.gridx = 3;
        gbcIdLabel.gridy = 3;
        mainPane.add(usernameLabel, gbcIdLabel);
        usernameField = new JTextField();
        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.gridwidth = 4;
        gbcTextField.fill = GridBagConstraints.BOTH;
        gbcTextField.insets = new Insets(0, 0, 5, 5);
        gbcTextField.gridx = 4;
        gbcTextField.gridy = 3;
        usernameField.setColumns(20);
        mainPane.add(usernameField, gbcTextField);
    }

    private void buildPwd() {
        pwdLabel = new JLabel("Mot de passe :");
        GridBagConstraints gbcPwdLabel = new GridBagConstraints();
        gbcPwdLabel.insets = new Insets(0, 0, 5, 5);
        gbcPwdLabel.gridx = 3;
        gbcPwdLabel.gridy = 5;
        mainPane.add(pwdLabel, gbcPwdLabel);
        pwdField = new JPasswordField();
        pwdField.setColumns(20);
        GridBagConstraints gbcPasswordField = new GridBagConstraints();
        gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
        gbcPasswordField.gridwidth = 4;
        gbcPasswordField.insets = new Insets(0, 0, 5, 5);
        gbcPasswordField.gridx = 4;
        gbcPasswordField.gridy = 5;
        mainPane.add(pwdField, gbcPasswordField);
    }

    private void register(){
        User user = client.getUser();
        // Enregistrement comme client activement connecté auprès du serveur
        try{
            user.getStubServer().register(user);
            System.err.println("Enregistrement auprès du serveur réussi");
        }catch (Exception e){
            System.err.println("Erreur lors de l'enregistrement auprès du serveur : " + e);
            e.printStackTrace();
        }
    }
    private void buildButton() {

        connectButton = new JButton("Se connecter");
        GridBagConstraints gbcConnectButton = new GridBagConstraints();
        gbcConnectButton.insets = new Insets(0, 0, 0, 5);
        gbcConnectButton.gridx = 5;
        gbcConnectButton.gridy = 7;
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(pwdField.getPassword());
                try {
                    client.logIn(username, password);
                    register();
                    frame.dispose();
                } catch (ConnexionRefusedException connexionRefusedException){
                    //TODO afficher un message graphique
                    System.err.println("Username et/ou mot de passe incorrect : " + connexionRefusedException);
                    connexionRefusedException.printStackTrace();
                }
            }
        });
        mainPane.add(connectButton, gbcConnectButton);
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}