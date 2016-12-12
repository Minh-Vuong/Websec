package HA4_B3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OAEP {
	private MessageDigest md;
	private String mgfSeed;
	private int maskLen;

	public OAEP(String mgfSeed, int maskLen) {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// step 3 in RFC. For counter from 0 to \ceil (maskLen / hLen) - 1
		int ceil = (maskLen + md.getDigestLength() - 1) / md.getDigestLength();
		byte[] convertedSeed = fromHextoByteArray(mgfSeed);
		byte[] temp = new byte[0];

		// I2OSP-part. I2OSP converts a nonnegative integer to an octet string
		// of a specified length. In our case ceil
		for (int i = 0; i < ceil; i++) {
			// 3B. Concatenate the hash of the seed mgfSeed and C to the octet
			// string "temp"
			temp = link(temp, encryption(convertedSeed, i));
		}

		byte[] output = new byte[maskLen];
		System.arraycopy(temp, 0, output, 0, output.length);

		System.out.println(fromByteArrayToHex(output));

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
																// .
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

}
