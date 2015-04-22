package helgamaria.vinnuhelga;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import helgamaria.vinnuhelga.sql.JobObject;
import helgamaria.vinnuhelga.sql.dbFunctions;

/**
 * Created by frimann on 8.4.2015.
 */
public class makeCSVFile {

    private static final String TAG = "MEDIA";
    public static void save(Context ctx){

        dbFunctions dbFunc = new dbFunctions(ctx);
        dbFunc.open();
        List<JobObject> listOfJobsDb = dbFunc.selectAllJobs();
        dbFunc.close();

        File root = android.os.Environment.getExternalStorageDirectory();

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "WorkData.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            String line = "";
            pw.print("DATE,WORKER ROLE,JOB NAME,JOB TYPE,STARTTIME,STOPTIME,TIME(in seconds)\r\n");
            for(int i = 0; i < listOfJobsDb.size();i++){
                DateFormat form = new SimpleDateFormat("hh:mm:ss");

                try{
                    Date start = form.parse(listOfJobsDb.get(i).getStartTime());
                    Date stop = form.parse(listOfJobsDb.get(i).getStopTime());
                    Long diff = stop.getTime() - start.getTime();
                    String alltime = Long.toString(diff/1000);
                    line = listOfJobsDb.get(i).getDate() +","+
                            listOfJobsDb.get(i).getRole_name()+","+
                            listOfJobsDb.get(i).getJobName() +","+
                            listOfJobsDb.get(i).getJobType() +","+
                            listOfJobsDb.get(i).getStartTime() +","+
                            listOfJobsDb.get(i).getStopTime() +","+
                            alltime+ "\r\n";
                    pw.print(line);
                }catch(ParseException p){
                    p.printStackTrace();
                }
            }
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
