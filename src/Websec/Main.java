package Websec;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Martin on 2016-11-08.
 */
public class Main {

    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);


        while (scan.hasNextLine()) {
            String line = new String(scan.nextLine());
            int indexOfX = line.indexOf('X');
            line = line.replace('X', '0');

            //BigInteger cardNbr = new BigInteger(line);

            if (indexOfX == 15) {
                System.out.println(MathOperations.checksum(line));
            } else {
				System.out.println(MathOperations.valueOfX(line, indexOfX));
            }
        }
    }
}