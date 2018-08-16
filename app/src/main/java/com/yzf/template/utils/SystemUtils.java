package com.yzf.template.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class SystemUtils {
	/**
	 * 返回app运行状态 1:程序在前台运行 2:程序在后台运行 3:程序未启动 注意：需要配置权限<uses-permission
	 * android:name="android.permission.GET_TASKS" />
	 */
	public static boolean isAppAlive(Context context, String pageName) {

		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

		// 判断程序是否在栈顶
		if (list.get(0).topActivity.getPackageName().equals(pageName)) {
			return true;
		} else {
			// 判断程序是否在栈里
			for (ActivityManager.RunningTaskInfo info : list) {
				if (info.topActivity.getPackageName().equals(pageName)) {
					return true;
				}
			}
			return false;// 栈里找不到，返回3
		}
	}
}
