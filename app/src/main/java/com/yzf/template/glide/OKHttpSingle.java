package com.yzf.template.glide;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;

/**
 * OkHttpClient 单例
 *
 * @author Yzf
 * @date 2018-8-3 11:16:02
 */
public class OKHttpSingle {

    private OKHttpSingle() {

    }

    protected static volatile OkHttpClient instance;

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OKHttpSingle.class) {
                if (instance == null) {
                    instance = ProgressManager.getInstance().with(new OkHttpClient.Builder())
                            .build();
                }
            }
        }
        return instance;
    }

}
