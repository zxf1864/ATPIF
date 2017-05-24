package com.afei.atpif.SocketClient;

import java.util.Arrays;

import timber.log.Timber;

/**
 * Created by afei on 2017-05-23.
 */

/**
 * <pre>
 * 自己定义的协议
 *  数据包格式
 * +——----——+——-----——+——----——+
 * |协议开始标志| 功能 / 长度             |   数据       | crc
 * +——----——+——-----——+——----——+
 * 1.协议开始标志head_data，为int类型的数据，16进制表示为0Xeb
 * 2.传输数据的长度contentLength，int类型
 * 3.要传输的数据
 * </pre>
 */
public class ATPIFSocketProtocol {
    /**
     * 消息的开头的信息标志
     */
    private int head_data = SocketConst.Header;
    /**
     * 消息的功能
     */
    private int contentFunc;
    /**
     * 消息的长度
     */
    private int contentLength;
    /**
     * 消息的内容
     */
    private byte[] content;


    /**
     * 用于初始化，SmartCarProtocol
     *
     * @param contentLength
     *            协议里面，消息数据的长度
     * @param content
     *            协议里面，消息的数据
     */
    public ATPIFSocketProtocol(int contentFunc,int contentLength, byte[] content) {
        this.contentFunc = contentFunc;
        this.contentLength = contentLength;
        this.content = content;
        Timber.d(this.toString());
    }

    public int getHead_data() {
        return head_data;
    }

    public int getContentFunc() {
        return contentFunc;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentFunc(int contentFunc)
    {
        this.contentFunc = contentFunc;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ATPIFSocketProtocol [head_data=" + head_data +  ", contentFunc=" + contentFunc + ", contentLength="
                + contentLength + ", content=" + ByteUtil.bytesToHex(content) + "]";
    }

}
