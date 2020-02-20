package org.tensorflow.lite.examples.detection.fruit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.room.FruitEntity;

import java.util.List;

public class FruitListActivityAdapter extends RecyclerView.Adapter<FruitListActivityAdapter.ViewHolder> {

    private Context context;
    private List<FruitEntity> fruitList;

    public FruitListActivityAdapter(Context context, List<FruitEntity> fruitList) {
        this.context = context;
        this.fruitList = fruitList;
    }

    @NonNull
    @Override
    public FruitListActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_daftar_buah, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitListActivityAdapter.ViewHolder holder, int position) {
        byte[] decodedBytes = Base64.decode(fruitList.get(position).getFruitImage(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        Glide.with(context).load(decodedBitmap).into(holder.imageBuah);

        String namaBuah = fruitList.get(position).getFruitName();
        holder.txtNamaBuah.setText(namaBuah.replaceAll("_", " ").toLowerCase());
        holder.txtKadarAir.setText("Kadar air: ".concat(String.valueOf(fruitList.get(position).getFruitWater()))+"%");
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBuah;
        TextView txtNamaBuah, txtKadarAir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageBuah = itemView.findViewById(R.id.imgBuah);
            txtNamaBuah = itemView.findViewById(R.id.txtNamaBuah);
            txtKadarAir = itemView.findViewById(R.id.txtKadarAir);
        }
    }
}
