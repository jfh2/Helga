package helgamaria.vinnuhelga.sql;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * Created by frimann on 23.2.2015.
 */
public class JobObject {
    //initialize variables for the object
    private int id;
    private Boolean isactive = false;
    private String date = null;
    private String job_name = null;
    private String startTime = null;
    private String stopTime = null;
    private String job_type = null;
    //get and set id for object
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    //initialize the jobObject
    public void initJobObj(){
        this.date = null;
        this.job_name = null;
        this.startTime = null;
        this.stopTime = null;
        this.job_type = null;
        this.isactive = false;
    }
    //boolean switch to determine if we are counting time
    public void on(){
        this.isactive = true;
    }
    public void off(){
        this.isactive = false;
    }
    //is it on or off
    public Boolean isItOn(){
        return this.isactive;
    }
    //get and set date for object
    public String getDate(){
        return this.date;
    }

    public void setDate(String dateString){
        if(dateString == null) {
            DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            this.date = dateformat.format(date);
        }else{
            this.date = dateString;
        }
    }
    //Get and set for startTime and stopTime
    public void setStartTime(String startingtime){
        if(startingtime == null) {
            DateFormat dateformat = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date();
            this.startTime = dateformat.format(date);
        }else{
            this.startTime = startingtime;
        }
    }
    public void setStopTime(String stoppingtime){
        if(stoppingtime == null) {
            DateFormat dateformat = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date();
            this.stopTime = dateformat.format(date);
        }else{
            this.stopTime = stoppingtime;
        }
    }

    public String getStartTime(){
        return this.startTime;
    }
    public String getStopTime(){
        return this.stopTime;
    }

    //get and set for jobname
    public void setJobName(String jobName){
        this.job_name = jobName;
    }
    public String getJobName(){
        return this.job_name;
    }

    //get and set for job type
    public void setJobType(String jobType){
        this.job_type = jobType;
    }
    public String getJobType(){
        return this.job_type;
    }

    //save job to db
    public void saveToDb(Context context){
        if((this.job_name == null|| this.job_type == null)||
           (this.startTime == null || this.stopTime == null)){

            System.out.println("bitch please1");
        }else{
            dbFunctions dbfunc = new dbFunctions(context);
            dbfunc.open();
            dbfunc.newJobRow(this.date,
                    this.job_name,
                    this.startTime,
                    this.stopTime,
                    this.job_type);
            dbfunc.close();
            initJobObj();
        }
    }
}
