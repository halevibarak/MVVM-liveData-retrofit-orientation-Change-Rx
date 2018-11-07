package com.test.halevi.barakapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.test.halevi.barakapp.R;

import static com.test.halevi.barakapp.activities.MainActivity.DESCRIPTION;

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
