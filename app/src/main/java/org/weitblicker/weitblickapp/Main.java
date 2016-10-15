package org.weitblicker.weitblickapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void send(View view) {
        Intent intent = new Intent(this, ProjectList.class);
        startActivity(intent);
    }
}
