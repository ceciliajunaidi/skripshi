package org.tensorflow.lite.examples.detection.room;

import android.content.Context;

import androidx.room.Room;

    public class FruitRepository {

        private static FruitDatabase fruitDatabase = null;

        public static FruitDatabase getFruitDatabase(Context context) {
            if (fruitDatabase == null) {
                fruitDatabase = Room.databaseBuilder(context, FruitDatabase.class, "db_fruit").build();
            }
            return fruitDatabase;
        }
    }

