package com.zividig.newnestziv.ui.fragment.mycar.other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zividig.newnestziv.R;

/**
 * Created by adolph
 * on 2017-03-07.
 */

public class GridAdapter extends BaseAdapter{

    private String[] itemTexts = {
            "图片抓拍",
            "实时视频",
            "车辆信息",
            "车辆定位",
            "电子围栏",
            "轨迹查询"};

    private int[] itemImages = {
            R.drawable.snap_main,
            R.drawable.video_main,
            R.drawable.carinfo_main,
            R.drawable.car_location_main,
            R.drawable.fence_main,
            R.drawable.track_main};

    private Context mContext;

    public GridAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return itemTexts.length;
    }

    @Override
    public Object getItem(int position) {
        return itemTexts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.layout_function_button, null);

            holder = new GridViewHolder();
            holder.gridItemImage = (ImageView) convertView.findViewById(R.id.grid_item_image);
            holder.gridItemText = (TextView) convertView.findViewById(R.id.grid_item_tv);

            convertView.setTag(holder);
        }else {
            holder = (GridViewHolder) convertView.getTag();
        }

        holder.gridItemImage.setImageResource(itemImages[position]);
        holder.gridItemText.setText(itemTexts[position]);

        return convertView;
    }

    static class GridViewHolder{
        ImageView gridItemImage;
        TextView gridItemText;
    }
}
