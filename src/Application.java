import java.awt.AWTException;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Contains the top-level GUI component and serves as the entry point into the
 * application.
 * @author Robin Zhang
 *
 */
public class Application implements Runnable {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Application());
	}

	@Override
	public void run() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		JFrame frame = createFrame();
		JMenuBar menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);
		
		MainContent mc = null;
		try {
			mc = new MainContent();
		} catch (AWTException e) {
			// ERR: could not instantiate Robot
			System.out.println("Error creating Robot");
			System.exit(-1);
		}
		
		frame.setContentPane(mc);
		frame.pack();
		
		frame.setVisible(true);
	}
	
	/*
	 * Creates the main frame with custom appearance.
	 */
	private JFrame createFrame() {
		JFrame frame = new JFrame("Macro Application");
//		frame.setLocation(x, y);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
	
	/*
	 * Creates the custom menu bar.
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
		JMenuItem mAbout = new JMenuItem("About");
		help.add(mHelp);
		help.addSeparator();
		help.add(mAbout);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		
		return menuBar;
	}
}
