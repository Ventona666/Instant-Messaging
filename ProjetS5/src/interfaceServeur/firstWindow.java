package interfaceServeur;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class firstWindow {

	// Container
	private JFrame frame;

	// Panels

	private JPanel mainPanel;

	// Buttons
	private JButton gestionGroupeButton;
	private JButton gestionUtilisateurButton;

	private void buildComponents() {
		frame = new JFrame("Interface serveur");
		mainPanel = new JPanel();
		gestionGroupeButton = new JButton("Gérer les groupes");
		gestionGroupeButton.setSize(new Dimension(40, 6));

		gestionUtilisateurButton = new JButton("Gérer les utilisateurs");
	}

	private void setFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 500);
		frame.setLocationRelativeTo(null);

		frame.setResizable(false);

		frame.setContentPane(mainPanel);
	}

	private void setMainPanel() {
		GridLayout layout = new GridLayout(2, 1, 20, 10);
		mainPanel.setLayout(layout);
		// gestionGroupeButton.setBackground(Color.cyan);
		gestionGroupeButton.setFont(gestionGroupeButton.getFont().deriveFont(31.0f));
		mainPanel.add(gestionGroupeButton);
		// TODO changer les couleurs des boutons
		gestionUtilisateurButton.setFont(gestionUtilisateurButton.getFont().deriveFont(34.0f));
		mainPanel.add(gestionUtilisateurButton);

	}

	private void build() {
		buildComponents();
		setFrame();
		setMainPanel();

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		firstWindow f = new firstWindow();
		f.build();
	}

}
