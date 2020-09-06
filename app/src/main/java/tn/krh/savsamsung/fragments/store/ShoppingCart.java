package tn.krh.savsamsung.fragments.store;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.savsamsung.Adapters.commande.ShoppingItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.database.AppDataBase;
import tn.krh.savsamsung.entity.shoppingCart;
import tn.krh.savsamsung.retrofit.INodeJS;
import tn.krh.savsamsung.retrofit.RetroClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCart extends Fragment {
    Context mContext;
    INodeJS myApi;
    Button ConfirmCMDShoppingCart,DeleteAllShoppingItemsBtn;
    TextView prixTTInput;
    @SerializedName("ShoppingCartList")
    @Expose
    List<shoppingCart> shoppingCarts = new ArrayList<>();
    RecyclerView shoppingCartRVList;
    private AppDataBase database;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ShoppingCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        //set buttons
        ConfirmCMDShoppingCart = view.findViewById(R.id.ConfirmCMDShoppingCart);
        DeleteAllShoppingItemsBtn = view.findViewById(R.id.DeleteAllShoppingItemsBtn);
        database = AppDataBase.getAppDatabase(getActivity());
        mPreference = getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
        int user_id = mPreference.getInt("IdUser",0);
        String email = mPreference.getString("emailUser","");
        prixTTInput = view.findViewById(R.id.PRIXTTShoppingCart);
        if(prixTTInput.getText().toString().equals("")){
            prixTTInput.setText("aucun produit selectioné");
        }
        //set data for shopping cart list
        shoppingCarts = database.userDao().getShoppingCartItems(user_id);
        if(shoppingCarts.size() == 0){
            if(prixTTInput.getText().toString().equals("")){
                prixTTInput.setText("aucun produit selectioné");
            }
        }
        else {
            List<shoppingCart> newShoppingCList = shoppingCarts;
            int TT = 0;
            for (shoppingCart item:shoppingCarts){
                TT += item.getPrixTT();
                prixTTInput.setText(String.valueOf(TT) + " DT");
            }

        }

        //Set adapter for RC Shopping Items
        shoppingCartRVList = view.findViewById(R.id.ShoppingCartRVList);
        ShoppingItemsAdapter SitemsAdapter = new ShoppingItemsAdapter(mContext,shoppingCarts);
        shoppingCartRVList.setAdapter(SitemsAdapter);
        shoppingCartRVList.setLayoutManager(new LinearLayoutManager(mContext));
        SitemsAdapter.notifyDataSetChanged();
        //Delete shopping item
        DeleteAllShoppingItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove int
                database = AppDataBase.getAppDatabase(mContext);
                database.userDao().deleteAllShoppingCartItemsForUser(user_id);
                SitemsAdapter.RefreshDataOnDeleteItem(shoppingCarts);
                SitemsAdapter.notifyDataSetChanged();
                prixTTInput.setText("aucun produit selectioné");
            }
        });
        //confirm commande
        ConfirmCMDShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shoppingCarts.size() == 0) Toast.makeText(getContext(),"aucun produit selectionné",Toast.LENGTH_SHORT).show();
                else {
                    for (shoppingCart item:shoppingCarts){
                        Random random = new Random();
                        int max = 10000;
                        int min = 0001;
                        int generated_num = random.nextInt(max - min) + min;
                        Retrofit retrofit = RetroClient.getInstance();
                        myApi = retrofit.create(INodeJS.class);
                        compositeDisposable.add(myApi.AddNewCommand(item.getUserId(),generated_num,item.getProduitId(),item.getProduitQuantite())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {

                                        Toast.makeText(getContext(),"nous enverrons un mail pour votre commande",Toast.LENGTH_SHORT).show();
                                        String message = "mail test de confirmation de votre commande"+generated_num;
                                        String subject = "Confirmation de commande REF:"+generated_num;
                                        /*
                                        //send Mail
                                        JavaMailAPI javaMailAPI = new JavaMailAPI(mContext,email,subject,message);
                                        javaMailAPI.execute();
                                                                               */
                                    }
                                })
                        );
                    }
                }

            }
        });
        return view;
    }


}
