import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class App {
	static String ITOA64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static int hashLenght = 55;
	final static int min = 7;
    final static int max = 30;
    final static int baseLog = 7; 

	public static void main(String[] args) throws NoSuchAlgorithmException {
		try {
			String passwordToCrypt = "dos";
			System.out.println("Result: "+encryptText(passwordToCrypt));
		}catch (Exception ex) {
			System.out.println("err: "+ex);
		}
	}

	public static String encryptText(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return crypt(text, generateSetting());
	}
	public static String generateSetting() throws NoSuchAlgorithmException {
		StringBuilder output = new StringBuilder();
		output.append("$S$")
		.append(ITOA64.charAt(baseLog))
		.append(base64Encode(randomBytes(6), 6));
		return output.toString();
	}
	public static String randomBytes(int count) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[count];
		SecureRandom.getInstanceStrong().nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}
	public static String crypt(String password, String storedPsw) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String setting =storedPsw.substring(0, 12); 
		int laps = 1 << getRoundsValidated(setting.substring(3, 4));
		System.out.println("laps:"+laps);
		String salt = setting.substring(4, 12);
		String base = hash(salt.concat(password));
		do {
			base = hash(base.concat(password));
		}while(--laps>0);
		String output = setting.concat(base64Encode(base, base.length()));
		return output.substring(0, hashLenght);
	}
	public static int getRoundsValidated(String caracter) {
		int rounds = ITOA64.indexOf(caracter);
		if(rounds < min)
			return min;
		if(rounds > max)
			return max;
		return rounds;
	}
	public static String hash(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		byte[] hash = digest.digest(
				text.getBytes(StandardCharsets.ISO_8859_1));
		return new String(hash, "ISO_8859_1");
	}
	public static String base64Encode(String input, int count) {
		String output = "";
		int i = 0;
		do {
			int value = (int)input.charAt(i++);
			output += ITOA64.charAt(value & 0x3f);
			
			if (i < count) {
				value |= (int)input.charAt(i) << 8;
			}
			output += ITOA64.charAt((value >> 6) & 0x3f);
			if (i++ >= count) {
		        break;
		    }
			if (i < count) {
		        value |= (int)input.charAt(i) << 16;
		      }
		      output += ITOA64.charAt((value >> 12) & 0x3f);
		      if (i++ >= count) {
		        break;
		      }
		      output += ITOA64.charAt((value >> 18) & 0x3f);
					
		}while(i < count);
		return output;
	}
}