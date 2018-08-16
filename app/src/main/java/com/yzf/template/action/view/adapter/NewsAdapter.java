package com.yzf.template.action.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzf.template.R;
import com.yzf.template.action.view.adapter.Info.NewsData;
import com.yzf.template.action.view.adapter.Info.NewsInfo;
import com.yzf.template.glide.ImageLoader;
import com.yzf.template.utils.AppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //上下文
    protected Context mContext;

    //数据
    protected NewsData newsData;


    //监听接口
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected OnBannerItmeClick onBannerItmeClick;
    protected OnHeadViewItemClick onHeadViewItemClick;


    protected int mLastPosition = -1;

    protected static final int TYPE_HEADER = 1001;
    protected static final int TYPE_NORMAL = 1002;

    protected View mHeadView;

    /**
     * ALT + INSERT 快捷方法
     */
    public NewsAdapter(Context context, NewsData data) {
        mContext = context;
        newsData = data;
    }

    public OnBannerItmeClick getOnBannerItmeClick() {
        return onBannerItmeClick;
    }

    public void setOnBannerItmeClick(OnBannerItmeClick onBannerItmeClick) {
        this.onBannerItmeClick = onBannerItmeClick;
    }

    public OnHeadViewItemClick getOnHeadViewItemClick() {
        return onHeadViewItemClick;
    }

    public void setOnHeadViewItemClick(OnHeadViewItemClick onHeadViewItemClick) {
        this.onHeadViewItemClick = onHeadViewItemClick;
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OnItemLongClickListener getmOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public View getmHeadView() {
        return mHeadView;
    }

    public void setmHeadView(View mHeadView) {
        this.mHeadView = mHeadView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //如果有header 那就新建一个holder 没有header就绑定一个新的holder
        if (mHeadView != null && viewType == TYPE_HEADER) return new Holder(mHeadView);
        View v = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.news_item, parent, false);
        RecyclerView.ViewHolder holder = new NewHolder(v);
        if (holder == null)
            throw new RuntimeException("请现在createViewHolder()中返回ViewHolder");
        return holder;
    }

    protected void bindViewHolder(RecyclerView.ViewHolder holder, final NewsData data, final int pos) {
        ((NewsAdapter.NewHolder) holder).tv_name.setText("");
        ImageLoader.getInstance().loadImage(mContext,data.getData().get(pos).getUrl(), ((NewsAdapter.NewHolder) holder).tv_img, AppUtil.getScreenWith(),AppUtil.dip2px(mContext,140));
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //有header position是0  return 强制结束不绑定bindViewHolder
        if (getItemViewType(position) == TYPE_HEADER) return;
        // 获取真实位置
        final int pos = getRelPostion(holder);
        final NewsInfo data = newsData.getData().get(pos);
        bindViewHolder(holder, newsData, pos);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(pos, data);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemClick(pos, data);
                    return false;
                }
            });
        }
    }

    protected View getView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    public void add(int position, NewsInfo item) {
        newsData.getData().add(position, item);
        notifyItemInserted(position);
    }

    public void addMove(List<NewsInfo> data) {
        int startPosition = mHeadView == null ? newsData.getData().size() : newsData.getData().size() + 1;
        newsData.getData().addAll(data);
        notifyItemRangeInserted(startPosition, newsData.getData().size());
    }

    public void delete(int position) {
        newsData.getData().remove(position);
        notifyItemRemoved(position);
    }

    public List<NewsInfo> getList() {
        return newsData.getData();
    }

    public void setList(List<NewsInfo> items) {
        newsData.setData(items);
        notifyDataSetChanged();
    }

    protected void setItemAppearAnimal(RecyclerView.ViewHolder holder, int position, @AnimRes int type) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null
                && params instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) params;
            p.setFullSpan(true);
        }

    }

    private int getRelPostion(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();

        return mHeadView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        if (newsData.getData() == null) {
            return 0;
        }
        return mHeadView == null ? newsData.getData().size() : newsData.getData().size() + 1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, NewsInfo data);
    }

    public interface OnItemLongClickListener {
        void onItemClick(int position, NewsInfo data);
    }

    public interface OnBannerItmeClick {
        void onBannerItemClick(String url);
    }

    public interface OnHeadViewItemClick {
        void onHeadViewItmeClick(int pos, View view);
    }

    protected class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);

        }

    }

    protected class NewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView tv_name;
        @BindView(R.id.item_img)
        ImageView tv_img;

        public NewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

    }
}
