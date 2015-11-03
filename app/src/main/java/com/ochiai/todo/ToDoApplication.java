package com.ochiai.todo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by ochiai on 2015/11/01.
 */
public class ToDoApplication extends Application {

    @Override
    public  void  onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Task.class);



        Parse.initialize(this, "zFHlStg8SPGLXgYeYJpiN2XChp6comRbQdA762HV", "47nQbm9VL2zo6KJGwYytpAuVfSQRnKVV7bq2p4sg");
    }
}
