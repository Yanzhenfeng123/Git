package com.yzf.template.action.view.adapter;

import android.content.Context;

import com.yzf.template.R;
import com.yzf.template.action.view.adapter.Info.MineInfo;
import com.yzf.template.base.BaseRecyclerAdapter;
import com.yzf.template.base.BaseRecyclerHolder;

public class VipAdapter extends BaseRecyclerAdapter<MineInfo> {

    private boolean night;

    public VipAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public void setNight(boolean night) {
        this.night = night;
        notifyDataSetChanged();
    }

    @Override
    public void convert(BaseRecyclerHolder holder, MineInfo item, int position) {
            holder.setText(R.id.tv_title,item.getTitle());
            holder.setText(R.id.tv_content,item.getContent());
            holder.setImageResource(R.id.iv_mine_icon,item.getResId());
            if (position==0){
                if (!night){
                    holder.setImageResource(R.id.iv_select,R.mipmap.item_select_dark);
                }else {
                    holder.setImageResource(R.id.iv_select,R.mipmap.item_selected_dark);
                }
            }else {
                holder.setImageResource(R.id.iv_select,R.mipmap.right_arrow);
            }
    }
}
