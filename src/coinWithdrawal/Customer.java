package coinWithdrawal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Customer {
	private int [] quadruple;
	private int id;
	private MessageDigest md;
	private ArrayList<BigInteger>signatures, list;
	private ArrayList<Integer>randomI;
	private BigInteger x, y, e, n, modn;

	public Customer() throws NoSuchAlgorithmException{
		md = MessageDigest.getInstance("SHA-1");
		id = 1;
		this.signatures = signatures;
		this.randomI = randomI;
		this.e = e;
		this.n = n;
	}
	
	public void signedBlindSignatures(ArrayList<BigInteger>signatures){
		this.signatures = signatures;
	}
	
	
	
}
