package com.oztz.hackinglabmobile.helper;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Tobi on 20.03.2015.
 */
public class JsonHelper {

    /*public void getWebsite(String url){
        new RequestTask().execute(url);
    }*/

    public String readUrl(String urlString) throws IOException {
        InputStream inStream = null;
        try{
            URL url = new URL(urlString);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setReadTimeout(5000);
            con.setConnectTimeout(5000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            inStream = con.getInputStream();
            String result = readIt(inStream);
            Log.d("DEBUG", result);
            return result;
        } finally {
            if(inStream != null){
                inStream.close();
            }
        }
    }

    public String doPost(String urlString, String jsonData){
        DefaultHttpClient httpClient = createHttpClient();
        HttpPost httpPost = new HttpPost(urlString);
        StringEntity se;
        try {
            se = new StringEntity(jsonData);
            se.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }

    }

    private DefaultHttpClient createHttpClient()
    {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 8080));
            registry.register(new Scheme("https", sf, 40010));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
    }
}


