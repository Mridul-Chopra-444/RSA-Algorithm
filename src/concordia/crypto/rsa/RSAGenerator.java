/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 28-Feb-2021
 */
package concordia.crypto.rsa;

import java.io.IOException;
import java.util.Properties;

import concordia.app.utils.Loggable;
import concordia.crypto.utils.PrimeNumberUtils;

import static concordia.crypto.utils.ModularArithmetic.gcd;
import static concordia.crypto.utils.ModularArithmetic.inverse;
import static concordia.app.utils.FileUtils.readProperties;
import static concordia.app.utils.FileUtils.writeProperties;
import static concordia.crypto.utils.PrimeNumberUtils.generatePrime;
import static concordia.crypto.rsa.RSAFileConstants.*;

/**
 *  Contains all the utils for implementing RSA
 */
public class RSAGenerator extends Loggable{
	
	private String fileName ;
	private Properties props;
	private int bitSize; // bit size to be used to generate the prime numbers
	private int minValue; // minimum value each prime number must have
	
	public RSAGenerator(String applicationFile) throws Exception
	{
		
	//		Initializing the properties from the properties file
			try 
			{
				this.fileName = applicationFile;
				this.props = readProperties(applicationFile);
				String bitSize = props.getProperty(BIT_SIZE);
				String minVal = props.getProperty(MIN_PRIME_VALUE);
				
				this.bitSize = Integer.parseInt(bitSize);
				this.minValue = Integer.parseInt(minVal); 
			}
			catch(Exception e) 
			{
				error("Error occoured. Please check correct parameteres in your file. ",e);
				throw e;
			}
	}
	
	/**
	 *  Generate the p and q primes numbers, calculates n and 
	 *  writes them to the file. 
	 *  If already genereated, new p,q and n will not be generated.
	 */
	public void generateRSAPrimes() 
	{
		
//		Check if p,q and n already generated or not.
		try 
		{
			
			if ( 	props.getProperty(P)!= null || 
					props.getProperty(Q)!= null || 
					props.getProperty(N)!= null
				) 
			{
				warn("Some value of p, q or n already exists. Please remove them and then generate new ones.");
				return;
			}
		} 
		catch (Exception e1) 
		{
			error("Some error occoured while checking previous p,q and n. Terminating generation",e1);
		}
		
		long p = 0, q = 0, n = 0;
		
//		make sure that p and q are not same numbers		
		while( p == q) 
		{
			p = generatePrime(bitSize, minValue);
			q = generatePrime(bitSize, minValue);
		}
		
		n = p*q;
		
		Properties props = new Properties();
//		writing p,q and n to the file
		props.setProperty(P, p+"");
		props.setProperty(Q, q+"");
		props.setProperty(N, n+"");
		
		try 
		{
			writeProperties(props, fileName);
			info("Successfully generated p,q and n");
			updateProperties();
		} 
		catch (IOException e) 
		{
			error("Some error occoured while writing p,q and n",e);
		}
	}
	
	/**
	 *  Generates the public and private keys to be used for RSA encryption
	 *  and writes them to the file
	 */
	public void generateKeys() 
	{
		long p=0, q=0, n=0, phi=0; // variable required for e and d calculation
		long e =0, d =0 ; // private and public keys to be generated.
		
		try
		{
			
			if ( 	props.getProperty(PRIVATE_KEY)!= null || 
					props.getProperty(PUBLIC_KEY)!= null 
				) 
			{
				warn("Some value of d, e already exists. Please remove them and then generate new ones.");
				return;
			}
			
			if ( 	props.getProperty(P)== null || 
					props.getProperty(Q)== null ||
					props.getProperty(N)== null
				) 
			{
				warn("Could not find values of p,q  for key generation. Terminating process");
				return;
			}
			else
			{
				p = Long.parseLong(props.getProperty(P));
				q = Long.parseLong(props.getProperty(Q));
				n = Long.parseLong(props.getProperty(N));
			}
			
		}
		catch(Exception ex)
		{
			warn("Some error while reading p and q.");
			return;
		}
		
		phi = (p-1)*(q-1);
		
		while( gcd(e,phi) != 1)
		{
			e = (int)(Math.random()*(minValue-n-1) + n-1 );
		}
		
		d = inverse(e,phi);
		
		info("Calculated the public key and private key");
		
		Properties props = new Properties();
//		writing e, d to the file
		props.setProperty(PUBLIC_KEY, e+"");
		props.setProperty(PRIVATE_KEY, d+"");
		
		try 
		{
			writeProperties(props, fileName);
			info("Added the public and private keys");
			updateProperties();
		} 
		catch (IOException ex) 
		{
			error("Some error occoured while writing p,q and n",ex);
		}
		
	}
	
	public void updateProperties()
	{
		try 
		{
			this.props = readProperties(fileName);
		} 
		catch (Exception e) 
		{
			error("Error while updating properties",e);
		}
	}

}
