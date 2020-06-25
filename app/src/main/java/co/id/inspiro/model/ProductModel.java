package co.id.inspiro.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import co.id.inspiro.database.Constan;

/**
 * Created by ahmadasrori.id@gmail.com on 24/06/20.
 */
@Entity(tableName = Constan.nama_table)

public class ProductModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constan.id)
    private int id;

    @ColumnInfo(name = Constan.name)
    private String name;

    @ColumnInfo(name = Constan.price)
    private int price;

    @ColumnInfo(name = Constan.stock)
    private int stock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}