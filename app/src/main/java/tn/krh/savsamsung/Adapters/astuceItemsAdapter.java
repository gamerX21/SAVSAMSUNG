package tn.krh.savsamsung.Adapters;

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
import tn.krh.savsamsung.entity.astuce;
import tn.krh.savsamsung.retrofit.INodeJS;

public class astuceItemsAdapter extends RecyclerView.Adapter<astuceItemsAdapter.ViewHolder> implements Filterable {
    private List<astuce> astuces;
    private List<astuce> astuces_filtred;
    Context mContext;
    INodeJS myApi;
    public astuceItemsAdapter(Context context, List<astuce> astucess) {
        this.astuces = astucess;
        mContext = context;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public ViewHolder(final View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.astuceDesc);

        }
    }
    //overRide
    @NonNull
    @Override
    public astuceItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.astuce_item_rv, parent, false);
        astuceItemsAdapter.ViewHolder viewHolder = new astuceItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull astuceItemsAdapter.ViewHolder holder, int position) {
        final astuce astuceItem = astuces.get(position);
        TextView description = holder.description;
        description.setText(astuceItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return astuces.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<astuce> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(astuces_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (astuce item:astuces_filtred){
                    if(item.getDescription().toLowerCase().contains(filtrePattern)){
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
            astuces.clear();
            astuces.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
