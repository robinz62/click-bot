import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddCommandPanel extends JPanel {
	
	private final DefaultListModel<Command> list;
	
	private static final String CLICKPANEL = "Click";
	private static final String MOVEPANEL = "Move";
	private static final String TYPEPANEL = "Type";
	private static final String WAITPANEL = "Wait";
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates the panel containing all the controls to add a command.
	 * @param list the list to add the command to
	 */
	public AddCommandPanel(DefaultListModel<Command> list) {
		this.list = list;
		this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		this.setLayout(new BorderLayout());
		((BorderLayout) this.getLayout()).setVgap(8);
		
		String[] cbItems = { CLICKPANEL, MOVEPANEL, TYPEPANEL, WAITPANEL };
		JComboBox<String> cb = new JComboBox<>(cbItems);
		
		JPanel cards = new JPanel(new CardLayout());
		cards.setBorder(BorderFactory.createTitledBorder("Parameters"));
		cards.add(createClickCard(), CLICKPANEL);
		cards.add(createMoveCard(), MOVEPANEL);
		cards.add(createTypeCard(), TYPEPANEL);
//		cards.add(createWaitCard(), WAITPANEL);
		
		this.add(cb, BorderLayout.PAGE_START);
		this.add(cards, BorderLayout.CENTER);
		
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String chosenPanel = (String) cb.getSelectedItem();
				((CardLayout) cards.getLayout()).show(cards, chosenPanel);
			}
		});
		
		((CardLayout) cards.getLayout()).show(cards, CLICKPANEL);
	}
	
	/**
	 * Creates the click card containing the click parameters.
	 * @return the click card
	 */
	private JPanel createClickCard() {
		GridBagConstraints gc;
		JPanel clickPanel = new JPanel(new GridBagLayout());
		clickPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// declared above so that checkbox can access them
		JTextField xTextField = new JTextField();
		JTextField yTextField = new JTextField();
		
		// Checkbox for selecting a location or not
		JCheckBox clickCurrLoc = new JCheckBox(
				"Click at current location");
		clickCurrLoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clickCurrLoc.isSelected()) {
					xTextField.setEditable(false);
					yTextField.setEditable(false);
				} else {
					xTextField.setEditable(true);
					yTextField.setEditable(true);
				}
			}
		});
		
		// Field for x-coordinate
		JPanel xCoord = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		xCoord.add(new JLabel("X: "), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		xTextField.setPreferredSize(new Dimension(80, 23));
		xTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		xCoord.add(xTextField, gc);
		
		// Field for y-coordinate
		JPanel yCoord = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		yCoord.add(new JLabel("Y: "), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		yTextField.setPreferredSize(new Dimension(80, 23));
		yTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		yCoord.add(yTextField, gc);
		
		// Button to click location
		JButton clickLoc = new JButton("Click Location");
		clickLoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				// https://stackoverflow.com/questions/2419555/how-to-obtain-mouse-click-coordinates-outside-my-window-in-java
			}
		});
		
		// Checkboxes for buttons
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
		JCheckBox left = new JCheckBox("L");
		left.setSelected(true);
		JCheckBox right = new JCheckBox("R");
		JCheckBox middle = new JCheckBox("M");
		buttonsPanel.add(left);
		buttonsPanel.add(right);
		buttonsPanel.add(middle);
		
		// Radio group for click type
		JPanel radioPanel = new JPanel(new GridLayout(1, 3));
		JRadioButton click = new JRadioButton("click");
		click.setSelected(true);
		JRadioButton down = new JRadioButton("down");
		JRadioButton up = new JRadioButton("up");
		ButtonGroup group = new ButtonGroup();
		group.add(click);
		group.add(down);
		group.add(up);
		radioPanel.add(click);
		radioPanel.add(down);
		radioPanel.add(up);
		
		// Button to add command
		JButton add = new JButton("Add");
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
					if (!clickCurrLoc.isSelected()) {
						int x = Integer.parseInt(xTextField.getText());
						int y = Integer.parseInt(yTextField.getText());
						list.addElement(new Click(buttons, cType, x, y));
					} else {
						list.addElement(new Click(buttons, cType));
					}
					((Window) (AddCommandPanel.this.getTopLevelAncestor())).dispose();
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		// Add and style components
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		clickPanel.add(clickCurrLoc, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(xCoord, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(yCoord, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(clickLoc, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 4;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(buttonsPanel, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 5;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(radioPanel, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 6;
		gc.insets = new Insets(8, 0, 0, 0);
		clickPanel.add(add, gc);
		
		return clickPanel;
	}
	
	/**
	 * Creates the move card containing the move parameters.
	 * @return the move card
	 */
	private JPanel createMoveCard() {
		GridBagConstraints gc;
		JPanel movePanel = new JPanel(new GridBagLayout());
		movePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// Field for x-coordinate
		JPanel xCoord = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		xCoord.add(new JLabel("X: "), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		JTextField xTextField = new JTextField();
		xTextField.setPreferredSize(new Dimension(80, 23));
		xTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		xCoord.add(xTextField, gc);
		
		// Field for y-coordinate
		JPanel yCoord = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		yCoord.add(new JLabel("Y: "), gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		JTextField yTextField = new JTextField();
		yTextField.setPreferredSize(new Dimension(80, 23));
		yTextField.setFont(new Font("Consolas", Font.PLAIN, 12));
		yCoord.add(yTextField, gc);
		
		// Button to click location
		JButton clickLoc = new JButton("Click Location");
		clickLoc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO same as in clickPanel
				// https://stackoverflow.com/questions/2419555/how-to-obtain-mouse-click-coordinates-outside-my-window-in-java
			}
		});
		
		// Button to add command
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int x = Integer.parseInt(xTextField.getText());
					int y = Integer.parseInt(yTextField.getText());
					list.addElement(new Move(x, y));
					((Window) (AddCommandPanel.this.getTopLevelAncestor())).dispose();
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		// Add and style components
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(8, 0, 0, 0);
		movePanel.add(xCoord, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(8, 0, 0, 0);
		movePanel.add(yCoord, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.insets = new Insets(8, 0, 0, 0);
		movePanel.add(clickLoc, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.insets = new Insets(8, 0, 0, 0);
		movePanel.add(add, gc);
		
		return movePanel;
	}
	
	private JPanel createTypeCard() {
		GridBagConstraints gc;
		JPanel typePanel = new JPanel(new GridBagLayout());
		typePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		// declared above so that other components can reference it
		JTextField stringField = new JTextField();
		
		// Button for recording keystroke
		JPanel recordKey = new JPanel(new GridBagLayout());
		JButton recordButton = new JButton("Record Key");
		JTextField keyText = new JTextField();
		keyText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (keyText.getText().equals("")) {
					stringField.setEditable(true);
				} else {
					stringField.setEditable(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		keyText.setPreferredSize(new Dimension(50, 21));
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (keyText.isEditable()) {
					// TODO listen to the next keystroke
				}
			}
		});
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		recordKey.add(recordButton, gc);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 4, 0, 0);
		recordKey.add(keyText, gc);
		
		// Radio group for click type
		JPanel radioPanel = new JPanel(new GridLayout(1, 3));
		JRadioButton type = new JRadioButton("click");
		type.setSelected(true);
		JRadioButton down = new JRadioButton("down");
		JRadioButton up = new JRadioButton("up");
		ButtonGroup group = new ButtonGroup();
		group.add(type);
		group.add(down);
		group.add(up);
		radioPanel.add(type);
		radioPanel.add(down);
		radioPanel.add(up);
		
		// Separator
		JLabel sep = new JLabel("----OR----");
		
		// Textfield for typing string
		stringField.setPreferredSize(new Dimension(100, 23));
		stringField.addFocusListener(new FocusListener() {
			private boolean showingHint = true;
			@Override
			public void focusGained(FocusEvent arg0) {
				if (showingHint) {
					stringField.setText("");
					stringField.setForeground(Color.BLACK);
					showingHint = false;
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!showingHint && stringField.getText().isEmpty()) {
					stringField.setForeground(new Color(117, 117, 117));
					stringField.setText("type string");
					showingHint = true;
				}
			}
		});
		stringField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (stringField.getText().equals("type string")
						|| stringField.getText().equals("")) {
					keyText.setEditable(true);
				} else {
					keyText.setEditable(false);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
		});
		
		stringField.setPreferredSize(new Dimension(108, 23));
		stringField.setForeground(new Color(117, 117, 117));
		stringField.setText("type string");
		
		// Button to add command
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!stringField.isEditable()) {
						// keyText must be filled
						Keytype.KeytypeMode kType = null;
						if (type.isSelected()) {
							kType = Keytype.KeytypeMode.TYPE;
						} else if (down.isSelected()) {
							kType = Keytype.KeytypeMode.DOWN;
						} else if (up.isSelected()) {
							kType = Keytype.KeytypeMode.UP;
						}
						int code = -1; // TODO return a Keytype object using the code generated from record
						list.addElement(new Keytype(kType, code));
					} else if (!keyText.isEditable()) {
						// stringField must be filled
						list.addElement(new Keytype(stringField.getText()));
					}
					((Window) (AddCommandPanel.this.getTopLevelAncestor())).dispose();
				} catch (NumberFormatException e) {
					return;
				}
			}
		});
		
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		typePanel.add(recordKey, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(8, 0, 0, 0);
		typePanel.add(radioPanel, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.insets = new Insets(8, 0, 0, 0);
		typePanel.add(sep, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.insets = new Insets(8, 0, 0, 0);
		typePanel.add(stringField, gc);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 4;
		gc.insets = new Insets(8, 0, 0, 0);
		typePanel.add(add, gc);
		
		return typePanel;
	}

}
