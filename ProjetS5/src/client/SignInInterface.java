package client;

import java.awt.*;
import javax.swing.*;

class SignInInterface extends JFrame{

    // Containers
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel panel;
    private JPanel panel2;

    // Labels
    private JLabel title;
    private JLabel idLabel;
    private JLabel pwdLabel;

// Buttons

    private JButton connect;

    // Fields
    private JTextField idField;
    private JPasswordField pwdField;

    // Colors
    private Color blue = new Color(16,79,85,255);


    public SignInInterface() {}

    private void buildComponents() {
        frame =  new JFrame("Connexion");
        mainPanel = new JPanel();
        panel = new JPanel();
        panel2 = new JPanel();

        title = new JLabel("nom application");
        idLabel = new JLabel("Identifiant :");
        pwdLabel = new JLabel("Mot de passe :");


        idField = new JTextField(20);
        pwdField = new JPasswordField(20);

        // Button
        connect = new JButton("Se connecter");

    }


    private void setMainPanelLayout() {
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
        horizontalGroup.addGroup(layout.createParallelGroup(
                        GroupLayout.Alignment.CENTER)
                .addComponent(title)
                .addComponent(panel)
                .addComponent(panel2)
                .addComponent(connect));
        layout.setHorizontalGroup(horizontalGroup);


        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        verticalGroup.addGroup(layout.createParallelGroup()
                .addComponent(title));
        verticalGroup.addGroup(layout.createParallelGroup()
                .addComponent(panel));
        verticalGroup.addGroup(layout.createParallelGroup()
                .addComponent(panel2));
        verticalGroup.addGroup(layout.createParallelGroup()
                .addComponent(connect));
        layout.setVerticalGroup(verticalGroup);
    }


    private void setPanelLayout() {

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        panel.setLayout(layout);
        panel.add(idLabel);
        panel.add(idField);
        FlowLayout layout2 = new FlowLayout(FlowLayout.CENTER);
        panel2.setLayout(layout2);
        panel2.add(pwdLabel);

        panel2.add(pwdField);

    }

    private void setMainFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setContentPane(mainPanel);
    }


    public void build() {
        buildComponents();
        setMainFrame();
        setMainPanelLayout();
        setPanelLayout();
        frame.setVisible(true);


    }
}