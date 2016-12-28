package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.BaseAdapter;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.CommentItem;
import com.dribbble.evilchaos.shots.util.TimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujiachao on 2016/12/28.
 */

public class CommListAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private List<CommentItem> datas = new ArrayList<>();

    public CommListAdapter(Context context,List<CommentItem> datas) {
        this.mInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.comment_item,null);
            holder.commenterAvatar = (SimpleDraweeView)convertView.findViewById(R.id.comment_author_avatar);
            holder.commContent = (TextView)convertView.findViewById(R.id.comment_content);
            holder.commName = (TextView)convertView.findViewById(R.id.commenter_name);
            holder.commLikeNum = (TextView)convertView.findViewById(R.id.comment_like_number);
            holder.commTime = (TextView)convertView.findViewById(R.id.comment_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommentItem commentItem = datas.get(position);
        holder.commenterAvatar.setImageURI(commentItem.getUser().getAvatar_url());
        holder.commName.setText(commentItem.getUser().getName());
        holder.commContent.setText(Html.fromHtml(commentItem.getBody()).toString().trim());
        holder.commTime.setText(TimeUtils.getTimeFromStandardFormat(commentItem.getUpdated_at()));
        holder.commLikeNum.setText(String.valueOf(commentItem.getLikes_count()));

        return convertView;
    }


    class ViewHolder {
        SimpleDraweeView commenterAvatar;
        TextView commName;
        TextView commContent;
        TextView commTime;
        TextView commLikeNum;
    }
}
