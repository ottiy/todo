package com.ochiai.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by ochiai on 2015/11/01.
 */
public class TaskAdapter extends ArrayAdapter<Task>
    implements CompoundButton.OnCheckedChangeListener{

    private  int mResourceId;
    private LayoutInflater mInflater;


    public TaskAdapter(Context context, int resource,List<Task> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResourceId = resource;
    }

    @Override
    public View getView(int porion,View convationView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convationView == null) {

            convationView = mInflater.inflate(mResourceId, null);
            viewHolder = new ViewHolder(convationView);
            convationView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convationView.getTag();
        }
            Task task = getItem(porion);

            viewHolder.checkBox.setText(task.getTitle());
            viewHolder.checkBox.setOnCheckedChangeListener(null);
            viewHolder.checkBox.setChecked(task.isCheck());
            viewHolder.checkBox.setOnCheckedChangeListener(this);
            viewHolder.checkBox.setTag(task);

            return convationView;
    }
    public  class  ViewHolder{
        CheckBox checkBox;

        public  ViewHolder(View view){
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }
    }

    @Override
    public  void  onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        Task task = (Task) buttonView.getTag();
        task.setCheck(isChecked);
        task.saveInBackground();
    }
}