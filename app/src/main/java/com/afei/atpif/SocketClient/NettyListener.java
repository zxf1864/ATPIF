package com.afei.atpif.SocketClient;

/**
 * Created by afei on 2017-05-22.
 */


import io.netty.buffer.ByteBuf;

public interface NettyListener {

    public final static byte STATUS_CONNECT_SUCCESS = 1;

    public final static byte STATUS_CONNECT_CLOSED = 0;

    public final static byte STATUS_CONNECT_ERROR = 0;


    /**
     * 当接收到系统消息
     */
    void onMessageResponse(ATPIFSocketProtocol byteBuf);

    /**
     * 当服务状态发生变化时触发
     */
    public void onServiceStatusConnectChanged(int statusCode);
}
