package com.peter.guardianangel.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.peter.guardianangel.bean.MyLocation;
import com.peter.guardianangel.bean.User;

public class UserData {

    private final String TAG = this.getClass().getSimpleName();

    private static final UserData ourInstance = new UserData();

    public static UserData getInstance() {
        return ourInstance;
    }

    private String deviceId;

    private Context context;

    private User user;

    private String genkey;

    private MyLocation partnerLocation;

    private SocketClient socketClient = null;

    // 用户当前位置
    private MyLocation location;

    private UserData() {
    }

    public void init(Context context){
        this.context = context;
        deviceId = getInnerDeviceId(context);
    }

    public String getDeviceId(){
        return deviceId;
    }

    private String getInnerDeviceId(Context context) {
        String serial = "";
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return szDevIDShort;
        }
        String devid = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if(devid != null) {
            szDevIDShort = szDevIDShort + serial + devid;
        }else {
            szDevIDShort = szDevIDShort + serial;
        }

        Log.d(TAG, "device id : " + szDevIDShort);
        return szDevIDShort;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.setGenkey(genkey);
    }

    public void setGenkey(String genkey) {
        this.genkey = genkey;
    }

    public String getGenkey() {
        return genkey;
    }

    public MyLocation getPartnerLocation() {
        return partnerLocation;
    }

    public void setPartnerLocation(MyLocation partnerLocation) {
        this.partnerLocation = partnerLocation;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }
}
