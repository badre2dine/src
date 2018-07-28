package utilitie;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	private static byte[] randomAESKey = null;
	private static final int AES_KEY_LENGTH = 32;
	private static final String STATIC_PUB_KEY_B64 =
		      "MIIBUzANBgkqhkiG9w0BAQEFAAOCAUAAMIIBOwKCATIAgucoka9J2PXcNdjcu6CuDmgteIMB+rih"
		      + "2UZJIuSoNT/0J/lEKL/W4UYbDA4U/6TDS0dkMhOpDsSCIDpO1gPG6+6JfhADRfIJItyHZflyXNUj"
		      + "WOBG4zuxc/L6wldgX24jKo+iCvlDTNUedE553lrfSU23Hwwzt3+doEfgkgAf0l4ZBez5Z/ldp9it"
		      + "2NH6/2/7spHm0Hsvt/YPrJ+EK8ly5fdLk9cvB4QIQel9SQ3JE8UQrxOAx2wrivc6P0gXp5Q6bHQo"
		      + "ad1aUp81Ox77l5e8KBJXHzYhdeXaM91wnHTZNhuWmFS3snUHRCBpjDBCkZZ+CxPnKMtm2qJIi57R"
		      + "slALQVTykEZoAETKWpLBlSm92X/eXY2DdGf+a7vju9EigYbX0aXxQy2Ln2ZBWmUJyZE8B58CAwEA"
		      + "AQ==";
	private static final String publicKeyBase64 =
		"MIIBUzANBgkqhkiG9w0BAQEFAAOCAUAAMIIBOwKCATIAgucoka9J2PXcNdjcu6CuDmgteIMB+rih" +
		"2UZJIuSoNT/0J/lEKL/W4UYbDA4U/6TDS0dkMhOpDsSCIDpO1gPG6+6JfhADRfIJItyHZflyXNUj" +
		"WOBG4zuxc/L6wldgX24jKo+iCvlDTNUedE553lrfSU23Hwwzt3+doEfgkgAf0l4ZBez5Z/ldp9it" +
		"2NH6/2/7spHm0Hsvt/YPrJ+EK8ly5fdLk9cvB4QIQel9SQ3JE8UQrxOAx2wrivc6P0gXp5Q6bHQo" +
		"ad1aUp81Ox77l5e8KBJXHzYhdeXaM91wnHTZNhuWmFS3snUHRCBpjDBCkZZ+CxPnKMtm2qJIi57R" +
		"slALQVTykEZoAETKWpLBlSm92X/eXY2DdGf+a7vju9EigYbX0aXxQy2Ln2ZBWmUJyZE8B58CAwEA" +
		"AQ==";
	
	
	
	
	public static String makePEM(byte[] decryptedKey) {
		String encodedKey = Base64.getEncoder().encodeToString(decryptedKey);
		int len = encodedKey.length();
		String resultKey = "";
		for(int i = 0; i < len; i += 76)
			if(i + 76 < len)	
				resultKey += encodedKey.substring(i, i + 76) + '\n';
			else
				resultKey += encodedKey.substring(i, len - 1) + '\n';
		return "-----BEGIN PUBLIC KEY-----\n" + resultKey + "-----END PUBLIC KEY-----";
	}
	
	public static byte[] encryptCredentials(byte[] decryptedKey, String login, String password, String salt) throws Exception {
		byte[] encryptedCredentials = null;
		Buffer buffer = new Buffer(new byte[8300]);
		buffer.writeUTF(salt);
		randomAESKey = generateRandomAESKey();
		buffer.writeBytes(randomAESKey);
		buffer.writeByte((byte) login.length());
		buffer.writeUTF(login);
		buffer.writeUTF(password);
		
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec spec = new X509EncodedKeySpec(decryptedKey);
			PublicKey publicKey = kf.generatePublic(spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedCredentials = cipher.doFinal(buffer.getArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedCredentials;
	}
	
	private static byte[] generateRandomAESKey() {
		byte[] key = new byte[AES_KEY_LENGTH];
		for(int i = 0; i < AES_KEY_LENGTH; ++i)
			key[i] = (byte) Math.floor(Math.random() * 256);
		return key;
	}
	
	
	

		  /**
		   * Constructeur privé de la classe utilitaire.
		   */
		  

		  /**
		   * Permet d'instancier un objet Java correspondant à la clé publique
		   * contenu dans les sources du jeu.
		   *
		   * @return L'objet java contenant la clé publique.
		   */
		  private static PublicKey getStaticPubKey() {
		    byte[] binaryKey = Base64.getDecoder().decode(STATIC_PUB_KEY_B64.getBytes());
		    KeySpec spec = new X509EncodedKeySpec(binaryKey);
		    try {
		      KeyFactory factory = KeyFactory.getInstance("RSA");
		      return factory.generatePublic(spec);
		    } catch (Exception exception) {
		      return null;
		    }
		  }

		  /**
		   * Permet de récupérer la clé publique contenu dans le HelloConnectMessage.
		   *
		   * @param pStaticPubKey La clé publique contenu dans les sources du jeu.
		   * @param pDatas La clé publique cryptée par le serveur qui est contenu dans
		   *     le HelloConnectMessage.
		   * @return L'objet java contenant la clé publique contenu dans le HelloConnectMessage.
		   */
		  public static byte[] decodeWithAES(byte[] ticket) {
				byte[] decryptedTicket = null;
				try {
					
					System.out.println(new String(randomAESKey));
					SecretKeySpec AESKeySpec = new SecretKeySpec(randomAESKey, "AES");
					
					Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
				    byte[] iv = new byte[16];
			        for(int i = 0; i < 16; i ++)
			            iv[i] = randomAESKey[i];

			        IvParameterSpec ivspec  = new IvParameterSpec(iv);
					cipher.init(Cipher.DECRYPT_MODE, AESKeySpec, ivspec);
					decryptedTicket = cipher.doFinal(ticket);
					randomAESKey = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return decryptedTicket;
			}
		  private static PublicKey decryptPubKey(final PublicKey pStaticPubKey,
		      final byte[] pDatas) {
		    try {
		      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		      cipher.init(Cipher.DECRYPT_MODE, pStaticPubKey);
		      byte[] binaryDatas = cipher.doFinal(pDatas);
		      KeySpec spec = new X509EncodedKeySpec(binaryDatas);
		      KeyFactory factory = KeyFactory.getInstance("RSA");
		      return factory.generatePublic(spec);
		    } catch (Exception exception) {
		      return null;
		    }
		  }public static byte[] decryptReceivedKey(byte[] encryptedKey) {
				byte[] resultKey = null;
				try {
				    byte[] decodedKey = Base64.getDecoder().decode(publicKeyBase64);
					X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
					KeyFactory kf = KeyFactory.getInstance("RSA");
					PublicKey pk = kf.generatePublic(spec);
					Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
					cipher.init(Cipher.DECRYPT_MODE, pk);	
					resultKey = cipher.doFinal(encryptedKey);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return resultKey;
			}

		  /**
		   * Permet de générer le credentials qui devra être envoyé dans l'IdentificationMessage.
		   *
		   * @param pPubKey La clé publique contenu dans le HelloConnectMessage
		   *     (décrypté par la méthode decryptPubKey)
		   * @param pAesKey Une clé AES généré aléatoirement qui doit avoir une taille de 32 octets.
		   * @param pSalt Le salt reçus dans le HelloConnectMessage qui doit avoir une taille de 32 octets.
		   * @param pUser Le nom d'utilisateur pour se connecter au jeu.
		   * @param pPassword Le mot de passe de l'utilisateur.
		   * @return Le credentials a envoyer dans l'IdentificationMessage.
		   */
		  private static byte[] encryptCredentials(final PublicKey pPubKey, final byte[] pAesKey,
		      final String pSalt, final String pUser, final String pPassword) {
		    try {
		      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		      cipher.init(Cipher.ENCRYPT_MODE, pPubKey);
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      CipherOutputStream stream = new CipherOutputStream(baos, cipher);
		      /**
		       * Les données doivent être crypter avec la clé publique reçus dans le HelloConnectMessage.
		       * Elles doivent avoir le format suivant :
		       *
		       * 32 octets : Salt
		       * 32 octets : Clé AES
		       * 1 octet : La taille du nom d'utilisateur
		       * Taille variable (inférieur à 255 octets) : Le nom de l'utilisateur
		       * Taille variable : Le mot de passe du compte
		       */
		      stream.write(pSalt.getBytes());
		      stream.write(pAesKey);
		      stream.write((byte) pUser.length());
		      stream.write(pUser.getBytes());
		      stream.write(pPassword.getBytes());
		      stream.close();
		      return baos.toByteArray();
		    } catch (Exception exception) {
		      return null;
		    }
		  }

		  /**
		   * Permet d'obtenir le credentials qui devra être envoyé dans l'IdentificationMessage.
		   *
		   * @param pEncodedKey La clé publique cryptée par le serveur reçus dans le HelloConnectMessage.
		   *     (données brut)
		   * @param pSalt Le salt reçus dans le HelloConnectMessage qui avoir une taille de 32 octets.
		   * @param pAesKey Une clé AES générée aléatoirement qui doit avoir une taille de 32 octets.
		   * @param pUser Le nom d'utilisateur pour se connecter au jeu.
		   * @param pPassword Le mot de passe de l'utilisateur.
		   * @return Le credentials a envoyer dans l'IdentificationMessage.
		 * @throws NoSuchAlgorithmException 
		   */
		  public static byte[] getCredentials(final byte[] pEncodedKey, final String pSalt, final String pUser, final String pPassword) throws NoSuchAlgorithmException {
		    PublicKey staticPubKey = getStaticPubKey();
		    PublicKey pubKey = decryptPubKey(staticPubKey, pEncodedKey);
		 randomAESKey=Encryption.generateRandomAESKey();
		   System.out.println(new String("\n"+randomAESKey+"\n"));
		    return encryptCredentials(pubKey, randomAESKey, pSalt, pUser, pPassword);
		  }

}