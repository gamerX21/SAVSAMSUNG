package tn.krh.savsamsung.Adapters.spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.krh.savsamsung.R;
import tn.krh.savsamsung.SpinnerData.SpinnerItem;

public class TypeSpinnerAdapter extends ArrayAdapter<SpinnerItem> {
    int groupid;
    Activity context;
    ArrayList<SpinnerItem> list;
    LayoutInflater inflater;
    public TypeSpinnerAdapter(Activity context, int groupid, int id, ArrayList<SpinnerItem> list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }
    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.SpinnerImg);
        imageView.setImageResource(list.get(position).getImgId());
        TextView textView=(TextView)itemView.findViewById(R.id.SpinnerTxt);
        textView.setText(list.get(position).getName());
        return itemView;
    }
    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}
