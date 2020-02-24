package com.ylb.util.openfire.client.auth;

public class AuthToken {
    private static final long serialVersionUID = 2L;
    private final String username;


    public static AuthToken generateUserToken(String username) {
        if (username != null && !username.isEmpty()) {
            return new AuthToken(username);
        } else {
            throw new IllegalArgumentException("Argument 'username' cannot be null.");
        }
    }

    public static AuthToken generateAnonymousToken() {
        return new AuthToken((String)null);
    }

    public static AuthToken generateOneTimeToken(String token) {
        return new AuthToken.OneTimeAuthToken(token);
    }

    /** @deprecated */
    @Deprecated
    public AuthToken(String jid) {
        if (jid == null) {
            this.username = null;
        } else {
            int index = jid.indexOf("@");
            if (index > -1) {
                this.username = jid.substring(0, index);
            } else {
                this.username = jid;
            }

        }
    }

    /** @deprecated */
    @Deprecated
    public AuthToken(String jid, Boolean anonymous) {
        if (jid != null && (anonymous == null || !anonymous)) {
            int index = jid.indexOf("@");
            if (index > -1) {
                this.username = jid.substring(0, index);
            } else {
                this.username = jid;
            }

        } else {
            this.username = null;
        }
    }

    public String getUsername() {
        return this.username;
    }

//    /** @deprecated */
//    @Deprecated
//    public String getDomain() {
//        return (String)XMPPServerInfo.XMPP_DOMAIN.getValue();
//    }

    public boolean isAnonymous() {
        return this.username == null;
    }

    public static class OneTimeAuthToken extends AuthToken {
        public OneTimeAuthToken(String token) {
            super(token);
        }
    }
}
