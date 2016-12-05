package HA3_B2;

public class main {
	
	public static void main(String args[]) {

		double[] poly = { 9, 19, 5 };

		double[] members = { 1, 4, 5};
		
		double[] shared = { 0, 37, 18, 40, 44, 28};

		double[] points = { 0, 1385, 2028};

		MathOperations Scheme = new MathOperations(poly, members, shared, points);

	}

}