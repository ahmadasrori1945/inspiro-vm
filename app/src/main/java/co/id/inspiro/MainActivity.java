package co.id.inspiro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.inspiro.adapter.ProductAdapter;
import co.id.inspiro.database.ProductDatabase;
import co.id.inspiro.database.Session;
import co.id.inspiro.model.ProductModel;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvProduct)
    RecyclerView rvKelas;
    @BindView(R.id.tvDate)
    TextView tvDate;

    private ProductDatabase productDatabase;
    private List<ProductModel> productModelList;
    int status=0;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        session = new Session(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Membuat object database
        productDatabase = ProductDatabase.createDatabase(this);
        // Membuat membuat object List
        productModelList = new ArrayList<>();
        if (!session.isStatus()) {
            addData();
        }
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
//        String format = s.format(new Date());
//        tvDate.setText(format);

        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();
                final String finalTime = String.format(Locale.US, "%d-%d-%d %d:%d:%d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDate.setText(finalTime);
                    }
                });
            }

        }, 1000, 1000); //Initial Delay and Period for update (in milliseconds)
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Mengosongkan List agar dipastikan list dapat disi dengan data yg paling baru
        productModelList.clear();

        // Mengambil data dari Sqlite
        getData();

        // Mensetting dan proses menampilkan data ke RecyclerView
        rvKelas.setLayoutManager(new GridLayoutManager(this, 2));
        rvKelas.setAdapter(new ProductAdapter(this, productModelList));

    }

    private void getData() {

        // Operasi mengambil data dari database Sqlite
        productModelList = productDatabase.productDao().select();
    }

    private void addData() {
        // Membuat object KelasModel untuk menampung data
        ProductModel productModel1 = new ProductModel();

        // Memasukkan data ke dalam KelasModel
        productModel1.setName("Biskuit");
        productModel1.setPrice(6000);
        productModel1.setStock(7);

        ProductModel productModel2 = new ProductModel();
        productModel2.setName("Chips");
        productModel2.setPrice(8000);
        productModel2.setStock(6);

        ProductModel productModel3 = new ProductModel();
        productModel3.setName("Oreo");
        productModel3.setPrice(10000);
        productModel3.setStock(5);

        ProductModel productModel4 = new ProductModel();
        productModel4.setName("Tango");
        productModel4.setPrice(12000);
        productModel4.setStock(4);

        ProductModel productModel5 = new ProductModel();
        productModel5.setName("Cokelat");
        productModel5.setPrice(15000);
        productModel5.setStock(3);

        // Perintah untuk melakukan operasi Insert menggunakan siswaDatabase
        productDatabase.productDao().insert(productModel1);
        productDatabase.productDao().insert(productModel2);
        productDatabase.productDao().insert(productModel3);
        productDatabase.productDao().insert(productModel4);
        productDatabase.productDao().insert(productModel5);
        session.setStatus(true);
    }

}
