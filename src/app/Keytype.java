package app;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A command for typing keys.
 * @author Robin Zhang
 *
 */
public final class Keytype implements Command {

	private final int code;
	private final String typeString;
	private final KeytypeMode kMode;
	private final String asString;
	private final String cmdString;
	
	/**
	 * The mapping from strings to the Java-specified KeyEvent code. Available
	 * strings are listed below. Note that the keys refer to the physical
	 * button, disregarding any modifiers such as SHIFT. For example, the key
	 * "!" is mapped to the same {@code KeyEvent} code as "1".
	 * <ul>
	 *   <li><tt>abcdefghijklmnopqrstuvwxyz</tt></li>
	 *   <li><tt>ABCDEFGHIJKLMNOPQRSTUVWXYZ</tt></li>
	 *   <li><tt>0123456789-=</tt></li>
	 *   <li><tt>!@#$%^&*()_+</tt></li>
	 *   <li><tt>,./;'[]\</tt></li>
	 *   <li><tt><>?:"{}|</tt></li>
	 *   <li><tt>alt, backspace, capslock, ctrl, delete, down, end, enter, esc,
	 *           f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, home, ins,
	 *           kp_down, kp_left, kp_right, kp_up, left, numlock, pgdn, pgup,
	 *           right, scrolllock, shift, space, tab, up</tt></li>
	 * </ul>
	 */
	public static final Map<String, Integer> keyMap;
	private static final Set<Character> requiresShift;
	
	static {
		keyMap = new HashMap<>();
		keyMap.put("a", KeyEvent.VK_A);
		keyMap.put("b", KeyEvent.VK_B);
		keyMap.put("c", KeyEvent.VK_C);
		keyMap.put("d", KeyEvent.VK_D);
		keyMap.put("e", KeyEvent.VK_E);
		keyMap.put("f", KeyEvent.VK_F);
		keyMap.put("g", KeyEvent.VK_G);
		keyMap.put("h", KeyEvent.VK_H);
		keyMap.put("i", KeyEvent.VK_I);
		keyMap.put("j", KeyEvent.VK_J);
		keyMap.put("k", KeyEvent.VK_K);
		keyMap.put("l", KeyEvent.VK_L);
		keyMap.put("m", KeyEvent.VK_M);
		keyMap.put("n", KeyEvent.VK_N);
		keyMap.put("o", KeyEvent.VK_O);
		keyMap.put("p", KeyEvent.VK_P);
		keyMap.put("q", KeyEvent.VK_Q);
		keyMap.put("r", KeyEvent.VK_R);
		keyMap.put("s", KeyEvent.VK_S);
		keyMap.put("t", KeyEvent.VK_T);
		keyMap.put("u", KeyEvent.VK_U);
		keyMap.put("v", KeyEvent.VK_V);
		keyMap.put("w", KeyEvent.VK_W);
		keyMap.put("x", KeyEvent.VK_X);
		keyMap.put("y", KeyEvent.VK_Y);
		keyMap.put("z", KeyEvent.VK_Z);
		
		keyMap.put("A", KeyEvent.VK_A);
		keyMap.put("B", KeyEvent.VK_B);
		keyMap.put("C", KeyEvent.VK_C);
		keyMap.put("D", KeyEvent.VK_D);
		keyMap.put("E", KeyEvent.VK_E);
		keyMap.put("F", KeyEvent.VK_F);
		keyMap.put("G", KeyEvent.VK_G);
		keyMap.put("H", KeyEvent.VK_H);
		keyMap.put("I", KeyEvent.VK_I);
		keyMap.put("J", KeyEvent.VK_J);
		keyMap.put("K", KeyEvent.VK_K);
		keyMap.put("L", KeyEvent.VK_L);
		keyMap.put("M", KeyEvent.VK_M);
		keyMap.put("N", KeyEvent.VK_N);
		keyMap.put("O", KeyEvent.VK_O);
		keyMap.put("P", KeyEvent.VK_P);
		keyMap.put("Q", KeyEvent.VK_Q);
		keyMap.put("R", KeyEvent.VK_R);
		keyMap.put("S", KeyEvent.VK_S);
		keyMap.put("T", KeyEvent.VK_T);
		keyMap.put("U", KeyEvent.VK_U);
		keyMap.put("V", KeyEvent.VK_V);
		keyMap.put("W", KeyEvent.VK_W);
		keyMap.put("X", KeyEvent.VK_X);
		keyMap.put("Y", KeyEvent.VK_Y);
		keyMap.put("Z", KeyEvent.VK_Z);
		
		keyMap.put("1", KeyEvent.VK_1);
		keyMap.put("2", KeyEvent.VK_2);
		keyMap.put("3", KeyEvent.VK_3);
		keyMap.put("4", KeyEvent.VK_4);
		keyMap.put("5", KeyEvent.VK_5);
		keyMap.put("6", KeyEvent.VK_6);
		keyMap.put("7", KeyEvent.VK_7);
		keyMap.put("8", KeyEvent.VK_8);
		keyMap.put("9", KeyEvent.VK_9);
		keyMap.put("0", KeyEvent.VK_0);
		
		keyMap.put("!", KeyEvent.VK_1);
		keyMap.put("@", KeyEvent.VK_2);
		keyMap.put("#", KeyEvent.VK_3);
		keyMap.put("$", KeyEvent.VK_4);
		keyMap.put("%", KeyEvent.VK_5);
		keyMap.put("^", KeyEvent.VK_6);
		keyMap.put("&", KeyEvent.VK_7);
		keyMap.put("*", KeyEvent.VK_8);
		keyMap.put("(", KeyEvent.VK_9);
		keyMap.put(")", KeyEvent.VK_0);
		
		keyMap.put(",", KeyEvent.VK_COMMA);
		keyMap.put(".", KeyEvent.VK_PERIOD);
		keyMap.put("/", KeyEvent.VK_SLASH);
		keyMap.put(";", KeyEvent.VK_SEMICOLON);
		keyMap.put("'", KeyEvent.VK_QUOTE);
		keyMap.put("[", KeyEvent.VK_OPEN_BRACKET);
		keyMap.put("]", KeyEvent.VK_CLOSE_BRACKET);
		keyMap.put("\\", KeyEvent.VK_BACK_SLASH);
		keyMap.put("`", KeyEvent.VK_BACK_QUOTE);
		keyMap.put("-", KeyEvent.VK_MINUS);
		keyMap.put("=", KeyEvent.VK_EQUALS);
		
		keyMap.put("<", KeyEvent.VK_COMMA);
		keyMap.put(">", KeyEvent.VK_PERIOD);
		keyMap.put("?", KeyEvent.VK_SLASH);
		keyMap.put(":", KeyEvent.VK_SEMICOLON);
		keyMap.put("\"", KeyEvent.VK_QUOTE);
		keyMap.put("{", KeyEvent.VK_OPEN_BRACKET);
		keyMap.put("}", KeyEvent.VK_CLOSE_BRACKET);
		keyMap.put("|", KeyEvent.VK_BACK_SLASH);
		keyMap.put("~", KeyEvent.VK_BACK_QUOTE);
		keyMap.put("_", KeyEvent.VK_MINUS);
		keyMap.put("+", KeyEvent.VK_EQUALS);
		
		keyMap.put("alt", KeyEvent.VK_ALT);
		keyMap.put("backspace", KeyEvent.VK_BACK_SPACE);
		keyMap.put("capslock", KeyEvent.VK_CAPS_LOCK);
		keyMap.put("ctrl", KeyEvent.VK_CONTROL);
		keyMap.put("delete", KeyEvent.VK_DELETE);
		keyMap.put("down", KeyEvent.VK_DOWN);
		keyMap.put("end", KeyEvent.VK_END);
		keyMap.put("enter", KeyEvent.VK_ENTER);
		keyMap.put("esc", KeyEvent.VK_ESCAPE);
		keyMap.put("f1", KeyEvent.VK_F1);
		keyMap.put("f2", KeyEvent.VK_F2);
		keyMap.put("f3", KeyEvent.VK_F3);
		keyMap.put("f4", KeyEvent.VK_F4);
		keyMap.put("f5", KeyEvent.VK_F5);
		keyMap.put("f6", KeyEvent.VK_F6);
		keyMap.put("f7", KeyEvent.VK_F7);
		keyMap.put("f8", KeyEvent.VK_F8);
		keyMap.put("f9", KeyEvent.VK_F9);
		keyMap.put("f10", KeyEvent.VK_F10);
		keyMap.put("f11", KeyEvent.VK_F11);
		keyMap.put("f12", KeyEvent.VK_F12);
		keyMap.put("home", KeyEvent.VK_HOME);
		keyMap.put("ins", KeyEvent.VK_INSERT);
		keyMap.put("kp_down", KeyEvent.VK_KP_DOWN);
		keyMap.put("kp_left", KeyEvent.VK_KP_LEFT);
		keyMap.put("kp_right", KeyEvent.VK_KP_RIGHT);
		keyMap.put("kp_up", KeyEvent.VK_KP_UP);
		keyMap.put("left", KeyEvent.VK_LEFT);
		keyMap.put("numlock", KeyEvent.VK_NUM_LOCK);
		keyMap.put("pgdn", KeyEvent.VK_PAGE_DOWN);
		keyMap.put("pgup", KeyEvent.VK_PAGE_UP);
		keyMap.put("right", KeyEvent.VK_RIGHT);
		keyMap.put("scrolllock", KeyEvent.VK_SCROLL_LOCK);
		keyMap.put("shift", KeyEvent.VK_SHIFT);
		keyMap.put("space", KeyEvent.VK_SPACE);
		keyMap.put("tab", KeyEvent.VK_TAB);
		keyMap.put("up", KeyEvent.VK_UP);
		
		requiresShift = new HashSet<>();
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()_+{}:\"<>?|";
		for (char c : chars.toCharArray()) {
			requiresShift.add(c);
		}
	}

	/**
	 * Creates a command that will perform a key event using the input code.
	 * @param kMode the type of action to be performed
	 * @param code the Java KeyEvent code
	 * 
	 * @see KeytypeMode
	 * @see KeyEvent
	 */
	public Keytype(KeytypeMode kMode, int code) {
		this.kMode = kMode;
		this.code = code;
		typeString = null;
		asString = generateString();
		cmdString = (kMode == KeytypeMode.TYPE ? "type"
				: kMode == KeytypeMode.DOWN ? "kdown" : "kup")
				+ " " + KeyEvent.getKeyText(code);
	}
	
	/**
	 * Creates a command that will type out the input string.
	 * @param s the string to type
	 */
	public Keytype(String s) {
		this.kMode = KeytypeMode.TYPE;
		code = -1;
		typeString = s;
		asString = "type \"" + s + "\"";
		cmdString = asString;
	}
	
	@Override
	public void execute(Robot r) {
		if (typeString == null) {
			if (kMode == KeytypeMode.TYPE) {
				r.keyPress(code);
				r.keyRelease(code);
			} else if (kMode == KeytypeMode.DOWN) {
				r.keyPress(code);
			} else if (kMode == KeytypeMode.UP) {
				r.keyRelease(code);
			}
		} else {
			for (char c : typeString.toCharArray()) {
				if (requiresShift.contains(c)) {
					r.keyPress(KeyEvent.VK_SHIFT);
				}
				r.keyPress(keyMap.get(Character.toString(c)));
				r.keyRelease(keyMap.get(Character.toString(c)));
				if (requiresShift.contains(c)) {
					r.keyRelease(KeyEvent.VK_SHIFT);
				}
			}
		}
	}
	
	/**
	 * Creates a Keytype command from an input String. See usage below:
	 * <ul>
	 *   <li>{@code [type/kdown/kup] [key]} - type the input key; a list of
	 *       available keys is determined by {@link #keyMap}</li>
	 *   <li>{@code type "[string]"} - type the input string literally i.e.
	 *       case is preserved, and keys that require SHIFT are allowed</li>
	 * </ul>
	 * @param s the string to type
	 * @return the resultant Keytype
	 * @throws IllegalArgumentException if the string is not properly formatted
	 */
	public static Keytype fromString(String s) {
		String[] toks = s.split(" ");
		if (toks[0].equals("type")) {
			// this is the case where the command is <type "some string">
			if (toks.length >= 2 && toks[1].charAt(0) == '\"'
					&& s.charAt(s.length() - 1) == '\"') {
				String toType = s.substring(s.indexOf('\"') + 1, s.lastIndexOf('\"'));
				return new Keytype(toType);
			} else if (toks.length == 2) {
				String key = toks[1].toLowerCase();
				Integer code = keyMap.get(key);
				if (code == null) {
					throw new IllegalArgumentException();
				}
				return new Keytype(KeytypeMode.TYPE, code);
			} else {
				throw new IllegalArgumentException();
			}
		} else if (toks[0].equals("kdown") || toks[0].equals("kup")) {
			KeytypeMode kMode;
			if (toks[0].equals("kdown")) {
				kMode = KeytypeMode.DOWN;
			} else {
				kMode = KeytypeMode.UP;
			}
			if (toks.length == 2) {
				String key = toks[1].toLowerCase();
				Integer code = keyMap.get(key);
				if (code == null) {
					throw new IllegalArgumentException();
				}
				return new Keytype(kMode, code);
			} else {
				throw new IllegalArgumentException();
			}
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
	
	/**
	 * Generates the string representation of this command for when typing a
	 * single character/button.
	 * @return the string representation of this command
	 */
	private String generateString() {
		String command = "ERR";
		if (kMode == KeytypeMode.TYPE) {
			command = "type ";
		} else if (kMode == KeytypeMode.DOWN) {
			command = "kdown";
		} else if (kMode == KeytypeMode.UP) {
			command = "kup  ";
		}
		return command + " " + KeyEvent.getKeyText(code);
	}
	
	public enum KeytypeMode {
		TYPE,
		DOWN,
		UP
	}
}
