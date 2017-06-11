package com.example.android.notepad;


import android.content.ContentValues;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



 public class GetTime {

    public static String getNowDate()
    {
        Date dt= new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//24小时格式
        String time = sdf.format(dt);
        return time;
    }
/*
    static public long Get_Now_Time_Long()
    {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");//设置显示格式df.format(dt)
        Long now = Long.valueOf( df.format(dt) );////////////////////////////////////
        return now;
    }

    static public Date Get_Now_Time_date()
    {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");//设置显示格式df.format(dt)
        return dt;
    }


    static public ContentValues Get_Title_Now(ContentValues values)
    {
        Long now = GetTime.Get_Now_Time_Long();
        //values值取出Title，并且  +" "+now
        values.put(NotePad.Notes.COLUMN_NAME_TITLE, values.get(NotePad.Notes.COLUMN_NAME_TITLE) +" "+now );
        return values;
    }
*/

}

