/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 06-Mar-2021
 */
package concordia.app.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StringUtils {

	/**
	 *  Converts the given string into a list of integers of the given byteSize
	 *  
	 *  @param String s : String we want to convert 
	 *  @param int byteSize : size of each partition in bytes
	 *  @return List of BigInteger
	 */
	public static List<BigInteger> stringToInt(String s, int byteSize)
	{

		List<String> chunks = divideString(s, byteSize);
		List<BigInteger> integerChunks = chunks.stream()
										.map(chunk -> toHex(chunk))
										.map(chunk -> new BigInteger(chunk+"",16))
										.collect(Collectors.toList());
		
		
		return integerChunks;
		
	}
	
	
	/**
	 *  Converts the list of integers into the String representation
	 *  @parm list : list of integer
	 *  @return string converted from int represenation
	 */
	public static String intToString(List<BigInteger> list)
	{
		
		String result = list.stream()
							.map(chunk -> chunk.toString(16) ) // convert int to hex representation
							.map(chunk -> hexToString(chunk)) // convert hex representation to string
							.collect(Collectors.joining()); // join all the Strings
		
		return result;
		
	}
	
	
	/**
	 *  Converts the given String into the hex format
	 *  @param s : String to convert to hex format
	 *  @return result : hex format of the string
	 */
	public static String toHex(String s)
	{
		String result = "";
		
		for(char c : s.toCharArray())
		{
			result += Integer.toHexString((int) c);
		}
		
		return result;
	}
	
	
	/**
	 *  Convert the hex string into the java string
	 *  
	 *  @param s : hex string to convert
	 *  @return result : the string converted from hex representation
	 */
	public static String hexToString(String s)
	{
		String result = "";
		
		for(int i=0; i<s.length(); i+=2)
		{
			String temp = s.substring(i,i+2);
			result += (char) Integer.parseInt(temp,16);
		}
		
		return result;
	}
	
	/**
	 *  Divides the string in the given equal parts. 
	 *   
	 *   @param s  : the string to be broken down
	 *   @param len : the parts to be done 
	 *   @return result : List of sivided strings. 
	 */
	public static List<String> divideString(String s, int len)
	{
		if( len <= 0  || len > s.length()) // basic check
		{
			throw new IllegalArgumentException("Length should be between 0 and length of the supplied string.");
		}
		
		List<String> result = new ArrayList<String>();
		int ptr1 =0;
		int ptr2 = ptr1 + len;
		
		while( ptr2 != s.length() ) 
		{ 
			result.add(s.substring(ptr1,ptr2)); // get the substring and add to result
			ptr1 = ptr2;
			ptr2 = ptr2 + len <= s.length() ? ptr2 + len : s.length();
		}
		
		result.add(s.substring(ptr1,ptr2)); // the last part of the string got missed in above loop so add that
		
		return result;
	}
	
}

