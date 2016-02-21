package medical_report.narayan.com.medicalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by oozie on 2/21/16.
 */
public class CustomReportAdapter extends ArrayAdapter<Report> {

    public CustomReportAdapter(Context context, ArrayList<Report> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Report report = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewReport);
        // Populate the data into the template view using the data object
        tvName.setText(report.recordName + " " + report.date);
        return convertView;
    }
}
