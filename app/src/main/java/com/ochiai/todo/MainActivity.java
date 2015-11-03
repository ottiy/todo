package com.ochiai.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mTaskListView;
    private TaskAdapter mAdapter;

    private EditText mTitleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskListView = (ListView) findViewById((R.id.listView));
        mAdapter = new TaskAdapter(getApplicationContext(),
                R.layout.listview_item_task,
                new ArrayList<Task>());
        mTaskListView.setAdapter(mAdapter);

        //webからtaskクラスの一覧を取ってくるぜ！！
        ParseQuery<Task> parseQuery = new ParseQuery<>(Task.class);
        parseQuery.findInBackground(new FindCallback<Task>() {
            @Override
            public void done (List < Task > list, ParseException e){
                if (e == null) {
                    mAdapter.addAll(list);
                } else {
                    e.printStackTrace();
                }
            }
        });
        Button addButton = new Button(getApplicationContext());
        addButton.setText("追加する");
        addButton.setOnClickListener(this);
        mTaskListView.addFooterView(addButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        showDialog();
    }
    private  void  showDialog(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_new_task, null);
        //タイトルを入力するEditTextを取得
        mTitleEditText = (EditText) view.findViewById(R.id.editTextTitle);

        //ダイアログの本体を作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("タスクを追加");

        builder.setPositiveButton("追加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addNewTask(mTitleEditText.getText().toString());
                dialog.dismiss();
            }


        });
        builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void addNewTask(String title){

        final Task task = new Task();
        task.setTitle(title);
        task.setCheck(false);
        task.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
            if(e == null) {
                mAdapter.add(task);
            }else {
                Toast.makeText(
                        getApplicationContext(),
                        "追加に失敗しました",
                        Toast.LENGTH_LONG).show();

            }
            }
        });
    }
}
