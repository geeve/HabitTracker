package com.example.android.habittracker;

import android.content.ContentValues;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDbAccess;
import com.example.android.habittracker.data.HabitModel;

import static android.R.attr.id;

public class EditorActivity extends AppCompatActivity {

    //是新增还是修改，false新增，true修改
    private boolean status = false;

    private int mId;

    EditText etHabit;
    EditText etDate;
    EditText etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        etHabit = (EditText) findViewById(R.id.edit_habit);
        etDate = (EditText) findViewById(R.id.edit_date);
        etTime = (EditText) findViewById(R.id.edit_time);

        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        if(bundle == null){
            actionBar.setTitle(R.string.Add_Habit_Title);
            status = false;
            return;
        }else{
            actionBar.setTitle(R.string.Edit_Habit_Title);
            status = true;
        }

        //如果是编辑Habit，就通过Id将修改前的内容填上
        mId = bundle.getInt(HabitContract.HabitEntry._ID);
        HabitDbAccess habitDbAccess = new HabitDbAccess(this);
        HabitModel habitModel = habitDbAccess.queryModelById(mId);

        etHabit.setText(habitModel.getHabit());
        String[] dateTime = habitModel.getHabittime().split(getString(R.string.space));
        etDate.setText(dateTime[0]);
        etTime.setText(dateTime[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                if(!status) {
                    insertHabit();

                }else {
                    updateHabit(mId);
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertHabit(){

        String habit = etHabit.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if(habit.isEmpty() || date.isEmpty() || time.isEmpty()){
            Toast.makeText(this, R.string.error_tips,Toast.LENGTH_LONG);
            return;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT,habit);
        contentValues.put(HabitContract.HabitEntry.COLUMNS_HABIT_TIME,date + getString(R.string.space) + time);

        HabitDbAccess habitDbAccess = new HabitDbAccess(this);

        habitDbAccess.insertData(contentValues);
    }

    private void updateHabit(int id){

        String habit = etHabit.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();

        if(habit.isEmpty() || date.isEmpty() || time.isEmpty()){
            Toast.makeText(this,R.string.error_tips,Toast.LENGTH_LONG);
            return;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitContract.HabitEntry.COLUMNS_HABIT_CONTENT,habit);
        contentValues.put(HabitContract.HabitEntry.COLUMNS_HABIT_TIME,date + " " + time);

        HabitDbAccess habitDbAccess = new HabitDbAccess(this);

        habitDbAccess.updateData(id,contentValues);
    }

}
