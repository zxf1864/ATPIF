package com.afei.atpif.SocketClient;

/**
 * Created by afei on 2017-05-22.
 */


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;


import com.afei.atpif.Model.LineData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import timber.log.Timber;

/**
 *
 * Created by LiuSaibao on 11/17/2016.
 */
public class NettyService extends Service implements NettyListener {

    private NetworkReceiver receiver;
    private static String sessionId = null;

    private ScheduledExecutorService mScheduledExecutorService;
    private void shutdown() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NetworkReceiver();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        // 自定义心跳，每隔20秒向服务器发送心跳包
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                byte[] requestBody = {(byte) 0xFE, (byte)0xED, (byte)0xFE, 5};
                NettyClient.getInstance().sendMsgToServer(requestBody, new ChannelFutureListener() {    //3
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {                //4
                            Timber.d("Write heartbeat successful");

                        } else {
                            Timber.e("Write heartbeat error");
                            WriteLogUtil.writeLogByThread("heartbeat error");
                        }
                    }
                });
            }
        }, 20, 20, TimeUnit.SECONDS);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NettyClient.getInstance().setListener(this);
        connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {		//连接状态监听
        Timber.d("connect status:%d", statusCode);
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
            authenticData();
        } else {
            WriteLogUtil.writeLogByThread("tcp connect error");
        }
    }

    /**
     * 认证数据请求
     */
    private void authenticData() {
        AuthModel auth = new AuthModel();
        auth.setI(1);
        auth.setU("sn");
        auth.setN("name");
        auth.setF("1");
        auth.setT((int)(System.currentTimeMillis() / 1000));
        byte[] content = RequestUtil.getEncryptBytes(auth);
        byte[] requestHeader = RequestUtil.getRequestHeader(content, 1, 1001);
        byte[] requestBody = RequestUtil.getRequestBody(requestHeader, content);
        NettyClient.getInstance().sendMsgToServer(requestBody, new ChannelFutureListener() {    //3
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {                //4
                    Timber.d("Write auth successful");
                } else {
                    Timber.d("Write auth error");
                    WriteLogUtil.writeLogByThread("tcp auth error");
                }
            }
        });
    }

    @Override
    public void onMessageResponse(ATPIFSocketProtocol byteBuf) {
        DeframeHandle(byteBuf);
    }

    private void DeframeHandle(ATPIFSocketProtocol byteBuf)
    {
        int header = byteBuf.getHead_data();
        int func = byteBuf.getContentFunc();
        int len = byteBuf.getContentLength();

        String log = String.format("header=%d, func=%d, len = %d", header, func, len);
        Timber.i(log);

        final ATPIFSocketProtocol protocol = byteBuf;

        LineData.getInstance().AddData(protocol);
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        */
    }

    private void handle(int t, int i, int f) {
        // TODO 实现自己的业务逻辑
    }

    private void connect(){
        if (!NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        shutdown();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connect();
                }
            }
        }
    }
}
