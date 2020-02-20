package org.tensorflow.lite.examples.detection.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FruitEntity {

    public FruitEntity(String fruitName, String fruitImage, Double fruitAvgWeight, Double fruitWater) {
        this.fruitName = fruitName;
        this.fruitImage = fruitImage;
        this.fruitAvgWeight = fruitAvgWeight;
        this.fruitWater = fruitWater;
    }

    @PrimaryKey(autoGenerate = true)
    public int fruitId;

    public String fruitName;

    public String fruitImage;

    public Double fruitAvgWeight;

    public Double fruitWater;

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitImage() {
        return fruitImage;
    }

    public void setFruitImage(String fruitImage) {
        this.fruitImage = fruitImage;
    }

    public Double getFruitAvgWeight() {
        return fruitAvgWeight;
    }

    public void setFruitAvgWeight(Double fruitAvgWeight) {
        this.fruitAvgWeight = fruitAvgWeight;
    }

    public Double getFruitWater() {
        return fruitWater;
    }

    public void setFruitWater(Double fruitWater) {
        this.fruitWater = fruitWater;
    }
}
