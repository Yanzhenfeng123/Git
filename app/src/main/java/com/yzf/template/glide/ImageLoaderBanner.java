package com.yzf.template.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoaderInterface;


public abstract class ImageLoaderBanner implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context.getApplicationContext());
        return imageView;
    }

}