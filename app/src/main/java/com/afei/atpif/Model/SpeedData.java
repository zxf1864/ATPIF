package com.afei.atpif.Model;

import com.afei.atpif.SocketClient.ATPIFSocketProtocol;
import com.afei.atpif.SocketClient.ByteUtil;

/**
 * Created by afei on 2017-05-26.
 */

public class SpeedData {
    private int speed;
    private int postion;
    private int acc;
    private int track;

    public SpeedData(ATPIFSocketProtocol protocol)
    {
        speed = ByteUtil.byteArrayToInt(ByteUtil.subBytes(protocol.getContent(),0,2));
        postion = ByteUtil.byteArrayToInt(ByteUtil.subBytes(protocol.getContent(),0,2));
    }


}


