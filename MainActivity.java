 package minayu.crystalhome;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

 public class MainActivity extends AppCompatActivity {
// public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private AssignSQLiteOpenHelper helper;
    private PackageManager manager;
    // アプリ登録未登録での分岐用
    private int assignedFlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
//        findViewById(android.R.id.content).setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_FULLSCREEN |
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        // デフォルトのホームと同じ壁紙を設定
        WallpaperManager wpm = WallpaperManager.getInstance(getApplication());
        Drawable wallpaper = wpm.getDrawable();
        ConstraintLayout layout = findViewById(R.id.main_layout);
        layout.setBackground(wallpaper);

        manager = getPackageManager();

        helper = new AssignSQLiteOpenHelper(MainActivity.this);

        Button btn1 = findViewById(R.id.button1);
        // 登録アプリのアプリ名、アイコン表示、起動
        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    showAppInfo();
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    doAssign(1);
                    Log.v("OnTouch", "Touch Up");
                }
                return false;
            }
        });

        btn1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 削除
                deleteApp();
                return false;
            }
        });

        //for文化する
        //ボタン長押しでアサインを削除する機能をついか（削除の際アラートを出す）
//        findViewById(R.id.button1).setOnTouchListener(this);
//        findViewById(R.id.button2).setOnTouchListener(this);
//        findViewById(R.id.button3).setOnTouchListener(this);
//        findViewById(R.id.button4).setOnTouchListener(this);
//        findViewById(R.id.button5).setOnTouchListener(this);
//        findViewById(R.id.button6).setOnTouchListener(this);
//        findViewById(R.id.button7).setOnTouchListener(this);
//        findViewById(R.id.button8).setOnTouchListener(this);
//        findViewById(R.id.button9).setOnTouchListener(this);
    }

    // アプリ一覧の表示
    public void showApps(View v) {
        Intent i = new Intent(this, AppsListActivity.class);
        assignedFlg = 1;
        i.putExtra("Flg", assignedFlg);
        startActivity(i);
    }

     // アプリ名、アイコン表示
    public void showAppInfo() {
        //アプリ名アイコン取得、表示処理をかく
    }

    // 登録アプリ削除
     public void deleteApp() {
        // アプリが登録されている場合咲くじぉする

     }

//     @Override
//     public boolean onTouch(View v, MotionEvent event) {
//        int id = v.getId();
//        switch(id) {
//            //書き換え考える
//            case R.id.button1:
//                doAssign(1);
//                return true;
//            case R.id.button2:
//                doAssign(2);
//                return true;
//            case R.id.button3:
//                doAssign(3);
//                return true;
//            case R.id.button4:
//                doAssign(4);
//                return true;
//            case R.id.button5:
//                doAssign(5);
//                return true;
//            case R.id.button6:
//                doAssign(6);
//                return true;
//            case R.id.button7:
//                doAssign(7);
//                return true;
//            case R.id.button8:
//                doAssign(8);
//                return true;
//            case R.id.button9:
//                doAssign(9);
//                return true;
//        }
//         return false;
//     }

     // ボタンにアプリの登録
     private void doAssign(int i) {
         // 新規登録
         if (helper.getItemFromId(i) == null) {
             assignedFlg = 0;
             Intent intentList = new Intent(this, AppsListActivity.class);
             intentList.putExtra("Flg", assignedFlg);
             intentList.putExtra("Id", i);
  //           intentList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intentList);
             //overridePendingTransition(0, 0);
         } else { //登録したアプリに遷移する
             String a = helper.getItemFromId(i).getAppName();
             assignedFlg = 1;
             Intent intentApp = manager.getLaunchIntentForPackage(a);
             intentApp.putExtra("Flg", assignedFlg);
             // assignのIDもputExtraする
             // 全アプリ一覧ボタンに不具合、修正する
             MainActivity.this.startActivity(intentApp);
         }
     }
 }
