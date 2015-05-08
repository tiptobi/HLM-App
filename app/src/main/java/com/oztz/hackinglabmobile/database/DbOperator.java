package com.oztz.hackinglabmobile.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Tobi on 08.05.2015.
 */
public class DbOperator {

    HackingLabDbHelper helper;

    public DbOperator(Context context){
        helper = new HackingLabDbHelper(context);
    }

    public boolean addQrCode(String Payload){
        try {
            String[] parts = Payload.split("-");
            if (parts.length == 3) {
                SQLiteDatabase db = helper.getWritableDatabase();
                String query = "INSERT INTO " + helper.QR_TABLE_NAME + "" +
                        "(role, eventid, secret) VALUES ('" +
                        parts[0] + "', " + parts[1] + ", '" + parts[2] + "');";
                db.execSQL(query);
                Log.d("DEBUG", query);
                return true;
            }
            else{
                return false;
            }
        } catch(Exception e){
            Log.d("DEBUG", e.getMessage());
            return false;
        }
    }

    public String getQrCode(String role, int eventid){
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM " + HackingLabDbHelper.QR_TABLE_NAME +
                " WHERE role = '" + role + "' AND eventid = " + String.valueOf(eventid);
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() > 0){
            c.moveToFirst();
            return c.getString(c.getColumnIndex("role")) + "-" +
                    c.getInt(c.getColumnIndex("eventid")) + "-" +
                    c.getString(c.getColumnIndex("secret"));
        } else{
            return null;
        }
    }
}