package org.tensorflow.lite.examples.detection.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

    public class FruitRepository {

        private FruitDatabase fruitDatabase;

        public FruitRepository(Context context) {
            fruitDatabase = Room.databaseBuilder(context, FruitDatabase.class, "db_fruit").build();
        }

        public void insertDataFruit(FruitEntity fruit) {
            Completable.fromAction(() -> fruitDatabase.fruitDao().insertDataFruit(fruit))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            Log.d("DebugCode", "Insert data success");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("DebugCode", "Insert data failed");
                        }
                    });
        }

        public Observable<FruitEntity> getFruitByName(String fruitName) {
            return fruitDatabase.fruitDao().getFruitByName(fruitName);
        }


        public Observable<List<FruitEntity>> getFruitList() {
            return fruitDatabase.fruitDao().getFruitList();
        }
    }

