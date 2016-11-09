package Websec;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Martin on 2016-11-08.
 */
public class MathOperations {

	public static int valueOfX(String cardNbr, int indexOfX) {
		int total = 0;
		int checksum = Integer.parseInt(String.valueOf(cardNbr.substring(15)));

		for (int i = 0; i < 15; i++) {
			int temp = Integer.parseInt(cardNbr.substring(i, i + 1));
			if (i % 2 != 0) {
				temp *= 2;
				if (temp >= 10) {
					temp -= 9;
				}
			}
			total += temp;
		}

		total %= 10;
		total = Math.abs(total -= checksum);
		if (indexOfX % 2 != 0) {
			total /= 2;
		}

		return total;
	}

	/**
	 * WORKS
	 **/
	public static int checksum(String cardNbr) {
		int total = 0;

		for (int i = 0; i < 16; i++) {
			int temp = Integer.parseInt(cardNbr.substring(i, i + 1));
			if (i % 2 != 0) {
				temp *= 2;
				if (temp >= 10) {
					temp -= 9;
				}
			}
			total += temp;
		}

		total %= 10;

		return total;
	}

}