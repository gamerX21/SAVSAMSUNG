package tn.krh.savsamsung.activities.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.retrofit.INodeJS;
import tn.krh.savsamsung.retrofit.RetroClient;

public class SignUp extends AppCompatActivity {
    EditText fullname,email,adresse,password,tel;
    Button SignupBtn;
    INodeJS myApi;
    RetroClient retroClient;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        adresse = findViewById(R.id.adresse);
        tel = findViewById(R.id.tel);
        SignupBtn = findViewById(R.id.SignUpBtn);
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getText().toString().equals("") || email.getText().toString().equals("") || adresse.getText().toString().equals("") || password.getText().toString().equals("") || tel.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"vérifier vos informations",Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterUser(email.getText().toString(),fullname.getText().toString(),password.getText().toString(),adresse.getText().toString(),tel.getText().toString());

                }
            }
        });
    }
    private void RegisterUser(String email, String fullname, String password,String adresse,String tel) {
        int telC = Integer.parseInt(tel);
        compositeDisposable.add(myApi.registerUser(email,fullname,password,adresse,telC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

                    }
                })
        );
    }
    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
