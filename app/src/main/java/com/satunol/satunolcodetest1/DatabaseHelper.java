package com.satunol.satunolcodetest1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.satunol.satunolcodetest1.model.PointModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper  {

    // creating a constant variables for our database. below variable is for our database name.
    private static final String DB_NAME = "satunol.db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "points";

    // below variable is for our id column.
    private static final String ID = "id";

    // below variable is for our x coordinate column
    private static final String X = "x";

    // below variable id for our y coordinate column.
    private static final String Y = "y";

    // creating a constructor for our database handler.
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating an sqlite query and we are setting our column names along with their data types.
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + X + " REAL,"
                + Y + " REAL)";

        // execute above sql query
        db.execSQL(query);
    }

    // this method is used to add new point to our sqlite database.
    public void generateRandomPoints(PointModel pointModel) {

        //remove all old data

        // on below line we are creating a variable for  our sqlite database and calling writable method as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a variable for content values.
        ContentValues values = new ContentValues();
        // on below line we are passing all values along with its key and value pair.
        values.put(X, pointModel.getX());
        values.put(Y, pointModel.getY());
        // after adding all values we are passing content values to our table.
        db.insert(TABLE_NAME, null, values);
        // at last we are closing our database after adding database.
        db.close();
    }

    public List<PointModel> getAllPoints() {
        List<PointModel> pointModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (c.moveToNext()) {
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") float x = c.getFloat(c.getColumnIndex("x"));
            @SuppressLint("Range") float y = c.getFloat(c.getColumnIndex("y"));

            PointModel pointModel = new PointModel(id, x, y);
            pointModelList.add(pointModel);

//            Log.d("valerie", "ID: " + id + ", X: " + x + ", Y: " + y);
        }
        c.close();
        db.close();

        return pointModelList;
    }

    public void resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
        Log.d("valerie", "success delete");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
