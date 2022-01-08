package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import object.CampusUser;
import object.Group;
import object.User;
import server.Server;

public class ServeurListUser {
	private Group group;
	private Server server;
	// Components
	private JFrame frame;
	private JScrollPane scrollPane;
	private JButton ajouterButton;

	private void buildFrame() {
		frame = new JFrame("Ajouter un utilisateur dans le groupe");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(400, 500));
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		setframe();
		frame.setVisible(true);

	}

	private void setframe() {
		User[] listUser = new User[2];
		// User[] listUser = new User[server.getAllUser().size()];
		// listUser = server.getAllUser().toArray(listUser);
		listUser[0] = new CampusUser(1, "Sabrina", "Sikder", "caca"); // a enlever
		listUser[1] = new CampusUser(2, "Hugo", "Deleye", "zizi"); // a enlever
		JList<User> userJList = new JList<>(listUser);
		scrollPane = new JScrollPane(userJList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ajouterButton = new JButton("Ajouter");
		// TODO listener pour le Jbutton

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(ajouterButton, BorderLayout.SOUTH);

	}

	public static void main(String[] args) {
		ServeurListUser l = new ServeurListUser();
		l.buildFrame();
	}

}
