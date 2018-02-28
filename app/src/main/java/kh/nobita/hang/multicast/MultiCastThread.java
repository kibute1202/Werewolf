package kh.nobita.hang.multicast;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import kh.nobita.hang.activity.MultiplayerWifi;

public class MultiCastThread extends Thread {
    private String TAG = MultiplayerWifi.class.getSimpleName();

    private static final String IP_GROUP = "225.4.5.6";
    private static final int PORT_GROUP = 32555;
    private static final int Max = 2048;
    private static byte[] buffer = new byte[Max];

    MulticastSocket multicastSocketSend;
    MulticastSocket multicastSocketRecei;
    DatagramPacket datagramPacketSend;
    DatagramPacket datagramPacketRecei;

    private static class MultiCastThreadHelper {
        private static final MultiCastThread INSTANCE = new MultiCastThread();
    }

    public static MultiCastThread getInstance() {
        return MultiCastThreadHelper.INSTANCE;
    }

    private MultiCastThread() {
        try {
            multicastSocketRecei = new MulticastSocket(PORT_GROUP);
            multicastSocketRecei.joinGroup(InetAddress.getByName(IP_GROUP));
            datagramPacketRecei = new DatagramPacket(buffer, Max);
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                multicastSocketRecei.receive(datagramPacketRecei);
                String data = ((new String(buffer)).trim()).substring(1);
                Log.d(TAG, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(final String data) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    multicastSocketSend = new MulticastSocket();
                    datagramPacketSend = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(IP_GROUP), PORT_GROUP);
                    Log.d(TAG, "Have been send: " + data + " at " + datagramPacketSend.getAddress().getHostAddress() + " with " + multicastSocketSend.getLocalPort());
                    multicastSocketSend.send(datagramPacketSend);
                    multicastSocketSend.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
