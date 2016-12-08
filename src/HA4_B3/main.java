package HA4_B3;

import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Insert mgfSeed: ");
		String mgfSeed = sc.next(); //seed from which mask is generated, an octet string
		System.out.println("Insert maskLen");
		int maskLen = sc.nextInt(); //intended length in octets of the mask, at most 2^32 hLen
		OAEP oaep = new OAEP(mgfSeed, maskLen);
	}

}
