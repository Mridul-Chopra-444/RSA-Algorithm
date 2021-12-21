/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 20-Mar-2021
 */
package concordia.app.rsa.drivers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import concordia.crypto.utils.ModularArithmetic;
import concordia.crypto.utils.ModularArithmeticV2;
import concordia.crypto.utils.PrimeNumberUtils;

import static concordia.crypto.utils.ModularArithmetic.crt;

public class Driver {

	public static void main(String[] args)
	{
		System.out.println("Long : "+ModularArithmetic.mod(4745504L, 3755990093L, 2147483647L));
//		
//		BigInteger a = new BigInteger("4745504");
//		BigInteger b = new BigInteger("3755990093");
//		BigInteger c = new BigInteger("2147483647");
//		
//		System.out.println(ModularArithmeticV2.mod(a,b,c));
//		
		BigInteger z = new BigInteger("3455985627");
		System.out.println(z.multiply(z));
		System.out.println(3455985627L*3455985627L);
		
		System.out.println(PrimeNumberUtils.bitSize(3455985627L));
		
	}
	
}
