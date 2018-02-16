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
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
// public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private AssignSQLiteOpenHelper helper;
    private PackageManager manager;
    // アプリ登録未登録での分岐用
    // 登録済みアプリの起動をするか、アプリの一覧の画面起動をするかの分岐に使用する
    private int assignedFlg = 0;
    // ロングタップとタップが同時に起動しないようにするために使用
    // ロングタップがされた場合は1にセットされる
    private int longClickFlg = 0;

    // imageボタンのロングタップ
    private int imgLongclickFlg = 0;

    // 画面上にある各ボタンのID
    final private int buttonIdArray[] = {R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8,
            R.id.button9};
    // assignするボタンのID
    public int btnId = 1;

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

         // ボタンのタップリスナー9個
         for (int i = 0; i < buttonIdArray.length; i++) {
             Button btn = findViewById(buttonIdArray[i]);
             btn.setOnTouchListener(new View.OnTouchListener() {
                 @Override
                 public boolean onTouch(View v, MotionEvent event) {
                     getButtonId(v);
                     onTouchAction(v, event);
                     return false;
                 }
             });
         }

         // ボタンのロングタップリスナー9個
         for (int i = 0; i < buttonIdArray.length; i++) {
             Button btn = findViewById(buttonIdArray[i]);
             btn.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     getButtonId(v);
                     Assign tmp = helper.getItemFromId(btnId);
                     // 登録済みの場合のみ未登録処理を行う
                     if (tmp != null) {
                         deleteApp(btnId);
                         Log.d("longclick", "longtap");
                         Toast.makeText(MainActivity.this, "未登録にしました。", Toast.LENGTH_SHORT).show();
                     }
                     longClickFlg = 1;
                     return true;
                 }
             });
         }

         ImageButton iBtn = findViewById(R.id.imageButton);
         iBtn.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 if (imgLongclickFlg == 0) {
                     imgLongclickFlg = 1;
                     showAppInfo();
                 } else {
                     imgLongclickFlg = 0;
                     for (int i = 0; i < buttonIdArray.length; i++) {
                         Button btn = findViewById(buttonIdArray[i]);
                         btn.setText("");
                     }
                 }
                 return false;
             }
         });
     }

    // 押されたボタンのIdを取得
    public void getButtonId(View v) {
        int tapButtonId = v.getId();
        // buttonIdNameは「button1,button2」など、buttonIdは「1,2」などの持ち方 アサインに使用するのは数字(1,2)のみ
        String buttonIdName = v.getContext().getResources().getResourceEntryName(tapButtonId);
        Log.d("view", buttonIdName);
        // button2 → 2
        String buttonId = buttonIdName.substring(6);
        Log.d("buttonid", buttonId);
        btnId = Integer.parseInt(buttonId);
    }

    public void onTouchAction (View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            showAppInfo();
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            // ロングタップをしている場合はアサインの必要はない
            if (longClickFlg == 0) {
                doAssign(btnId);
            } else {
                // 初期化
                longClickFlg = 0;
            }
        }
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
        for (int i = 0; i < buttonIdArray.length; i++) {
            Button btn = findViewById(buttonIdArray[i]);
            // 配列の0個目を参照している時idは1になる
            int id = i + 1;
            Assign tmp = helper.getItemFromId(id);
            String setStr;
            try {
                if (tmp.getAppName() == null) {
                    setStr = "未登録";
                } else {
                    setStr =tmp.getAppName();
                }
            } catch (NullPointerException e) {
                setStr = "未登録";
            }
            btn.setText(setStr);
        }
    }

    // 登録アプリ削除
    public void deleteApp(int id) {
        // アプリが登録されている場合削除する
        helper.deleteAssign(id);
    }


    // ボタンにアプリの登録
    private void doAssign(int i) {
        // 新規登録
        if (helper.getItemFromId(i) == null) {
            assignedFlg = 0;
            Intent intentList = new Intent(this, AppsListActivity.class);
            intentList.putExtra("Flg", assignedFlg);
            intentList.putExtra("Id", i);
            //intentList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
