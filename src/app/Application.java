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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	private MainPanel mainPanel;
	private File lastFile;
	
	private final static String helpWebsite;
	private final static String aboutMessage;
	
	static {
		helpWebsite = "http://github.com/robinz62/click-bot";
		aboutMessage = "Created by Robin Zhang 2018.\n"
				+ "For more details, click the help menu item.";
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
		
		mainPanel = null;
		try {
			mainPanel = new MainPanel();
		} catch (AWTException e) {
			System.out.println("Error creating Robot");
			System.exit(-1);
		}
		
		frame.setContentPane(mainPanel);
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
		mNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.clear();
				lastFile = null;
			}
		});
		JMenuItem mOpen = new JMenuItem("Open...");
		mOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					lastFile = ClickFilesManager.open(mainPanel);
				} catch (IOException e) {
					System.err.println("failed to open file");
					e.printStackTrace();
				}
			}
		});
		JMenuItem mSave = new JMenuItem("Save");
		mSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (lastFile == null) {
						lastFile = ClickFilesManager.saveAs(mainPanel);
					} else {
						lastFile = ClickFilesManager.save(mainPanel, lastFile);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JMenuItem mSaveAs = new JMenuItem("Save As...");
		mSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					lastFile = ClickFilesManager.saveAs(mainPanel);
				} catch (IOException e) {
					System.err.println("failed to save");
					e.printStackTrace();
				}
			}
		});
		JMenuItem mExit = new JMenuItem("Exit");
		mExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
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
	 * Opens the README on the default browser.
	 */
	private void showHelp() {
		try {
			java.awt.Desktop.getDesktop().browse(new URI(helpWebsite));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.err.println("Malformed URI");
		}
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
