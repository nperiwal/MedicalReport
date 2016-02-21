package medical_report.narayan.com.medicalreport;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ViewReportActivity extends ListActivity {


    private static final String LOG = "ViewReportActivity";
    static ArrayList<Report> listItems = new ArrayList<Report>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        displayItems();
    }

    public void displayItems() {
        Log.v(LOG, "onAddItems");
        CustomReportAdapter adapter = new CustomReportAdapter(this, listItems);
        ListView listView = getListView();
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(this, GenerateReportActivity.class);
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Report value = (Report) adapter.getItemAtPosition(position);
                Intent i = new Intent(v.getContext(), GenerateReportActivity.class);
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(value.recordName);
                arrayList.add(value.date);
                arrayList.add(value.description);
                arrayList.add(value.url);
                i.putExtra("reportKey", arrayList);
                startActivity(i);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });
    }


}
