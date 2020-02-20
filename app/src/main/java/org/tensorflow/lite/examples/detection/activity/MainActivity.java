package org.tensorflow.lite.examples.detection.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.R;

public class MainActivity extends AppCompatActivity {

    CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout() {
        card1 = findViewById(R.id.card1);
        card1.setOnClickListener(v -> {
            Intent intentDetect = new Intent(getApplicationContext(), DetectorActivity.class);
            startActivity(intentDetect);
        });

        card2 = findViewById(R.id.card2);
        card2.setOnClickListener(v -> {
            Intent intentDaftar = new Intent(getApplicationContext(), FruitListActivity.class);
            startActivity(intentDaftar);
        });

        card3 = findViewById(R.id.card3);
        card3.setOnClickListener(v -> {
            Intent intentAbout = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intentAbout);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        finish();
    }
}
