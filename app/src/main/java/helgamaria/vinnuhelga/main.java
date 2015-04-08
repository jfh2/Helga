package helgamaria.vinnuhelga;

import helgamaria.vinnuhelga.sql.JobObject;
import helgamaria.vinnuhelga.sql.dbFunctions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.PowerManager;
import android.view.View.OnClickListener;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class main extends ActionBarActivity {
    PowerManager pm;
    PowerManager.WakeLock wl;
    dbFunctions dbFunc = new dbFunctions(this);
    private ListView lv1,lv2,lv3,lv4,lv5,lv6;
    //lists with jobobjects
    private List<JobObject> joblist1 = new ArrayList<JobObject>();
    private List<JobObject> joblist2 = new ArrayList<JobObject>();
    private List<JobObject> joblist3 = new ArrayList<JobObject>();
    private List<JobObject> joblist4 = new ArrayList<JobObject>();
    private List<JobObject> joblist5 = new ArrayList<JobObject>();
    private List<JobObject> joblist6 = new ArrayList<JobObject>();
    //creating the list for each listview
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();
    List<String> list4 = new ArrayList<String>();
    List<String> list5 = new ArrayList<String>();
    List<String> list6 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "myTag");
        wl.acquire();
        //finding the listviews
        lv1 = (ListView)findViewById(R.id.fyrsta);
        lv2 = (ListView)findViewById(R.id.annad);
        lv3 = (ListView)findViewById(R.id.tridja);
        lv4 = (ListView)findViewById(R.id.fjorda);
        lv5 = (ListView)findViewById(R.id.fimmta);
        lv6 = (ListView)findViewById(R.id.sjotta);
        //changin backround colors to each one of them
        lv1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lv2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lv3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lv4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lv5.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lv6.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //creating the adapters
        ArrayAdapter<String> ad1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        ArrayAdapter<String> ad2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list2);
        ArrayAdapter<String> ad3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list3);
        ArrayAdapter<String> ad4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list4);
        ArrayAdapter<String> ad5 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list5);
        ArrayAdapter<String> ad6 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list6);
        //connecting adapters to the view
        lv1.setAdapter(ad1);
        lv2.setAdapter(ad2);
        lv3.setAdapter(ad3);
        lv4.setAdapter(ad4);
        lv5.setAdapter(ad5);
        lv6.setAdapter(ad6);
        prepareJobLists();
        setListeners();
    }
    private void prepareJobLists(){
        List<List<String>> initlist = dbFunc.selectAllConstants();
        List<String> templist;

        if(initlist.size() == 0) return;
        System.out.println(initlist.size());
        for(int i = 0; i < initlist.size(); i++){
            templist = initlist.get(i);
            String decide = templist.get(1);
            String value = templist.get(0);
            if(decide.equals("1")){
                list1.add(value);
            }else if(decide.equals("2")){
                list2.add(value);
            }else if(decide.equals("3")){
                list3.add(value);
            }else if(decide.equals("4")){
                list4.add(value);
            }else if(decide.equals("5")){
                list5.add(value);
            }else if(decide.equals("6")){
                list6.add(value);
            }
        }

    }
    private void setListeners(){
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobObject obj = null;
                if(joblist1.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist1.add(obj);
                }else{
                    for(int i = 0; i < joblist1.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist1.get(i).getId() == position){
                            obj = joblist1.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist1.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv1.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("1");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JobObject obj = null;
                if(joblist2.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist2.add(obj);
                }else{
                    for(int i = 0; i < joblist2.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist2.get(i).getId() == position){
                            obj = joblist2.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist2.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv2.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("2");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobObject obj = null;
                if(joblist3.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist3.add(obj);
                }else{
                    for(int i = 0; i < joblist3.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist3.get(i).getId() == position){
                            obj = joblist3.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist3.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv3.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("3");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobObject obj = null;
                if(joblist4.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist4.add(obj);
                }else{
                    for(int i = 0; i < joblist4.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist4.get(i).getId() == position){
                            obj = joblist4.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist4.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv4.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("4");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobObject obj = null;
                if(joblist5.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist5.add(obj);
                }else{
                    for(int i = 0; i < joblist5.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist5.get(i).getId() == position){
                            obj = joblist5.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist5.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv5.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("5");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });
        //for first listview
        /*
        TODO: setja í loopu fyrir alla ef hægt
         */
        lv6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobObject obj = null;
                if(joblist6.size() == 0){
                    obj = new JobObject();
                    obj.setId(position);
                    joblist6.add(obj);
                }else{
                    for(int i = 0; i < joblist6.size(); i++){
                        //athuga hvort það sé object með id = position
                        if(joblist6.get(i).getId() == position){
                            obj = joblist6.get(i);
                            break;
                        }
                    }
                    if(obj == null){
                        obj = new JobObject();
                        obj.setId(position);
                        joblist6.add(obj);
                    }
                }
                if(!obj.isItOn()){
                    String name = lv6.getItemAtPosition(position).toString();
                    //hér ef það er off, setjum það on
                    obj.setDate(null);
                    obj.setStartTime(null);
                    obj.setJobType("6");
                    obj.setJobName(name);
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    obj.on();
                }else{
                    //hér ef það er on setjum það off
                    obj.setStopTime(null);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    obj.saveToDb(getApplicationContext());
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            /*
            TODO: add alert to say that all existing jobs will be discarded
             */
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("All running jobs will be lost")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(wl.isHeld()){
                                wl.release();
                            }
                            Intent intent = new Intent(getApplicationContext(), settings.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        if(id == R.id.action_overview){
            /*
            TODO: add alert to say that all existing jobs will be discarded
             */
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("All running jobs will be lost")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(wl.isHeld()){
                                wl.release();
                            }
                            Intent intent = new Intent(getApplicationContext(), overview.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
