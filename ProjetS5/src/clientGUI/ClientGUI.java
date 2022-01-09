package clientGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import client.Client;
import object.Group;
import object.Message;
import object.Thread;

public class ClientGUI {

    private Client client;
    private Thread currentThread = null;

    // Components

    private JFrame frame;
    private JPanel mainPane;
    private JTree threadTree;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomRightPanel;
    private JPanel topPanel;
    private JScrollPane scrollPane;
    private DefaultTableModel messageModel;
    private JTable messageTable;
    JPanel imgUserInfo;
    JPanel imgNewThread;
    JPanel imgLogOut;

    // private Color blue = new Color(16, 79, 85, 255);

    public ClientGUI(Client client) {
        this.client = client;
    }

    private void buildComponents() {
        buildTopPanel();
        buildTree();
        buildScrollPane();
        buildLeftPanel();
        buildBottomRightPanel();
        buildMessageTable();
        buildRightPanel();
        buildSplitPane();
    }

    private void setMainFrame() {
        frame = new JFrame("Poucavor 2000");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000, 600));
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        buildMainPane();
        frame.setContentPane(mainPane);
    }

    private void buildMainPane() {
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPane.setLayout(new BorderLayout());
        buildComponents();
    }

    private void buildSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        splitPane.setDividerLocation(350);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        mainPane.add(splitPane, BorderLayout.CENTER);
    }

    private void buildLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void buildRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(bottomRightPanel, BorderLayout.SOUTH);
        rightPanel.add(messageTable, BorderLayout.CENTER);
    }

    private void buildBottomRightPanel() {
        bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        bottomRightPanel.add(textArea, BorderLayout.CENTER);
        JButton btnNewButton = new JButton("Envoyer");
        bottomRightPanel.add(btnNewButton, BorderLayout.EAST);
        JSeparator separator = new JSeparator();
        bottomRightPanel.add(separator, BorderLayout.NORTH);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = textArea.getText();
                client.getUser().sendMessage(message, currentThread);
                textArea.setText(""); // Pour enlever le contenu du text area
            }
        });
    }

    private void buildTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel firstName = new JLabel(client.getUser().getFirstName());
        topPanel.add(firstName);
        JLabel lastName = new JLabel(client.getUser().getLastName());
        topPanel.add(lastName);
        imgUserInfo = new JPanel();
        topPanel.add(imgUserInfo);
        imgNewThread = new JPanel();
        topPanel.add(imgNewThread);
        imgLogOut = new JPanel();
        topPanel.add(imgLogOut);
    }

    private void buildScrollPane() {
        scrollPane = new JScrollPane(threadTree);
    }

    private void buildTree() {
        DefaultMutableTreeNode groups = new DefaultMutableTreeNode("Groupes");
        TreeMap<Group, TreeSet<Thread>> threadList = client.getUser().getAllThread();
        for (Group group : threadList.keySet()) {
            DefaultMutableTreeNode groupTemp = new DefaultMutableTreeNode(group.getName());
            if (!threadList.get(group).isEmpty())
                groups.add(groupTemp);
            for (Thread thread : threadList.get(group)) {
                DefaultMutableTreeNode threadTemp = new DefaultMutableTreeNode(thread.getTitle());
                threadTemp.setUserObject(thread);
                groupTemp.add(threadTemp);
            }
        }
        threadTree = new JTree(groups);
        threadTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) threadTree.getLastSelectedPathComponent();
                if (node == null || !node.isLeaf())
                    return;
                currentThread = (Thread) node.getUserObject();
                updateMessageTableNewThread();
            }
        });
    }

    private void buildIcons() {
        /*
        ImageIcon addIcon;
        JLabel iconLabel;
        addIcon = new ImageIcon(ClassLoader.getSystemResource("client/plus.png"));
        iconLabel = new JLabel(addIcon);
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new NewThreadGUI(client.getUser()).build();
            }
        });
        imgNewThread.add(iconLabel);
        addIcon = new ImageIcon(ClassLoader.getSystemResource("client/user.png"));
        iconLabel = new JLabel(addIcon);
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new UserInfoGUI(client.getUser()).build();
            }
        });
        imgUserInfo.add(iconLabel);
        addIcon = new ImageIcon(ClassLoader.getSystemResource("client/logout.png"));
        iconLabel = new JLabel(addIcon);
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                client.logOut();
            }
        });
        imgLogOut.add(iconLabel);*/
    }

    private void buildMessageTable() {
        String[] columns = { "", "" };
        messageModel = new DefaultTableModel(columns, 0);
        messageTable = new JTable(messageModel);


    }

    private void setPositionMessage(Message currentMessage) {
        Message messageRight = null;
        Message messageLeft = null;
        if (currentMessage.getId() == client.getUser().getId())
            messageLeft = currentMessage;
        else
            messageRight = currentMessage;
        messageModel.addRow(new Object[] { messageLeft, messageRight });
    }

    private void updateMessageTable() {
        List<Message> messageList = new ArrayList<>(currentThread.getMessageList());
        for (Iterator<Message> it = messageList.listIterator(messageModel.getRowCount()); it.hasNext();) {
            Message currentMessage = it.next();
            setPositionMessage(currentMessage);
        }
    }

    private void updateMessageTableNewThread() {
        for (int i = 0; i < messageModel.getRowCount(); i++) {
            messageModel.removeRow(i);
        }
        for (Message currentMessage : currentThread.getMessageList()) {
            setPositionMessage(currentMessage);
        }
    }

    private void buildMessageStyle() {

    }

    private void updateTree() {
        // CHANGER L'ARBRE SI NOUVEAU MESSAGE OU NOUVEAU THREAD
    }

    public void build() {
        setMainFrame();
        frame.setVisible(true);
    }

}