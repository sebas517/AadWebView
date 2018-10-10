package org.izv.aad.aadwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActividadPrincipal extends AppCompatActivity {

    static final String TAG = "MITAG";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        webView = findViewById(R.id.wvMoodle);
        webView.getSettings().setJavaScriptEnabled(true);

        InterfaceAplicacionWeb iaw = new InterfaceAplicacionWeb();
        Preferencias pref = new Preferencias(this);
        webView.addJavascriptInterface(iaw, "puente");
        webView.addJavascriptInterface(pref, "preferencias");
        Boolean bandera = false;
        String usuario="";
        String pass="";
        if (pref.obtenerCredenciales() != "null"){
            bandera = true;
            String[] credenciales = pref.obtenerCredenciales().split(Preferencias.getGlue());
            usuario = credenciales[0];
            pass = credenciales[1];
            System.out.println("Estos son las cred ahora: " + usuario + "------" + pass);
        }
        webView.loadUrl("http://www.juntadeandalucia.es/averroes/centros-tic/18700098/moodle2/login/index.php");

        final Boolean finalBandera = bandera;
        final String finalUsuario = usuario;
        final String finalPass = pass;
        webView.setWebViewClient(new WebViewClient() {
            int contador = 0;
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                Log.v(TAG, url);
                if(contador == 0) {
                    Log.v(TAG, "on page finished");
                    final String javaScript;
                    if (finalBandera){
//                        javaScript = "" +
//                                "var boton = document.getElementById('loginbtn');" +
//                                "boton.addEventListener('click', function() {" +
//                                "    var nombre = document.getElementById('username').value;" +
//                                "    var clave  = document.getElementById('password').value;" +
//                                "    puente.sendData(nombre, clave);" +
//                                "    preferencias.sendData(nombre, clave);" +
//                                "});";
                        javaScript = "" +
                                "document.getElementById('username').value='"+ finalUsuario +"';" +
                                "document.getElementById('password').value='"+ finalPass +"';" +
                                "document.getElementById('login').submit()";

                    }else {
                        javaScript = "" +
                                "var boton = document.getElementById('loginbtn');" +
                                "boton.addEventListener('click', function() {" +
                                "    var nombre = document.getElementById('username').value;" +
                                "    var clave  = document.getElementById('password').value;" +
                                "    puente.sendData(nombre, clave);" +
                                "    preferencias.sendData(nombre, clave);" +
                                "});";

                    }
                    webView.loadUrl("javascript: " + javaScript);

                }
                contador++;
            }
        });
    }
}
