package org.izv.aad.aadwebview;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

import static org.izv.aad.aadwebview.ActividadPrincipal.TAG;

public class Preferencias {
    private Context context;
    private static String glue = "0a0a0a0a0a0a0a0a";

    public Preferencias(Context context) {
        this.context = context;
    }

    public static String getGlue(){
        return glue;
    }

    public void guardarCredenciales(String usuario, String pass) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("credenciales", usuario + glue + pass);
        editor.apply();
    }
    public String obtenerCredenciales() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String v = pref.getString("credenciales", "null");
        return v;
    }
    @JavascriptInterface
    public void sendData(String usuario, String pass) {
        guardarCredenciales(usuario, pass);
        Log.v(TAG, "guardamos en preferences....");
    }
}
