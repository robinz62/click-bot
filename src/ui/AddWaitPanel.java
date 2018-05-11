package ui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.Application;
import app.Command;
import app.Wait;

public class AddWaitPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AddWaitPanel(DefaultListModel<Command> list, MainPanel mp) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Initialize components to use
		
		JLabel label = new JLabel("Enter time in ms");
		
		JTextField timeTextField = new JTextField();
		timeTextField.setPreferredSize(new Dimension(80, 23));
		
		JButton add = new JButton("Add");
		
		// Hook up components/functionality
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int time = Integer.parseInt(timeTextField.getText());
					list.addElement(new Wait(time));
					((Window) (AddWaitPanel.this.getTopLevelAncestor())).dispose();
					mp.setLastCommandAdded("Wait");
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		Application.addComponent(this, label, 0, 0, 0, 0, 0, 0);
		Application.addComponent(this, timeTextField, 0, 1, 8, 0, 0, 0);
		Application.addComponent(this, add, 0, 2, 8, 0, 0, 0);
	}
}
