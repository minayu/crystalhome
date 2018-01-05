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

        findViewById(R.id.button1).setOnTouchListener(this);
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
            case R.id.button9:
                if (helper.getItemFromId(9) == null) {
                    Log.d("tmp", String.valueOf(helper.getItemFromId(9)));
                    Intent i = new Intent(this, AppsListActivity.class);
                    startActivity(i);
                } else {
                    String a = helper.getItemFromId(9).getAppname();
                    Log.v("appname :", a);
                    Intent i = manager.getLaunchIntentForPackage(a);
                    MainActivity.this.startActivity(i);
                }
                return true;
            case R.id.button1:
                Log.d("TAG", "Button1 Touched!");
                return true;
        }
         return false;
     }
 }
