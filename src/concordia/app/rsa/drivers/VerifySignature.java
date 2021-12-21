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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import concordia.app.utils.Loggable;
import concordia.crypto.rsa.RSA;

public class VerifySignature extends Loggable
{
	
	public static void main(String[] args)
	{
		try 
		{
			verifySign();
		} 
		catch (Exception e) 
		{
			error("Some error occoured. Please check parameters are specified correctly.",e);
		}
	}
	
	public static void verifySign() throws Exception
	{
			Properties props = readProperties(APP_FILE);
			String message = null;
			
			// the message will be encrypted using receiver's public key i.e. e and n\
			BigInteger d,n;
			
			if(	props.getProperty(PARTNER_PUBLIC_KEY)!=null && 
				props.getProperty(PARTNER_MOD)!=null &&
				props.getProperty(PARTNER_SIGN)!=null
				)
			{
				d = new BigInteger(props.getProperty(PARTNER_PUBLIC_KEY).trim());
				n = new BigInteger(props.getProperty(PARTNER_MOD).trim());
				message = props.getProperty(PARTNER_SIGN);
			}
			else 
			{
				warn("Parameters for signature not specified correctly. Please check and try agian.");
				return;
			}
			
			
			// convert the message into BigIntegers
			List<String> msgList = Arrays.asList(message.split(",")); 
			List<BigInteger> msg = msgList.stream()
											.map(m-> new BigInteger(m.trim()))
											.collect(Collectors.toList());
			
			RSA myRsa = new RSA();
			String decryptedMessage = myRsa.verifySignature(msg, d, n);
			
			props = new Properties();
			props.setProperty(PARTNER_SIGN_DEC, decryptedMessage);
			
			writeProperties(props,APP_FILE);
			info("Signature Verification successfully completed.");
	}
	
}
