package com.example.android.moviesdatabase.Ui;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.example.android.moviesdatabase.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by emres on 7.08.2017.
 */
//For set default font in application
public class DefaultFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}
