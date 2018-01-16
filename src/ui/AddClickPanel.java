package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import app.Application;
import app.Click;
import app.Command;

public class AddClickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public AddClickPanel(DefaultListModel<Command> list) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Initialize components to use
		
		JCheckBox clickCurrLocBox = new JCheckBox("Click at current location");
		
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
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
		JCheckBox left = new JCheckBox("L");
		JCheckBox right = new JCheckBox("R");
		JCheckBox middle = new JCheckBox("M");
		buttonsPanel.add(left);
		buttonsPanel.add(right);
		buttonsPanel.add(middle);
		left.setSelected(true);
		
		JPanel radioPanel = new JPanel(new GridLayout(1, 3));
		JRadioButton click = new JRadioButton("click");
		JRadioButton down = new JRadioButton("down");
		JRadioButton up = new JRadioButton("up");
		ButtonGroup group = new ButtonGroup();
		group.add(click);
		group.add(down);
		group.add(up);
		radioPanel.add(click);
		radioPanel.add(down);
		radioPanel.add(up);
		click.setSelected(true);
		
		JButton add = new JButton("Add");
		
		// Hook up components/functionality
		
		clickCurrLocBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clickCurrLocBox.isSelected()) {
					xTextField.setEditable(false);
					yTextField.setEditable(false);
				} else {
					xTextField.setEditable(true);
					yTextField.setEditable(true);
				}
			}
		});
		
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
					int buttons = 0;
					if (left.isSelected()) {
						buttons |= InputEvent.BUTTON1_DOWN_MASK;
					}
					if (right.isSelected()) {
						buttons |= InputEvent.BUTTON2_DOWN_MASK;
					}
					if (middle.isSelected()) {
						buttons |= InputEvent.BUTTON3_DOWN_MASK;
					}
					Click.ClickMode cType = null;
					if (click.isSelected()) {
						cType = Click.ClickMode.CLICK;
					} else if (down.isSelected()) {
						cType = Click.ClickMode.DOWN;
					} else if (up.isSelected()) {
						cType = Click.ClickMode.UP;
					}
					if (!clickCurrLocBox.isSelected()) {
						int x = Integer.parseInt(xTextField.getText());
						int y = Integer.parseInt(yTextField.getText());
						list.addElement(new Click(buttons, cType, x, y));
					} else {
						list.addElement(new Click(buttons, cType));
					}
					((Window) (AddClickPanel.this.getTopLevelAncestor())).dispose();
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		Application.addComponent(this, clickCurrLocBox, 0, 0, 0, 0, 0, 0);
		Application.addComponent(this, xCoord, 0, 1, 8, 0, 0, 0);
		Application.addComponent(this, yCoord, 0, 2, 8, 0, 0, 0);
		Application.addComponent(this, clickLocButton, 0, 3, 8, 0, 0, 0);
		Application.addComponent(this, buttonsPanel, 0, 4, 8, 0, 0, 0);
		Application.addComponent(this, radioPanel, 0, 5, 8, 0, 0, 0);
		Application.addComponent(this, radioPanel, 0, 6, 8, 0, 0, 0);
	}
}
