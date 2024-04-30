package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Model.doList;
import Utils.Util;

public class DbHandler extends SQLiteOpenHelper {
    public DbHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_TABLE = "CREATE TABLE" + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_TITLE + " TEXT,"
                + Util.KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.DATABASE_NAME);
        onCreate(db);
    }
    public void addList(doList doList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_TITLE, doList.getTitle());
        contentValues.put(Util.KEY_DESCRIPTION, doList.getDescription());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }
    public int updateList(doList doList){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_TITLE, doList.getTitle());
        contentValues.put(Util.KEY_DESCRIPTION, doList.getDescription());

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[]{String.valueOf(Model.doList.getId())});

    }
}
