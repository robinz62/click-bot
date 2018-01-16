package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
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
import app.Move;

public class AddMovePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public AddMovePanel(DefaultListModel<Command> list) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Initialize components to use
		
		JPanel xCoord = new JPanel(new GridBagLayout());
		JLabel xLabel = new JLabel("X: ");
		JTextField xTextField = new JTextField();
		xTextField.setPreferredSize(new Dimension(80, 23));
		xTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		Application.addComponent(xCoord, xLabel, 0, 0, 0, 0, 0, 0);
		Application.addComponent(xCoord, xTextField, 1, 0, 0, 0, 0, 0);
		
		JPanel yCoord = new JPanel(new GridBagLayout());
		JLabel yLabel = new JLabel("Y: ");
		JTextField yTextField = new JTextField();
		yTextField.setPreferredSize(new Dimension(80, 23));
		yTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		Application.addComponent(yCoord, yLabel, 0, 0, 0, 0, 0, 0);
		Application.addComponent(yCoord, yTextField, 1, 0, 0, 0, 0, 0);
		
		JButton clickLocButton = new JButton("Select Location");
		
		JButton add = new JButton("Add");
		
		// Hook up components/functionality
		
		clickLocButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				xTextField.setText(Integer.toString(p.x));
				yTextField.setText(Integer.toString(p.y));
			}
		});
		
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int x = Integer.parseInt(xTextField.getText());
					int y = Integer.parseInt(yTextField.getText());
					list.addElement(new Move(x, y));
					((Window) (AddMovePanel.this.getTopLevelAncestor())).dispose();
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		Application.addComponent(this, xCoord, 0, 0, 0, 0, 0, 0);
		Application.addComponent(this, yCoord, 0, 1, 8, 0, 0, 0);
		Application.addComponent(this, clickLocButton, 0, 2, 8, 0, 0, 0);
		Application.addComponent(this, add, 0, 3, 8, 0, 0, 0);
	}
}
