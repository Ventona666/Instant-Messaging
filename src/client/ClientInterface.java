package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class ClientInterface {
	
	
	// Containers
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel topLeftPanel;
	private JPanel welcomePanel;
	private JPanel buttonPanel;
	private JPanel bottomPanel;
	private JScrollPane ticketTreeScrollPane;
	private JScrollPane chatScrollPane;
	private JScrollPane textScrollPane;
	
	private JSplitPane splitPane ;
	
	
	// Buttons 
	private JButton newTicketButton;
	private JButton signOutButton;
	private JButton sendButton;
	private JButton myInfoButton;
	private JButton groupMember;
	
	
	// Text area
	private JTextArea textArea;
	
	// other components
	private JTree ticketTree;
	private JTable messageTable;
	private JLabel usernameLabel;
	
	private String username = "exemple";
	
	private Color blue = new Color(16,79,85,255);
	
	public ClientInterface() {
		// mettre en parametre un user pour qu'on puisse recupérer 
		// la liste de ses tickets etc...
		
		build();
	}
	
	private void buildComponents() {
		frame = new JFrame();
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		textArea= new JTextArea(2,55);
		sendButton = new JButton("Envoyer");
		ticketTree = new JTree();
		ticketTreeScrollPane = new JScrollPane();
		leftPanel = new JPanel();
		topLeftPanel = new JPanel();
		usernameLabel = new JLabel(username);
		myInfoButton = new JButton("my infos");
		rightPanel = new JPanel();
	//	splitPane = new JSplitPane();
	}
	
	private void setMainFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800,600));
		frame.setLocationRelativeTo(null);
	
		setMainPanel();
		frame.add(mainPanel);
		frame.setContentPane(mainPanel);
	}
	
	private void setMainPanel() {
	//	BorderLayout layout = new BorderLayout();
		//FlowLayout layout = new FlowLayout();
		setLeftPanel();
		setTopLeftPanel();
		setRightPanel();
		setBottomPanel();
		setSplitPane();
		
		mainPanel.add(splitPane);
		//mainPanel.setLayout(layout);
		
		//
	//	
		//
		
		//
		
		
		//mainPanel.add(bottomPanel,BorderLayout.PAGE_END);
		mainPanel.setBackground(blue);
		
	}
	
	private void setSplitPane () {
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);  
		
	}
	
	
	private void setBottomPanel() {
		FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
		bottomPanel.setLayout(layout);
	//	textArea.setSize(200, 100); // a modifier
		bottomPanel.add(textArea);
		bottomPanel.add(sendButton);
		//rightPanel.add(bottomPanel,BorderLayout.SOUTH);
	//	mainPanel.add(bottomPanel,BorderLayout.SOUTH);
		
		
		
	}
	private void setTopLeftPanel() {
		FlowLayout layout = new FlowLayout();
		topLeftPanel.setLayout(layout);
		topLeftPanel.add(usernameLabel,BorderLayout.NORTH);
		topLeftPanel.add(myInfoButton);
		
		leftPanel.add(topLeftPanel,BorderLayout.NORTH);
		
		
		
	}
	
	
	
	private void setLeftPanel() {
		BorderLayout layout = new BorderLayout();
		
		leftPanel.setLayout(layout);
		leftPanel.add(ticketTreeScrollPane,BorderLayout.SOUTH);
		ticketTreeScrollPane.add(ticketTree);
		//leftPanel.add(topLeftPanel,BorderLayout.NORTH);
		mainPanel.add(leftPanel,BorderLayout.WEST);
		
		
	}
	
	private void setRightPanel() {
		BorderLayout layout = new BorderLayout();
		rightPanel.setLayout(layout);
		setBottomPanel();
		rightPanel.add(bottomPanel,BorderLayout.SOUTH);
		mainPanel.add(rightPanel,BorderLayout.SOUTH);
		
		
		
	}
	
	
	private void build() {
		buildComponents();
		setMainFrame();
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		ClientInterface client = new ClientInterface();
	}
	
	
	
	
	
	
	

}
