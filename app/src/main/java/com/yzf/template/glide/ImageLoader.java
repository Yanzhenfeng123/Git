package com.yzf.template.glide;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yzf.template.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;


/**
 * 图片加载工具类
 *
 * @author Yzf
 * @description Glide 封装
 * @date 2018-7-27 16:34:08
 * GlideApp.with(this)
 * .load(url)
 * .apply(options)
 * .into(imageView);
 * <p>
 * RequestOptions options = new RequestOptions()
 * .placeholder(R.drawable.loading)
 * .error(R.drawable.error)
 * .skipMemoryCache(true)
 * .diskCacheStrategy(DiskCacheStrategy.NONE)
 * .transforms(new BlurTransformation(), new GrayscaleTransformation())
 * //压缩的大小  单位像素
 * .override(100,300)
 * .circleCrop();
 * <p>
 * <p>
 * DiskCacheStrategy.NONE： 表示不缓存任何内容。
 * DiskCacheStrategy.DATA： 表示只缓存原始图片。
 * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
 * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
 * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
 * CenterCrop、FitCenter、CircleCrop
 */
public class ImageLoader {

    //  加载失败图片
    private static int errorImageView = R.mipmap.ic_load_fail;

    private static RequestManager requestManager;

    private volatile static ImageLoader instance;
    private static Bitmap bitmap;


    /**
     * Returns singleton class instance
     */
    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    protected ImageLoader() {
    }

    /**
     * Begin a load with Glide by passing in a context.
     *
     * @see #initGlide(Activity)
     * @see #initGlide(android.app.Fragment))
     * @see #initGlide(android.support.v4.app.Fragment))
     * @see #initGlide(FragmentActivity))
     */
    public ImageLoader initGlide(Context mContext) {
        requestManager = GlideApp.with(mContext);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link Activity}'s lifecycle and that uses the
     * given {@link Activity}'s default options.
     *
     * @return A RequestManager for the given activity that can be used to start a load.
     */
    public ImageLoader initGlide(Activity mContext) {
        requestManager = GlideApp.with(mContext);
        return instance;
    }

    /**
     * Begin a load with Glide that will tied to the give {@link FragmentActivity}'s lifecycle
     * and that uses the given {@link FragmentActivity}'s default options.
     *
     * @param activity The activity to use.
     * @return A RequestManager for the given FragmentActivity that can be used to start a load.
     */
    public ImageLoader initGlide(FragmentActivity activity) {
        requestManager = GlideApp.with(activity);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link android.app.Fragment}'s lifecycle and that uses
     * the given {@link android.app.Fragment}'s default options.
     *
     * @param fragment The fragment to use.
     * @return A RequestManager for the given Fragment that can be used to start a load.
     */
    public ImageLoader initGlide(android.app.Fragment fragment) {
        requestManager = GlideApp.with(fragment);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link android.support.v4.app.Fragment}'s lifecycle and
     * that uses the given {@link android.support.v4.app.Fragment}'s default options.
     *
     * @param fragment The fragment to use.
     * @return A RequestManager for the given Fragment that can be used to start a load.
     */
    public ImageLoader initGlide(android.support.v4.app.Fragment fragment) {
        requestManager = GlideApp.with(fragment);
        return instance;
    }

    public void loadImagePlace(Context context, String url, ImageView mImageView) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).centerCrop().override(480, 240);
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }

    public void loadImage(Context context, String url, ImageView mImageView) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).centerCrop().override(480, 240);
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }
    public void loadImage(Context context, String url, ImageView mImageView, int width, int height) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).centerCrop().override(width, height);
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }

    public void loadImage(String url) {
        if (requestManager != null) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).centerCrop();
            requestManager.load(url).apply(options).preload();
        }
    }

    public void loadImage(int imagerResuce, ImageView mImageView) {
        if (requestManager != null) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).centerCrop();
            requestManager.load(imagerResuce).apply(options).into(mImageView);
        }
    }
    public static void loadBlurImage(Context context,String imagerResuce, ImageView mImageView,int width,int height) {
            RequestOptions options = new RequestOptions().transforms(new BlurTransformation());
            GlideApp.with(context).load(imagerResuce).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).override(width,height).apply(options).into(mImageView);
    }
    public void loadBlurImage(int imagerResuce, ImageView mImageView) {
        if (requestManager != null) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(errorImageView).error(errorImageView).transforms(new BlurTransformation(25),new GrayscaleTransformation()).centerCrop();
            requestManager.load(imagerResuce).apply(options).into(mImageView);
        }
    }
    public static void loadCircleImage(Context context, String url, ImageView mImageView) {
        if (requestManager != null) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).circleCrop();
            requestManager.load(url).apply(options).into(mImageView);
        }
    }

    public static void loadCircleImage(Context context, Integer resourceId, ImageView mImageView) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(errorImageView).error(errorImageView).transform(new BrightnessFilterTransformation()).circleCrop();
        GlideApp.with(context).load(resourceId).apply(options).into(mImageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView mImageView, int errorImage) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(errorImageView).error(errorImage).transform(new BrightnessFilterTransformation()).circleCrop();
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }

    public static void loadCircleImage(Context context, Integer resourceId, ImageView mImageView, int errorImage) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(errorImageView).error(errorImage).transform(new BrightnessFilterTransformation()).circleCrop();
        GlideApp.with(context).load(resourceId).apply(options).into(mImageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView mImageView, int round) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).error(errorImageView).transform(new RoundedCornersTransformation(round, 0, RoundedCornersTransformation.CornerType.BOTTOM)).centerCrop();
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView mImageView, int round, int errorImage) {
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).error(errorImage).transform(new RoundedCornersTransformation(round, 0, RoundedCornersTransformation.CornerType.BOTTOM)).centerCrop();
        GlideApp.with(context).load(url).apply(options).into(mImageView);
    }

    public static Bitmap loadImageToBitmap(Context context, String url) {

        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(R.mipmap.ic_load_fail).error(errorImageView).transform(new BrightnessFilterTransformation());
        GlideApp.with(context).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (resource != null)
                    bitmap = resource;
            }
        });
        return bitmap;
    }

    private Bitmap createFailedBitmap(Context context, int ic_load_fail) {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, ic_load_fail);
    }


}
