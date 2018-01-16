package app;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * A command for clicking the mouse.
 * @author Robin Zhang
 *
 */
public final class Click implements Command {

	private final int buttons;
	private final boolean specifiedLoc;
	private final int x;
	private final int y;
	private final ClickMode cMode;
	private final String asString;
	
	/**
	 * Creates a command that will click the mouse. The button masks are used
	 * as specified in {@link InputEvent}.
	 * @param buttons a combination of one or more button masks
	 * @param cType the type of action to be performed
	 * 
	 * @see InputEvent
	 * @see ClickMode
	 */
	public Click(int buttons, ClickMode cType) {
		this.buttons = buttons;
		specifiedLoc = false;
		x = 0;
		y = 0;
		this.cMode = cType;
		asString = generateString();
	}
	
	/**
	 * Creates a command that will click at the specified location. The button
	 * masks are used as specified in {@link InputEvent}.
	 * @param buttons a combination of one or more button masks
	 * @param cType the type of action to be performed
	 * @param x X position
	 * @param y Y position
	 * 
	 * @see InputEvent
	 * @see ClickMode
	 */
	public Click(int buttons, ClickMode cType, int x, int y) {
		this.buttons = buttons;
		specifiedLoc = true;
		this.x = x;
		this.y = y;
		this.cMode = cType;
		asString = generateString();
	}
	
	@Override
	public void execute(Robot r) {
		if (specifiedLoc) {
			r.mouseMove(x, y);
		}
		
		if (cMode == ClickMode.CLICK) {
			r.mousePress(buttons);
			r.mouseRelease(buttons);
		} else if (cMode == ClickMode.DOWN) {
			r.mousePress(buttons);
		} else if (cMode == ClickMode.UP) {
			r.mousePress(buttons);
		}
	}
	
	/**
	 * Creates a Click command from an input String. See usage below:
	 * <ul>
	 *   <li>{@code [click/mdown/mup]} - perform a left click/press/release at
	 *       the current location</li>
	 *   <li>{@code [click/mdown/mup] [l|r|m]} - perform the specified action
	 *       or combination of actions (l, m, r) at the current location</li>
	 *   <li>{@code [click/mdown/up] [x-pos] [y-pos]} - perform a left action
	 *       at the specified location</li>
	 *   <li>{@code [click/mdown/up] [x-pos] [y-pos] [l|r|m]} - perform the
	 *       specified action or combination of actions (l, m, r) at the
	 *       specified location</li>
	 * </ul>
	 * @param s the string to parse
	 * @return the resultant Click
	 * @throws IllegalArgumentException if the string is not properly formatted
	 */
	public static Click fromString(String s) {
		String[] toks = s.split(" ");
		ClickMode cType;
		if (toks[0].equals("click")) {
			cType = ClickMode.CLICK;
		} else if (toks[0].equals("mdown")) {
			cType = ClickMode.DOWN;
		} else if (toks[0].equals("mup")) {
			cType = ClickMode.UP;
		} else {
			throw new IllegalArgumentException();
		}
		if (toks.length == 1) {
			return new Click(InputEvent.BUTTON1_DOWN_MASK, cType);
		} else if (toks.length == 2) {
			return new Click(parseButtons(toks[1]), cType);
		} else if (toks.length == 3) {
			return new Click(InputEvent.BUTTON1_DOWN_MASK,
					cType,
					Integer.parseInt(toks[1]),
					Integer.parseInt(toks[2]));
		} else if (toks.length == 4) {
			return new Click(parseButtons(toks[3]),
					cType,
					Integer.parseInt(toks[1]),
					Integer.parseInt(toks[2]));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Parses the input string for characters l/L, r/R, m/M corresponding to
	 * a mouse click and creates the appropriate mask.
	 * @param s the string to parse
	 * @return the buttons mask as specified in {@link InputEvent}
	 * @throws IllegalArgumentException if input string contains characters
	 *         that are not l, r, or m.
	 */
	private static int parseButtons(String s) {
		s = s.toLowerCase();
		int buttons = 0;
		for (char c : s.toCharArray()) {
			if (c == 'l') {
				buttons |= InputEvent.BUTTON1_DOWN_MASK;
			} else if (c == 'r') {
				buttons |= InputEvent.BUTTON2_DOWN_MASK;
			} else if (c == 'm') {
				buttons |= InputEvent.BUTTON3_DOWN_MASK;
			} else {
				throw new IllegalArgumentException();
			}
		}
		return buttons;
	}
	
	@Override
	public String toString() {
		return asString;
	}
	
	private String generateString() {
		String command = "ERR";
		if (cMode == ClickMode.CLICK) {
			command = "click";
		} else if (cMode == ClickMode.DOWN) {
			command = "mdown";
		} else if (cMode == ClickMode.UP) {
			command = "mup  ";
		}
		String loc = specifiedLoc
				? "[" + String.format("%4d", x) + ", " + String.format("%4d", y) + "]"
				: "[curr loc  ]";
		String but = "";
		if ((buttons & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			but += "L";
		}
		if ((buttons & InputEvent.BUTTON2_DOWN_MASK) != 0) {
			but += "R";
		}
		if ((buttons & InputEvent.BUTTON3_DOWN_MASK) != 0) {
			but += "M";
		}
		return command + " " + loc + " " + but;
	}

	public enum ClickMode {
		CLICK,
		DOWN,
		UP
	}
}
