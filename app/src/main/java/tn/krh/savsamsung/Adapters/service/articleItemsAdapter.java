package tn.krh.savsamsung.Adapters.service;

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
import tn.krh.savsamsung.activities.article.ArticleDetails;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.retrofit.INodeJS;

public class articleItemsAdapter extends RecyclerView.Adapter<articleItemsAdapter.ViewHolder> implements Filterable {
    private List<article> articles;
    private List<article> articles_filtred;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    Context mContext;
    INodeJS myApi;
    public articleItemsAdapter(Context context, List<article> articless) {
        this.articles = articless;
        mContext = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView descripyion;
        public Button articleBtn;
        public  Button infoArticleBtn;
        public ImageView img;
        public SharedPreferences mPreference;
        public ViewHolder(final View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.articleNom);
            descripyion = itemView.findViewById(R.id.articleDesc);
            articleBtn = itemView.findViewById(R.id.articleBtn);
            infoArticleBtn = itemView.findViewById(R.id.infoArticleBtn);
            img = itemView.findViewById(R.id.articleImg);
        }
    }
    //overRide
    @NonNull
    @Override
    public articleItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.reparation_item_rv, parent, false);
        articleItemsAdapter.ViewHolder viewHolder = new articleItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull articleItemsAdapter.ViewHolder holder, int position) {
        final article articleItem = articles.get(position);
        TextView nom = holder.nom;
        TextView decription = holder.descripyion;
        Button articleBtn = holder.articleBtn;
        Button infoArticleBtn = holder.infoArticleBtn;
        ImageView img = holder.img;
        final SharedPreferences[] mPreference = {holder.mPreference};
        //SET DATA TO ITEMS
        nom.setText(articleItem.getNom());
        decription.setText(articleItem.getTitre());
        //get Image
        String HelpItem_img = articleItem.getImg();
        //set btn action message & root
        if(articleItem.getType().equals("Nettoyage"))
        {
            articleBtn.setText("boutique en ligne");
        }
        //load Img

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<ResponseBody> callImg = myApi.getArticleItemImg(HelpItem_img);
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


        infoArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreference[0].edit();
                editor.putInt("ArticleId",articleItem.getId());
                editor.apply();
                Context context = v.getContext();
                context.startActivity(new Intent(context, ArticleDetails.class));
            }
        });




        /*
        articleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreference[0] = v.getContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreference[0].edit();
                editor.putInt("ArticleId",articleItem.getId());
                editor.apply();
                Context context = v.getContext();
                context.startActivity(new Intent(context, ArticleDetails.class));
            }
        });

         */



    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<article> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(articles_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (article item:articles_filtred){
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
            articles.clear();
            articles.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
