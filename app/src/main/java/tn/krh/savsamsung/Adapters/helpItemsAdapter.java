package tn.krh.savsamsung.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Switch;
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
import tn.krh.savsamsung.entity.HelpItem;
import tn.krh.savsamsung.retrofit.INodeJS;

public class helpItemsAdapter extends RecyclerView.Adapter<helpItemsAdapter.ViewHolder> implements Filterable {
    private List<HelpItem> helps;
    private List<HelpItem> helps_filtred;

    Context mContext;
    INodeJS myApi;
    public helpItemsAdapter(Context context, List<HelpItem> helps) {
        this.helps = helps;
        mContext = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView descripyion;
        public  Button helpBtn;
        public ImageView img;
        public SharedPreferences mPreference;
        public ViewHolder(final View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.helpNom);
            descripyion = itemView.findViewById(R.id.helpDesc);
            helpBtn = itemView.findViewById(R.id.helpBtn);
            img = itemView.findViewById(R.id.helpImg);
        }
    }
    //overRide
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.cardview_help_item, parent, false);
        helpItemsAdapter.ViewHolder viewHolder = new helpItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HelpItem helpItem = helps.get(position);
        TextView nom = holder.nom;
        TextView decription = holder.descripyion;
        Button helpBtn = holder.helpBtn;
        ImageView img = holder.img;
        final SharedPreferences[] mPreference = {holder.mPreference};
        //SET DATA TO ITEMS
        nom.setText(helpItem.getNom());
        decription.setText(helpItem.getDescription());
        //get Image
        String HelpItem_img = helpItem.getImg();
        //load Img

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<ResponseBody> callImg = myApi.getHelpItemImg(HelpItem_img);
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








        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.samsung.com/fr/support/category/home-appliances/laundry/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Context context = v.getContext();
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return helps.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HelpItem> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(helps_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (HelpItem item:helps_filtred){
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
            helps.clear();
            helps.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
