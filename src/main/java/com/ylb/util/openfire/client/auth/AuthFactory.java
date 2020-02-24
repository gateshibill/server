package com.ylb.util.openfire.client.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.security.sasl.SaslException;
import javax.xml.bind.DatatypeConverter;

public class AuthFactory {
//    private static final SystemProperty<String> PASSWORD_KEY = Builder.ofType(String.class).setKey("passwordKey").setDynamic(true).build();
//    public static final SystemProperty<Class> AUTH_PROVIDER = Builder.ofType(Class.class).setKey("provider.auth.className").setBaseClass(AuthProvider.class).setDefaultValue(DefaultAuthProvider.class).setDynamic(true).addListener(AuthFactory::initProvider).build();
	private static String passwordKey = "VN1TA7Pn834VIdB";
	// passwordKey VN1TA7Pn834VIdB

	private static AuthProvider authProvider = null;
	private static MessageDigest digest;
	private static final Object DIGEST_LOCK = new Object();
	private static Blowfish cipher = null;
	public static final String ONE_TIME_PROPERTY = "oneTimeAccessToken";
	private static Random randGen = new SecureRandom();
	private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			.toCharArray();
	private static final char[] zeroArray = "0000000000000000000000000000000000000000000000000000000000000000"
			.toCharArray();

	public AuthFactory() {
	}

	private static void initProvider(Class clazz) {
		if (authProvider == null || !clazz.equals(authProvider.getClass())) {
			try {
				authProvider = (AuthProvider) clazz.newInstance();
			} catch (Exception var2) {
				authProvider = new DefaultAuthProvider();
			}
		}
	}

	/** @deprecated */
	public static AuthProvider getAuthProvider() {
		return authProvider;
	}

	public static boolean isProviderInstanceOf(Class<?> c) {
		return c.isInstance(authProvider);
	}

//    public static boolean isProviderHybridInstanceOf(Class<? extends AuthProvider> clazz) {
//        return authProvider instanceof HybridAuthProvider && ((HybridAuthProvider)authProvider).isProvider(clazz);
//    }

	public static boolean supportsPasswordRetrieval() {
		return authProvider.supportsPasswordRetrieval();
	}

	public static String getPassword(String username) {
		return authProvider.getPassword(username.toLowerCase());

	}

	public static boolean setPassword(String username, String password) {
		return authProvider.setPassword(username, password);
	}

	public static boolean checkPassword(String testPassword, String encryptedPassword, String salt, String storedKey) {
//	return authProvider.checkPassword(testPassword, encryptedPassword, salt, storedKey);
//	    public boolean checkPassword(String testPassword, String encryptedPassword, String salt, String storedKey) {
		boolean result = false;
		int iterations = 4096;
		if (testPassword != null && encryptedPassword != null && salt != null && storedKey != null) {

			String plainText = AuthFactory.decryptPassword(encryptedPassword);
			if (plainText.equals(testPassword)) {
				return true;
			}

			try {
				byte[] saltShaker = DatatypeConverter.parseBase64Binary(salt);
				byte[] clientSalter = ScramUtils.createSaltedPassword(saltShaker, testPassword, iterations);
				byte[] testClientKey = ScramUtils.computeHmac(clientSalter, "Client Key");
				byte[] testStoredKey = MessageDigest.getInstance("SHA-1").digest(testClientKey);

				byte[] storedKeyByte = DatatypeConverter.parseBase64Binary(storedKey);
				result = Arrays.equals(storedKeyByte, testStoredKey);
			} catch (NoSuchAlgorithmException | SaslException var23) {
				return result;
			}
		}

		return result;
	}

	public static AuthToken authenticate(String username, String password) {
		authProvider.authenticate(username, password);
		return AuthToken.generateUserToken(username);
	}

	public static String createDigest(String token, String password) {
		synchronized (DIGEST_LOCK) {
			digest.update(token.getBytes());
			return DatatypeConverter.printHexBinary(digest.digest(password.getBytes())).toLowerCase();
		}
	}

	public static String encryptPassword(String password) {
		if (password == null) {
			return null;
		} else {
			Blowfish cipher = getCipher();
			if (cipher == null) {
				throw new UnsupportedOperationException();
			} else {
				return cipher.encryptString(password);
			}
		}
	}

	public static String decryptPassword(String encryptedPassword) {
		if (encryptedPassword == null) {
			return null;
		} else {
			Blowfish cipher = getCipher();
			if (cipher == null) {
				throw new UnsupportedOperationException();
			} else {
				return cipher.decryptString(encryptedPassword);
			}
		}
	}

	private static String randomString(int length) {
		if (length < 1) {
			return null;
		} else {
			char[] randBuffer = new char[length];

			for (int i = 0; i < randBuffer.length; ++i) {
				randBuffer[i] = numbersAndLetters[randGen.nextInt(numbersAndLetters.length)];
			}

			return new String(randBuffer);
		}
	}

	private static synchronized Blowfish getCipher() {
		if (cipher != null) {
			return cipher;
		} else {
			try {
//                String keyString = (String)PASSWORD_KEY.getValue();
				String keyString = passwordKey;
				if (keyString == null) {
					keyString = randomString(15);
//                    PASSWORD_KEY.setValue(keyString);
					passwordKey = keyString;
					if (!keyString.equals(passwordKey)) {
						return null;
					}
				}

				cipher = new Blowfish(keyString);
			} catch (Exception var2) {
//                Log.error(var2.getMessage(), var2);
			}

			return cipher;
		}
	}

	public static boolean supportsScram() {
		return authProvider.isScramSupported();
	}

	public static String getSalt(String username) {
		return authProvider.getSalt(username);
	}

	public static int getIterations(String username) {
		return authProvider.getIterations(username);
	}

	public static String getServerKey(String username) {
		return authProvider.getServerKey(username);
	}

	public static String getStoredKey(String username) {
		return authProvider.getStoredKey(username);
	}

	public static Long getModifyDate(String username) {
		return authProvider.getModifyDate(username);
	}

//    public static boolean isOneTimeAccessTokenEnabled() {
//        return org.apache.commons.lang3.StringUtils.isNotBlank(JiveGlobals.getXMLProperty("oneTimeAccessToken"));
//    }

//    public static AuthToken checkOneTimeAccessToken(String userToken)  {
//        String accessToken = JiveGlobals.getXMLProperty("oneTimeAccessToken");
//        if (isOneTimeAccessTokenEnabled() && accessToken.equals(userToken)) {
//            JiveGlobals.deleteXMLProperty("oneTimeAccessToken");
//            return AuthToken.generateOneTimeToken(accessToken);
//        } else {
//            throw new UnauthorizedException();
//        }
//    }

	static {
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException var1) {
//            Log.error(LocaleUtils.getLocalizedString("admin.error"), var1);
		}

		initProvider(DefaultAuthProvider.class);
//        initProvider((Class)AUTH_PROVIDER.getValue());
	}

	public static String getPasswordKey() {
		return passwordKey;
	}

	public static void setPasswordKey(String passwordKey) {
		AuthFactory.passwordKey = passwordKey;
	}

	public static void main(String[] args) {
		AuthFactory.setPasswordKey("VN1TA7Pn834VIdB");
		Boolean isAuthenticated = AuthFactory.checkPassword("123456",
				"6f776a2f6505bf0dd6bbd7a6f8a7b3e122a3a9c8bad276007f2df5257a8fff19", "DvaFDcNJPKOW1Lbe0/JYCgre2NOqNHF8",
				"xYYRe8HSIAQP4f8EsDj+pVVkkj8=");
		System.out.println(isAuthenticated);
	}

}
