package tn.krh.savsamsung.fragments.signin;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.profile.ProfileActivity;
import tn.krh.savsamsung.activities.signup.SignUp;
import tn.krh.savsamsung.retrofit.INodeJS;
import tn.krh.savsamsung.retrofit.RetroClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFrag extends Fragment  {
    //implements GoogleApiClient.OnConnectionFailedListener
    Context mContext;
    EditText email,password;
    SignInButton signInButton;
    Button SignInNrml;
    private GoogleApiClient googleApiClient;
    Button SignUpBtn;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    private static final int SIGN_IN = 1;
    public SignInFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        email = view.findViewById(R.id.emailSignUp);
        password = view.findViewById(R.id.pwdSignIn);
        SignUpBtn = view.findViewById(R.id.SignUpBtn);
        SignInNrml = view.findViewById(R.id.SignInNrmlBtn);

         /*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage((FragmentActivity) getContext(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API , gso).build();

        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });

         */
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUp.class));
            }
        });
        SignInNrml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getContext(),"vérifier vos informations svp",Toast.LENGTH_SHORT).show();
                }
                else
                    loginUser(email.getText().toString(),password.getText().toString());
            }
        });
        return view;
    }
    private void loginUser(String email, String password) {
        compositeDisposable.add(myApi.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("password")){
                            //Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                                mPreference = getContext().getSharedPreferences(sharedPrefFile,getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = mPreference.edit();
                                editor.putString("emailUser",email);
                                editor.putInt("UserSession",1);
                                editor.apply();
                                Toast.makeText(getContext(),"Connexion avec succés",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), ProfileActivity.class));

                        }
                        else Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }    /*
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }else{
                Toast.makeText(getContext(),"Login with google api failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

     */
}
