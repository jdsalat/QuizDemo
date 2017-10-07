package com.machinetest.quiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.machinetest.quiz.beans.UserAnswerBean;

/**
 * Created by Javed.Salat on 8/8/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quiz_db";
    private static final String TABLE_QUEST = "table_user_answer";


    private static final String KEY_QUES_ID = "question_id";
    private static final String KEY_ANSWER_ID = "answer_id";
    private static final String KEY_IS_SYNCED_WITH_SERVER = "sync_flag";

    private SQLiteDatabase dbase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_QUES_ID + " INTEGER , " + KEY_ANSWER_ID
                + " INTEGER, " + KEY_IS_SYNCED_WITH_SERVER + " TINYINT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        onCreate(db);
    }


    // Adding new question
    public long saveUserAnswers(UserAnswerBean userAnswerBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ANSWER_ID, userAnswerBean.getAnswerId());
        values.put(KEY_QUES_ID, userAnswerBean.getQuestionId());
        values.put(KEY_IS_SYNCED_WITH_SERVER, 0);
        // Inserting Row
        return db.insert(TABLE_QUEST, null, values);
    }


    public Cursor getPendingUserAnswer() {
        String selectQuery = " SELECT " + KEY_ANSWER_ID + " , " + KEY_QUES_ID + " FROM " + TABLE_QUEST + " where " + KEY_IS_SYNCED_WITH_SERVER + "= 0 ";
        dbase = this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        Log.e("", "Data Collected From DATA BASE");
        return cursor;
    }


    public void deleteUploadedData() {
        dbase = this.getWritableDatabase();
        dbase.delete(TABLE_QUEST, null, null);
        Log.e("", "Data Deleted");
    }
}
