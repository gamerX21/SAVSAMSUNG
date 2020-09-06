package tn.krh.savsamsung.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroClient extends AppCompatActivity {
    private  static Retrofit instance;
    public static String adr;
    public static Retrofit getInstance(){
        if(instance == null)
            instance = new Retrofit.Builder().baseUrl("http://10.0.2.2:1337")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return instance;
    }
}
