package com.rajeev.timesinternetassignment.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.rajeev.timesinternetassignment.R;
import com.rajeev.timesinternetassignment.beans.DataBean;
import com.rajeev.timesinternetassignment.databinding.CustomLvSingleRowBinding;
import com.rajeev.timesinternetassignment.utils.ServiceParameters;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.PVHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<DataBean> arrayList;
    private MyProductsListener listener;

    public ProductsAdapter(Context context, ArrayList<DataBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        setHasStableIds(true);
    }


    public void setListener(MyProductsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @NonNull
    @Override
    public PVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            this.inflater = LayoutInflater.from(parent.getContext());
        }

        CustomLvSingleRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_lv_single_row, parent, false);
        return new PVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PVHolder holder, int pos) {

        if (arrayList != null) {
            DataBean dataBean = arrayList.get(pos);
            String imgUrlThumb = dataBean.getI();
            String title = dataBean.getT();
            String price = "Rs. " + dataBean.getP();

            if(dataBean.getSd()!=null && !dataBean.getSd().isEmpty())
            {  Spanned short_desc = Html.fromHtml(dataBean.getSd());
                holder.binding.tvDescName.setVisibility(View.VISIBLE);
                holder.binding.tvDescName.setText(short_desc);
            }
            else {
                holder.binding.tvDescName.setVisibility(View.GONE);
            }

            holder.binding.tvName.setText(title);
            holder.binding.tvPrice.setText(price);

            setProfilePic(holder, imgUrlThumb, holder.binding.imgCVProfile);
        }
    }

    private void setProfilePic(@NonNull PVHolder holder,
                               String imageUri, final ImageView imageView) {

        if (imageUri != null && !imageUri.isEmpty()) {
            holder.binding.progressBar.setVisibility(View.VISIBLE);
            ServiceParameters.getInstance().setUpProgressbarCover(context, holder.binding.progressBar);

            Glide.with(context.getApplicationContext())
                    .load(imageUri)
                    .centerCrop()
                    .into(new SimpleTarget<GlideDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageDrawable(resource);

                            holder.binding.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            holder.binding.progressBar.setVisibility(View.GONE);

                            setProfilePic(holder, imageUri, imageView);
                        }
                    });
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_product_i));
        }

    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public ArrayList<DataBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<DataBean> arrayList) {
        this.arrayList = arrayList;
    }

    // INTERFACE
    public interface MyProductsListener {
        void onItemClick(View v, int pos);
    }

    // VIEW HOLDER CLASS
    class PVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CustomLvSingleRowBinding binding;

        PVHolder(@NonNull CustomLvSingleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    if (listener != null)
                        listener.onItemClick(v, getAdapterPosition());
                    break;
            }
        }
    }
}
