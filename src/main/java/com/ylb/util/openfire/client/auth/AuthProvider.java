package com.ylb.util.openfire.client.auth;

public interface AuthProvider {
    void authenticate(String var1, String var2);

    String getPassword(String var1);

    boolean setPassword(String var1, String var2);

    boolean checkPassword(String testPassword, String encryptedPassword, String salt, String storedKey);

    boolean supportsPasswordRetrieval();

    boolean isScramSupported();

    String getSalt(String var1);

    int getIterations(String var1);

    String getServerKey(String var1);

    String getStoredKey(String var1);

    Long getModifyDate(String var1);
    
}
