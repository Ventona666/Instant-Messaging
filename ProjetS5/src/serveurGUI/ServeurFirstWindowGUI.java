package serveurGUI;

import object.User;
import server.Server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ServeurFirstWindowGUI {
	private Server server;
	private JFrame frame;

	public ServeurFirstWindowGUI(Server server){
		this.server = server;
	}

	public void build() {

		frame = new JFrame("Interface Serveur");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(388, 359);
		frame.setLocationRelativeTo(null);

		frame.setLayout(new GridLayout(2, 1, 0, 0));

		JButton gestionGroupeButton = new JButton("G\u00E9rer les groupes ");
		gestionGroupeButton.setFont(gestionGroupeButton.getFont().deriveFont(34.0f));
		gestionGroupeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServeurGestionGroupes serveurGestionGroupes = new ServeurGestionGroupes(server);
				serveurGestionGroupes.build();
				frame.dispose();
			}
		});
		frame.add(gestionGroupeButton);

		JButton gestionUtilisateurButton = new JButton("G\u00E9rer les utilisateurs");
		gestionUtilisateurButton.setFont(gestionUtilisateurButton.getFont().deriveFont(34.0f));
		frame.add(gestionUtilisateurButton);
		frame.setVisible(true);

	}

}
