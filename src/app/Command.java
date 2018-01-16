package app;
import java.awt.Robot;

/**
 * An interface that specifies an action that can be executed.
 * @author Robin Zhang
 *
 */
public interface Command {
	
	/**
	 * Executes a command given a Robot.
	 * @param r the robot that will perform the command.
	 */
	public void execute(Robot r);
}
