package org.tensorflow.lite.examples.detection.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.room.FruitEntity;
import org.tensorflow.lite.examples.detection.room.FruitRepository;

import java.text.DecimalFormat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CalculationActivity extends AppCompatActivity {

    ImageView imageResult;
    TextView txtResult, displayInteger, txtResultCalculation;
    String result;
    Button btnHitung, btnIncrease, btnDecrease;

    FruitEntity fruit;
    FruitRepository fruitRepository;

    int minteger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        Intent intent = getIntent();
        result = intent.getStringExtra("result");
//        result = result.replaceAll("_", " ").toLowerCase();

        txtResultCalculation = findViewById(R.id.txtResultCalculation);
        txtResult = findViewById(R.id.txtResult);
        txtResult.setText(result);
        imageResult = findViewById(R.id.imgIcon);

        fruitRepository = new FruitRepository(this);
        fruitRepository.getFruitByName(result.toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FruitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FruitEntity fruitEntity) {
                fruit = fruitEntity;

                byte[] decodedBytes = Base64.decode(fruit.fruitImage, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                Glide.with(CalculationActivity.this).load(decodedBitmap).into(imageResult);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        btnHitung = findViewById(R.id.btnHitung);
        btnHitung.setEnabled(false);
        btnHitung.setOnClickListener(v -> {
            DecimalFormat formatHasil = new DecimalFormat("#0.00");
            Double hasil = (minteger * fruit.getFruitAvgWeight() / 100) * fruit.getFruitWater();
//            txtResultCalculation.setText(String.valueOf(hasil));
            txtResultCalculation.setText(formatHasil.format(hasil));

            Intent intentResult = new Intent(getApplicationContext(), FinalResultActivity.class);
            intentResult.putExtra("finalResult", String.valueOf(formatHasil.format(hasil)));
            intentResult.putExtra("result", fruit.fruitName);
            intentResult.putExtra("jumlahBuah", minteger);
            startActivity(intentResult);
//
//            finish();
        });

        btnIncrease = findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(v -> {
            minteger = minteger + 1;

            display(minteger);
        });

        btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(v -> {
            minteger = minteger - 1;

            display(minteger);
        });

        displayInteger = findViewById(R.id.integer_number);
        displayInteger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (minteger == 0) {
                    btnDecrease.setEnabled(false);
                    btnHitung.setEnabled(false);
                } else {
                    btnDecrease.setEnabled(true);
                    btnHitung.setEnabled(true);
                }
            }
        });
    }

    private void display(int number) {
        displayInteger.setText(String.valueOf(number));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
