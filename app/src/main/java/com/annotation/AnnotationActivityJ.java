package com.annotation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AnnotationActivityJ extends AppCompatActivity {
    private static final String TAG = "AnnotationActivityJ";

    @BtsBindView(R.id.annotation_btn)
    private TextView mBtn;
    @BtsBindViews({R.id.annotation_array_btn, R.id.annotation_array_btn1})
    private ArrayList<View> mListUIs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        BtsBindHelper.INSTANCE.bind(this);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mListUIs.forEach(action -> {
                if (action instanceof TextView) {
                    Log.i(TAG, "list content is " + ((TextView) action).getText());
                }
            });
        }*/
        Log.i(TAG, "mBtn content is " + mBtn.getText());
        for (int i = 0, count = mListUIs.size(); i < count; i++) {
            View value = mListUIs.get(i);
            if (value instanceof TextView) {
                Log.i(TAG, "list content is " + ((TextView) value).getText());
            }
        }
    }

    @BtsOnClick({R.id.annotation_btn, R.id.annotation_btn1, R.id.annotation_next_btn})
    private void onBtnClick(View view) {
        if (view instanceof TextView) {
            Log.i(TAG, "tv content is " + ((TextView) view).getText());
        }
        if (view.getId() == R.id.annotation_next_btn) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy call");
    }
}
