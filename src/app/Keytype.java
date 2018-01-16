package app;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public final class Keytype implements Command {

	private final int code;
	private final int[] codeSequence;
	private final KeytypeMode kMode;
	private final String asString;
	
	private static final Map<String, Integer> keyMap;
	
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
		
		keyMap.put("0", KeyEvent.VK_0);
		keyMap.put("1", KeyEvent.VK_1);
		keyMap.put("2", KeyEvent.VK_2);
		keyMap.put("3", KeyEvent.VK_3);
		keyMap.put("4", KeyEvent.VK_4);
		keyMap.put("5", KeyEvent.VK_5);
		keyMap.put("6", KeyEvent.VK_6);
		keyMap.put("7", KeyEvent.VK_7);
		keyMap.put("8", KeyEvent.VK_8);
		keyMap.put("9", KeyEvent.VK_9);
		
		keyMap.put(",", KeyEvent.VK_COMMA);
		keyMap.put(".", KeyEvent.VK_PERIOD);
		keyMap.put(";", KeyEvent.VK_SEMICOLON);
		keyMap.put("'", KeyEvent.VK_QUOTE);
		keyMap.put("[", KeyEvent.VK_OPEN_BRACKET);
		keyMap.put("]", KeyEvent.VK_CLOSE_BRACKET);
		keyMap.put("\\", KeyEvent.VK_BACK_SLASH);
		keyMap.put("`", KeyEvent.VK_BACK_QUOTE);
		keyMap.put("-", KeyEvent.VK_MINUS);
		keyMap.put("=", KeyEvent.VK_EQUALS);
		
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
	}
	
	public Keytype(KeytypeMode kMode, int code) {
		this.kMode = kMode;
		this.code = code;
		codeSequence = null;
		asString = generateString();
	}
	
	public Keytype(String s) {
		this.kMode = null;
		code = -1;
		codeSequence = new int[s.length()];
		for (int i = 0; i < codeSequence.length; ++i) {
			codeSequence[i] = KeyEvent.getExtendedKeyCodeForChar(s.charAt(i));
		}
		asString = "type \"" + s.toUpperCase() + "\"";
	}
	
	@Override
	public void execute(Robot r) {
		if (codeSequence == null) {
			if (kMode == KeytypeMode.TYPE) {
				r.keyPress(code);
				r.keyRelease(code);
			} else if (kMode == KeytypeMode.DOWN) {
				r.keyPress(code);
			} else if (kMode == KeytypeMode.UP) {
				r.keyRelease(code);
			}
		} else {
			for (int c : codeSequence) {
				r.keyPress(c);
				r.keyRelease(c);
			}
		}
	}

	public static Keytype fromString(String s) {
		String[] toks = s.split(" ");
		if (toks[0].equals("type")) {
			if (toks.length >= 2 && toks[1].charAt(0) == '\"'
					&& s.charAt(s.length() - 1) == '\"') {
				String toType = s.substring(s.indexOf('\"') + 1, s.lastIndexOf('\"'));
				for (char c : toType.toCharArray()) {
					if (!Character.isLetterOrDigit(c)) {
						throw new IllegalArgumentException();
					}
				}
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
