package org.tensorflow.lite.examples.detection.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FruitEntity.class}, version = 1, exportSchema = false)
public abstract class FruitDatabase extends RoomDatabase {

    public abstract FruitDao fruitDao();
}
