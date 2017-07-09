package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/7/9 0009.
 * com.example.android.habittracker.data,HabitTracker
 */

public class HabitContract {
    public HabitContract() {

    }

    public static final class HabitEntry implements BaseColumns{

        //表名称
        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;

        //习惯内容
        public static final String COLUMNS_HABIT_CONTENT = "habitcontext";

        //动作时间
        public static final String COLUMNS_HABIT_TIME = "habittime";

    }
}
