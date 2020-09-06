package tn.krh.savsamsung.Adapters.produit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.commande.Commande;
import tn.krh.savsamsung.activities.store.DetailsStoreItem;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.retrofit.INodeJS;

public class produitItemsAdapter extends RecyclerView.Adapter<produitItemsAdapter.ViewHolder> implements Filterable {
    private List<produit> produit;
    private List<produit> produit_filtred;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    Context mContext;
    INodeJS myApi;
    public produitItemsAdapter(Context context, List<produit> produits) {
        this.produit = produits;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView type;
        public TextView prix;
        public ImageView img;
        public Button CmdBtn;
        public CardView CardVClicked;
        public SharedPreferences mPreference;
        public ViewHolder(final View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.produitNom);
            type = itemView.findViewById(R.id.produitType);
            prix = itemView.findViewById(R.id.produitPrix);
            img = itemView.findViewById(R.id.produitImg);
            CmdBtn = itemView.findViewById(R.id.CommanderBtn);
            CardVClicked = itemView.findViewById(R.id.CardVStore);
        }
    }
    //overRide
    @NonNull
    @Override
    public produitItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.store_item_rv, parent, false);
        produitItemsAdapter.ViewHolder viewHolder = new produitItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull produitItemsAdapter.ViewHolder holder, int position) {
        final produit produitItem = produit.get(position);
        TextView nom = holder.nom;
        TextView type = holder.type;
        TextView prix = holder.prix;
        Button CmdBtn = holder.CmdBtn;
        ImageView img = holder.img;
        CardView CardViewClicked = holder.CardVClicked;
        //SET DATA TO ITEMS
        final SharedPreferences[] mPreference = {holder.mPreference};
        nom.setText(produitItem.getNom());
        type.setText("Catégorie : "+produitItem.getType());
        //cvrt prix to int
        String prixC = String.valueOf(produitItem.getPrix());
        prix.setText(prixC + "DT");
        //card view clicked => show details
        CardViewClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreference[0].edit();
                editor.putInt("ProduitId",produitItem.getId());
                editor.apply();
                Context context = v.getContext();
                context.startActivity(new Intent(context, DetailsStoreItem.class));
            }
        });
        CmdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreference[0].edit();
                editor.putInt("ProduitId",produitItem.getId());
                editor.apply();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Commande.class));
            }
        });
        //get Image
        String serviceItem_img = produitItem.getImg();
        //load Img

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<ResponseBody> callImg = myApi.getHelpItemImg(serviceItem_img);
        callImg.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        img.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> calll, Throwable t) {
                // TODO
            }
        });
    }
    @Override
    public int getItemCount() {
        return produit.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<produit> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(produit_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (produit item:produit_filtred){
                    if(item.getNom().toLowerCase().contains(filtrePattern)){
                        filtredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            produit.clear();
            produit.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
