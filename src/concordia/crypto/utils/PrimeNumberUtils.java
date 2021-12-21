/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 27-Feb-2021
 */
package concordia.crypto.utils;

import static concordia.crypto.utils.ModularArithmetic.mod;

/**
 *	This class contains the utilities related to prime numbers 
 */
public class PrimeNumberUtils {

	/**
	 *  Check if the number is prime or not by using the miller's primality test 
	 *  
	 *  @param number
	 *  @param accuracy
	 */
	public static boolean isPrime(long number, long accuracy) 
	{
		
//		Basic prime number checks 
		if (number <= 1) 
			return false;
		
		if(number == 2 || number ==3) 
			return true; 
		
		if( number%2 == 0 ) 
			return false;
		
//		check according to miller's test
		for(long i=1 ; i<=accuracy; i++) 
		{
			if( ! millerTest(number) ) 
				return false; 

		}
		
		return true; 
		
	}
	
	/**
	 *  Checks if a number is prime according to miller's test. <br>
	 *  
	 *  In miller's test we pick any random longeger a between 1 and n-1 (exclusive) <br>
	 *  According to Fermat's theorm a^n-1 = 1 (mod n) if n is prime<br>
	 *  
	 *  <br>
	 *  
	 *  a^n-1 = 1 (mod n)	<br>
	 *  a^n-1  - 1 = 0 (mod n)	<br>
	 *  
	 *  <br>
	 *  (a^(n-1)/2-1) * (a^(n-1/2)+1) = 0 (mod n)						<br>
	 *  (a^(n-1)/4-1) * (a^(n-1)/4 + 1) * (a^(n-1)/2 + 1 ) = 0 (mod n)	<br>
	 *  
	 *  <br>
	 *  We do the above steps until we get power of a as odd number d 
	 *  
	 *  <br>
	 *  (a^d - 1)(a^d + 1) .... (a^(n-1)/4-1) * (a^(n-1)/4 + 1) * (a^(n-1)/2 + 1 )  = 0 (mod n) <br>
	 *  
	 *  <br>
	 *  Now is prime if it divides any of the above factors according to Gaussian theorm
	 *  
	 *  <br>
	 *  In other words we should get remainder as 1 or -1 if we divide a^d with n and <br>
	 *  1 for rest of the powers of a <br>
	 *  
	 *  @param number
	 *  
	 */
	private static boolean millerTest(long number) 
	{
// 		calculating the d 
		long d = number -1 ;
		while( d%2 == 0 ) 
		{
			d/=2;
		}
		
//		finding a random number between 2 and number -2
		long a = (long)(Math.random() * (number -4) + 2);
				
//		calculating the a^d mod number
		long x = (long) mod(a, number, d);
		
//		if for the first time mod is 1 or -1 then its prime by miller test
		if( x == number-1 || x ==1)
			return true;
		
//		increasing the d to check for the other numbers
		d*=2;
		
		while( d <= (number-1)/2 ) 
		{
			x = (long) mod(x*x, number);  // calculating the mod of next power of d simply by squaring previous x 
			d*=2;
			
//			if the value of mod by any number is -1 then its prime
			if(x == number-1)
				return true;
		}
		
		return false;
	}
	
	
	/**
	 *  Generates a prime number of given bitsize greater than the minimum value 
	 *  
	 *   @param bitsize 
	 *   @param min 
	 */
	public static long generatePrime(long bitSize, long min) throws IllegalArgumentException
	{
		
//		if the supplied minimum value is greater than maximum possible values throw exception
		if(min > Math.pow(2, bitSize)-1) 
		{
			throw new IllegalArgumentException("Minimum value is greater than max possible value");
		}
		
		long n = 0;
		
		while ( !isPrime(n,40) | bitSize(n)!= bitSize) 
		{
			n = (long)(Math.random() * (Math.pow(2, bitSize) -1) + min) ; // generating a random number between specified range
		}
		
		return n;
	}
	
	/**
	 *  Calculates the bit count of the given number
	 * @param num
	 * @return
	 */
	public static int bitSize(long num) {
		return Long.toBinaryString(num).length();
	}
}
