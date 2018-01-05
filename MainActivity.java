 package minayu.crystalhome;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // デフォルトのホームと同じ壁紙を設定
        WallpaperManager wpm = WallpaperManager.getInstance(getApplication());
        Drawable wallpaper = wpm.getDrawable();
        ConstraintLayout layout = findViewById(R.id.main_layout);
        layout.setBackground(wallpaper);
    }

    // アプリ一覧の表示
    public void showApps(View v) {
         Intent i = new Intent(this, AppsListActivity.class);
         startActivity(i);
    }
}
