package coinWithdrawal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Customer {
    private int[] quadruple;
    private int id;
    private MessageDigest md;
    private ArrayList<BigInteger> signatures, list, bList;
    private ArrayList<Integer> randomI;
    private BigInteger x, y, e, n, modn;
    private ArrayList<int[]> twoKQuad;

    public Customer() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-1");
        id = 1;
        this.signatures = signatures;
        this.randomI = randomI;
        this.e = e;
        this.n = n;
        init();
    }

    private void init(){
        ArrayList<int[]> list = new ArrayList<>();
        int counter = 0;
        Random rand = new Random();
        while (counter < 2000) {
            int[] temp = new int[4];
            for(int i = 0; i < temp.length; i++){
                temp[i] = rand.nextInt(1000);
            }
            list.add(temp);
        }
        counter++;
        this.twoKQuad = list;
    }

    public BigInteger calcX(int a, int c) {
        String outputY = Integer.toString(a + c);
        md.update(outputY.getBytes());
        BigInteger output = new BigInteger(md.digest());
        return output;
    }

    public BigInteger calcY(int a, int d) {
        int temp = a ^ d;
        String outputX = Integer.toString(temp + d);
        md.update(outputX.getBytes());
        BigInteger output = new BigInteger(md.digest());
        return output;
    }

    public void calcB() {
        for (int[] array : twoKQuad) {
            x = calcX(array[0], array[1]);
            y = calcY(array[0], array[2]);
            BigInteger calcTemp = mathoperation(x, y);
            BigInteger R = BigInteger.valueOf(array[3]).pow(e.intValue());
            BigInteger B = R.multiply(calcTemp).mod(modn);

            bList.add(B);
        }
    }

    private BigInteger mathoperation(BigInteger x, BigInteger y) {
        BigInteger a = x.xor(y);
        //String str = String.format("%040x", a);
        //System.out.println(a)
        return a;
    }

}
