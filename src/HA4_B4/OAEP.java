package HA4_B4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OAEP {
	private MessageDigest md;
	private int k;

	public OAEP() {
		k = 128;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public void decode(String EM) {
		// Få EM och få ut en decodad version utav EM

		// Steg 1: Om L inte används så kör lHash = Hash (L)
		byte[] ConvertedEM = fromHextoByteArray(EM);
		String L = "";
		md.update(fromHextoByteArray(L));
		byte[] lHash = md.digest();
		byte[] maskedSeed = new byte[md.getDigestLength()];
		byte[] maskedDB = new byte[k - md.getDigestLength() - 1];

		// Steg 2: Seperera meddelandet till en singel oktet Y så att EM = Y ||
		// maskedSeed || maskedDB
		System.arraycopy(ConvertedEM, 1, maskedSeed, 0, maskedSeed.length);
		System.arraycopy(ConvertedEM, maskedSeed.length + 1, maskedDB, 0, maskedDB.length);

		// Steg 3: Låt seedMask = MGF(maskedDB, hLen)
		byte[] seedMask = MGF(maskedDB, md.getDigestLength());

		// Steg 4: Låt seed = maskedSeed \xor seedmask
		byte[] seed = xor(maskedSeed, seedMask);

		// Steg 5: låt dbMask = MGF(seed, k - hlen -1)
		byte[] dbMask = MGF(seed, k - md.getDigestLength() - 1);

		// Steg 6: låt DB = maskedDB \xor dbMask
		byte[] DB = xor(maskedDB, dbMask);

		// Steg 7: separera DB till EN oktet sträng lHash' av längden hLen
		// en paddingsträng, och meddelandet M DB = lHash' || PS || 0x01 || M
		Byte b = new Byte((byte) 0x01);
		byte[] M = null;
		for (int i = lHash.length; i < DB.length; i++) {
			Byte a = DB[i];
			if (a.compareTo(b) == 0) {
				M = new byte[DB.length - i - 1];
				System.arraycopy(DB, i + 1, M, 0, M.length);
			}
		}

		System.out.println("Decoded string: " + fromByteArrayToHex(M));
		// System.out.println("maskedSeed: " + fromByteArrayToHex(maskedSeed));
		// System.out.println("maskedDB: " + fromByteArrayToHex(maskedDB));

	}

	public void encode(String M, String seed) { // s 21-22. för beskrivning
		// Ta två parametrar och få ut EM. Längden ska vara 128 byte lång.

		// Steg 1: Ska en tom L-sträng och det låt detta vara lHash = Hash (L)
		String L = "";

		byte[] Mbytes = fromHextoByteArray(M);

		md.update(fromHextoByteArray(L));

		byte[] lHash = md.digest();
		// Steg 2: Genererra padding sträng PS consisting of k - mLen - 2hLen -
		// 2 zero octets
		byte[] PS = new byte[k - Mbytes.length - 2 * md.getDigestLength() - 2];

		// Steg 3: Sätt ihop lHash, ps, en singel oktet 0x01 och meddelandet M.
		byte[] temp = link(link(lHash, PS), link(new byte[] { 0x01 }, Mbytes));

		// Steg 4: Generera en random oktet sträng med längeden raandom
		byte[] DB = new byte[k - md.getDigestLength() - 1];

		System.arraycopy(temp, 0, DB, 0, temp.length);

		byte[] convertedSeed = fromHextoByteArray(seed);

		// Steg 5: dbMask = MGF(seed, k - hlen- 1)
		byte[] dbMask = MGF(convertedSeed, (k - md.getDigestLength() - 1));

		// Steg 6: maskedDB = DB \xor dbMask
		byte[] maskedDB = xor(DB, dbMask);

		// Steg 7: seedMask = MGF(maskedDB, hLen)
		byte[] seedMask = MGF(maskedDB, md.getDigestLength());

		// Steg 8: maskedSeed = seed \xor seedmask
		byte[] maskedSeed = xor(convertedSeed, seedMask);

		// Steg 9: SKapa en singel octet med hexadecimalerna EM = 0x00 ||
		// maskedSeed || maskedDB med längden k
		byte[] EM = link(link(new byte[] { 0x00 }, maskedSeed), maskedDB);

		System.out.println("Encoded string: " + fromByteArrayToHex(EM));
		// System.out.println("maskedSeed: " + toHex(maskedSeed));
		// System.out.println("maskedDB: " + toHex(maskedDB));
		// System.out.println("-----");

	}

	private byte[] fromHextoByteArray(String input) {
		if ((input.length() % 2) != 0)
			throw new IllegalArgumentException("Input string must contain an even number of characters");

		final byte result[] = new byte[input.length() / 2];
		final char enc[] = input.toCharArray();
		for (int i = 0; i < enc.length; i += 2) {
			StringBuilder sb = new StringBuilder(2);
			sb.append(enc[i]).append(enc[i + 1]);
			result[i / 2] = (byte) Integer.parseInt(sb.toString(), 16);
		}
		return result;
	}

	private String fromByteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	private byte[] encryption(byte[] byteTemp, int intTemp) { // C = I2OSP
																// (counter, 4)
																// // .
		md.reset();
		md.update(byteTemp);
		md.update(new byte[3]);
		md.update((byte) intTemp);
		byte[] digest = md.digest();
		return digest;

	}

	private byte[] link(byte[] a, byte[] b) {
		byte[] temp = new byte[a.length + b.length];
		System.arraycopy(a, 0, temp, 0, a.length);
		System.arraycopy(b, 0, temp, a.length, b.length);
		return temp;
	}

	private byte[] xor(byte[] a, byte[] b) {
		byte[] XORD = new byte[a.length];
		for (int i = 0; i < XORD.length; i++) {
			XORD[i] = (byte) (a[i] ^ b[i]);
		}
		return XORD;
	}

	private byte[] MGF(byte[] seed, int maskLen) {
		int ceil = (maskLen + md.getDigestLength() - 1) / md.getDigestLength();
		byte[] T = new byte[0];
		for (int i = 0; i < ceil; i++) {
			T = link(T, encryption(seed, i));
		}
		byte[] output = new byte[maskLen];
		System.arraycopy(T, 0, output, 0, output.length);
		return output;
	}

}
