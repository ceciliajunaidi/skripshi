package org.tensorflow.lite.examples.detection.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.fruit.FruitListActivityAdapter;
import org.tensorflow.lite.examples.detection.room.FruitDatabase;
import org.tensorflow.lite.examples.detection.room.FruitEntity;
import org.tensorflow.lite.examples.detection.room.FruitRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FruitListActivity extends AppCompatActivity {

    RecyclerView rvBuah;
    FruitRepository fruitRepository;
    FruitDatabase fruitDatabase;
    FruitListActivityAdapter fruitListAdapter;
    List<FruitEntity> arrayFruit = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_list);

        rvBuah = findViewById(R.id.rvBuah);
        fruitListAdapter = new FruitListActivityAdapter(this, arrayFruit);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FruitListActivity.this);
        rvBuah.setLayoutManager(layoutManager);
        rvBuah.setAdapter(fruitListAdapter);

        fruitRepository = new FruitRepository();
        fruitDatabase = FruitRepository.getFruitDatabase(this);
        fruitDatabase.fruitDao().getFruitList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<FruitEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<FruitEntity> fruitEntities) {
                        arrayFruit.addAll(fruitEntities);
                        fruitListAdapter.notifyDataSetChanged();

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
