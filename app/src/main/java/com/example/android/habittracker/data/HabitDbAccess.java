package com.example.android.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/9 0009.
 * com.example.android.habittracker.data,HabitTracker
 */

public class HabitDbAccess {
    public HabitDbAccess(Context c) {
        this.context = c;
        habitDbHelper = new HabitDbHelper(context);
    }

    private Context context;
    private HabitDbHelper habitDbHelper;

    /**
     *向表中插入一行数据
     * @param contentValues key-values
     */
    public void insertData(ContentValues contentValues){

        //如果contentValue没有数据就return
        if(contentValues.size() <1){
            return;
        }

        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME,null,contentValues);

        if(newRowId == -1){
            Toast.makeText(context,"Add habits error!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Add habits success with id:"+Long.toString(newRowId),Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 根据id删除表中一行数据
     * @param id primry key
     */
    public void deleteData(int id){

        String selection = HabitContract.HabitEntry._ID + " = ?";

        String[] selectionArgs = {""};

        selectionArgs[0] = Integer.toString(id);

        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        db.delete(HabitContract.HabitEntry.TABLE_NAME,selection,selectionArgs);
    }

    /**
     * 根据Id，更新表中的数据
     * @param id primary key
     */
    public void updateData(int id, ContentValues values){

        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        String selection = HabitContract.HabitEntry._ID + " = ?";

        String[] selectionArgs = {""};

        selectionArgs[0] = Integer.toString(id);

        db.update(HabitContract.HabitEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    /**
     * 检索出表中所有的数据
     * @return arraylist<HabitModel>
     */
    public ArrayList<HabitModel> queryDataAll(){
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();
        ArrayList<HabitModel> habits = new ArrayList<>();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT,
                HabitContract.HabitEntry.COLUMNS_HABIT_TIME
        };

        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,projection,null,null,null,null,null);

        try {
            while (cursor.moveToNext()){
                int idColumnsIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
                int habitContenColumnsIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT);
                int timeColumnsIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMNS_HABIT_TIME);

                int id = cursor.getInt(idColumnsIndex);
                String habitContent = cursor.getString(habitContenColumnsIndex);
                String time = cursor.getString(timeColumnsIndex);

                habits.add(new HabitModel(id,habitContent,time));
            }
        }finally {
            cursor.close();
        }

        return habits;
    }

    /**
     * 根据Id检索出数据
     * @param id primary key
     * @return habitmodel
     */
    public HabitModel queryModelById(int id){
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();
        HabitModel habitModel = null;

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT,
                HabitContract.HabitEntry.COLUMNS_HABIT_TIME
        };

        String selection = HabitContract.HabitEntry._ID + " = ? ";
        String[] selectionArgs = {""};
        selectionArgs[0] = Integer.toString(id);

        Cursor c = db.query(HabitContract.HabitEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
        try {
            while (c.moveToNext()){
                int idColumnsIndex = c.getColumnIndex(HabitContract.HabitEntry._ID);
                int habitContenColumnsIndex = c.getColumnIndex(HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT);
                int timeColumnsIndex = c.getColumnIndex(HabitContract.HabitEntry.COLUMNS_HABIT_TIME);

                int tid = c.getInt(idColumnsIndex);
                String habitContent = c.getString(habitContenColumnsIndex);
                String time = c.getString(timeColumnsIndex);

                habitModel = new HabitModel(tid,habitContent,time);
            }
        }finally {
            c.close();
        }

        return habitModel;
    }
}
