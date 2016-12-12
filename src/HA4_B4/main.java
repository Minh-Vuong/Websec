package HA4_B4;

import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Insert M: ");
		String M = sc.next(); 
		System.out.println("Insert Seed: ");
		String Seed = sc.next(); 
		System.out.println("Insert EM: ");
		String EM = sc.next(); 
		
		
		OAEP OAEP = new OAEP();
		OAEP.encode(M, Seed);
		OAEP.decode(EM);

	}

}
