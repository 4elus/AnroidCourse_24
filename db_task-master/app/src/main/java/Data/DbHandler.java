package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.doList;
import Utils.Util;

public class DbHandler extends SQLiteOpenHelper {
    public DbHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_TITLE + " TEXT,"
                + Util.KEY_DESCRIPTION + " TEXT,"
                + Util.KEY_STATUS +  " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_LIST_TABLE);
}
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }
    public int updateList(doList doList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_TITLE, doList.getTitle());
        contentValues.put(Util.KEY_DESCRIPTION, doList.getDescription());
        contentValues.put(Util.KEY_STATUS, doList.getCheck());

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[]{String.valueOf(doList.getId())});
    }
//    public int updateListCheckBox(doList doList){
//        SQLiteDatabase db= this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Util.KEY_STATUS, doList.getCheck());
//
//
//        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
//                new String[]{String.valueOf(doList.getId())});
//    }
    public void deleteList(doList doList){
        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(doList.getId())});
    }


    public void addList(doList doList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_TITLE, doList.getTitle());
        contentValues.put(Util.KEY_DESCRIPTION, doList.getDescription());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }


    /// get
    public doList getList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // добавил статус
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_TITLE, Util.KEY_DESCRIPTION},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        doList list = null;

        if (cursor != null && cursor.moveToFirst()) {
            int listId = cursor.getInt(0);
            String title = cursor.getString(1);
            String desc = cursor.getString(2);

            list = new doList(listId, title, desc);
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<doList> doAllList(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<doList> doLists = new ArrayList<>();

        String allList = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(allList, null);


        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String desciption = cursor.getString(2);
            // статус
            Log.i("List info", "ID: " + id + " Title: " + title + " Description: " + desciption + "\n");

            doLists.add(new doList(id, title, desciption)); // статус
        }
        cursor.close();
        db.close();

        return doLists;
    }
}
