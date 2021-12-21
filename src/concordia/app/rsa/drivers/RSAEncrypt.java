/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 05-Mar-2021
 */
package concordia.app.rsa.drivers;

import static concordia.app.utils.FileUtils.readProperties;
import static concordia.app.utils.FileUtils.writeProperties;
import static concordia.crypto.rsa.RSAFileConstants.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

import concordia.app.utils.Loggable;
import concordia.crypto.rsa.RSA;

public class RSAEncrypt extends Loggable
{
	public static void main(String[] args)
	{
		try 
		{
			encrypt();
		} 
		catch (Exception e) 
		{
			error("Some error occoured. Please check parameters are specified correctly.",e);
		}
	}
	
	public static void encrypt() throws Exception
	{
			Properties props = readProperties(APP_FILE);
			String message = null;
			
			// the message will be encrypted using receiver's public key i.e. e and n\
			BigInteger e,n;
			if(
				props.getProperty(PARTNER_PUBLIC_KEY)!=null && 
				props.getProperty(PARTNER_MOD)!=null &&
				props.getProperty(MESSAGE)!=null
				)
			{
				e = new BigInteger(props.getProperty(PARTNER_PUBLIC_KEY).trim());
				n = new BigInteger(props.getProperty(PARTNER_MOD).trim());
				message = props.getProperty(MESSAGE);
			}
			else 
			{
				warn("Parameters for encryption not specified correctly. Please check and try agian.");
				return;
			}
			
			
			RSA myRsa = new RSA();
			List<BigInteger> cipher = myRsa.encrypt(message, e, n);
			
			props = new Properties();
			props.setProperty(MESSAGE_ENC, cipher.toString());
			
			writeProperties(props,APP_FILE);
			info("Encryption successfully completed.");
	}
	
}
