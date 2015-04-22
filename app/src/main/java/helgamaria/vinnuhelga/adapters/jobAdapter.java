package helgamaria.vinnuhelga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import helgamaria.vinnuhelga.R;
import helgamaria.vinnuhelga.sql.JobObject;

/**
 * Created by frimann on 29.3.2015.
 */
public class jobAdapter extends ArrayAdapter<JobObject> {

    private Context context;
    private List<JobObject> job;


    public jobAdapter(Context context, List<JobObject> job){
        super(context, R.layout.listview_item_row, job);
        this.context = context;
        this.job = job;
    }
    @Override
    public View getView(int position, View rowView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(rowView == null) {
            rowView = inflater.inflate(R.layout.listview_item_row, parent, false);
        }
            //initialize the fields
            TextView date = (TextView)rowView.findViewById(R.id.dateField);
            TextView role = (TextView)rowView.findViewById(R.id.roleField);
            TextView name = (TextView)rowView.findViewById(R.id.nameField);
            TextView type = (TextView)rowView.findViewById(R.id.typeField);
            TextView start = (TextView)rowView.findViewById(R.id.startField);
            TextView stop = (TextView)rowView.findViewById(R.id.stopField);
            //insert variables
            date.setText(job.get(position).getDate());
            name.setText(job.get(position).getJobName());
            type.setText(job.get(position).getJobType());
            stop.setText(job.get(position).getStopTime());
            start.setText(job.get(position).getStartTime());
            role.setText(job.get(position).getRole_name());


        return rowView;
    }
}
