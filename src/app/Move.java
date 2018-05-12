package app;
import java.awt.Robot;

/**
 * A command for moving the mouse.
 * @author Robin Zhang
 *
 */
public final class Move implements Command {
	
	private final int x;
	private final int y;
	private final String asString;
	private final String cmdString;
	
	/**
	 * Creates a command that will move the mouse.
	 * @param x X position
	 * @param y Y position
	 */
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
		asString = "move  " + "[" + x + ", " + y + "]";
		cmdString = "move " + x + " " + y;
	}
	
	@Override
	public void execute(Robot r) {
		r.mouseMove(x, y);
	}
	
	/**
	 * Creates a Move command from an input String. See usage below:
	 * <ul>
	 *   <li>move [x-pos] [y-pos] - move the pointer to the specified location
	 * </ul>
	 * @param s the string to parse
	 * @return the resultant Move
	 * @throws IllegalArgumentException if the string is not properly formatted
	 */
	public static Move fromString(String s) {
		String[] toks = s.split(" ");
		if (!toks[0].equals("move")) {
			throw new IllegalArgumentException();
		}
		if (toks.length == 3) {
			return new Move(Integer.parseInt(toks[1]),
					Integer.parseInt(toks[2]));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String getStringCmd() {
		return cmdString;
	}
	
	@Override
	public String toString() {
		return asString;
	}

}
