package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Car;
import Utils.Util;

public class DbHandler extends SQLiteOpenHelper {
    public DbHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARS_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PRICE + " DOUBLE" + ")";

        db.execSQL(CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.DATABASE_NAME);
        onCreate(db);
    }

    public void addCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, car.getName());
        contentValues.put(Util.KEY_PRICE, car.getPrice());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public int updateCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, car.getName());
        contentValues.put(Util.KEY_PRICE, car.getPrice());

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID = "=?",
                new String[]{String.valueOf(car.getId())});

    }

    public void deleteCar(Car car){
        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(Util.TABLE_NAME, Util.KEY_ID = "=?",
                new String[]{String.valueOf(car.getId())});
    }


    public Car getCar(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PRICE},
                Util.KEY_ID + "=?", null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return new Car(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
    }

    public List<Car> allCars(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Car> carList = new ArrayList<>();

        String allCars = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(allCars, null);


        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Double price = cursor.getDouble(2);

            carList.add(new Car(name, price));
        }
        cursor.close();
        db.close();

        return carList;
    }
}
