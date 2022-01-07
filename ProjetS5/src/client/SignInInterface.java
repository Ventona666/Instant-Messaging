package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import object.User;

class SignInInterface extends JFrame {

    // Containers
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel panelId;
    private JPanel panelPwd;

    private JPanel centerPanel;
    private JPanel topPanel;

    // Labels
    private JLabel appName;
    private JLabel title;
    private JLabel idLabel;
    private JLabel pwdLabel;

    // Buttons

    private JButton connect;

    // Fields
    private JTextField idField;
    private JPasswordField pwdField;

    // Colors
    private Color blue = new Color(16, 79, 85, 255);
    // private Color blue1 = new Color();
    // private Color blue1 = new Color(ABORT);

    public SignInInterface() {
        build();

    }

    public ClientGUI Connection(User user) {
        ClientGUI app = new ClientGUI(user);
        frame.dispose();
        return app;
    }

    private void setCenterPanel() {

        mainPanel.setBackground(blue);
        topPanel.setBackground(blue);

        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

    }

    private void buildComponents() {
        frame = new JFrame("Connexion");
        mainPanel = new JPanel();
        panelId = new JPanel();
        panelPwd = new JPanel();

        centerPanel = new JPanel();
        topPanel = new JPanel();

        title = new JLabel("Identifiez - vous ");
        title.setForeground(Color.white);
        idLabel = new JLabel("Identifiant :     ");
        pwdLabel = new JLabel("Mot de passe :");

        idField = new JTextField(20);
        pwdField = new JPasswordField(20);

        // Button
        connect = new JButton("Se connecter");
        connect.setBackground(blue);
        connect.setForeground(Color.white);

    }

    private void setTopPanel() {
        appName = new JLabel("CC");
        appName.setForeground(Color.white);
        appName.setFont(new Font(Font.SANS_SERIF, 67, 78));
        appName.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(appName, BorderLayout.NORTH);
    }

    private void setMainPanelLayout() {
        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        centerPanel.setBackground(Color.darkGray);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
        horizontalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(title)
                .addComponent(panelId).addComponent(panelPwd).addComponent(connect));
        layout.setHorizontalGroup(horizontalGroup);

        GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
        verticalGroup.addGroup(layout.createParallelGroup().addComponent(title));

        verticalGroup.addGroup(layout.createParallelGroup().addComponent(panelId));
        verticalGroup.addGroup(layout.createParallelGroup().addComponent(panelPwd));
        verticalGroup.addGroup(layout.createParallelGroup().addComponent(connect));
        layout.setVerticalGroup(verticalGroup);
    }

    private void setPanelLayout() {

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        panelId.setLayout(layout);
        panelId.add(idLabel);
        panelId.add(idField);
        FlowLayout layout2 = new FlowLayout(FlowLayout.CENTER);
        panelPwd.setLayout(layout2);
        panelPwd.add(pwdLabel);

        panelPwd.add(pwdField);

    }

    private void setMainFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        frame.setContentPane(mainPanel);

        setCenterPanel();
    }

    public void build() {
        buildComponents();

        setMainFrame();

        setCenterPanel();
        setMainPanelLayout();

        setPanelLayout();
        setTopPanel();
        frame.setVisible(true);

    }

    private void addEventListeners() {
        addEventListenerConnectButton();
        // addEventListenerMainWindow();
    }

    private void addEventListenerConnectButton() {
        connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {

        SignInInterface page = new SignInInterface();

    }

}