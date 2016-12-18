package HA5_B4;

import java.math.BigInteger;
import java.util.Arrays;

public class Encode {

	public Encode() {

	}

	public String encode(String Integer) {

		String input = new BigInteger(Integer).toString(16);

		//padding incase the lenght is not even.
		if (input.length() % 2 == 1) {
			input = "0" + input;
		}

		byte[] intInByte = fromHextoByteArray(input);
		String first = fromByteArrayToHex(intInByte).substring(0, 1);

		String[] a = { "8", "9", "A", "B", "C", "D", "E", "F" };

		boolean padding = false;

		if (Arrays.asList(a).contains(first)) {
			padding = true;
		}
		//if-satser which declares lenght and then adds the correct "size" parameter for the encodment followed by the value in hex
		if (intInByte.length < 127) {

			if (padding) {
				return ("02" + String.format("%02X", intInByte.length + 1) + "00" + fromByteArrayToHex(intInByte));

			} else {
				return ("02" + String.format("%02X", intInByte.length) + fromByteArrayToHex(intInByte));

			}

		} else if (intInByte.length < 255) {

			if (padding) {
				return ("02" + "81" + String.format("%02X", intInByte.length + 1) + "00" + fromByteArrayToHex(intInByte));

			} else {
				return ("02" + "81" + String.format("%02X", intInByte.length) + fromByteArrayToHex(intInByte));

			}

		} else if (intInByte.length < 65535) {
			if (padding) {
				return ("02" + "82" + String.format("%04X", intInByte.length + 1) + "00" + fromByteArrayToHex(intInByte));

			} else {
				return ("02" + "82" + String.format("%04X", intInByte.length) + fromByteArrayToHex(intInByte));

			}

		} else {
			return ("Size is not compatible with this program");
		}

	}

	public byte[] fromHextoByteArray(String input) {
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


	public String fromByteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

}