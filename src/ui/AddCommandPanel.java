package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import app.Command;

/**
 * The panel from which users can select the type of command they want to add
 * as well as customize the parameters. This panel uses a ComboBox for the user
 * to select the command type, and a CardLayout swaps between panels as
 * appropriate.
 * @author Robin Zhang
 *
 */
public class AddCommandPanel extends JPanel {
	
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
		this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		this.setLayout(new BorderLayout());
		((BorderLayout) this.getLayout()).setVgap(8);
		
		String[] cbItems = { CLICKPANEL, MOVEPANEL, TYPEPANEL, WAITPANEL };
		JComboBox<String> cb = new JComboBox<>(cbItems);
		
		JPanel cards = new JPanel(new CardLayout());
		cards.setBorder(BorderFactory.createTitledBorder("Parameters"));
		cards.add(new AddClickPanel(list), CLICKPANEL);
		cards.add(new AddMovePanel(list), MOVEPANEL);
		cards.add(new AddTypePanel(list), TYPEPANEL);
		cards.add(new AddWaitPanel(list), WAITPANEL);
		
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
}
