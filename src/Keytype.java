import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public final class Keytype implements Command {

	private final int code;
	private final int[] codeSequence;
	private final String asString;
	
	private static final Map<String, Integer> keyMap;
	
	static {
		keyMap = new HashMap<>();
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
	
	public Keytype(char c) {
		code = KeyEvent.getExtendedKeyCodeForChar(c);
		codeSequence = null;
		asString = "type " + c;
	}
	
	public Keytype(int code) {
		this.code = code;
		codeSequence = null;
		asString = "type " + KeyEvent.getKeyText(code);
	}
	
	public Keytype(String s) {
		code = -1;
		codeSequence = new int[s.length()];
		for (int i = 0; i < codeSequence.length; ++i) {
			codeSequence[i] = KeyEvent.getExtendedKeyCodeForChar(s.charAt(i));
		}
		asString = "type \"" + s + "\"";
	}
	
	@Override
	public void execute(Robot r) {
		if (codeSequence == null) {
			r.keyPress(code);
			r.keyRelease(code);
		} else {
			for (int c : codeSequence) {
				r.keyPress(c);
				r.keyRelease(c);
			}
		}
	}

	public static Keytype fromString(String s) {
		String[] toks = s.split(" ");
		if (!toks[0].equals("type")) {
			throw new IllegalArgumentException();
		}
		if (toks.length >= 2 && toks[1].charAt(0) == '\"'
				&& s.charAt(s.length() - 1) == '\"') {
			String toType = s.substring(s.indexOf('\"') + 1, s.lastIndexOf('\"'));
			return new Keytype(toType);
		} else if (toks.length == 2) {
			String key = toks[1].toLowerCase();
			if (key.length() == 1) {
				return new Keytype(key.charAt(0));
			} else {
				Integer code = keyMap.get(key);
				if (code == null) {
					throw new IllegalArgumentException();
				}
				return new Keytype(keyMap.get(key));
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toString() {
		return asString;
	}
	
}
