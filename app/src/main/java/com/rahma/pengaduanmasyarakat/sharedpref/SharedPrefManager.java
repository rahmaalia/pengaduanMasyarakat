package com.rahma.pengaduanmasyarakat.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_PENGADUAN="spPengaduan";
    public static final String SP_NAMA ="spNama";
    public static final String SP_USERNAME="spUsername";
    public static final String SP_TELP="spTelp";
    public static final String SP_NIK="spNik";

    public static final int SP_IDUSER = 1;
    public static final String SP_LOGIN="spLogin";
    public static final String SP_LOGIN_PETUGAS="spLoginPetugas";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp=context.getSharedPreferences(SP_PENGADUAN,Context.MODE_PRIVATE);
        spEditor=sp.edit();
    }
    public void saveSPString(String keySp, String value){
        spEditor.putString(keySp, value);
        spEditor.commit();
    }
    public void saveSPint(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpNama(){
        return sp.getString(SP_NAMA, "");
    }
    public String getSpUsername(){
        return sp.getString(SP_USERNAME, "");
    }
    public String getSpTelp(){
        return sp.getString(SP_TELP, "");
    }
    public String getSpNik(){
        return sp.getString(SP_NIK, "");
    }
    public Boolean getSpLogin(){
        return sp.getBoolean(SP_LOGIN, false);
    }
    public Boolean getSpLoginPetugas() {
        return sp.getBoolean(SP_LOGIN_PETUGAS,false);
    }
    public int getSpIduser(){
        return sp.getInt(String.valueOf(SP_IDUSER),2);
    }

}
