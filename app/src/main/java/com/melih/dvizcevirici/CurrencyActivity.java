package com.melih.dvizcevirici;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyActivity extends AppCompatActivity {
    TextView trytext,cadtext,jpytext,usdtext,cnytext,gbptext;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        trytext = findViewById(R.id.texttry);
        cadtext = findViewById(R.id.textcad);
        usdtext = findViewById(R.id.textusd);
        jpytext = findViewById(R.id.textjpy);
        cnytext = findViewById(R.id.textcny);
        gbptext = findViewById(R.id.textgbp);

       MobileAds.initialize(this,"ca-app-pub-9849003097005236~7656962463");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void getrates(View view){
        DownloadData downloadData = new DownloadData();

        try{
            String url = "http://data.fixer.io/api/latest?access_key=9818e5cd970a048f5df2d473c8a5b7ca&format=1";
            downloadData.execute(url);
        } catch (Exception e){

        }
    }

    private class DownloadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStream.read();

                while (data >0){
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                return result;
            } catch (Exception e){
                Toast.makeText(CurrencyActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println(base);
                String rates = jsonObject.getString("rates");
                System.out.println(rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String tryy= jsonObject1.getString("TRY");
                trytext.setText("TRY: " + tryy);

                String cad= jsonObject1.getString("CAD");
                cadtext.setText("CAD: " + cad);

                String usd= jsonObject1.getString("USD");
                usdtext.setText("USD: " + usd);

                String jpy= jsonObject1.getString("JPY");
                jpytext.setText("JPY: " + jpy);

                String cny = jsonObject1.getString("CNY");
                cnytext.setText("CNY:" +cny);

                String gbp = jsonObject1.getString("GBP");
                gbptext.setText("GBP:" +gbp);
            }catch (Exception e){
                Toast.makeText(CurrencyActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
            }

        }
    }
}
