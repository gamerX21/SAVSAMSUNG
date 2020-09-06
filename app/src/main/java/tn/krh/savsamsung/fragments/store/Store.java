package tn.krh.savsamsung.fragments.store;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import tn.krh.savsamsung.R;
import tn.krh.savsamsung.fragments.produits.accessoires;
import tn.krh.savsamsung.fragments.produits.electro_parts;
import tn.krh.savsamsung.fragments.produits.prd_nettoyage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Store extends Fragment {

    FrameLayout partsFL,accFL,nettoyageFL;
    accessoires accFrag;
    electro_parts electroPartsFrag;
    prd_nettoyage nettoyageFrag;
    public Store() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        //set fragments
        accFrag = new accessoires();
        electroPartsFrag = new electro_parts();
        nettoyageFrag = new prd_nettoyage();
        //set fragments to frame layout to load RV data 1
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.PartsFrag, electroPartsFrag).commit();
        //set fragments to frame layout to load RV data 2
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.NettoyageFrag, nettoyageFrag).commit();
        //set fragments to frame layout to load RV data 3
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.AccesoirsFrag, accFrag).commit();
        return view;
    }
}
