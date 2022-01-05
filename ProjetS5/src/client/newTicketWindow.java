package client;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class newTicketWindow {

	private JFrame frame;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel titlePanel;
	private JPanel groupPanel;
	private JPanel buttonPanel;

	private JButton valider;
	private JButton annuler;

	private JTextField titleField;
	private JTextArea firstMessageArea;
	private JComboBox<String> groupComboBox;

	private JLabel ticketTitle;
	private JLabel groupeLabel;
	private JLabel firstMessageLabel;

	private void buildComponents() {
		frame = new JFrame("Création de ticket ");
		topPanel = new JPanel();
		mainPanel = new JPanel();

		valider = new JButton("Valider");
		annuler = new JButton("Annuler");
		ticketTitle = new JLabel("Titre du ticket ");
		groupeLabel = new JLabel("Groupe");
		firstMessageLabel = new JLabel("Premier message ");
		titleField = new JTextField(15);
		groupComboBox = new JComboBox<>();
		titlePanel = new JPanel();
		groupPanel = new JPanel();
		buttonPanel = new JPanel();
		firstMessageArea = new JTextArea(10, 8);
		firstMessageArea.setLineWrap(true);

	}

	private void setTopPanel() {
		titlePanel.add(ticketTitle);
		titlePanel.add(titleField);
		groupPanel.add(groupeLabel);
		groupPanel.add(groupComboBox);
		buttonPanel.add(valider);
		buttonPanel.add(annuler);

		GroupLayout layout = new GroupLayout(topPanel);
		topPanel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
		horizontalGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)

				.addComponent(titlePanel)

				.addComponent(groupPanel).addComponent(firstMessageLabel).addComponent(firstMessageArea)
				.addComponent(buttonPanel));
		layout.setHorizontalGroup(horizontalGroup);

		GroupLayout.SequentialGroup verticalGroup = layout.createSequentialGroup();
		verticalGroup.addGroup(layout.createParallelGroup().addComponent(titlePanel));

		verticalGroup.addGroup(layout.createParallelGroup().addComponent(groupPanel));
		verticalGroup.addGroup(layout.createParallelGroup().addComponent(firstMessageLabel));
		verticalGroup.addGroup(layout.createParallelGroup().addComponent(firstMessageArea));
		verticalGroup.addGroup(layout.createParallelGroup().addComponent(buttonPanel));

		layout.setVerticalGroup(verticalGroup);

	}

	private void setMainPanel() {
		setTopPanel();
		// mainPanel.setBackground(Color.blue);
		mainPanel.add(topPanel);
	}

	private void setMainFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setMinimumSize(new Dimension(1000, 600));
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		setMainPanel();
		frame.add(mainPanel);

	}

	private void setComboBox() {
		groupComboBox = new JComboBox<>();
	}
	// TODO

	public void build() {
		buildComponents();
		setMainFrame();
		// setMainPanel();
		frame.setContentPane(mainPanel);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		newTicketWindow l = new newTicketWindow();
		l.build();
	}

}
