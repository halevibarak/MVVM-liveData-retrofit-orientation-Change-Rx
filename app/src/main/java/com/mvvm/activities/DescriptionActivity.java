package com.mvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mvvm.R;

import static com.mvvm.activities.MainActivity.DESCRIPTION;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Intent in = getIntent();
        final String desc = in.getStringExtra(DESCRIPTION);
        final TextView textView = findViewById(R.id.desc_text);
        textView.setText(desc);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescriptionActivity.this, DescriptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(DESCRIPTION,desc+"d");
                startActivity(intent);
            }
        });

    }
}
