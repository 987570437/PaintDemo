package com.itzb.paintdemo.colorfilter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.itzb.paintdemo.R;

import java.util.List;


public class ColorFilterAdapter extends RecyclerView.Adapter<ColorFilterAdapter.MyViewHolder> {


    private LayoutInflater mInflater;
    private List<float[]> filters;

    public ColorFilterAdapter(LayoutInflater mInflater, List<float[]> filters) {
        this.mInflater = mInflater;
        this.filters = filters;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        viewHolder = new MyViewHolder(mInflater.inflate(R.layout.list_color_filter_item, parent, false));
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return filters.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ColorFilter.imageViewColorFilter(holder.imageView, filters.get(position));
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img);
        }
    }

}


