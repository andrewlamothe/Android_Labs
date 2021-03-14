package com.cst2335.lamo0241;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {
    int count;
    static String TABLE_NAME = "CHATLIST";
    static String ID_COLUMN = "id";
    static String MESSAGE_COLUMN = "message";
    static String IS_SENT= "sent";
    static int VERSION_NUM = 1;


    public SQLite(@Nullable Context context) {

        super(context, TABLE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String makeTable = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME + "("+ID_COLUMN+" " +
                "INTEGER PRIMARY KEY AUTOINCREMENT, "+MESSAGE_COLUMN+" TEXT, "+IS_SENT+" BOOLEAN)";
        db.execSQL(makeTable);

    }
    public void printCursor(Cursor c, int version){
       int column_num = c.getColumnCount();
       int row_num = c.getCount();
      // ArrayList<String> table_contents = new ArrayList<String>();
        Log.e("VERSION:", String.valueOf(version));
        Log.e("NUMBER OF ROWS:", String.valueOf(row_num));
        Log.e("NUMBER OF COLUMNS:", String.valueOf(column_num));
        c.moveToFirst();
        do {
            String content1 = c.getString(c.getColumnIndex(MESSAGE_COLUMN));
            int content2 = c.getInt(c.getColumnIndex(IS_SENT));
            long content3 = c.getLong(c.getColumnIndex((ID_COLUMN)));
            String result_cursor = "Message: "+ content1 + ", Sent?: " + String.valueOf(content2) + ", ID: "+ String.valueOf(content3);
           // table_contents.add(result_cursor);
            Log.e("DATA LINE:",result_cursor);
            c.moveToNext();
        }
        while(!c.isAfterLast());


    }
    int getVersion(){
        return VERSION_NUM;
    }
    long addChat(Chat chat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues chats = new ContentValues();
        chats.put("message", chat.getText());
        chats.put("sent", chat.getSend());
        long really = db.insert(TABLE_NAME, null, chats);
        Log.e("RETURNED LONG ID",String.valueOf(really));
        return really;
        }
    public int returnCount(){
        return this.count;
    }

    public ArrayList<Chat> getChat() {
        ArrayList<Chat> returnChat = new ArrayList<Chat>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            String x = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN));
            int bool = cursor.getInt(cursor.getColumnIndex(IS_SENT));
            int id = cursor.getInt(cursor.getColumnIndex((ID_COLUMN)));
            Chat chatIDadd = new Chat(x,(bool==1));
            chatIDadd.setID(id);
            returnChat.add(chatIDadd);
            cursor.moveToNext();
        }
        while(!cursor.isAfterLast());
        printCursor(cursor,getVersion());
        return returnChat;
    }
    public void removeChat(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE id="+id);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }




}