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
import org.tensorflow.lite.examples.detection.room.FruitEntity;
import org.tensorflow.lite.examples.detection.room.FruitRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FinalResultActivity extends AppCompatActivity {

    Dialog dialogLoading;
    ImageView imgIconResult;
    TextView txtResultCalculation, txtNamaBuah, txtJumlahBuah;
    String finalResult, namaBuah;
    int jumlahBuah;

    Button btnSelesai, btnUlangi;

    FruitRepository fruitRepository;
    FruitEntity fruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        showLoading();
        dismissLoading();

        txtResultCalculation = findViewById(R.id.txtResultCalculation);
        Intent intent = getIntent();
        namaBuah = intent.getStringExtra("result");
        finalResult = intent.getStringExtra("finalResult");
        jumlahBuah = intent.getIntExtra("jumlahBuah", 0);

        imgIconResult = findViewById(R.id.imgIconResult);
        txtJumlahBuah = findViewById(R.id.txtJumlahBuah);
        txtNamaBuah = findViewById(R.id.txtNamaBuah);
//        namaBuah = namaBuah.replaceAll("_", " ").toLowerCase();
        txtNamaBuah.setText(namaBuah);
        txtJumlahBuah.setText(String.valueOf(jumlahBuah));

        fruitRepository = new FruitRepository(this);
        fruitRepository.getFruitByName(namaBuah.toLowerCase())
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
                        Glide.with(FinalResultActivity.this).load(decodedBitmap).into(imgIconResult);
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

    private void showLoading() {
        if (dialogLoading == null) {
            dialogLoading = new Dialog(this);
            dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogLoading.setContentView(R.layout.dialog_loading);
            dialogLoading.setCancelable(false);
            dialogLoading.setCanceledOnTouchOutside(false);

            if (dialogLoading.getWindow() != null) {
                dialogLoading.getWindow().setLayout
                        (LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            }
        }

        dialogLoading.show();
    }

    private void dismissLoading() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            dialogLoading.dismiss();
            txtResultCalculation.setText(finalResult);
        }, 1500);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
