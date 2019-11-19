/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thinkcore.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;

/**
 * @Title 监听网络广播
 */
public class TNetworkStateReceiver extends BroadcastReceiver {
    private static Boolean networkAvailable = false;
    private static TNetWorkUtil.netType netType;
    private static ArrayList<INetChangeListener> netChangeObserverArrayList = new ArrayList<INetChangeListener>();
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String TA_ANDROID_NET_CHANGE_ACTION = "think.android.net.conn.CONNECTIVITY_CHANGE";
    private static TNetworkStateReceiver that;
    private Context context;

    public static TNetworkStateReceiver getInstance() {
        if (that == null) {
            that = new TNetworkStateReceiver();
        }
        return that;
    }

    public void initConfig(Context context) {
        this.context = context;
        registerNetworkStateReceiver();
    }

    public void release() {
        unRegisterNetworkStateReceiver();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)
                || intent.getAction().equalsIgnoreCase(
                TA_ANDROID_NET_CHANGE_ACTION)) {
            Log.i("TANetworkStateReceiver", "网络状态改变.");
            if (!TNetWorkUtil.isNetworkAvailable(context)) {
                Log.i("TANetworkStateReceiver", "没有网络连接.");
                networkAvailable = false;
            } else {
                Log.i("TANetworkStateReceiver", "网络连接成功.");
                this.netType = TNetWorkUtil.getAPNType(context);
                networkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     */
    private void registerNetworkStateReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        context.getApplicationContext()
                .registerReceiver(getInstance(), filter);
    }

    /**
     * 检查网络状态
     */
    public void checkNetworkState() {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        context.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     */
    private void unRegisterNetworkStateReceiver() {
        try {
            context.getApplicationContext().unregisterReceiver(this);
        } catch (Exception e) {
            Log.d("TANetworkStateReceiver", e.getMessage());
        }
    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public TNetWorkUtil.netType getAPNType() {
        return this.netType;
    }

    private void notifyObserver() {
        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            INetChangeListener observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    observer.onConnect(this.netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     *
     * @param observer
     */
    public void registerObserver(INetChangeListener observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<>();
        }
        netChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     *
     * @param observer
     */
    public void removeRegisterObserver(INetChangeListener observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }
}