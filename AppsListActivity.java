package minayu.crystalhome;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppsListActivity extends AppCompatActivity {

    private PackageManager manager;
    private List<ListItem> apps;
    private ListView list;
    private int assignedFlg;
    private int assignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        Intent i = getIntent();
        assignedFlg = i.getIntExtra("Flg", 0);
        assignId = i.getIntExtra("Id", 0);
        loadApps();
        loadListView();
        addClickListener();
    }

    // アプリ一覧表示用
    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<ListItem>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);

        for(ResolveInfo ri : availableActivities) {
            ListItem app = new ListItem();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }

    private void loadListView() {
        list = findViewById(R.id.apps_list);

        ArrayAdapter<ListItem> adapter = new ArrayAdapter<ListItem>(this, R.layout.list_item, apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
//                return super.getView(position, convertView, parent);
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                }

                ImageView appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLabel = (TextView) convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).label);

                TextView appName = convertView.findViewById(R.id.item_app_name);
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };
        list.setAdapter(adapter);
    }

    private void addClickListener() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (assignedFlg == 1) {
                    Intent i = manager.getLaunchIntentForPackage(apps.get(position).name.toString());
                    AppsListActivity.this.startActivity(i);
                } else {
                    AssignSQLiteOpenHelper helper = new AssignSQLiteOpenHelper(AppsListActivity.this);
                    Assign src = new Assign(assignId, apps.get(position).name.toString());
                    helper.insertAssign(src);
                    Intent i = new Intent(AppsListActivity.this, MainActivity.class);
                    startActivity(i);
                    //overridePendingTransition(0, 0);
                }
            }
        });
    }
}
