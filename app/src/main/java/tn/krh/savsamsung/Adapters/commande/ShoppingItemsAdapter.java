package tn.krh.savsamsung.Adapters.commande;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.DiffCallback.ShoppingItemsDiffCallback;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.commande.Commande;
import tn.krh.savsamsung.activities.store.DetailsStoreItem;
import tn.krh.savsamsung.database.AppDataBase;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.entity.shoppingCart;
import tn.krh.savsamsung.entity.shoppingCart;
import tn.krh.savsamsung.retrofit.INodeJS;

public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ViewHolder> implements Filterable {
    public List<shoppingCart> shoppingCart;
    public List<shoppingCart> shoppingCart_filtred;
    public SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    Context mContext;
    private AppDataBase database;
    INodeJS myApi;
    public ShoppingItemsAdapter(Context context, List<shoppingCart> shoppingCarts) {
        this.shoppingCart = shoppingCarts;
        this.mContext = context;
    }
    public void RefreshDataOnDeleteItem (List<shoppingCart> newShList)
    {
        ShoppingItemsDiffCallback shoppingItemsDiffCallback = new ShoppingItemsDiffCallback(this.shoppingCart,newShList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(shoppingItemsDiffCallback);
        this.shoppingCart.clear();
        this.shoppingCart.addAll(newShList);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Pnom;
        public TextView Pprix;
        public TextView Pquantite;
        public TextView PrixTT;
        public ImageButton DeleteShoppingItemBtn;
        public SharedPreferences mPreference;
        public int TT=0;
        public ViewHolder(final View itemView) {
            super(itemView);
            Pnom = itemView.findViewById(R.id.ShoppingItemProduitNom);
            Pprix = itemView.findViewById(R.id.ShoppingItemProduitPrix);
            Pquantite = itemView.findViewById(R.id.ShoppingItemQuan);
            PrixTT = itemView.findViewById(R.id.ShoppingItemPTT);
            DeleteShoppingItemBtn = itemView.findViewById(R.id.DeleteShoppingItemBtn);
        }
    }
        //overRide
        @NonNull
        @Override
        public ShoppingItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View matchView = inflater.inflate(R.layout.shopping_cart_item, parent, false);
            ShoppingItemsAdapter.ViewHolder viewHolder = new ShoppingItemsAdapter.ViewHolder(matchView);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull ShoppingItemsAdapter.ViewHolder holder, int position) {
            final shoppingCart ShoppingItem = shoppingCart.get(position);
            TextView nom = holder.Pnom;
            TextView prix = holder.Pprix;
            TextView prixTT = holder.PrixTT;
            TextView quan = holder.Pquantite;
            ImageButton deleteShopppingItem = holder.DeleteShoppingItemBtn;
            int TTP = holder.TT;



            //SET DATA TO ITEMS
            int produit_id = ShoppingItem.getProduitId();
            int user_id = ShoppingItem.getUserId();
            quan.setText(String.valueOf(ShoppingItem.getProduitQuantite()));
            //remove item from list
            deleteShopppingItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = AppDataBase.getAppDatabase(v.getContext());
                    database.userDao().deleteShoppingCartItem(ShoppingItem);
                    shoppingCart.remove(ShoppingItem);
                    RefreshDataOnDeleteItem(shoppingCart);
                    Toast.makeText(v.getContext(),"produit supprimé avec succés",Toast.LENGTH_SHORT).show();

                }
            });
            //get produit DATA
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:1337")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            myApi = retrofit.create(INodeJS.class);
            Call<produit> call = myApi.getProduitById(produit_id);
            call.enqueue(new Callback<produit>() {
                @Override
                public void onResponse(Call<produit> call, Response<produit> response) {
                    produit Pitem = response.body();
                    nom.setText(Pitem.getNom());
                    String prixC = String.valueOf(Pitem.getPrix());
                    prix.setText(prixC +" DT");
                    //TTP += Pitem.getPrix();
                    int TT = (Pitem.getPrix() * ShoppingItem.getProduitQuantite());
                    prixTT.setText(String.valueOf(TT) + "DT");
                }

                @Override
                public void onFailure(Call<produit> call, Throwable t) {

                }
            });



            /*
            nom.setText(shoppingCartItem.getNom());
            type.setText("Catégorie : "+shoppingCartItem.getType());
            //cvrt prix to int
            String prixC = String.valueOf(shoppingCartItem.getPrix());
            prix.setText(prixC + "DT");
            //card view clicked => show details

             */
            
        }
        @Override
        public int getItemCount() {
            return shoppingCart.size();
        }
        @Override
        public Filter getFilter() {
            return filer;
        }
        Filter filer = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<shoppingCart> filtredList = new ArrayList<>();
                if(constraint == null || constraint.length() == 0){
                    filtredList.addAll(shoppingCart_filtred);
                }else {
                    String filtrePattern = constraint.toString().toLowerCase().trim();
                    for (shoppingCart item:shoppingCart_filtred){

                            filtredList.add(item);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtredList;
                return  results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shoppingCart.clear();
                shoppingCart.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
}
