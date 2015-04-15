package kiki.com.jlpsi.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();

    private static final String DB_NAME = "jlpsi.db";
    private static final int VERSION = 1;

    public static final String TABLE_PUPILS = "pupils";
    public static final String TABLE_COMPUTERS = "computers";
    public static final String TABLE_COMPLAINTS = "complaints";


    public static final String C_REG_NO = "reg_no";
    public static final String C_NAME = "name";
    public static final String C_STATUS = "status";
    public static final String C_PIC_URL = "pic_url";
    public static final String C_COMP_SERIAL = "comp_serial";
    public static final String C_ASSIGNED_BY = "assigned_by";

    public static final String C_DESCRIPTION = "description";
    public static final String C_TIME = "time";
    public static final String C_RESPONSE = "response";
    public static final String C_RESPONDED_BY = "responded_by";
    public static final String C_LOGGED_BY = "logged_by";
    public static final String C_PASSWORD = "password";

    public static final String C_ID = "id";


    public static final String C_CONDITION = "condition";
    public static final String C_PACKAGE_CODE = "package_code";
    public static final String C_ASSIGNED_TO = "assigned_to";


    private static final String CREATE_TABLE_PUPILS = "create table "
            + TABLE_PUPILS + "(" + C_REG_NO + " text primary key, " + C_NAME + " text, " + C_COMP_SERIAL + " text, " + C_PIC_URL + " text, " + C_STATUS + " text, " + C_ASSIGNED_BY + " text)";

    private static final String CREATE_TABLE_COMPUTERS = "create table "
            + TABLE_COMPUTERS + "(" + C_COMP_SERIAL + " text primary key, "
            + C_CONDITION + " text, " + C_PACKAGE_CODE + " text, " + C_DESCRIPTION
            + " text, " + C_ASSIGNED_TO + " text)";

    private static final String CREATE_TABLE_COMPLAINTS = "create table "
            + TABLE_COMPLAINTS + "(" + C_ID + " text primary key, " + C_DESCRIPTION
            + " text, " + C_TIME + " text," + C_RESPONSE + " text, "
            + C_RESPONDED_BY + " text, " + C_LOGGED_BY + " text)";


    private static DbHelper dbHelper = null;

    private DbHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, VERSION);
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (dbHelper == null)
            dbHelper = new DbHelper(context);
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PUPILS);
        db.execSQL(CREATE_TABLE_COMPLAINTS);
        db.execSQL(CREATE_TABLE_COMPUTERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
