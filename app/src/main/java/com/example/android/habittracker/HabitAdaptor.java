package com.example.android.habittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/9 0009.
 * com.example.android.habittracker,HabitTracker
 */

public class HabitAdaptor extends BaseAdapter {

    private Context context;
    private ArrayList<HabitModel> mHabits = new ArrayList<>();

    public HabitAdaptor(Context c, ArrayList<HabitModel> habits) {
        super();
        this.context = c;
        this.mHabits = habits;
    }

    @Override
    public int getCount() {
        return mHabits.size();
    }

    @Override
    public Object getItem(int i) {
        return mHabits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View currentView = view;
        if(currentView == null){
            currentView = LayoutInflater.from(context).inflate(R.layout.habit_list_item,viewGroup,false);
        }

        TextView tvHabit = (TextView) currentView.findViewById(R.id.item_habit);
        tvHabit.setText(mHabits.get(i).getHabit());

        TextView tvDate = (TextView) currentView.findViewById(R.id.item_time);
        tvDate.setText(mHabits.get(i).getHabittime());

        TextView tvId = (TextView) currentView.findViewById(R.id.item_id);
        tvId.setText(String.format("%d",mHabits.get(i).getId()));

        return currentView;
    }

    public void setHabits(ArrayList<HabitModel> habits){

        mHabits.clear();

        mHabits.addAll(habits);

    }
}
