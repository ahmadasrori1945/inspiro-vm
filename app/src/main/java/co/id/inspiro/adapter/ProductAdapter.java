package co.id.inspiro.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.inspiro.DetailProductActivity;
import co.id.inspiro.R;
import co.id.inspiro.database.Constan;
import co.id.inspiro.database.ProductDatabase;
import co.id.inspiro.model.ProductModel;

/**
 * Created by ahmadasrori.id@gmail.com on 24/06/20.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductModel> productModelList;
    private ProductDatabase productDatabase;
    private Bundle bundle;

    public ProductAdapter(Context context, List<ProductModel> kelasModelList) {
        this.context = context;
        this.productModelList = kelasModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false));
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Memindahkan data di dalam list dengan index position ke dalam KelasModel
        final ProductModel productModel = productModelList.get(position);

        // Menampilkan data ke layar
        holder.tvName.setText(productModel.getName());
        NumberFormat formatter = new DecimalFormat("#,###");
        String harga = formatter.format((double) productModel.getPrice());
        holder.tvPrice.setText("Rp. "+harga+" / pcs");
        if (productModel.getStock()>0) {
            holder.tvStock.setText("Tersedia : "+String.valueOf(productModel.getStock())+" pcs");
            // Onlick pada itemview
            holder.btnBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle = new Bundle();
                    bundle.putInt(Constan.KEY_ID_PRODUCT, productModel.getId());
                    bundle.putInt(Constan.price, productModel.getPrice());
                    bundle.putInt(Constan.stock, productModel.getStock());
                    bundle.putString(Constan.name, productModel.getName());
                    context.startActivity(new Intent(context, DetailProductActivity.class).putExtras(bundle));
                }
            });
        } else {
            holder.btnBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Stok kosong", Toast.LENGTH_SHORT).show();
                }
            });
            holder.tvStock.setText("Stok habis");
            holder.tvStock.setTextColor(Color.parseColor("#FF0202"));
        }
        if (productModel.getName().equals("Biskuit")) {
            holder.ivProduct.setImageResource(R.drawable.ic_biscuit);
        } else if (productModel.getName().equals("Chips")) {
            holder.ivProduct.setImageResource(R.drawable.ic_chips);
        } else if (productModel.getName().equals("Oreo")) {
            holder.ivProduct.setImageResource(R.drawable.ic_oreo);
        } else if (productModel.getName().equals("Tango")) {
            holder.ivProduct.setImageResource(R.drawable.ic_tango);
        } else {
            holder.ivProduct.setImageResource(R.drawable.ic_cokelat);
        }

        ColorGenerator generator = ColorGenerator.MATERIAL;

        // generate random color
        int color = generator.getRandomColor();
        holder.cvKelas.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.tvStock)
        TextView tvStock;
        @BindView(R.id.cvProduct)
        CardView cvKelas;
        @BindView(R.id.ivProduct)
        ImageView ivProduct;
        @BindView(R.id.btnBeli)
        Button btnBeli;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
