package org.tensorflow.lite.examples.detection.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.activity.MainActivity;
import org.tensorflow.lite.examples.detection.room.FruitEntity;
import org.tensorflow.lite.examples.detection.room.FruitRepository;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;
    DatabaseReference mFirebaseDatabase;
    FirebaseDatabase mFirebaseInstance;

    FruitRepository fruitRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        fruitRepository = new FruitRepository(this);

        if (sessionManager.isAlreadyOpen()) {
            openMainActivity();
        } else {
            sessionManager.createSession();

            mFirebaseInstance = FirebaseDatabase.getInstance();
            mFirebaseDatabase = mFirebaseInstance.getReference("fruits");
            mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot myData : dataSnapshot.getChildren()) {
                        fruitRepository.insertDataFruit(new FruitEntity(myData.getKey(),
                                myData.child("fruit_img").getValue(String.class),
                                myData.child("fruit_avg_weight").getValue(Double.class),
                                myData.child("fruit_water").getValue(Double.class)));
                    }

                    openMainActivity();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void openMainActivity() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }, 1500L);
    }
}
