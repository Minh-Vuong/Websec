package coinWithdrawal;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
    private ArrayList<BigInteger> bList;
    private BigInteger p, q, d, x, y, modn, m;
    public BigInteger n, e;
    private ArrayList<Customer> customers;
    private ArrayList<BigInteger[]> quadvaules;
    public ArrayList<Integer> selectedValues;
    private MessageDigest md;
    private int k;

    public Bank() throws NoSuchAlgorithmException {
        customers = new ArrayList<Customer>();
        p = new BigInteger(8, 15, new Random());
        q = new BigInteger(8, 15, new Random());
        n = q.multiply(p);
        m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        do {
            e = new BigInteger(16, new Random());
        }
        while ((e.compareTo(m) != 1) || (e.gcd(m).compareTo(BigInteger.valueOf(1)) != 0));
        d = e.modInverse(m);
        pickRandomValues();
        md = MessageDigest.getInstance("SHA-1");

    }

    public ArrayList<Integer> pickRandomValues() {
        int temp1 = 0;
        int index = 0;
        k = 1000;
        Random rand = new Random();
        ArrayList<Integer> number = new ArrayList<Integer>();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int size = 2 * k;

        for (int i = 0; i < size; i++) {
            number.add(i);
        }

        for (int l = 0; l < k; l++) {
            index = rand.nextInt(size);
            temp1 = number.get(index);
            temp.add(temp1);
            number.set(index, number.get(number.size() - 1));
            number.remove(number.size() - 1);
            size--;
        }

        selectedValues = temp;
        return selectedValues;
    }

    public boolean checkB(ArrayList<BigInteger[]> list) {
        quadvaules = list;
        BigInteger x;
        BigInteger y;
        int index = 0;
        for (BigInteger[] array : list) {
            x = calcX(array[0], array[1]);
            y = calcY(array[0], array[2]);
            BigInteger f = mathoperationF(x, y);
            BigInteger re = array[3].pow(e.intValue());
            BigInteger b = re.multiply(f).mod(n);
            if (b != bList.get(array[4].intValue())) {
                return false;
            }
            index++;
        }
        return true;
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

    private BigInteger mathoperationF(BigInteger x, BigInteger y) {
        BigInteger a = x.xor(y);
        return a;
    }

    public BigInteger signBlind() {
        ArrayList<BigInteger> blindSignatures = new ArrayList<BigInteger>();
        ArrayList<BigInteger> biToSign = bList;

        for (int i : selectedValues) {
            biToSign.remove(i);
        }

        //Begin blind signing
        BigInteger signed = null;
        for (BigInteger bi : biToSign) {
            signed = signed.multiply(bi.modPow(d, n));
        }

        blindSignatures.add(signed);
        BigInteger blindSignature = multiplyBlindSignatures(blindSignatures);
        return blindSignature;
    }

    private BigInteger multiplyBlindSignatures(ArrayList<BigInteger> blindSignatures) {
        BigInteger one = BigInteger.ONE;
        for (BigInteger b : blindSignatures) {
            one = one.multiply(b);
        }
        return one;
    }
}

