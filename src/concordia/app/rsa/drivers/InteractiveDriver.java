/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 05-Mar-2021
 */
package concordia.app.rsa.drivers;

import java.util.Scanner;
import static concordia.app.rsa.drivers.RSADecrypt.decrypt;
import static concordia.app.rsa.drivers.RSAEncrypt.encrypt;
import static concordia.app.rsa.drivers.GenerateSignature.createSign;
import static concordia.app.rsa.drivers.VerifySignature.verifySign;
import static concordia.app.rsa.drivers.RSAParamGenerator.generate;



public class InteractiveDriver {

	public static void main(String[] args)
	{
		while(true)
		{
			try 
			{
				menu();
			} 
			catch (Exception e) 
			{
				System.out.println("Some error occoured. Please check everything is correctly specified in the file.");
			}
		}
		
	}
	
	private static void menu() throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Enter 1 to generate RSA parameters i.e. p,q,n and e,d");
		System.out.println("2. Enter 2 to Encrypt the message in the file.");
		System.out.println("3. Enter 3 to Decrypt the cipher received in the file");
		System.out.println("4. Enter 4 to sign the text in the file");
		System.out.println("5. Enter 5 to verify signature of the partner.");
		
		int option = sc.nextInt();
		
		switch(option)
		{
			case 1 : 
			{
				generate();
				break;
			}
			
			case 2 : 
			{
				encrypt();
				break;
			}
			
			case 3 :
			{
				decrypt();
				break;
			}
			
			case 4 :
			{
				createSign();
				break;
			}
			
			case 5 :
			{
				verifySign();
				break;
			}
		}
	}
}
