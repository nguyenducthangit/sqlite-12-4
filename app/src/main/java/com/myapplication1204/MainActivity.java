package com.myapplication1204;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myapplication1204.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareDb();
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }

}