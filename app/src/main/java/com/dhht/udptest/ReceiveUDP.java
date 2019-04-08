package com.dhht.udptest;

import android.util.Log;
import com.dhht.udptest.threadpool.AppExecutors;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveUDP {

    AppExecutors mAppExecutors;
    MsgCallback mMsgCallback;


    public ReceiveUDP(AppExecutors appExecutors, MsgCallback msgCallback) {
        mAppExecutors = appExecutors;
        mMsgCallback = msgCallback;
    }

    public void waiteMsg(final int listenPort) {

        mAppExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    DatagramSocket responseSocket = new DatagramSocket(listenPort);
                    Log.e("waiteMsg", "开始等待消息");
                    while (true) {
                        responseSocket.receive(packet);
                        String addr = "发送地址为：" + packet.getSocketAddress();
                        String msg = "接收到数据：" + new String(packet.getData(), 0, packet.getLength());
                        final String receiveMsg = msg + "\n" + addr;

                        mAppExecutors.mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                mMsgCallback.msgBack(receiveMsg);
                            }
                        });

                        Log.e("ReceivedMsg：", receiveMsg);
                        String backData = "去你大爷的";
                        byte[] data = backData.getBytes();
                        Log.e("SendMsg：", "Send " + backData + " to " + packet.getSocketAddress());

                        DatagramPacket backPacket = new DatagramPacket(data, 0,
                                data.length, packet.getSocketAddress());
                        responseSocket.send(backPacket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface MsgCallback {
        void msgBack(String msg);
    }

}