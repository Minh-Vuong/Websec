package coinWithdrawal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Customer {
    private int id;
    private MessageDigest md;
    private ArrayList<BigInteger> bList, bSend;
    private BigInteger x, y, e, n, modn, blind, serial, sign, r;
    private HashMap<BigInteger, BigInteger[]> twoKQuadruple;

    public Customer(int id, BigInteger e, BigInteger n) throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-1");
        this.id = id;
        this.e = e;
        this.n = n;
        init();
    }

    private void init() {
        HashMap<BigInteger, BigInteger[]> list = new HashMap<>();

        int counter = 0;
        Random rand = new Random();
        BigInteger B = null;
        while (counter < 2000) {
            BigInteger[] temp = new BigInteger[4];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = new BigInteger(1, rand);
                B = calcB(temp);
            }
            list.put(B, temp);
        }
        counter++;
        this.twoKQuadruple = list;
    }


    public BigInteger calcX(BigInteger a, BigInteger c) {
        a.add(c);
        String inputX = a.toString();
        BigInteger output = new BigInteger(md.digest());
        md.update(inputX.getBytes());
        return output;
    }

    public BigInteger calcY(BigInteger a, BigInteger d) {
        BigInteger temp = a.xor(d);
        String inputX = temp.add(d).toString();
        md.reset();
        md.update(inputX.getBytes());
        BigInteger output = new BigInteger(md.digest());
        return output;
    }

    private BigInteger calcB(BigInteger[] array) {
        x = calcX(array[0], array[1]);
        y = calcY(array[0], array[2]);
        BigInteger calcTemp = mathoperationF(x, y);
        BigInteger r = array[3].modPow(e, n);
        BigInteger B = r.multiply(calcTemp).mod(n);
        return B;
    }

    private BigInteger mathoperationF(BigInteger x, BigInteger y) {
        BigInteger a = x.xor(y);
        // String str = String.format("%040x", a);
        // System.out.println(a);
        return a;
    }

    public ArrayList<BigInteger> sendB(int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            bSend.add(bList.get(indexes[i]));
        }
        for (BigInteger b : bSend) {
            System.out.println(b);
        }
        return bSend;
    }


    public HashMap<BigInteger, BigInteger[]> revealValues(ArrayList<BigInteger> bValues) {
        HashMap<BigInteger, BigInteger[]> temp = new HashMap<>();
        for (BigInteger b : bValues) {
            temp.put(b, twoKQuadruple.get(b));
        }
        removeFromB(bValues);
        return temp;
    }

    private void removeFromB(ArrayList<BigInteger> bValues) {
        for (BigInteger i : bValues) {
            twoKQuadruple.remove(i);
        }
    }

    public ArrayList<BigInteger[]> createList() {
        ArrayList<BigInteger[]> list = new ArrayList<>();
        return list;
    }

    public void extractSign(ArrayList<BigInteger> bValues){
        BigInteger serial =  BigInteger.ONE;
        for(BigInteger b : bValues){
            BigInteger[] vec = twoKQuadruple.get(b);
            BigInteger x = calcX(vec[0], vec[1]);
            BigInteger y = calcX(vec[0], vec[2]);
            serial = serial.multiply(mathoperationF(x, y).modInverse(e));
        }
        this.serial = serial;
    }

    public void calcSign(BigInteger blindSign){
        sign = blindSign.multiply(BigInteger.ONE.divide(r)).mod(n);
    }

    public void setBlindSignature(BigInteger blindSignature) {
        this.blind = blindSignature;
    }
}
