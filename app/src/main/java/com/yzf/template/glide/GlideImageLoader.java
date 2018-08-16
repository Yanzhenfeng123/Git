package com.yzf.template.glide;

import android.content.Context;
import android.widget.ImageView;


public class GlideImageLoader extends ImageLoaderBanner {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        ImageLoader.getInstance().loadImage(context,(String) path,imageView);
    }
}
