/** 
 * @Author : Mridul Chopra
 * Concordia Student Id : 40164868
 * Date Created : 05-Mar-2021
 */
package concordia.crypto.rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import concordia.app.utils.Loggable;
import concordia.app.utils.StringUtils;
import static concordia.crypto.utils.ModularArithmeticV2.*;

/**
 * This class is used to encrypt or decrypt the RSA message. 
 */
public class RSA extends Loggable{
	
	private BigInteger privateKey = null;
	private BigInteger modulus = null;
	private BigInteger publicKey = null;
	private BigInteger p= null ,q= null;
	
	public RSA() {
	}
	
	/**
	 *  @param long privateKey
	 *  @param long modulus
	 */
	public RSA(BigInteger privateKey, BigInteger modulus) 
	{
		this.privateKey = privateKey;
		this.modulus = modulus;
	}
	
	/**
	 * Encrypt the message to be sent using the public key (e,n)
	 * 
	 * @param msg : message to encrypt
	 * @param e : receiver's e
	 * @param n : receiver's n
	 */
	public List<BigInteger> encrypt(String msg, BigInteger e, BigInteger n) 
	{
		List<BigInteger> chunks = StringUtils.stringToInt(msg,3);
		
		List<BigInteger> encryptedList = chunks.stream()
										.map( chunk -> mod(chunk,n,e))
										.collect(Collectors.toList());
		
		System.out.println("Plain Msg in chunks : "+ chunks.toString());
		
		return encryptedList;
	}

	
	/**
	 *  Decrypt the message received using the private key of receiver
	 *  
	 *  @param cipher : Cipher text received
	 *  @param d : private key of the receiver
	 *  @param n : receiver's n
	 */
	public String decrypt(List<BigInteger> cipher) throws IllegalStateException
	{
		if(modulus == null || privateKey == null)
		{
			throw new IllegalStateException("Please initialize modulus and private Key first");
		}
		
		
		if( p != null & q != null)  // if p and q are set
			return crtDecrypt(cipher); // use crt to decrypt as its faster
		
		List<BigInteger> decrypted = cipher.stream()
											.map( text -> mod(text, modulus, privateKey ))
											.collect(Collectors.toList());
	
		System.out.println("Decrypted Message in Chunks = "+decrypted.toString());
		String text = StringUtils.intToString(decrypted);
		return text;
	}
	
	/**
	 *  Decrypts the message using Chinese Remainder Theorem
	 *  
	 *  @param cipher list of cipher integers to be decrypted
	 *  @return text Decrypted cipher text
	 */
	private String crtDecrypt(List<BigInteger> cipher)
	{
		List<BigInteger> mp = new ArrayList<BigInteger>();
		mp.add(null);
		mp.add(p);
		
		List<BigInteger> mq = new ArrayList<BigInteger>();
		mq.add(null);
		mq.add(q);
		
		
		List<BigInteger> decrypted = new ArrayList<BigInteger>();
		for(BigInteger text : cipher) 
		{
			// done mod of private key with p-1 and q-1
			// by using fermat's theorm here to make it faster			
			mp.set( 0, mod( text,p, mod(privateKey, p.subtract(BigInteger.ONE)) ) );
			mq.set( 0, mod( text,q, mod(privateKey, q.subtract(BigInteger.ONE)) ) );
			
			List<List<BigInteger>> eqns = new ArrayList<>();
			eqns.add(mp);
			eqns.add(mq);
			
			// decrypting using crt
			decrypted.add( crt(eqns, modulus) );
		}
		System.out.println("Decypted Msg in chunks "+decrypted.toString());
		String text = StringUtils.intToString(decrypted);
		return text;
	}
	
	/**
	 *  Creates a digital signature of the given text 
	 *   @param text : text to create digital signature of 
	 *   @return list of integers
	 */
	public List<BigInteger> signText(String text) throws IllegalStateException
	{
		if(modulus == null || privateKey == null)
		{
			throw new IllegalStateException("Please initialize modulus and private Key first");
		}
		
		return encrypt(text, privateKey, modulus);
	}

	/**
	 *  Return the signature after applying the public key
	 *  
	 *  @param sign
	 *  @param publicKey
	 *  @param modulus
	 *  
	 *  @return signature text decrypted
	 */
	public String verifySignature(List<BigInteger> sign, BigInteger publicKey, BigInteger modulus)
	{
		List<BigInteger> decrypted = sign.stream()
				.map( text -> mod(text, modulus, publicKey ))
				.collect(Collectors.toList());
		
		System.out.println("Partner Signature verification chunks : "+decrypted);
		
		String text = StringUtils.intToString(decrypted);
		return text;
	}
	/**
	 * @return the privateKey
	 */
	public BigInteger getPrivateKey() {
		return privateKey;
	}

	/**
	 * @param privateKey the privateKey to set
	 */
	public void setPrivateKey(BigInteger privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * @return the modulus
	 */
	public BigInteger getModulus() {
		return modulus;
	}

	/**
	 * @param modulus the modulus to set
	 */
	public void setModulus(BigInteger modulus) {
		this.modulus = modulus;
	}

	/**
	 * @return the publicKey
	 */
	public BigInteger getPublicKey() {
		return publicKey;
	}

	/**
	 * @param publicKey the publicKey to set
	 */
	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * @return the p
	 */
	public BigInteger getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(BigInteger p) {
		this.p = p;
	}

	/**
	 * @return the q
	 */
	public BigInteger getQ() {
		return q;
	}

	/**
	 * @param q the q to set
	 */
	public void setQ(BigInteger q) {
		this.q = q;
	}
	
	/**
	 *  Sets the primes p and q 
	 *  @param p 
	 *  @param q
	 */
	public void setPrimes(BigInteger p, BigInteger q)
	{
		this.p = p;
		this.q = q;
	}
	
	
}
