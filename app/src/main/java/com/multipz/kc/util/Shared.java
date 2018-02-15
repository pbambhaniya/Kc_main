package com.multipz.kc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Shared {
    SharedPreferences pref;
    Editor edit;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch1";
    private static final String USER_ID = "user_id";
    private static final String SALARY_BASE_TYPE = "salary_base_type";

    public Shared(Context c) {
        // TODO Auto-generated constructor stub
        pref = c.getSharedPreferences("file_pref", Context.MODE_PRIVATE);
        edit = pref.edit();
    }

    public void putBoolean(String key, boolean b) {
        edit.putBoolean(key, b);
        edit.commit();
    }

    public boolean getBoolean(String key, boolean def) {
        return pref.getBoolean(key, def);
    }

    public void putString(String key, String def) {
        edit.putString(key, def);
        edit.commit();
    }

    public String getString(String key, String def) {
        return pref.getString(key, def);
    }

    public void putInt(String key, int def) {
        edit.putInt(key, def);
        edit.commit();
    }

    public String getUserId() {
        String cust_id = pref.getString(USER_ID, "");
        return cust_id;
    }

    public void setUserId(String uid) {
        edit.putString(USER_ID, uid);
        edit.commit();
    }

    public String getSalaryBaseType() {
        String stype = pref.getString(SALARY_BASE_TYPE, "");
        return stype;
    }

    public void setSalaryBaseType(String type) {
        edit.putString(SALARY_BASE_TYPE, type);
        edit.commit();
    }

    public int getInt(String key) {
        return pref.getInt(key, 0);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        edit.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        edit.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch1(boolean isFirstTime) {
        edit.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        edit.commit();
    }

    public boolean isFirstTimeLaunch1() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
