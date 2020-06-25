package co.id.inspiro.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.inspiro.DetailProductActivity;
import co.id.inspiro.R;
import co.id.inspiro.database.Constan;
import co.id.inspiro.database.ProductDatabase;
import co.id.inspiro.model.MoneyModel;
import co.id.inspiro.model.ProductModel;

/**
 * Created by ahmadasrori.id@gmail.com on 24/06/20.
 */
public class MoneyAdapter extends RecyclerView.Adapter<MoneyAdapter.ViewHolder> {

    private final Context context;
    private final List<MoneyModel> moneyModelList;
    private ProductDatabase productDatabase;
    private Bundle bundle;
    private Dialog dialog;


    public MoneyAdapter(Context context, List<MoneyModel> moneyModelList, Dialog dialog) {
        this.context = context;
        this.moneyModelList = moneyModelList;
        this.dialog = dialog;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_money, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Memindahkan data di dalam list dengan index position ke dalam KelasModel
        final MoneyModel moneyModel = moneyModelList.get(position);

        // Menampilkan data ke layar
        holder.ivMoney.setImageResource(moneyModel.getGambar());
        holder.cvMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent("get-money");
                intent.putExtra("nominal",moneyModel.getNominal());
                intent.putExtra("gambar",moneyModel.getGambar());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moneyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivMoney)
        ImageView ivMoney;
        @BindView(R.id.cvMoney)
        CardView cvMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

