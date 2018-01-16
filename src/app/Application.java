package app;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.MainPanel;

/**
 * Contains the top-level GUI component and serves as the entry point into the
 * application.
 * @author Robin Zhang
 *
 */
public class Application implements Runnable {
	
	private JFrame frame;
	
	private final static String helpMessage;
	private final static String aboutMessage;
	
	static {
		helpMessage = "Look at the readme";
		aboutMessage = "put something here";
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Application());
	}

	@Override
	public void run() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Warning: could not set system look and feel");
        }
		
		frame = createFrame();
		JMenuBar menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);
		
		MainPanel mc = null;
		try {
			mc = new MainPanel();
		} catch (AWTException e) {
			System.out.println("Error creating Robot");
			System.exit(-1);
		}
		
		frame.setContentPane(mc);
		frame.pack();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screen.width / 2 - frame.getWidth() / 2,
				screen.height / 2 - frame.getHeight() / 2);
		frame.setVisible(true);
	}
	
	/**
	 * Creates the main, blank frame.
	 * @return the frame
	 */
	private JFrame createFrame() {
		JFrame frame = new JFrame("Macro Application");
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	/**
	 * Creates and populates the menu bar.
	 * @return the menu bar
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// File menu
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem mNew = new JMenuItem("New");
		JMenuItem mOpen = new JMenuItem("Open...");
		JMenuItem mSave = new JMenuItem("Save");
		JMenuItem mSaveAs = new JMenuItem("Save As...");
		JMenuItem mExit = new JMenuItem("Exit");
		file.add(mNew);
		file.add(mOpen);
		file.add(mSave);
		file.add(mSaveAs);
		file.addSeparator();
		file.add(mExit);
		
		// Edit menu
		JMenu edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		
		// Help menu
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		JMenuItem mHelp = new JMenuItem("View Help");
		mHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showHelp();
			}
		});
		JMenuItem mAbout = new JMenuItem("About");
		mAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showAbout();
			}
		});
		help.add(mHelp);
		help.addSeparator();
		help.add(mAbout);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		
		return menuBar;
	}
	
	/**
	 * Shows the help dialog.
	 */
	private void showHelp() {
		JOptionPane.showMessageDialog(frame,
				helpMessage,
				"Help",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Shows the about dialog.
	 */
	private void showAbout() {
		JOptionPane.showMessageDialog(frame,
				aboutMessage,
				"About",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Assuming the parent is using a GridBagLayout, adds the child component
	 * to the parent component at the specified location with specified insets.
	 * @param parent the parent container
	 * @param child the child component
	 * @param x the x coordinate of the desired grid cell
	 * @param y the y coordinate of the desired grid cell
	 * @param top the top inset
	 * @param left the left inset
	 * @param right the right inset
	 * @param bottom the bottom inset
	 * @param i the insets to apply to the child
	 */
	public static void addComponent(Container parent, Component child,
			int x, int y, int top, int left, int right, int bottom) {
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
		gc.gridy = y;
		gc.insets = new Insets(top, left, right, bottom);
		parent.add(child, gc);
	}
}
