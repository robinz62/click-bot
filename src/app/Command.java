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
	void execute(Robot r);
	
	/**
	 * Returns a valid String representation of the command. This is
	 * guaranteed to parse correctly if entered into the command interface.
	 * @return a parse-able String represenation of the command.
	 */
	default String getStringCmd() {
		throw new UnsupportedOperationException();
	}
}
