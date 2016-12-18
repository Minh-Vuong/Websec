package HA5_B4;

import java.math.BigInteger;
import java.util.Base64;


public class RSA {
	private BigInteger privateKey;
	private BigInteger p, q;
	private BigInteger publicKey;
	private BigInteger coeff;
	private BigInteger n;
	private BigInteger exp1, exp2;
	private Encode ec;

	public RSA(String pString, String qString, String eString) {
		p = new BigInteger(pString);
		q = new BigInteger(qString);
		publicKey = new BigInteger(eString);

		n = p.multiply(q);
		coeff = q.modInverse(p);	

		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

		privateKey = publicKey.modInverse(m);
		exp1 = privateKey.mod(p.subtract(BigInteger.ONE));
		exp2 = privateKey.mod(q.subtract(BigInteger.ONE));
		
		/*
		String N = ec.encode(getN()); 
		String pubKey = ec.encode(getPublicKey());
		String priKey = ec.encode(getPrivateKey()); 
		String p = ec.encode(getP()); 
		String q = ec.encode(getQ()); 
		String exponent1 = ec.encode(getExponent1());
		String exponent2 = ec.encode(getExponent2());
		String coefficient = ec.encode(getCoefficient());
		
		
		String temp = "020100" + N + pubKey + priKey + p + q + exponent1 + exponent2 + coefficient;
		int length = temp.length() / 2;

		String finalResult = "3082" + String.format("%04X", length) + temp;
		String encoded = Base64.getEncoder().encodeToString((ec.fromHextoByteArray(finalResult)));
		return encoded; */
	}


	public String getPrivateKey() {
		return privateKey.toString();
	}

	public String getPublicKey() {
		return publicKey.toString();
	}

	public String getN() {
		return n.toString();
	}

	public String getCoefficient() {
		return coeff.toString();
	}

	public String getExponent1() {
		return exp1.toString();
	}

	public String getExponent2() {
		return exp2.toString();
	}

	public String getP() {
		return p.toString();
	}

	public String getQ() {
		return q.toString();
	}
}