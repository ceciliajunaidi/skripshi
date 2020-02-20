package org.tensorflow.lite.examples.detection.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String SESSION = "SESSION";
    private static final String KEY_NOT_FIRST_OPEN = "KEY_NOT_FIRST_OPEN";

    @SuppressLint("CommitPrefEdits")
    SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(SESSION, PRIVATE_MODE);
        editor = pref.edit();
    }

    void createSession() {
        editor.putBoolean(KEY_NOT_FIRST_OPEN, true);

        editor.commit();
    }

    boolean isAlreadyOpen() {
        return pref.getBoolean(KEY_NOT_FIRST_OPEN, false);
    }
}
