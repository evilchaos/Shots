package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiachao on 2016/12/12.
 */

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mDatas = new ArrayList<>();
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int mLayoutResId;

    private OnItemClickListener mOnItemClickListener = null;

    public BaseAdapter(Context context,int mLayoutResId) {
        this(context,mLayoutResId,null);
    }

    public BaseAdapter(Context context,int mLayoutResId,List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutResId = mLayoutResId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(mLayoutResId,parent,false);
        return new BaseViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        T t = getItem(position);
        //绑定数据，子类实现
        bindData(holder,t);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() <= 0)
            return 0;

        return mDatas.size();
    }

    public T getItem(int position) {
        if (position >= mDatas.size())
            return null;

        return mDatas.get(position);
    }

    public void clear(){
        int itemCount = mDatas.size();
        mDatas.clear();
        this.notifyItemRangeRemoved(0,itemCount);
    }

    public List<T> getDatas(){

        return mDatas;
    }
    public void addData(List<T> datas){

        addData(0,datas);
    }

    public void addData(int position,List<T> datas){
        if(datas !=null && datas.size()>0) {

            this.mDatas.addAll(datas);
            this.notifyItemRangeChanged(position, datas.size());
        }
    }

    public abstract void bindData(BaseViewHolder viewHolder,T t);

    public interface OnItemClickListener {
        void onItemClick(View view ,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
