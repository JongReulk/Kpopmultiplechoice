package com.tenriver.kpopmultiplechoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tenriver.kpopmultiplechoice.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyKpopQuizMultipleChoice.db";
    private static final int DATABASE_VERSION = 2; // 데이터베이스 버전 항상 다르게 하기

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // 주의 깊게 코딩
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                // QuizContract에 있는 부분
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_SINGER + " TEXT, " +
                QuestionsTable.COLUMN_YEAR + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Question q1 = new Question("WMweEpGlu_U", "Butter","Dynamite", "Permission to Dance", "소리꾼 (Thunderous)", 1,"BTS",2021);
        addQuestion(q1);
        Question q2 = new Question("4TWR90KJl84", "Savage","Next Level", "Not Shy", "Black Mamba", 2,"aespa (에스파)",2021);
        addQuestion(q2);
        Question q3 = new Question("HzOjwL7IP_o", "LIAR LIAR","HAPPY", "Dun Dun Dance", "컬러링북 (Coloring Book)", 3,"OH MY GIRL(오마이걸)",2021);
        addQuestion(q3);
        Question q4 = new Question("v7bnOxV4jAc", "On The Ground","strawberry moon", "Coin", "라일락 (LILAC)", 4,"IU(아이유)",2021);
        addQuestion(q4);

    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_SINGER, question.getSinger());
        cv.put(QuestionsTable.COLUMN_YEAR, question.getYear());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSinger(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_SINGER)));
                question.setYear(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_YEAR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public List<Question> getQuestions(String year) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        /*
        for(int i = 0; i < year.length; i++){
            String[] selectionArgs = new String[]{year[i]};
        }*/


        String[] selectionArgs = new String[]{year};
        Log.e("Year", " : " + selectionArgs.length);
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_YEAR + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSinger(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_SINGER)));
                question.setYear(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_YEAR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
