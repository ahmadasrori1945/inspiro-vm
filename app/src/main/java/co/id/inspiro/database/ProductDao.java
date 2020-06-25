package co.id.inspiro.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.id.inspiro.model.ProductModel;

/**
 * Created by ahmadasrori.id@gmail.com on 24/06/20.
 */
@Dao
public interface ProductDao {

    // Mengambil data
    @Query("SELECT * FROM product ORDER BY name ASC")
    List<ProductModel> select();

    // Memasukkan data
    @Insert
    void insert(ProductModel productModel);

    // Mengupdate data
    @Update
    void update(ProductModel productModel);
}
