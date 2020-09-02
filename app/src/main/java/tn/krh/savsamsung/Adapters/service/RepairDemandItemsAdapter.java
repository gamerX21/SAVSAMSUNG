package tn.krh.savsamsung.Adapters.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.repairD;
import tn.krh.savsamsung.retrofit.INodeJS;

public class RepairDemandItemsAdapter extends RecyclerView.Adapter<RepairDemandItemsAdapter.ViewHolder> implements Filterable {
private List<repairD> repairDs;
private List<repairD> repairDs_filtred;
private SharedPreferences mPreference;
public static final String sharedPrefFile = "com.krh.app";
        Context mContext;
        INodeJS myApi;
public RepairDemandItemsAdapter(Context context, List<repairD> repairDss) {
        this.repairDs = repairDss;
        mContext = context;
        }



public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView type;
    public TextView etat;
    public TextView date;
    public Button DeclineRBtn;
    public ViewHolder(final View itemView) {
        super(itemView);
        type = itemView.findViewById(R.id.TypeRepair);
        etat = itemView.findViewById(R.id.EtatRepair);
        date = itemView.findViewById(R.id.DateRepair);
        DeclineRBtn = itemView.findViewById(R.id.DeclineDemandBtn);
    }
}
    //overRide
    @NonNull
    @Override
    public RepairDemandItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.repair_demand_item_rv, parent, false);
        RepairDemandItemsAdapter.ViewHolder viewHolder = new RepairDemandItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepairDemandItemsAdapter.ViewHolder holder, int position) {
        final repairD repairDItem = repairDs.get(position);
        TextView type = holder.type;
        TextView etat = holder.etat;
        TextView date = holder.date;
        Button DeclineRBtn = holder.DeclineRBtn;
        //SET DATA TO ITEMS
        type.setText(repairDItem.getType());
        etat.setText(repairDItem.getStatus());
        //Date to Sting
        /*
        String dateStr = "";
        Date dateC = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateC = repairDItem.getDateA();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateC);
        SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateStr = printFormat.format(calendar.getTime());

         */
        date.setText("2020-08-31");
    }

    @Override
    public int getItemCount() {
        return repairDs.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<repairD> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(repairDs_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (repairD item:repairDs_filtred){
                    if(item.getType().toLowerCase().contains(filtrePattern)){
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
            repairDs.clear();
            repairDs.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
