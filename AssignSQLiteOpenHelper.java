package minayu.crystalhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by minayu on 2018/01/06.
 */

// 各ボタンに割り当てたアプリを記録する
public class AssignSQLiteOpenHelper extends SQLiteOpenHelper {
    public AssignSQLiteOpenHelper(Context context) {
        super(context, "ASSIGN_DB" +
                "", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ASSIGN(id INTEGER, app_name TEXT)");
        db.execSQL("INSERT INTO ASSIGN VALUES(9,'com.android.chrome')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //    DBからAssignを読み込む
    public Assign getItemFromId(int argId) {
        Assign tmp = null;
        SQLiteDatabase db = getReadableDatabase();
        Log.d("id", String.valueOf(argId));
//        if( db == null ) return tmp;
        try {
            Cursor cur = db.rawQuery("select id, app_name from ASSIGN " +
                    "where id = ?", new String[]{String.valueOf(argId)});
            if (cur.moveToNext()) {
                tmp = new Assign(cur.getInt(0), cur.getString(1));
            }
            cur.close();
        } finally {
            db.close();
        }
        return tmp;
    }

    // DBにアプリを登録する
    public boolean insertAssign(Assign src) {
        long ret = -1;
        Assign tmp = getItemFromId(src.getId());
        if ( tmp == null ) {
            SQLiteDatabase db = getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put("id", src.getId());
                values.put("app_name", src.getAppName());
                ret = db.insert("ASSIGN", null, values);
            } finally {
                db.close();
            }
        } else {
            SQLiteDatabase db = getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put("app_name", tmp.getAppName());
                ret = db.update("ASSIGN", values, "id=?", new String[]{String.valueOf(tmp.getId())});
            } finally {
                db.close();
            }
        }
        return ret != -1;
    }
}
