package app.com.example.kirtsahaj.sherlocktv;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PointF;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/app.com.example.kirtsahaj.sherlocktv/databases/";
    private static String DB_NAME = "shows.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private DatabaseHelper ourHelper;

    // fields for table 1-
    public static final String KEY_EPISODE_NO = "episodeNo";
    public static final String KEY_SEASON_NO = "seasonNo";
    public static final String KEY_EPISODE_NAME = "episodeName";
    public static final String KEY_episode_Detail = "episodeDescription";

    public static final String DATABASE_TABLE = "episode";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist)
            return;
        else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public DatabaseHelper getInstance(Context context) {
        if (ourHelper == null) {
            ourHelper = new DatabaseHelper(context);
        }
        return this;
    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null)
            checkDB.close();
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getEpisode(int episode, int season){
       // myDataBase = this.getReadableDatabase();

        String[] columns = new String[]{ KEY_EPISODE_NAME, KEY_episode_Detail};
        ArrayList<String> result = new ArrayList<>();
        Cursor cursor =myDataBase.query(DATABASE_TABLE, columns, KEY_SEASON_NO + "=" + season +
                " AND " + KEY_EPISODE_NO + "=" + episode, null, null, null, null);
        try {
            if(cursor!=null){
                cursor.moveToFirst();
                result.add(cursor.getString(0));
                result.add(cursor.getString(1));
            }
        }catch (CursorIndexOutOfBoundsException e){
            cursor.close();
            return  null;
        }

        return result;
    }
}