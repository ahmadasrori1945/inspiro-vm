package co.id.inspiro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.id.inspiro.model.ProductModel;

/**
 * Created by ahmadasrori.id@gmail.com on 24/06/20.
 */
@Database(entities = {ProductModel.class}, version = 2)
public abstract class ProductDatabase extends RoomDatabase {

    public abstract ProductDao productDao();

    private static ProductDatabase INSTANCE;

    // Membuat method return untuk membuat database
    public static ProductDatabase createDatabase(Context context){
        synchronized (ProductDatabase.class){
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, ProductDatabase.class, "db_product")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
