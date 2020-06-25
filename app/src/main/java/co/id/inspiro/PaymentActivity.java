package co.id.inspiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.inspiro.adapter.MoneyAdapter;
import co.id.inspiro.database.Constan;
import co.id.inspiro.database.ProductDatabase;
import co.id.inspiro.model.MoneyModel;
import co.id.inspiro.model.ProductModel;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.cvMoney)
    CardView cvMoney;
    @BindView(R.id.ivMoney)
    ImageView ivMoney;
    @BindView(R.id.btnBayar)
    Button btnBayar;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    private List<MoneyModel> moneyModelList;
    Bundle bundle;
    int id, qty, total, kembalian, gambar, sisa, price;
    String nominal=null, name;
    ProductDatabase productDatabase;
    NumberFormat formatter = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        productDatabase = ProductDatabase.createDatabase(this);

        if (bundle != null) {
            id = bundle.getInt(Constan.KEY_ID_PRODUCT);
            qty = bundle.getInt("qty");
            total = bundle.getInt("total");
            sisa = bundle.getInt("sisa");
            price = bundle.getInt("price");
            name = bundle.getString("name");
        }
        String totalharga = formatter.format((double) total);
        tvTotal.setText("Rp. "+totalharga);
        cvMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyModelList = new ArrayList<MoneyModel>();
                MoneyModel moneyModel1 = new MoneyModel();
                moneyModel1.setNominal("2000");
                moneyModel1.setGambar(R.drawable.duarebu);
                moneyModelList.add(moneyModel1);
                MoneyModel moneyModel2 = new MoneyModel();
                moneyModel2.setNominal("5000");
                moneyModel2.setGambar(R.drawable.limarebu);
                moneyModelList.add(moneyModel2);
                MoneyModel moneyModel3 = new MoneyModel();
                moneyModel3.setNominal("10000");
                moneyModel3.setGambar(R.drawable.sepuluhrebu);
                moneyModelList.add(moneyModel3);
                MoneyModel moneyModel4 = new MoneyModel();
                moneyModel4.setNominal("20000");
                moneyModel4.setGambar(R.drawable.duapuluhrebu);
                moneyModelList.add(moneyModel4);
                MoneyModel moneyModel5 = new MoneyModel();
                moneyModel5.setNominal("50000");
                moneyModel5.setGambar(R.drawable.limapuluhrebu);
                moneyModelList.add(moneyModel5);
                popupMoney(PaymentActivity.this);
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nominal == null) {
                    Toast.makeText(PaymentActivity.this, "masukkan uang kamu dulu", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(nominal)>=total) {
                        saveData();
                        kembalian = Integer.parseInt(nominal) - total;
                        popupSuccess(PaymentActivity.this, kembalian);
                    } else {
                        Toast.makeText(PaymentActivity.this, "uang kamu kurang", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("get-money"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            nominal = intent.getStringExtra("nominal");
            gambar = intent.getIntExtra("gambar", 0);
            ivMoney.setImageResource(gambar);
        }
    };

    private void saveData() {

        // Membuat object kelasmodel
        ProductModel productModel = new ProductModel();

        // Memasukkan data ke kelasmodel
        productModel.setId(id);
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setStock(sisa);

        // Melakukan operasi update untuk mengupdate data ke sqlite
        productDatabase.productDao().update(productModel);
    }

    public void popupMoney(final Context mContext) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.list_money);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.setTitle("Input Code Here");
        RecyclerView rvMoney = dialog.findViewById(R.id.rvMoney);

        rvMoney.setLayoutManager(new GridLayoutManager(mContext, 1));
        rvMoney.setAdapter(new MoneyAdapter(mContext, moneyModelList, dialog));
        dialog.show();
        dialog.setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    public void popupSuccess(final Context mContext, int kembalian) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.layout_success);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button btnYes = dialog.findViewById(R.id.btnYes);
        TextView tvKembalian = dialog.findViewById(R.id.tvKembalian);
        TextView tvNilaiKembalian = dialog.findViewById(R.id.tvNilaiKembalian);
        if (kembalian>0) {
            NumberFormat formatter = new DecimalFormat("#,###");
            String formatKembali = formatter.format((double) kembalian);
            tvKembalian.setText("Silahkan ambil kembalian kamu sebesar");
            tvNilaiKembalian.setText("Rp. "+formatKembali);
        } else {
            tvKembalian.setVisibility(View.GONE);
            tvNilaiKembalian.setVisibility(View.GONE);
        }
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }

}
