package com.dribbble.evilchaos.shots.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dribbble.evilchaos.shots.R;


/**
 * Created by liujiachao on 2017/1/5.
 */

public class AppleItemView extends FrameLayout{
    private TextView itemTitle;
    private TextView itemNum;

    private OnClickActionListener mOnClickActionListener = null;

    public AppleItemView(Context context, AttributeSet attrs) {
        super(context,attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AppleItemView);
//        String itemTag = ta.getString(R.styleable.AppleItemView_itemTag);
//        String itemContent = ta.getString(R.styleable.AppleItemView_itemContent);

//        if (itemTag != null) {
//            setTitleText(itemTag);
//        }
//
//        if (itemContent != null) {
//            setItemNum(itemContent);
//        }

        LayoutInflater.from(context).inflate(R.layout.app_item,this);
        itemTitle = (TextView)findViewById(R.id.item_title);
        itemNum = (TextView)findViewById(R.id.item_num);
        ta.recycle();
    }

    public void setTitleText(String text) {
        itemTitle.setText(text);
    }

    public void setItemNum(String text) {
        itemNum.setText(text);
    }

    public void setOnClickActionListener(OnClickActionListener mOnClickActionListener) {
        this.mOnClickActionListener = mOnClickActionListener;
    }

    public interface OnClickActionListener {
        public void OnClickAction();
    }
}
