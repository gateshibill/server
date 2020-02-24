package com.ylb.util.openfire.client.auth;

import javax.security.sasl.SaslException;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

public class DefaultAuthProvider implements AuthProvider {
    private static final String LOAD_PASSWORD = "SELECT plainPassword,encryptedPassword FROM ofUser WHERE username=?";
    private static final String TEST_PASSWORD = "SELECT plainPassword,encryptedPassword,iterations,salt,storedKey,serverKey FROM ofUser WHERE username=?";
    private static final String UPDATE_PASSWORD = "UPDATE ofUser SET plainPassword=?, encryptedPassword=?, storedKey=?, serverKey=?, salt=?, iterations=? WHERE username=?";
    private static final SecureRandom random = new SecureRandom();
    private static final int defaultIterations = 4096;
    private static UserInfo userInfo;

    public DefaultAuthProvider() {
        this.userInfo = new UserInfo();
    }

    private DefaultAuthProvider.UserInfo getUserInfo(String username) {
        return this.getUserInfo(username, false);
    }

    private DefaultAuthProvider.UserInfo getUserInfo(String username, boolean recurse) {
        return userInfo;
//        if (!this.isScramSupported()) {
//            throw new UnsupportedOperationException();
//        } else {
//            Connection con = null;
//            PreparedStatement pstmt = null;
//            ResultSet rs = null;
//
//            try {
//                con = DbConnectionManager.getConnection();
//                pstmt = con.prepareStatement("SELECT plainPassword,encryptedPassword,iterations,salt,storedKey,serverKey FROM ofUser WHERE username=?");
//                pstmt.setString(1, username);
//                rs = pstmt.executeQuery();
//                if (!rs.next()) {
//                    throw new UserNotFoundException(username);
//                } else {
//                    DefaultAuthProvider.UserInfo userInfo = new DefaultAuthProvider.UserInfo();
//                    userInfo.plainText = rs.getString(1);
//                    userInfo.encrypted = rs.getString(2);
//                    userInfo.iterations = rs.getInt(3);
//                    userInfo.salt = rs.getString(4);
//                    userInfo.storedKey = rs.getString(5);
//                    userInfo.serverKey = rs.getString(6);
//                    if (userInfo.encrypted != null) {
//                        try {
//                            userInfo.plainText = AuthFactory.decryptPassword(userInfo.encrypted);
//                        } catch (UnsupportedOperationException var13) {
//                        }
//                    }
//
//                    if (!recurse && userInfo.plainText != null) {
//                        boolean scramOnly = JiveGlobals.getBooleanProperty("user.scramHashedPasswordOnly");
//                        if (scramOnly || userInfo.salt == null) {
//                            this.setPassword(username, userInfo.plainText);
//                            DefaultAuthProvider.UserInfo var8 = this.getUserInfo(username, true);
//                            return var8;
//                        }
//                    }
//
//                    DefaultAuthProvider.UserInfo var16 = userInfo;
//                    return var16;
//                }
//            } catch (SQLException var14) {
//                Log.error("User SQL failure:", var14);
//                throw new UserNotFoundException(var14);
//            } finally {
//                DbConnectionManager.closeConnection(rs, pstmt, con);
//            }
//        }
    }

    public String getSalt(String username) {
        return this.getUserInfo(username).salt;
    }

    public int getIterations(String username) {
        return this.getUserInfo(username).iterations;
    }

    public String getStoredKey(String username) {
        return this.getUserInfo(username).storedKey;
    }

    public String getServerKey(String username) {
        return this.getUserInfo(username).serverKey;
    }

    public Long getModifyDate(String username) {
        return this.getUserInfo(username).modifyDate;
    }

    public void authenticate(String username, String password) {
        if (username != null && password != null) {
            username = username.trim().toLowerCase();
            if (username.contains("@")) {
                int index = username.indexOf("@");
                String domain = username.substring(index + 1);
//                if (!domain.equals(XMPPServer.getInstance().getServerInfo().getXMPPDomain())) {
//                    throw new UnauthorizedException();
//                }

                username = username.substring(0, index);
            }

//            try {
                if (!this.checkPassword(username, password)) {
//                    throw new UnauthorizedException();
                }
//            }
//            catch (UserNotFoundException var5) {
//                throw new UnauthorizedException();
//            }
        } else {
//            throw new UnauthorizedException();
        }
    }

    public String getPassword(String username) {
        return this.getUserInfo(username).encrypted;
//        return AuthFactory.decryptPassword(this.getUserInfo(username).encrypted);

//        if (!this.supportsPasswordRetrieval()) {
//            throw new UnsupportedOperationException();
//        } else {
//            Connection con = null;
//            PreparedStatement pstmt = null;
//            ResultSet rs = null;
//            String encrypted;
//            if (username.contains("@")) {
//                int index = username.indexOf("@");
//                encrypted = username.substring(index + 1);
//                if (!encrypted.equals(XMPPServer.getInstance().getServerInfo().getXMPPDomain())) {
//                    throw new UserNotFoundException();
//                }
//
//                username = username.substring(0, index);
//            }
//
//            try {
//                con = DbConnectionManager.getConnection();
//                pstmt = con.prepareStatement("SELECT plainPassword,encryptedPassword FROM ofUser WHERE username=?");
//                pstmt.setString(1, username);
//                rs = pstmt.executeQuery();
//                if (!rs.next()) {
//                    throw new UserNotFoundException(username);
//                } else {
//                    String plainText = rs.getString(1);
//                    encrypted = rs.getString(2);
//                    String var7;
//                    if (encrypted != null) {
//                        try {
//                            var7 = AuthFactory.decryptPassword(encrypted);
//                            return var7;
//                        } catch (UnsupportedOperationException var12) {
//                        }
//                    }
//
//                    if (plainText == null) {
//                        throw new UnsupportedOperationException();
//                    } else {
//                        var7 = plainText;
//                        return var7;
//                    }
//                }
//            } catch (SQLException var13) {
//                throw new UserNotFoundException(var13);
//            } finally {
//                DbConnectionManager.closeConnection(rs, pstmt, con);
//            }
//        }
    }

    public boolean checkPassword(String testPassword, String encryptedPassword, String salt, String storedKey) {
        boolean result = false;
        int iterations = 4096;
        if (testPassword != null && encryptedPassword != null && salt != null && storedKey != null) {
            byte[] testStoredKey;

            String plainText = AuthFactory.decryptPassword(encryptedPassword);
            if (!plainText.equals(testPassword)) {
                return result;
            }

            try {
                byte[] saltShaker = DatatypeConverter.parseBase64Binary(salt);
                byte[] saltedPassword = ScramUtils.createSaltedPassword(saltShaker, testPassword, iterations);
                byte[] clientKey = ScramUtils.computeHmac(saltedPassword, "Client Key");
                testStoredKey = MessageDigest.getInstance("SHA-1").digest(clientKey);
            } catch (NoSuchAlgorithmException | SaslException var23) {
//                        Log.warn("Unable to check SCRAM values for PLAIN authentication.");
                return result;
            }

            result = DatatypeConverter.printBase64Binary(testStoredKey).equals(storedKey);
        }

        return result;
    }

    public boolean checkPassword(String username, String testPassword) {
        return false;
//        Connection con = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        String encrypted;
//        if (username.contains("@")) {
//            int index = username.indexOf("@");
//            encrypted = username.substring(index + 1);
//            if (!encrypted.equals(XMPPServer.getInstance().getServerInfo().getXMPPDomain())) {
//                throw new UserNotFoundException();
//            }
//
//            username = username.substring(0, index);
//        }
//
//        boolean var12;
//        try {
//            con = DbConnectionManager.getConnection();
//            pstmt = con.prepareStatement("SELECT plainPassword,encryptedPassword,iterations,salt,storedKey,serverKey FROM ofUser WHERE username=?");
//            pstmt.setString(1, username);
//            rs = pstmt.executeQuery();
//            if (!rs.next()) {
//                throw new UserNotFoundException(username);
//            }
//
//            String plainText = rs.getString(1);
//            encrypted = rs.getString(2);
//            int iterations = rs.getInt(3);
//            String salt = rs.getString(4);
//            String storedKey = rs.getString(5);
//            if (encrypted != null) {
//                try {
//                    plainText = AuthFactory.decryptPassword(encrypted);
//                } catch (UnsupportedOperationException var22) {
//                }
//            }
//
//            boolean scramOnly;
//            if (plainText == null) {
//                if (salt != null && iterations != 0 && storedKey != null) {
//                    byte[] saltShaker = DatatypeConverter.parseBase64Binary(salt);
//                    byte[] saltedPassword = null;
//                    byte[] clientKey = null;
//                    Object var14 = null;
//
//                    byte[] testStoredKey;
//                    try {
//                        byte[] saltedPassword = ScramUtils.createSaltedPassword(saltShaker, testPassword, iterations);
//                        byte[] clientKey = ScramUtils.computeHmac(saltedPassword, "Client Key");
//                        testStoredKey = MessageDigest.getInstance("SHA-1").digest(clientKey);
//                    } catch (NoSuchAlgorithmException | SaslException var23) {
//                        Log.warn("Unable to check SCRAM values for PLAIN authentication.");
//                        boolean var16 = false;
//                        return var16;
//                    }
//
//                    boolean var15 = DatatypeConverter.printBase64Binary(testStoredKey).equals(storedKey);
//                    return var15;
//                }
//
//                Log.warn("No available credentials for checkPassword.");
//                scramOnly = false;
//                return scramOnly;
//            }
//
//            scramOnly = JiveGlobals.getBooleanProperty("user.scramHashedPasswordOnly");
//            if (scramOnly) {
//                this.setPassword(username, plainText);
//            }
//
//            var12 = testPassword.equals(plainText);
//        } catch (SQLException var24) {
//            Log.error("User SQL failure:", var24);
//            throw new UserNotFoundException(var24);
//        } finally {
//            DbConnectionManager.closeConnection(rs, pstmt, con);
//        }
//
//        return var12;
    }

    public boolean generateAuthInfo(String username, String password) {
        String encryptedPassword = null;
        String storedKey = null;
        String serverKey = null;

        int iterations = defaultIterations;
        byte[] saltShaker = new byte[24];
        random.nextBytes(saltShaker);
        String salt = DatatypeConverter.printBase64Binary(saltShaker);

        try {
            byte[] saltedPassword = ScramUtils.createSaltedPassword(saltShaker, password, iterations);
            byte[] clientKey = ScramUtils.computeHmac(saltedPassword, "Client Key");
            byte[] storedKeyByte = MessageDigest.getInstance("SHA-1").digest(clientKey);
            byte[] serverKeyByte = ScramUtils.computeHmac(saltedPassword, "Server Key");

            storedKey = DatatypeConverter.printBase64Binary(storedKeyByte);
            serverKey = DatatypeConverter.printBase64Binary(serverKeyByte);
        } catch (NoSuchAlgorithmException | SaslException var24) {
//            Log.warn("Unable to persist values for SCRAM authentication.");
            return false;
        }

        try {
            encryptedPassword = AuthFactory.encryptPassword(password);
            password = null;
        } catch (UnsupportedOperationException var23) {
            return false;
        }

        this.userInfo.username = username;
        this.userInfo.plainText = password;
        this.userInfo.salt = salt;
        this.userInfo.iterations = iterations;
        this.userInfo.encrypted = encryptedPassword;
        this.userInfo.storedKey = storedKey;
        this.userInfo.serverKey = serverKey;
        this.userInfo.modifyDate = (new Date()).getTime();

        return true;
    }

    public boolean setPassword(String username, String password) {
        return this.generateAuthInfo(username, password);
//        boolean usePlainPassword = JiveGlobals.getBooleanProperty("user.usePlainPassword");
//        boolean scramOnly = JiveGlobals.getBooleanProperty("user.scramHashedPasswordOnly");
//        String encryptedPassword = null;
//        String salt;
//        if (username.contains("@")) {
//            int index = username.indexOf("@");
//            salt = username.substring(index + 1);
//            if (!salt.equals(XMPPServer.getInstance().getServerInfo().getXMPPDomain())) {
//                throw new UserNotFoundException();
//            }
//
//            username = username.substring(0, index);
//        }
//
//        byte[] saltShaker = new byte[24];
//        random.nextBytes(saltShaker);
//        salt = DatatypeConverter.printBase64Binary(saltShaker);
//        int iterations = (Integer)ScramSha1SaslServer.ITERATION_COUNT.getValue();
//        byte[] saltedPassword = null;
//        byte[] clientKey = null;
//        byte[] storedKey = null;
//        byte[] serverKey = null;
//
//        try {
//            byte[] saltedPassword = ScramUtils.createSaltedPassword(saltShaker, password, iterations);
//            byte[] clientKey = ScramUtils.computeHmac(saltedPassword, "Client Key");
//            storedKey = MessageDigest.getInstance("SHA-1").digest(clientKey);
//            serverKey = ScramUtils.computeHmac(saltedPassword, "Server Key");
//        } catch (NoSuchAlgorithmException | SaslException var24) {
//            Log.warn("Unable to persist values for SCRAM authentication.");
//        }
//
//        if (!scramOnly && !usePlainPassword) {
//            try {
//                encryptedPassword = AuthFactory.encryptPassword(password);
//                password = null;
//            } catch (UnsupportedOperationException var23) {
//            }
//        }
//
//        if (scramOnly) {
//            encryptedPassword = null;
//            password = null;
//        }
//
//        Connection con = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            con = DbConnectionManager.getConnection();
//            pstmt = con.prepareStatement("UPDATE ofUser SET plainPassword=?, encryptedPassword=?, storedKey=?, serverKey=?, salt=?, iterations=? WHERE username=?");
//            if (password == null) {
//                pstmt.setNull(1, 12);
//            } else {
//                pstmt.setString(1, password);
//            }
//
//            if (encryptedPassword == null) {
//                pstmt.setNull(2, 12);
//            } else {
//                pstmt.setString(2, encryptedPassword);
//            }
//
//            if (storedKey == null) {
//                pstmt.setNull(3, 12);
//            } else {
//                pstmt.setString(3, DatatypeConverter.printBase64Binary(storedKey));
//            }
//
//            if (serverKey == null) {
//                pstmt.setNull(4, 12);
//            } else {
//                pstmt.setString(4, DatatypeConverter.printBase64Binary(serverKey));
//            }
//
//            pstmt.setString(5, salt);
//            pstmt.setInt(6, iterations);
//            pstmt.setString(7, username);
//            pstmt.executeUpdate();
//        } catch (SQLException var21) {
//            throw new UserNotFoundException(var21);
//        } finally {
//            DbConnectionManager.closeConnection(pstmt, con);
//        }

    }

    public boolean supportsPasswordRetrieval() {
        return false;
//        boolean scramOnly = JiveGlobals.getBooleanProperty("user.scramHashedPasswordOnly");
//        return !scramOnly;
    }

    public boolean isScramSupported() {
        return true;
    }

    private class UserInfo {
        String username;
        String plainText;
        String encrypted;
        int iterations;
        String salt;
        String storedKey;
        String serverKey;
        Long modifyDate;

        private UserInfo() {
        }
    }
}
