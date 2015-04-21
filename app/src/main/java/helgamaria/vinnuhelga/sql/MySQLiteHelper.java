package helgamaria.vinnuhelga.sql;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by frimann on 23.2.2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{
    //db name and version
    private static final String DATABASE_NAME = "jobs.db";
    private static final int DATABASE_VERSION = 1;
    //name of tables
    public static final String TABLE_JOB = "jobs";
    public static final String TABLE_CONSTANTS = "constants";
    public static final String TABLE_ROLE = "role";
    //column names in job table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_JOB_NAME = "job_name";
    public static final String COLUMN_START = "start_time";
    public static final String COLUMN_STOP = "stop_time";
    public static final String COLUMN_JOB_TYPE = "job_type";
    public static final String COLUMN_ROLE_NAME_JOB = "role_name";

    //column names for job_name_constants table
    public static final String COLUMN_JOB_CONSTANT_ID = "id";
    public static final String COLUMN_JOB_CONSTANT_STRING = "constant";
    public static final String COLUMN_JOB_CONSTANT_TYPE = "type";
    public static final String COLUMN_ROLE_NAME_CONS = "role_name";

    //column names for roles table
    public static final String COLUMN_NAME = "name";
    //sql stataement for database creation
    private static  final String CREATE_TABLE_ROLE = "create table "
            + TABLE_ROLE + "("
            + COLUMN_NAME + " varchar,"
            + " PRIMARY KEY ("+ COLUMN_NAME + "));";


    private static final String CREATE_TABLE_JOB = "create table "
            + TABLE_JOB + "("
            //declare columns
            + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_DATE + " varchar, "
            + COLUMN_JOB_NAME + " varchar, "
            + COLUMN_JOB_TYPE + " varchar, "
            + COLUMN_START + " varchar, "
            + COLUMN_STOP + " varchar, "
            + COLUMN_ROLE_NAME_JOB + " varchar);";
    private static final String  CREATE_TABLE_JOB_NAME_CONSTANTS = "create table "
            + TABLE_CONSTANTS + "("
            + COLUMN_JOB_CONSTANT_ID +" integer primary key autoincrement, "
            + COLUMN_JOB_CONSTANT_STRING + " varchar, "
            + COLUMN_JOB_CONSTANT_TYPE + " varchar, "
            + COLUMN_ROLE_NAME_CONS + " varchar);";


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE_JOB);
        database.execSQL(CREATE_TABLE_JOB_NAME_CONSTANTS);
        database.execSQL(CREATE_TABLE_ROLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(MySQLiteHelper.class.getName(),
                "upgrading db from version " + oldVersion + " to "
                + newVersion + " it will destroy all old data");
        db.execSQL("drop table if exists " + TABLE_JOB);
        db.execSQL("drop table if exists "+ TABLE_CONSTANTS);
        db.execSQL("drop table if exists "+ TABLE_ROLE);
        onCreate(db);
    }
}
