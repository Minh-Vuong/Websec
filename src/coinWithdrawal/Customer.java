/*package coinWithdrawal;

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
	private BigInteger x, y, e, n, modn, blind;
	private HashMap<BigInteger, BigInteger[]> twoKQuadruple;

	public Customer(int id, BigInteger e, BigInteger n) throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("SHA-1");
		this.id = id;
		this.e = e;
		this.n = n;
		init();
	}
	
    private void init(){
        HahsMap<BigInteger, BigInteger[]> list = new ArrayList<>();
        int counter = 0;
        Random rand = new Random();
        while (counter < 2000) {
            BigInteger[] temp = new BigInteger[4];
            for(int i = 0; i < temp.length; i++){
                temp[i] = new BigInteger(1, rand);
            }
            list.add(temp);
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

	public void calcB() {
		for (BigInteger[] array : twoKQuadruple) {
			x = calcX(array[0], array[1]);
			y = calcY(array[0], array[2]);
			BigInteger calcTemp = mathoperationF(x, y);
			BigInteger r = array[3].modPow(e, n);
			BigInteger B = r.multiply(calcTemp).mod(modn);

			bList.add(B);
		}
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
	
	
	public ArrayList<BigInteger> revealValues(ArrayList<BigInteger>bValues){
		ArrayList<BigInteger>temp = new ArrayList<>();
		for(BigInteger b : bValues){
			temp.add(twoKQuadruple.get(index));
		}
		return temp;
		
	}
	
	public void removeFromB(){
		//ArrayList<BigInteger[]> quadruples = twoKQuadruple;
		for (BigInteger[] i : quadruples) {
			quadruples.remove(i);
		}
		
		BigInteger temp1 = BigInteger.ONE;
		BigInteger temp2 = BigInteger.ONE;
		for (BigInteger[] q : quadruples) {
			BigInteger rInverse = q[3].modInverse(n);
			temp1 = temp1.multiply(rInverse);
			
			BigInteger x = calcX(q[0], q[1]);
			BigInteger y = calcY(q[0], q[2]);
			BigInteger f = mathoperationF(x, y);
			temp2 = temp2.multiply(f);
		}
	}

	public ArrayList<BigInteger[]> createList() {
		ArrayList<BigInteger[]> list = new ArrayList<>();
		return list;
	}

	public void setBlindSignature(BigInteger blindSignature) {
		this.blind = blindSignature;
	}
}
*/