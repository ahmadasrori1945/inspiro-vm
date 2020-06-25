package co.id.inspiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.inspiro.database.Constan;

public class DetailProductActivity extends AppCompatActivity {
    int id;
    int price;
    int stock;
    int qty=0;
    int total=0;
    int stockSisa;
    String name;
    Bundle bundle;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvStock)
    TextView tvStock;
    @BindView(R.id.tvQty)
    TextView tvQty;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.ivProduct)
    ImageView ivProduct;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;
    @BindView(R.id.btnMin)
    LinearLayout btnMin;
    @BindView(R.id.btnAdd)
    LinearLayout btnAdd;
    NumberFormat formatter = new DecimalFormat("#,###");


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getInt(Constan.KEY_ID_PRODUCT);
            price = bundle.getInt(Constan.price);
            stock = bundle.getInt(Constan.stock);
            name = bundle.getString(Constan.name);
        }
        tvName.setText(name);
        String harga = formatter.format((double) price);
        tvPrice.setText("Rp. "+harga);
        tvStock.setText("Tersedia : "+String.valueOf(stock)+" pcs");
        if (name.equals("Biskuit")) {
            ivProduct.setImageResource(R.drawable.ic_biscuit);
        } else if (name.equals("Chips")) {
            ivProduct.setImageResource(R.drawable.ic_chips);
        } else if (name.equals("Oreo")) {
            ivProduct.setImageResource(R.drawable.ic_oreo);
        } else if (name.equals("Tango")) {
            ivProduct.setImageResource(R.drawable.ic_tango);
        } else {
            ivProduct.setImageResource(R.drawable.ic_cokelat);
        }

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty>0) {
                    stockSisa = stock - qty;
                    bundle = new Bundle();
                    bundle.putInt(Constan.KEY_ID_PRODUCT, id);
                    bundle.putInt("qty", qty);
                    bundle.putString("name", name);
                    bundle.putInt("price", price);
                    bundle.putInt("total", total);
                    bundle.putInt("sisa", stockSisa);
                    startActivity(new Intent(DetailProductActivity.this, PaymentActivity.class).putExtras(bundle));
                } else {
                    Toast.makeText(DetailProductActivity.this, "Masukkan jumlah barang", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty<stock) {
                    qty++;
                    tvQty.setText(String.valueOf(qty));
                    total = qty * price;
                    String totalharga = formatter.format((double) total);
                    tvTotal.setText("Rp. "+totalharga);
                }
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty>0) {
                    qty--;
                    tvQty.setText(String.valueOf(qty));
                    total = qty * price;
                    String totalharga = formatter.format((double) total);
                    tvTotal.setText("Rp. "+totalharga);
                }
            }
        });
    }
}
