import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class MainContent extends JPanel {
	
	private DefaultListModel<Command> list;
	private Robot robot;
	
	private static final long serialVersionUID = 1L;

	public MainContent() throws AWTException {
		robot = new Robot();
		
		list = new DefaultListModel<>();
		this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		this.setLayout(new GridBagLayout());
		
		JScrollPane list = createCommandList();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(8, 8, 8, 8);
		this.add(list, c);
		
		JPanel buttonsPanel = createButtonsPanel();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(7, 8, 8, 8);  // top = 7 to align with list's border
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(buttonsPanel, c);
	}
	
	/**
	 * Creates the box that lists commands.
	 * @return the list box
	 */
	private JScrollPane createCommandList() {
		JList<Command> commandList = new JList<>(list);
		commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		commandList.setVisibleRowCount(16);
		commandList.setFixedCellWidth(200);
		commandList.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(commandList);
		return scrollPane;
	}
	
	/**
	 * Create the panel containing user controls
	 * @return the panel
	 */
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		
		// create components
		JButton addCommandButton = createAddCommandButton();
		JTextField addCommandTextField = createAddCommandTextField();
		JButton runButton = createRunButton();
		
		// format components in layout
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 16, 0);
		buttonsPanel.add(addCommandButton, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 16, 0);
		buttonsPanel.add(addCommandTextField, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		buttonsPanel.add(runButton, c);
		
		return buttonsPanel;
	}
	
	/**
	 * Create the Add Command button.
	 * @return the Add Command button
	 */
	private JButton createAddCommandButton() {
		JButton button = new JButton("Add Command");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: create dialog that lets user select action
			}
		});
		button.setPreferredSize(new Dimension(110, 23));
		return button;
	}
	
	/**
	 * Create the text field for users to type commands.
	 * @return the text field
	 */
	private JTextField createAddCommandTextField() {
		JTextField textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = e.getActionCommand();
				String[] toks = input.split(" ");
				Command c = null;
				try {
					if (toks[0].equals("click")) {
						c = Click.fromString(input);
						textField.setText("");
					} else if (toks[0].equals("move")) {
						c = Move.fromString(input);
						textField.setText("");
					} else if (toks[0].equals("wait")) {
						c = Wait.fromString(input);
						textField.setText("");
					} else {
						// invalid input
						return;
					}
				} catch (IllegalArgumentException ex) {
					// invalid input
					return;
				}
				list.addElement(c);
			}
		});
		textField.setPreferredSize(new Dimension(108, 23));
		textField.setFont(new Font("Consolas", Font.PLAIN, 11));
		return textField;
	}
	
	/**
	 * Create the Run button.
	 * @return the Run button
	 */
	private JButton createRunButton() {
		JButton button = new JButton("RUN");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < list.size(); ++i) {
					list.get(i).execute(robot);
				}
			}
		});
		button.setPreferredSize(new Dimension(110, 23));
		return button;
	}
}
