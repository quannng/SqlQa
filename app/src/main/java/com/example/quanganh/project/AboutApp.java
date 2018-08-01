package com.example.quanganh.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class AboutApp extends AppCompatActivity {
    RatingBar simpleRatingBar;
    Button btnRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        simpleRatingBar =	(RatingBar)	findViewById(R.id.ratingBar);
        btnRatingBar=(Button)findViewById(R.id.btnRatingBar);
        btnRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(), "Tổng số ngôi sao:"+simpleRatingBar.getNumStars()+" \nSố lượng đánh giá:"+simpleRatingBar.getRating(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
