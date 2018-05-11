package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import app.Application;
import app.Command;
import app.Keytype;

public class AddTypePanel extends JPanel {
	
	private boolean showingHint = true;
	
	private static final long serialVersionUID = 1L;

	public AddTypePanel(DefaultListModel<Command> list, MainPanel mp) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Initialize components to use
		
		JPanel recordKeyComponent = new JPanel(new GridBagLayout());
		JButton recordButton = new JButton("Record Key");
		JTextField keyTextField = new JTextField();
		keyTextField.setPreferredSize(new Dimension(50, 21));
		Application.addComponent(recordKeyComponent, recordButton, 0, 0, 0, 0, 0, 0);
		Application.addComponent(recordKeyComponent, keyTextField, 1, 0, 0, 4, 0, 0);
		
		JPanel radioPanel = new JPanel(new GridLayout(1, 3));
		JRadioButton type = new JRadioButton("type");
		JRadioButton down = new JRadioButton("down");
		JRadioButton up = new JRadioButton("up");
		ButtonGroup group = new ButtonGroup();
		group.add(type);
		group.add(down);
		group.add(up);
		radioPanel.add(type);
		radioPanel.add(down);
		radioPanel.add(up);
		type.setSelected(true);
		
		JLabel sep = new JLabel("----OR----");
		
		JTextField stringTextField = new JTextField();
		stringTextField.setPreferredSize(new Dimension(108, 23));
		stringTextField.setForeground(new Color(117, 117, 117));
		stringTextField.setText("type string");
		
		// Hook up components/functionality
		
		recordButton.setEnabled(false); // TODO: not yet implemented
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (keyTextField.isEditable()) {
					// TODO: listen to the next keystroke
				}
			}
		});
		
		keyTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (keyTextField.getText().equals("")) {
					stringTextField.setEditable(true);
				} else {
					stringTextField.setEditable(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) { changedUpdate(e); }

			@Override
			public void removeUpdate(DocumentEvent e) { changedUpdate(e); }
		});
		
		stringTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (showingHint) {
					stringTextField.setText("");
					stringTextField.setForeground(Color.BLACK);
					showingHint = false;
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!showingHint && stringTextField.getText().isEmpty()) {
					stringTextField.setForeground(new Color(117, 117, 117));
					showingHint = true;
					stringTextField.setText("type string");
				}
			}
		});
		
		stringTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (showingHint || stringTextField.getText().equals("")) {
					keyTextField.setEditable(true);
				} else {
					keyTextField.setEditable(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) { changedUpdate(e); }

			@Override
			public void removeUpdate(DocumentEvent e) { changedUpdate(e); }
		});
		
		// Button to add command
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!stringTextField.isEditable()) {
						// it must be that keyText is filled
						Keytype.KeytypeMode kType = null;
						if (type.isSelected()) {
							kType = Keytype.KeytypeMode.TYPE;
						} else if (down.isSelected()) {
							kType = Keytype.KeytypeMode.DOWN;
						} else if (up.isSelected()) {
							kType = Keytype.KeytypeMode.UP;
						}
						// TODO: ensure record key functionality (once implemented) is
						//       compatible with this code retrieval
						int code = Keytype.keyMap.get(keyTextField.getText());
						list.addElement(new Keytype(kType, code));
					} else if (!keyTextField.isEditable()) {
						// it must be that stringField is filled
						list.addElement(new Keytype(stringTextField.getText()));
					} else {
						// neither text box has been filled
						return;
					}
					((Window) (AddTypePanel.this.getTopLevelAncestor())).dispose();
					mp.setLastCommandAdded("Type");
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		Application.addComponent(this, recordKeyComponent, 0, 0, 0, 0, 0, 0);
		Application.addComponent(this, radioPanel, 0, 1, 8, 0, 0, 0);
		Application.addComponent(this, sep, 0, 2, 8, 0, 0, 0);
		Application.addComponent(this, stringTextField, 0, 3, 8, 0, 0, 0);
		Application.addComponent(this, add, 0, 4, 8, 0, 0, 0);
	}
	
}
