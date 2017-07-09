package com.example.android.habittracker.data;

/**
 * Created by Administrator on 2017/7/9 0009.
 * com.example.android.habittracker.data,HabitTracker
 */

public class HabitModel {

    private int id;
    private String habit;
    private String habittime;

    public HabitModel(int _id,String habit,String habittime) {

        this.id = _id;
        this.habit = habit;
        this.habittime = habittime;

    }

    public int getId() {
        return id;
    }

    public String getHabit() {
        return habit;
    }

    public String getHabittime() {
        return habittime;
    }
}
