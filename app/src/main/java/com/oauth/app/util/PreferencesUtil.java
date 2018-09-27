package com.oauth.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.oauth.app.BuildConfig;
import com.securepreferences.SecurePreferences;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.security.GeneralSecurityException;

public final class PreferencesUtil {

    private static final String PREFS_REFRESH_TOKEN = "refresh_token";
    private static final String PREFS_EXPIRES_IN = "expiresIn";

    private Context context;
    private SharedPreferences preferences;
    private final String securePreferencesName;
    private Secure secure;
    private String authTokenCached;

    public PreferencesUtil(Context context,
                           SharedPreferences preferences,
                           String securedPreferencesName) {
        this.context = context;
        this.preferences = preferences;
        securePreferencesName = securedPreferencesName;
    }

    public void saveTokenData(String token, String refreshToken,
                              Long expiresIn) {
        preferences.edit()
                .putString(PREFS_REFRESH_TOKEN, refreshToken)
                .putLong(PREFS_EXPIRES_IN, expiresIn)
                .apply();
        // Reset secure preferences
        secure = null;
        setAuthToken(token);
    }

    public void clearAll() {
        preferences.edit().clear().apply();
        getSecure().clearAll();
        authTokenCached = null;
    }

    public boolean hasAuthToken() {
        return !getAuthToken().isEmpty();
    }

    public void setAuthToken(String token) {
        authTokenCached = token;
        getSecure().setAuthToken(token);
    }

    public String getAuthToken() {
        if (authTokenCached == null) {
            authTokenCached = getSecure().getAuthToken();
        }
        return authTokenCached != null ? authTokenCached : "";
    }

    public String getRefreshToken() {
        return preferences.getString(PREFS_REFRESH_TOKEN, "").trim();
    }

    public Long getExpiresIn() {
        return preferences.getLong(PREFS_EXPIRES_IN, 0);
    }

    private Secure getSecure() {
        if (secure == null && securePreferencesName != null) {
            String password = getRefreshToken();
            if (TextUtils.isEmpty(password)) {
                password = "default";
            }
            secure = new Secure(context, securePreferencesName, password);
        }
        return secure;
    }

    private static final class Secure {
        private static final String AUTH_TOKEN = "auth_token";

        private SecurePreferences securePreferences;

        private Secure(Context context, String name, String password) {
            try {
                AesCbcWithIntegrity.SecretKeys key = AesCbcWithIntegrity
                        .generateKeyFromPassword(password, "@%^~".getBytes(), 1000);
                securePreferences = new SecurePreferences(context, key, name);
            } catch (GeneralSecurityException e) {
                if (!BuildConfig.DEBUG) {

                }
            }
        }

        private void setAuthToken(String token) {
            securePreferences.edit().putString(AUTH_TOKEN, token).apply();
        }

        private String getAuthToken() {
            return securePreferences.getString(AUTH_TOKEN, null);
        }

        private void clearAll() {
            securePreferences.edit().clear().apply();
        }
    }
}
