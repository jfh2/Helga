package helgamaria.vinnuhelga;

import helgamaria.vinnuhelga.adapters.jobAdapter;
import helgamaria.vinnuhelga.sql.JobObject;
import helgamaria.vinnuhelga.sql.dbFunctions;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class overview extends ActionBarActivity {
    private dbFunctions dbFunc = new dbFunctions(this);
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //fetching the data from database to my string list
        dbFunc.open();
        List<JobObject> listOfJobsDb = dbFunc.selectAllJobs();
        dbFunc.close();
        System.out.println("listofjobs i overview");
        for(int i = 0; i < listOfJobsDb.size(); i++){
            System.out.println(listOfJobsDb.get(i).getJobType());
        }
        System.out.println("listofjobs i overview lokið");
        lv = (ListView)findViewById(R.id.listView);
        jobAdapter adapt = new jobAdapter(this, listOfJobsDb);
        lv.setAdapter(adapt);

        Button butt = (Button)findViewById(R.id.excel);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    makeCSVFile.save(getApplicationContext());
                    Toast.makeText(getApplicationContext(), ".txt file saved to device.", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Saving failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), settings.class);
            startActivity(intent);
        }
        if(id == R.id.action_home){
            Intent intent = new Intent(getApplicationContext(), main.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
