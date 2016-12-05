/*package coinWithdrawal;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) {
		try{
		Customer Alice = new Customer(1, BigInteger.ONE, BigInteger.valueOf(2));
		Bank bank = new Bank();
		if(!bank.checkB(Alice.createList())) {
			System.out.println("Error with B. Fraud?");
		}
		Alice.setBlindSignature(bank.signBlind());
		Alice.removeFromB();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
} */