package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

import ui.MainPanel;

public class ClickFilesManager {
	
	/**
	 * Saves the current command list to the specified file.
	 * @param mp the {@link MainPanel} from which to read the commands
	 * @param file the file to save to
	 * @return the file that was saved to
	 * @throws IOException
	 */
	public static File save(MainPanel mp, File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		List<Command> cmdList = mp.getCommands();
		for (Command c : cmdList) {
			bw.write(c.getStringCmd() + "\n");
		}
		bw.close();
		return file;
	}
	
	/**
	 * Opens a file dialog for the user to select a file to save the current
	 * command list to.
	 * @param mp the {@link MainPanel} from which to read the commands
	 * @return the {@link File} that was saved to, or {@code} null if cancelled
	 * @throws IOException
	 */
	public static File saveAs(MainPanel mp) throws IOException {
		JFileChooser jfc = new JFileChooser();
		int ret = jfc.showSaveDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = jfc.getSelectedFile();
		return save(mp, file);
	}
	
	/**
	 * Opens a file dialog for the user to select a file to load from.
	 * @param mp the {@link MainPanel} to which the commands will be loaded
	 * @return the {@link} File that was loaded, or {@code null} if cancelled
	 * @throws IOException
	 */
	public static File open(MainPanel mp) throws IOException {
		JFileChooser jfc = new JFileChooser();
		int ret = jfc.showOpenDialog(null);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File file = jfc.getSelectedFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
		mp.clear();
		String line = br.readLine();
		while (line != null) {
			mp.addCommand(line);
			line = br.readLine();
		}
		br.close();
		return file;
	}
}
