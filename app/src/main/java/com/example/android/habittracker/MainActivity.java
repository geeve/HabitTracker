package com.example.android.habittracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitDbAccess;
import com.example.android.habittracker.data.HabitDbHelper;
import com.example.android.habittracker.data.HabitModel;

import java.util.ArrayList;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    private ListView mList;
    private HabitAdaptor mHabitAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView)findViewById(R.id.habit_list);
        mHabitAdaptor = new HabitAdaptor(this,new ArrayList<HabitModel>());

        //注册上下文菜单
        registerForContextMenu(mList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_habit:
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void displayInfo(){
        HabitDbAccess habitDbAccess = new HabitDbAccess(this);

        ArrayList<HabitModel> habits = new ArrayList<>();
        habits = habitDbAccess.queryDataAll();

        mHabitAdaptor.setHabits(habits);

        mList.setAdapter(mHabitAdaptor);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_list_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        TextView tvid = (TextView)info.targetView.findViewById(R.id.item_id);
        int id = Integer.parseInt(tvid.getText().toString());

        switch (item.getItemId()){
            case R.id.habit_delete:
                //通过ID删除一行数据
                HabitDbAccess habitDbAccess = new HabitDbAccess(MainActivity.this);
                habitDbAccess.deleteData(id);
                displayInfo();
                return true;
            case R.id.habit_edit:
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);

                intent.putExtra(HabitContract.HabitEntry._ID,id);
                startActivityForResult(intent,100);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
