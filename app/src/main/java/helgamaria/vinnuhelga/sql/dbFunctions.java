package helgamaria.vinnuhelga.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import helgamaria.vinnuhelga.sql.MySQLiteHelper;
/**
 * Created by frimann on 23.2.2015.
 */
public class dbFunctions {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColums = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_DATE,
            MySQLiteHelper.COLUMN_JOB_NAME,
            MySQLiteHelper.COLUMN_START,
            MySQLiteHelper.COLUMN_STOP,
            MySQLiteHelper.COLUMN_JOB_TYPE};

    public dbFunctions(Context context){
        dbHelper = new MySQLiteHelper(context);
    }
    //methods to open and close connection
    public void open() throws android.database.SQLException{
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    //function to insert new job to db
    public void newJobRow(String date,
                          String job_name,
                          String start_time,
                          String stop_time,
                          String job_type,
                          String role_name){

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        values.put(MySQLiteHelper.COLUMN_JOB_NAME, job_name);
        values.put(MySQLiteHelper.COLUMN_START, start_time);
        values.put(MySQLiteHelper.COLUMN_STOP, stop_time);
        values.put(MySQLiteHelper.COLUMN_JOB_TYPE, job_type);
        values.put(MySQLiteHelper.COLUMN_ROLE_NAME_JOB, role_name);
        database.insert(MySQLiteHelper.TABLE_JOB, null, values);
        //database.close();
    }
    //count of all instances of jobs in database
    public int getCountOfJobs(){

        String countQuery = "select * from jobs;";
        Cursor cur = database.rawQuery(countQuery, null);
        //cur.close();
        int count = cur.getCount();
        cur.close();
        return count;
    }
    //select all jobs from database and
    //fetch them into as jobobject into list
    //so it can be shown later on screen
    public List<JobObject> selectAllJobs(){
        List<JobObject> allJobs = new ArrayList<JobObject>();
        Cursor cur;
        //select all columns from database
        cur = database.rawQuery("select * from jobs", null);
        cur.moveToFirst();
        //fetch cursor objects into jobobjects
        while(!cur.isAfterLast()){
            JobObject job = cursorToJob(cur);
            allJobs.add(job);
            cur.moveToNext();
        }
        cur.close();

        return allJobs;

    }
    //create a jobobject from cursor object
    private JobObject cursorToJob(Cursor cursor){
        //creating new jobobject
        JobObject job = new JobObject();
        //fetching variables from cursor into it
        job.setId(cursor.getInt(0));
        job.setDate(cursor.getString(1));
        job.setJobName(cursor.getString(2));
        job.setJobType(cursor.getString(3));
        job.setStartTime(cursor.getString(4));
        job.setStopTime(cursor.getString(5));
        job.setRole_name(cursor.getString(6));


        return job;
    }
    /*
    TODO: búa til alert sem spyr hvort þú sér viss
     */
    public void deleteAllJobs(){
        //eyða öllu úr database
        open();
        String query = "delete from jobs;";
        database.execSQL(query);
        close();
    }
    public void deleteAllConstants(String role_name){
        open();
        String consquery = "delete from constants where role_name = \'" + role_name + "\'";
        String rolequery = "delete from role where name = \'"+ role_name + "\'";
        database.execSQL(rolequery);
        database.execSQL(consquery);
        close();
    }
    //methods for constant table
    public List<List<String>> selectAllConstants(String role){
        open();
        String[] columns = {"constant", "type"};
        List<List<String>> lists = new ArrayList<List<String>>();

        Cursor cur = database.rawQuery("select constant, type from constants where role_name = \'" + role + "\';", null);
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            List<String> list = new ArrayList<String>();
            list.add(cur.getString(0));
            list.add(cur.getString(1));
            lists.add(list);
            cur.moveToNext();
        }
        cur.close();
        close();
        return lists;
    }
    public void deleteOneConstant(String constant, String role_name, String type){
        try{
            open();
            database.delete(MySQLiteHelper.TABLE_CONSTANTS,"constant = \'" + constant +"\'" + " and role_name = "+ "\'"+role_name+"\'" + "and type = \'"+ type + "\'", null);
            close();
        }catch(Exception e){

        }
    }
    public void insertOneConstant(String cons, String constype, String role_name){
        open();
        ContentValues content = new ContentValues();
        content.put("constant", cons);
        content.put("type", constype);
        content.put("role_name", role_name);
        database.insert("constants", null, content);
        close();
    }
    public void createRole(String role_name){
        open();
        ContentValues content = new ContentValues();
        content.put("name", role_name);
        database.insert("role",null ,content);
        close();

    }
    public List<String> getAllRoles(){
        List<String> roles = new ArrayList<String>();
        open();
        Cursor cur = database.rawQuery("select name from role;", null);
        cur.moveToFirst();
        while(!cur.isAfterLast()){
            roles.add(cur.getString(0));
            cur.moveToNext();
        }
        return roles;
    }
}
