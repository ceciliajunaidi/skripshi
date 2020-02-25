package org.tensorflow.lite.examples.detection.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.lite.examples.detection.CameraActivity;
import org.tensorflow.lite.examples.detection.DetectorActivity;
import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.room.FruitDatabase;
import org.tensorflow.lite.examples.detection.room.FruitEntity;
import org.tensorflow.lite.examples.detection.room.FruitRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FinalResultActivity extends AppCompatActivity {

    ImageView imgIconResult;
    TextView txtResultCalculation, txtNamaBuah, txtJumlahBuah;
    String finalResult, namaBuah;
    int jumlahBuah;

    Button btnSelesai, btnUlangi;

    FruitDatabase fruitDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        Intent intent = getIntent();
        finalResult = intent.getStringExtra("finalResult");
        jumlahBuah = intent.getIntExtra("jumlahBuah", 0);
        namaBuah = intent.getStringExtra("result");

        txtResultCalculation = findViewById(R.id.txtResultCalculation);
        txtResultCalculation.setText(finalResult);

        txtJumlahBuah = findViewById(R.id.txtJumlahBuah);
        txtJumlahBuah.setText(String.valueOf(jumlahBuah));

        txtNamaBuah = findViewById(R.id.txtNamaBuah);
        txtNamaBuah.setText(namaBuah);

        imgIconResult = findViewById(R.id.imgIconResult);
        fruitDatabase = FruitRepository.getFruitDatabase(this);
        fruitDatabase.fruitDao().getFruitByName(namaBuah.toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FruitEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FruitEntity fruitEntity) {
                        byte[] decodedBytes = Base64.decode(fruitEntity.fruitImage, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        Glide.with(FinalResultActivity.this).load(decodedBitmap).into(imgIconResult);

                        fruitDatabase.close();
                        Log.d("Database", "Database di close");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });

        btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(v -> {
            Intent intentSelesai = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentSelesai);

            finish();
        });

        btnUlangi = findViewById(R.id.btnUlangi);
        btnUlangi.setOnClickListener(v -> {
            Intent intentUlangi = new Intent(getApplicationContext(), DetectorActivity.class);
            startActivity(intentUlangi);

            finish();
        });

    }
}
