package testcutandchoose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		int temp1 = 0;
		int index = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("how many random numbers?");
		int k = sc.nextInt();
		Random rand = new Random();
		ArrayList<Integer> number = new ArrayList<Integer>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int size = 2*k; 
		
		for (int i = 0; i < size; i++){
			number.add(i);
		}
		
		for (int l = 0; l < k; l++){
			index = rand.nextInt(size);
			temp1 = number.get(index);
			temp.add(temp1);
			number.set(index, number.get(number.size()-1));
			number.remove(number.size()-1);
			size--;
		}
		
		System.out.println(temp);
	}

}
