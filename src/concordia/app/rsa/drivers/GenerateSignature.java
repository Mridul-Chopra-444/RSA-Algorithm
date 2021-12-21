/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 05-Mar-2021
 */
package concordia.app.rsa.drivers;

import static concordia.app.utils.FileUtils.readProperties;
import static concordia.app.utils.FileUtils.writeProperties;

import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import concordia.app.utils.Loggable;
import concordia.crypto.rsa.RSA;
import static concordia.crypto.rsa.RSAFileConstants.*;

public class GenerateSignature extends Loggable
{

	public static void main(String[] args)
	{
		try 
		{
			createSign();
		} 
		catch (Exception e) 
		{
			error("Some error occoured. Please check parameters are specified correctly.",e);
		}
	}
	
	public static void createSign() throws Exception
	{
			Properties props = readProperties(APP_FILE);
			String message = null;
			
			// the message will be encrypted using receiver's public key i.e. e and n\
			BigInteger d,n;
			if(	props.getProperty(PRIVATE_KEY)!=null && 
				props.getProperty(N)!=null &&
				props.getProperty(SIGN_TEXT)!=null
				)
			{
				d = new BigInteger(props.getProperty(PRIVATE_KEY).trim());
				n = new BigInteger(props.getProperty(N).trim());
				message = props.getProperty(SIGN_TEXT);
			}
			else 
			{
				warn("Parameters for signature not specified correctly. Please check and try agian.");
				return;
			}
			
			
			RSA myRsa = new RSA(d,n);
			List<BigInteger> cipher = myRsa.signText(message);
			
			props = new Properties();
			props.setProperty(SIGNATURE, cipher.toString());
			
			writeProperties(props,APP_FILE);
			info("Signing text successfully completed.");
	}
	
}
