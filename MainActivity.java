 package minayu.crystalhome;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import java.util.List;

 public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private AssignSQLiteOpenHelper helper;
    private PackageManager manager;
    // アプリ登録未登録での分岐用
    private int AssignedFlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // デフォルトのホームと同じ壁紙を設定
        WallpaperManager wpm = WallpaperManager.getInstance(getApplication());
        Drawable wallpaper = wpm.getDrawable();
        ConstraintLayout layout = findViewById(R.id.main_layout);
        layout.setBackground(wallpaper);


        manager = getPackageManager();

        helper = new AssignSQLiteOpenHelper(MainActivity.this);

        //for文化する
        findViewById(R.id.button1).setOnTouchListener(this);
        findViewById(R.id.button2).setOnTouchListener(this);
        findViewById(R.id.button3).setOnTouchListener(this);
        findViewById(R.id.button4).setOnTouchListener(this);
        findViewById(R.id.button5).setOnTouchListener(this);
        findViewById(R.id.button6).setOnTouchListener(this);
        findViewById(R.id.button7).setOnTouchListener(this);
        findViewById(R.id.button8).setOnTouchListener(this);
        findViewById(R.id.button9).setOnTouchListener(this);
    }

    // アプリ一覧の表示
    public void showApps(View v) {
         Intent i = new Intent(this, AppsListActivity.class);
         startActivity(i);
    }

     @Override
     public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        switch(id) {
            //書き換え考える
            case R.id.button1:
                doAssign(1);
                return true;
            case R.id.button2:
                doAssign(2);
                return true;
            case R.id.button3:
                doAssign(3);
                return true;
            case R.id.button4:
                doAssign(4);
                return true;
            case R.id.button5:
                doAssign(5);
                return true;
            case R.id.button6:
                doAssign(6);
                return true;
            case R.id.button7:
                doAssign(7);
                return true;
            case R.id.button8:
                doAssign(8);
                return true;
            case R.id.button9:
                doAssign(9);
                return true;
        }
         return false;
     }

     // ボタンにアプリの登録
     private void doAssign(int i) {
         // 新規登録
         if (helper.getItemFromId(i) == null) {
             AssignedFlg = 0;
             Intent intentList = new Intent(this, AppsListActivity.class);
             startActivity(intentList);
         } else { //登録したアプリに遷移する
             String a = helper.getItemFromId(i).getAppName();
             AssignedFlg = 1;
             Intent intentApp = manager.getLaunchIntentForPackage(a);
             intentApp.putExtra("Flg", AssignedFlg);
             MainActivity.this.startActivity(intentApp);
         }
     }
 }
