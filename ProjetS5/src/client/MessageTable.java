package client;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MessageTable extends JTable {
	private Thread thread;

	private JScrollPane scrollPane;

	public MessageTable(Thread thread) {
		super();
		this.thread = thread;

	}

	private JPanel mainPanel;

	private void setTable() {

	}

	private void buildComponent() {
		mainPanel = new JPanel();
		scrollPane = new JScrollPane();

	}

	private void build() {
		buildComponent();

	}

	public static void main(String[] args) {

	}

}
