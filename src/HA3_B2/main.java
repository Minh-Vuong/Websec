package HA3_B2;

public class main {
	
	public static void main(String args[]) {

		double[] poly = { 3, 15, 17, 16, 11 };

		double[] members = { 1, 2, 3, 4, 8};
		
		double[] shared = { 0, 32, 51, 51, 35, 54, 43, 47};

		double[] points = { 0, 2622, 10731, 30900, 435936};

		MathOperations Scheme = new MathOperations(poly, members, shared, points);

	}

}