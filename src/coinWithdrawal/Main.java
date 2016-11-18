package coinWithdrawal;

import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Customer Alice = new Customer();
		Bank bank = new Bank();
		Alice.calcB();
		if(!bank.checkB(Alice.createList())) {
			System.out.println("Error with B. Fraud?");
		}
		Alice.setBlindSignature(bank.signBlind());
		Alice.removeFromB();
	}
}