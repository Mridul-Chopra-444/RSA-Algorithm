/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 27-Feb-2021
 */
package concordia.crypto.utils;

import java.util.List;

/**
 *  Contains all the utilities related to modular arithmetic
 */
public class ModularArithmetic {

	/**
	 *  Return the remainder when a is divided by b or 
	 *  return a mod b
	 *  @param a 
	 *  @param b
	 */
	public static long mod(long a , long b) 
	{
		return a >= 0 ? a%b : b - -a%b;
	}
	
	
	/**
	 *  Return the  a^powA mod b. Uses square and multiply technique
	 *  to calculate the result.
	 *  
	 *  @param a 
	 *  @param b
	 *  @param powA i.e power of a 
	 */
	public static long mod(long a, long b, long powA) 
	{	
		// in case the power is negative we need to get inverse
		if( powA < 0) {
			powA = -powA;
			a = inverse(a,b);
		}
		
		
//		converting the power to binary representation
		String pow = Integer.toBinaryString((int) powA);
		
//		Initialize the final result and current modulus to be used in loop
		long result = 1;
		long currentMod = mod(a,b);
		
//		Iterate the pow string from reverse
		for( int i = pow.length()-1; i>=0; i--) 
		{
			
//			get the boolean value present at that place
			int bool = Integer.parseInt(pow.charAt(i)+"");
			
			// multiply with result if boolean value is 1
			if ( bool == 1 ) 
			{
				result *= bool*currentMod;
				result = mod(result, b);
			}
//		update the current mod by squaring and getting new value
			currentMod = mod(currentMod*currentMod,b);
			
		}
		
		return mod(result,b);
		
	}
 
	/**
	 *  Calculates the moudulus inverse of a wrt n i.e.
	 *  
	 *  a^-1 mod n
	 *  
	 *  
	 *  @param a 
	 *  @param n
	 */
	public static long inverse(long a, long n) 
	{
		a = mod(a,n); // in case a > n or a < 0
		
		// inverse only exists if gcd is 1 
		if( gcd(a,n)!=1 )
			throw new IllegalArgumentException("Inverse does not exist for a = "+a+ " and n = "+n);
		
		// initialize variables which we will update in loops
		long x = n, y = a, p = 0, q =1;
		long rem = x%y, que = x/y;
		
		while(rem > 0) 
		{
			 x = y;
			 y = rem;
			 
			 long prevQ = q;
			 q = p - que * q;
			 p = prevQ;
			 
			 rem = x%y;
			 que = x/y;
		}
		
		return mod(q,n); // return the inverse mod is done in case we get negative number
	}
	
	
	/**
	 *  Calcluate the Greatest Common divisor of a and b.
	 *  calculates gcd according to euclidian algorithm
	 */
	public static long gcd(long a, long b) {
		
		if( a == 0)
			return b;
		
		if( b == 0)
			return a;
		
		return a > b ? gcd(b, a%b) : gcd(a , b%a);
		
	}
	
	/**
	 *  Solves the given given list of modular equations 
	 *  using Chinese Remainder Theorm (CRT)
	 *  
	 *  @param eqns equations to be computed
	 *  @param mod the modulus for the resultant equation
	 *  @return result using crt
	 */
	public static long crt( List< List<Long> > eqns, long mod) 
	{
		long result = 0;
		
		for( List<Long> eqn : eqns)
		{
			
			long tempMod = eqn.get(1); // mod for the current part
			long p1 = eqn.get(0); // part 1 contains the multiplications
			long p2 = 1; // part 2 contains multiplication of inverses
			
			// calculating p1 and p2
			for( List<Long> eq : eqns)
			{
				if(eq == eqn)
					continue;
				p1 *= eq.get(1);
				p2 *= inverse(eq.get(1), tempMod);
			}
			
			// adding to the final result
			result += mod(p1 * p2, mod);
		}
		
		return mod(result, mod); // mod with mod of resultant equation
	}
	
}
