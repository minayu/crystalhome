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
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {
// public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private AssignSQLiteOpenHelper helper;
    private PackageManager manager;
    // アプリ登録未登録での分岐用
    private int assignedFlg;
    private int longClickFlg = 0;
    final private int btnNo1 = 1;
    final private int btnNo2 = 2;
    final private int btnNo3 = 3;
    final private int btnNo4 = 4;
    final private int btnNo5 = 5;
    final private int btnNo6 = 5;
    final private int btnNo7 = 7;
    final private int btnNo8 = 8;
    final private int btnNo9 = 9;
//    final private int buttonNumArray[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
//    final private String buttonNameArray[] = {"btn1", "btn2", "btn3", "btn4", "btn5",
//            "btn6", "btn7", "btn8", "btn9"};
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

         // for文にする
         Button btn1 = findViewById(R.id.button1);
         Button btn2 = findViewById(R.id.button2);
         Button btn3 = findViewById(R.id.button3);
         Button btn4 = findViewById(R.id.button4);
         Button btn5 = findViewById(R.id.button5);
         Button btn6 = findViewById(R.id.button6);
         Button btn7 = findViewById(R.id.button7);
         Button btn8 = findViewById(R.id.button8);
         Button btn9 = findViewById(R.id.button9);

         //
         for (int i = 0; i < buttonIdArray.length; i++) {
             Button btn = findViewById(buttonIdArray[i]);
             btn.setOnTouchListener(new View.OnTouchListener() {
                 @Override
                 public boolean onTouch(View v, MotionEvent event) {
                     // タップされたボタンのIdを取得してアサインに使用
                     int tapButtonId = v.getId();
                     // buttonIdNameは「button1,button2」など、buttonIdは「1,2」などの持ち方 アサインに使用するのは数字(1,2)のみ
                     String buttonIdName = v.getContext().getResources().getResourceEntryName(tapButtonId);
                     Log.d("view", buttonIdName);
                     // button2 → 2
                     String buttonId = buttonIdName.substring(6);
                     Log.d("buttonid", buttonId);
                     btnId = Integer.parseInt(buttonId);
                     onTouchAction(v, event);
                     return false;
                 }
             });
         }


//        btn2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
////                if(event.getAction() == MotionEvent.ACTION_DOWN) {
////                    showAppInfo();
////                } else if(event.getAction() == MotionEvent.ACTION_UP) {
////                    if (longClickFlg == 0) {
////                        doAssign(btnNo2);
////                    } else {
////                        longClickFlg = 0;
////                    }
////                }
//                onTouchAction(v, event);
//                return false;
//            }
//        });

//         btn3.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo3);
//                 }
//                 return false;
//             }
//         });

//         btn4.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo4);
//                 }
//                 return false;
//             }
//         });
//
//         btn5.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo5);
//                 }
//                 return false;
//             }
//         });
//
//         btn5.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo5);
//                 }
//                 return false;
//             }
//         });
//
//         btn6.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo6);
//                 }
//                 return false;
//             }
//         });
//
//         btn7.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo7);
//                 }
//                 return false;
//             }
//         });
//
//         btn8.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo8);
//                 }
//                 return false;
//             }
//         });
//
//         btn9.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View v, MotionEvent event) {
//                 // TODO Auto-generated method stub
//                 if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                     showAppInfo();
//                 } else if(event.getAction() == MotionEvent.ACTION_UP) {
//                     doAssign(btnNo9);
//                 }
//                 return false;
//             }
//         });

         for (int i = 0; i < buttonIdArray.length; i++) {
             Button btn = findViewById(buttonIdArray[i]);
             btn.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     // 削除
                     int tapButtonId = v.getId();
                     // buttonIdNameは「button1,button2」など、buttonIdは「1,2」などの持ち方 アサインに使用するのは数字(1,2)のみ
                     String buttonIdName = v.getContext().getResources().getResourceEntryName(tapButtonId);
                     Log.d("view", buttonIdName);
                     // button2 → 2
                     String buttonId = buttonIdName.substring(6);
                     Log.d("buttonid", buttonId);
                     btnId = Integer.parseInt(buttonId);
                     deleteApp(btnId);
                     Log.d("longclick", "OK");
                     Toast.makeText(MainActivity.this, "LongTap", Toast.LENGTH_SHORT).show();
                     longClickFlg = 1;
                     return true;
                 }
             });
         }

         // TODO
         // こちらもOnTouchと同じように簡潔なコードで9このボタンにリスナーを登録する
         btn1.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 // 削除
                 deleteApp(btnNo1);
                 Log.d("longclick", "OK");
                 Toast.makeText(MainActivity.this, "LongTap", Toast.LENGTH_SHORT).show();
                 longClickFlg = 1;
                 return true;
             }
         });

         btn2.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 // 削除
                 deleteApp(btnNo2);
                 Log.d("longclick", "OK");
                 Toast.makeText(MainActivity.this, "LongTap", Toast.LENGTH_SHORT).show();
                 longClickFlg = 1;
                 return true;
             }
         });
     }

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

    public void onTouchAction (View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            showAppInfo();
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
    }

    // 登録アプリ削除
     public void deleteApp(int id) {
        // アプリが登録されている場合咲くじぉする
         helper.deleteAssign(id);
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
