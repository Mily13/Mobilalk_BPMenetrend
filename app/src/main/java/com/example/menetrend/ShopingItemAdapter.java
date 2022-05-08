package com.example.menetrend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.menetrend.ShopingItem;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ShopingItemAdapter extends RecyclerView.Adapter<ShopingItemAdapter.ViewHolder> {
    private ArrayList<ShopingItem> mShopingItemData = new ArrayList<>();
    private Context mContext;
    private int lastPosition = -1;

    ShopingItemAdapter(Context context, ArrayList<ShopingItem> itemsData) {
        this.mShopingItemData = itemsData;
        this.mContext = context;
    }

    @Override
    public ShopingItemAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShopingItemAdapter.ViewHolder holder, int position) {
        ShopingItem currentItem = mShopingItemData.get(position);

        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShopingItemData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // Member Variables for the TextViews
        private TextView mJaratszamText;
        private TextView mMegallokText;
        private TextView mIndulasokText;

        ViewHolder(View itemView) {
            super(itemView);

            mJaratszamText = itemView.findViewById(R.id.jaratszam);
            mMegallokText = itemView.findViewById(R.id.megallok);
            mIndulasokText = itemView.findViewById(R.id.indulasok);
        }

        void bindTo(ShopingItem currentItem){
            mJaratszamText.setText(currentItem.getJaratszam());
            mMegallokText.setText(currentItem.getMegallok());
            mIndulasokText.setText(currentItem.getIndulasok());

            itemView.findViewById(R.id.deletebutton).setOnClickListener(view -> ((JaratActivity)mContext).deleteItem(currentItem));
        }
    };
}
