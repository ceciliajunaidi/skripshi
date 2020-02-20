package org.tensorflow.lite.examples.detection.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface FruitDao {

    @Insert
    void insertDataFruit(FruitEntity fruitEntity);

    @Query("SELECT * FROM fruitentity where fruitName= :fruitName")
    Observable<FruitEntity> getFruitByName(String fruitName);

    @Query("SELECT * FROM fruitentity order by fruitName asc")
    Observable<List<FruitEntity>> getFruitList();

}

