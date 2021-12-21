/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 28-Feb-2021
 */
package concordia.app.utils;

import java.util.logging.*;

public class Loggable {

	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected static void info(String log) 
	{
		LOGGER.log(Level.INFO, log);
	}
	
	protected static void warn(String log) 
	{
		LOGGER.log(Level.WARNING, log);
	}
	
	protected static void error(String log, Exception e ) 
	{
		LOGGER.log(Level.SEVERE, log);
		LOGGER.log(Level.SEVERE, e.toString());
	}
}
