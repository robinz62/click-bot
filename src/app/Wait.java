package app;
import java.awt.Robot;

/**
 * A command for waiting.
 * @author Robin Zhang
 *
 */
public final class Wait implements Command {

	private final int timeInMillis;
	private final String asString;
	
	/**
	 * Creates a command that waits for the specified time.
	 * @param timeInMillis
	 */
	public Wait(int timeInMillis) {
		this.timeInMillis = timeInMillis;
		asString = "wait  " + timeInMillis;
	}
	
	@Override
	public void execute(Robot r) {
		r.delay(timeInMillis);
	}
	
	/**
	 * Creates a Wait command from an input String. See usage below:
	 * <ul>
	 *   <li>move [time] - wait for the specified time (in milliseconds)
	 * </ul>
	 * @param s the string to parse
	 * @return the resultant Wait
	 * @throws IllegalArgumentException if the string is not properly formatted
	 */
	public static Wait fromString(String s) {
		String[] toks = s.split(" ");
		if (!toks[0].equals("wait")) {
			throw new IllegalArgumentException();
		}
		if (toks.length == 2) {
			return new Wait(Integer.parseInt(toks[1]));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public String toString() {
		return asString;
	}
	
}
