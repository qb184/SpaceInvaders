package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NextLevelActivity extends AppCompatActivity {
    private Button level2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_level);

        level2 = findViewById(R.id.level2);

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent level2intent = new Intent(NextLevelActivity.this, MainActivity.class);
                startActivity(level2intent);
            }
        });
    }
}