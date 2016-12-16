package com.dribbble.evilchaos.shots.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private SparseArray<View> views;
    private BaseAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(View itemView,BaseAdapter.OnItemClickListener mOnItemClickListener) {
        super(itemView);
        views = new SparseArray<>();
        itemView.setOnClickListener(this);

        this.mOnItemClickListener = mOnItemClickListener;
    }

    protected <T extends View> T retrieveView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id,view);
        }

        return (T) view;
    }

    public View getView(int id) {

        return retrieveView(id);
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId){
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
