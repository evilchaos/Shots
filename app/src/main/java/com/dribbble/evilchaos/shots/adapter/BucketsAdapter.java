package com.dribbble.evilchaos.shots.adapter;

import android.content.Context;

import com.dribbble.evilchaos.shots.R;
import com.dribbble.evilchaos.shots.entity.BucketData;
import java.util.List;

/**
 * Created by liujiachao on 2017/1/13.
 */

public class BucketsAdapter extends SimpleAdapter<BucketData> {

    public BucketsAdapter(Context context, List<BucketData> datas) {
        super(context, R.layout.buckets_item, datas);
    }

    @Override
    public void bindData(BaseViewHolder viewHolder, BucketData bucketData) {
        viewHolder.getTextView(R.id.buckets_name).setText(bucketData.getName());
        viewHolder.getTextView(R.id.buckets_shots_num).setText(String.valueOf(bucketData.getShots_count()));
    }
}
