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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import concordia.app.utils.Loggable;
import concordia.crypto.rsa.RSA;

public class RSADecrypt extends Loggable
{
		public static void main(String[] args)
		{
			try 
			{
				decrypt();
			} 
			catch (Exception e) 
			{
				error("Some error occoured. Please check the parameters are entered correctly.",e);
			}
		}
			
		public static void decrypt() throws Exception
		{
				Properties props = readProperties(APP_FILE);
				String message = null;
				
				BigInteger d = null,n = null;
				BigInteger p = null,q = null;
				if(		props.getProperty(PRIVATE_KEY)!=null && 
						props.getProperty(N)!=null && 
						props.getProperty(PARTNER_MESSAGE)!=null &&
						props.getProperty(P)!=null &&
						props.getProperty(Q) != null 
					)
				{
					d = new BigInteger(props.getProperty(PRIVATE_KEY).trim());
					n = new BigInteger(props.getProperty(N).trim());
					p = new BigInteger(props.getProperty(P).trim());
					q = new BigInteger(props.getProperty(Q).trim());
					message = props.getProperty(PARTNER_MESSAGE);
				}
				else
				{
					warn("Parameters for decrytion not specifed. Please check and try again.");
				}
				
				// convert the message into BigIntegers
				List<String> msgList = Arrays.asList(message.split(",")); 
				List<BigInteger> msg = msgList.stream()
												.map(m-> new BigInteger(m.trim()))
												.collect(Collectors.toList());
				
				RSA myRsa = new RSA(d,n);
				myRsa.setPrimes(p, q); // specify p and q to decrypt using crt
				String decryptedMessage = myRsa.decrypt(msg);
				
				props = new Properties();
				props.setProperty(PARTNER_MESSAGE_DEC, decryptedMessage);
				
				writeProperties(props,APP_FILE);
				info("Decryption successfully completed.");

		}

}
