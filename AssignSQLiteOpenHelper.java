package minayu.crystalhome;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by minayu on 2018/01/06.
 */

// 各ボタンに割り当てたアプリを記録する
public class AssignSQLiteOpenHelper extends SQLiteOpenHelper {
    public AssignSQLiteOpenHelper(Context context) {
        super(context, "ASSIGN_DB2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ASSIGN(id INTEGER, appname TEXT)");
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
            Cursor cur = db.rawQuery("select id, appname from ASSIGN " +
                    "where id = ?", new String[]{String.valueOf(argId)});
            if (cur.moveToNext()) {
                tmp = new Assign(cur.getInt(0), cur.getString(1));
            }
            cur.close();
//        } catch (SQLException ex) {
//            tmp = null;
//            db.close();
        } finally {
            db.close();
        }
        return tmp;
    }
}
