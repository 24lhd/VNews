package com.duongstudio.obj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.duongstudio.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D on 8/11/2017.
 */

public abstract class AdaptorADS extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private RecyclerView recyclerView;
    private Object doiTuongCanThem;
    public static final int ADS = 1;
    public static final int ITEM = 0;
    private int viTriThem = 6;
    private List<Object> listObject;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public List<Object> getListObject() {
        return listObject;
    }

    public Object getDoiTuongCanThem() {
        return doiTuongCanThem;
    }

    public static int getITEM() {
        return ITEM;
    }

    public static int getADS() {
        return ADS;
    }

    public static void addNativeExpressAds(ArrayList<Object> objects,
                                           Object doiTuongCanThem,
                                           int viTriAdd) {
    }

    public AdaptorADS(RecyclerView recyclerView, List<Object> listObject, Object doiTuongCanThem, int viTriThem, Context context) {
        this.recyclerView = recyclerView;
        this.listObject = listObject;
        this.doiTuongCanThem = doiTuongCanThem;
        this.viTriThem = viTriThem;
        this.context = context;
        for (int i = viTriThem; i <= listObject.size(); i += viTriThem) {
            listObject.add(i, doiTuongCanThem);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        return (position % viTriThem == 0 && position > 0 && Config.isOnline(context)) ? ADS : ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ADS:
                ViewHolderAds viewHolderB = (ViewHolderAds) holder;
                ViewHolderAds(viewHolderB, position);
                return;
            default:
            case ITEM:
                ViewHolderItem viewHolderA = (ViewHolderItem) holder;
                ViewHolderItem(viewHolderA, position);
                return;
        }
    }


    @Override
    public int getItemCount() {
        return listObject.size();
    }


    public int getViTriThem() {
        return viTriThem;
    }

    public void setViTriThem(int viTriThem) {
        this.viTriThem = viTriThem;
    }

    public abstract void ViewHolderAds(ViewHolderAds viewHolder, int position);

    public abstract void ViewHolderItem(ViewHolderItem viewHolder, int position);

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderAds extends RecyclerView.ViewHolder {
        public ViewHolderAds(View itemView) {
            super(itemView);
        }
    }
}
