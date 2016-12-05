package HA3_B2;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class MathOperations {

	public MathOperations(double[] poly, double[] members, double[] share, double[] points) {
		PolynomialFunction pvtpoly = new PolynomialFunction(poly);
		share[0] = pvtpoly.value(1);

		double myPoint = 0;
		for (double d : share) {
			myPoint += d;

		}
		
		//Lagrange Interpolation
		points[0] = myPoint;
		double deactivationCode = 0;
		for (int i = 0; i < points.length; i++) {
			double interpolation = 1;
			for (int j = 0; j < members.length; j++) {
				if (i != j) {
					interpolation *= ((members[j]) / (members[j] - members[i]));

				}

			}
			deactivationCode += interpolation * points[i];
		}
		System.out.println("Code: " + (int) deactivationCode);
	}
}