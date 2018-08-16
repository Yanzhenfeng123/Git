package com.yzf.template.action.view.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.signature.EmptySignature;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vondear.rxui.view.dialog.RxDialogScaleView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.yzf.core.http.loading.CustomProgressDialog;
import com.yzf.template.R;
import com.yzf.template.action.view.activity.DetailScaleActivity;
import com.yzf.template.action.view.adapter.Info.NewsData;
import com.yzf.template.action.view.adapter.Info.NewsInfo;
import com.yzf.template.action.view.adapter.NewsAdapter;
import com.yzf.template.base.HttpListToolBarFragment;
import com.yzf.template.glide.DataCacheKey;
import com.yzf.template.glide.ImageLoader;
import com.yzf.template.glide.GlideImageLoader;
import com.yzf.template.utils.ScreenUtils;
import com.yzf.template.view.RefreshBottomView;
import com.yzf.template.view.RefreshHeadView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.body.ProgressInfo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NewsFragment extends HttpListToolBarFragment implements NewsAdapter.OnBannerItmeClick, NewsAdapter.OnItemClickListener {

    private String[] img = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670212730&di=52390554f50f17aad77d708950c0a459&imgtype=0&src=http%3A%2F%2Fandroid.tgbus.com%2Fbizhi%2FUploadFiles_7790%2F201203%2F20120326094034197.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670384909&di=4b857b1e56dffb723e582c5984c1484b&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1610715006%2C3867191707%26fm%3D214%26gp%3D0.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670424862&di=9980e38717884cf950b9f944cf585bd0&imgtype=0&src=http%3A%2F%2Fandroid-wallpapers.25pp.com%2F20140314%2F0852%2F532252da025ff9_900x675.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670447014&di=ebcf44b739ec01faf2b0e4a565493114&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd788d43f8794a4c278a7177009f41bd5ac6e39e0.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670498910&di=dd0c1b3fd77ae7df466b99d54350061c&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fent%2Ftransform%2F20170215%2FKjWj-fyamkpy9493436.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670513760&di=23438b16b51f6f6008b4edb7e393257a&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2014%2F113%2F32%2F20SG1BO8V3B3_1000x500.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670549992&di=b44331a4252d984ffb347173570a8957&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2291971638%2C89868487%26fm%3D214%26gp%3D0.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670581663&di=3d47dfd6e0767e9a1fd26dc294e49c17&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Flarge%2F72e88047jw1ecseho9mzxj20f007875y.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670743460&di=71ad36d220758edececf0d8bf5295442&imgtype=0&src=http%3A%2F%2Fvpic.video.qq.com%2F3388556%2Fm0352ci0rim_ori_3.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670911536&di=378cee7597ec51daa4f85c43a08eea7f&imgtype=0&src=http%3A%2F%2Fmpic.haiwainet.cn%2Fthumb%2Fd%2Fuploadfile%2F2015%2F1229%2F20151229072120976%2Cw_480.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670939513&di=f8b8ffac8f113e6627b2b54cc006688a&imgtype=0&src=http%3A%2F%2Fi9.download.fd.pchome.net%2Ft_600x1024%2Fg1%2FM00%2F0D%2F0D%2FooYBAFShHNaIZod-AAhMgl_BbgIAACMKADAopAACEya810.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532670998795&di=8a9565ce0194d972184510b09cdf548f&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201406%2F13%2F20140613173042_TwJxs.thumb.700_0.jpeg"};
    private List<NewsInfo> data;
    private List<NewsInfo> list;
    private NewsData newsData;
    CustomProgressDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        setToolBarTitle("资讯");
        data = new ArrayList<>();
        newsData = new NewsData();
        list = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            NewsInfo info = new NewsInfo();
            info.setName("哈哈" + i);
            info.setUrl(img[i]);
            data.add(info);
            list.add(info);
        }
        newsData.setImg(img);
        newsData.setData(data);
        newsData.setUrl1("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533262961&di=d9721cbc9895aa0bcdc672156a5d8576&imgtype=jpg&er=1&src=http%3A%2F%2Fimgfs.oppo.cn%2Fuploads%2Fthread%2Fattachment%2F2017%2F04%2F06%2F14914331976112.jpg");
        newsData.setUrl2("http://www.qiwen007.com/images/image/2016/1103/6361377813618392144749351.jpg");
        newsData.setUrl3("http://m1.sinaimg.cn/maxwidth.640/m1.sinaimg.cn/b7c4c0be576dc14e642a8eb00cae94fe_362_500.jpg");
        newsData.setUrl4("http://img2.imgtn.bdimg.com/it/u=2905649902,1519096845&fm=214&gp=0.jpg");
        setData(recyclerView, refreshLayout);
    }


    @Override
    protected void setData(RecyclerView recyclerView, final TwinklingRefreshLayout refreshLayout) {
        if (refreshLayout != null && recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            refreshLayout.setHeaderView(new RefreshHeadView(getActivity()));
            refreshLayout.setBottomView(new RefreshBottomView(getActivity()));
            refreshLayout.setWaveHeight(140);
            refreshLayout.setOverScrollBottomShow(true);
            refreshLayout.setEnableRefresh(true);
            refreshLayout.setEnableOverScroll(true);
            if (data != null && data.size() > 0) {
                final NewsAdapter adapter = new NewsAdapter(getActivity(), newsData);
                recyclerView.setAdapter(adapter);

                //配置header
                View headView = LayoutInflater.from(getActivity()).inflate(R.layout.news_head_view, null);
                LinearLayout line_bg = headView.findViewById(R.id.line_head);
                int width = (ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dip2px(getActivity(), 24)) / 2;
                LinearLayout.LayoutParams img_params = new LinearLayout.LayoutParams(width, width);
                ImageView img1 = headView.findViewById(R.id.head_img1);
                ImageView img2 = headView.findViewById(R.id.head_img2);
                ImageView img3 = headView.findViewById(R.id.head_img3);
                ImageView img4 = headView.findViewById(R.id.head_img4);
                img1.setLayoutParams(img_params);
                img2.setLayoutParams(img_params);
                img3.setLayoutParams(img_params);
                img4.setLayoutParams(img_params);
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initListener(newsData.getUrl1());
                    }
                });
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initListener(newsData.getUrl2());
                    }
                });
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initListener(newsData.getUrl3());
                    }
                });
                img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initListener(newsData.getUrl4());
                    }
                });
                ImageLoader.getInstance().initGlide(getActivity()).loadImage(getActivity(), newsData.getUrl1(), img1, width, width);
                ImageLoader.getInstance().initGlide(getActivity()).loadImage(getActivity(), newsData.getUrl2(), img2, width, width);
                ImageLoader.getInstance().initGlide(getActivity()).loadImage(getActivity(), newsData.getUrl3(), img3, width, width);
                ImageLoader.getInstance().initGlide(getActivity()).loadImage(getActivity(), newsData.getUrl4(), img4, width, width);
                View banner_view = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout, null);
                Banner banner = banner_view.findViewById(R.id.banner);
                banner.setImages(Arrays.asList(img))
                        .setImageLoader(new GlideImageLoader())
                        .start();
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        onBannerItemClick(img[position]);
                    }
                });
                line_bg.addView(banner_view);
                adapter.setmHeadView(headView);
                //监听接口
//                adapter.setOnHeadViewItemClick(this);
                adapter.setOnBannerItmeClick(this);
                adapter.setmOnItemClickListener(this);

                refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                    @Override
                    public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                refreshLayout.finishRefreshing();
//                                newsData.getData().clear();
                                adapter.setList(list);
                            }
                        });
                    }

                    @Override
                    public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                        Observable.timer(2000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                refreshLayout.finishLoadmore();
//                                adapter.addMove(data);
                            }
                        });
                    }
                });
            }
        }
    }

    //添加响应监听
    private void initListener(String url) {
//        ImageLoader.loadImageToBitmap(getActivity(), url);
//        ProgressManager.getInstance().addResponseListener(url, getGlideListener(url));
        Intent intent = new Intent(getActivity(), DetailScaleActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    //监听图片加载速度
    private ProgressListener getGlideListener(final String url) {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                int progress = progressInfo.getPercent();
                Log.d("yy", "--Glide-- " + progress + " %  " + progressInfo.getSpeed() + " byte/s  " + progressInfo.toString());
                if (progress >= 0) {
                    if (dialog == null) {
                        dialog = CustomProgressDialog.createDialog(getActivity());
                    }
                }
                if (progressInfo.isFinish()) {
                    dialog.dismiss();
                    RxDialogScaleView Rxdialog = new RxDialogScaleView(getActivity());
                    Rxdialog.setImage(getCacheFile(url).getAbsolutePath(), false);
                    Rxdialog.show();
                } else {
                    initListener(url);
                }

            }

            @Override
            public void onError(long id, Exception e) {
                Observable.timer(0, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                dialog.dismiss();
                            }
                        });
            }
        };
    }


    @Override
    public void onBannerItemClick(String url) {
        initListener(url);
    }


    @Override
    public void onItemClick(int position, NewsInfo data) {
        initListener(data.getUrl());
    }


    public File getCacheFile(String id) {
        DataCacheKey dataCacheKey = new DataCacheKey(new GlideUrl(id), EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(dataCacheKey);
        try {
            int cacheSize = 100 * 1000 * 1000;
            DiskLruCache diskLruCache = DiskLruCache.open(new File(getActivity().getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, cacheSize);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
