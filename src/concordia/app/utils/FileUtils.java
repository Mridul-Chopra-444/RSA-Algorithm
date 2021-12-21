/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 28-Feb-2021
 */
package concordia.app.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 *  Contains all the utility methods required for file reading and writitng
 */
public class FileUtils{
	
	public static Properties readProperties(String fileName) throws Exception
	{
		
		FileInputStream file = new FileInputStream(fileName);
		Properties props  = new Properties();
		
		if(file == null)
			throw new FileNotFoundException(fileName);
		
		props.load(file);			
		return props;
	}
	
	public static void writeProperties(Properties props, String fileName) throws IOException
	{
		Path path = Paths.get(fileName);
		
		String toWrite = "\n";
		for( String property : props.stringPropertyNames()) 
		{
			toWrite += property + "=" + props.getProperty(property) +"\n";
		}
		
		Files.write(path, toWrite.getBytes(), StandardOpenOption.APPEND);
	}

}
