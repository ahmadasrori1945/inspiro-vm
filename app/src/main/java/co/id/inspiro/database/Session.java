package co.id.inspiro.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ahmadasrori.id@gmail.com on 25/06/20.
 */
public class Session {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;


    private static final String PREFER_NAME = "esatpam";

    public Session(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setStatus(boolean state){
        editor.putBoolean("status", state);
        editor.commit();
    }

    public boolean isStatus(){
        return pref.getBoolean("status", false);
    }

}