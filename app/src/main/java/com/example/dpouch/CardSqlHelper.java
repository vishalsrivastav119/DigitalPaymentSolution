package com.example.dpouch;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 586924 on 11/8/2016.
 */

public class CardSqlHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "TransactionDetails";
    public static final String COL_TOKEN_ID = "TokenId";
    public static final String COL_CARD_NUMBER= "CardNumber";
    public static final String COL_AMOUNT="Amount";
    public static final String COL_TIMESTAMP="Timestamp";

    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_TOKEN_ID + " TEXT, " + COL_TIMESTAMP + " VARCHAR2(10),"+ COL_CARD_NUMBER + " VARCHAR2(10),"+ COL_AMOUNT +" VARCHAR2(10));";

    public CardSqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

        //loading initial values into the database here
        ContentValues cv = new ContentValues(2);
        cv.put(COL_TOKEN_ID, "4962160000001123");
        cv.put(COL_CARD_NUMBER,"************7990");
        cv.put(COL_AMOUNT,"45");
        cv.put(COL_TIMESTAMP,"12 Oct 2016");
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
