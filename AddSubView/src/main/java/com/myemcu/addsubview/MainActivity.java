package com.myemcu.addsubview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.myemcu.addsubview.view.AddSubView;

public class MainActivity extends AppCompatActivity {

    private AddSubView add_sub_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 不实例化的话，就会报空指针异常
        add_sub_view = (AddSubView) findViewById(R.id.add_sub_view);

        add_sub_view.setOnAddSubValueListener(new AddSubView.OnAddSubValueListener() {
            @Override
            public void onAddSubValue(int value) {
                Toast.makeText(MainActivity.this,"回调值："+value,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
