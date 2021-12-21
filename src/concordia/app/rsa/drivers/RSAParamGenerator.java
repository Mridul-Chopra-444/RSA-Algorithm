/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 05-Mar-2021
 */
package concordia.app.rsa.drivers;

import concordia.app.utils.Loggable;
import concordia.crypto.rsa.RSAGenerator;
import static concordia.crypto.rsa.RSAFileConstants.*;


public class RSAParamGenerator extends Loggable{
	
	public static void main(String[] args)
	{
		try 
		{
			generate();
		} catch (Exception e) 
		{
			info("Some error occoured. Please check everything is specified correctly in file.");
		}
	}
	
	public static void generate() throws Exception
	{
			RSAGenerator myGenerator = new RSAGenerator(APP_FILE);
			myGenerator.generateRSAPrimes(); // create p, q and n 
			myGenerator.generateKeys(); // create e and d
	}

}
