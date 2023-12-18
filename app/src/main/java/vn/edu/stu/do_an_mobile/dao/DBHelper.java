package vn.edu.stu.do_an_mobile.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import vn.edu.stu.do_an_mobile.model.BANBE;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "doan_mobile.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "BANBE";
    private static final String TABLE_PRODUCT2 = "MANGXAHOI";
    private static final String COLUMN_MADINHDANH = "madinhdanh";
    private static final String COLUMN_BIETDANH = "bietdanh";
    private static final String COLUMN_HO = "ho";
    private static final String COLUMN_TEN = "ten";
    private static final String COLUMN_NGAYSINH= "ngaysinh";
    private static final String COLUMN_MAIL = "mail";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_HINH= "hinh";
    private static final String COLUMN_MADINHDANH2 = "ma";
    private static final String COLUMN_TENMXH = "tenmxh";
    private static final String COLUMN_LINK = "link";


    public DBHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public ArrayList<BANBE> getAll() {

        ArrayList<BANBE> banbe = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_PRODUCT  , null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            String ma = cursor.getString(0);
            String bietdanh = cursor.getString(1);
            String ho = cursor.getString(2);
            String ten = cursor.getString(3);
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            Date startDate = null;
            try {
                startDate = df.parse(cursor.getString(4));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String mail = cursor.getString(5);
            String phone = cursor.getString(6);
            byte[] hinh = cursor.getBlob(7);

            //

            Cursor cursor2 = db.rawQuery("SELECT * from "+TABLE_PRODUCT2, null);
            ArrayList<MANGXAHOI> mxh = new ArrayList<>();
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                if(Objects.equals(cursor2.getString(0), ma)){
                    mxh.add( new MANGXAHOI(cursor2.getString(1),cursor2.getString(2)));
                }
                cursor2.moveToNext();
            }
            cursor2.close();
            
            
            banbe.add(new BANBE(ma,bietdanh,ho,ten,mail,startDate,phone,mxh,hinh));
            cursor.moveToNext();
        }

        cursor.close();
        return banbe   ;


    }


    public BANBE getOne(String id) {
        BANBE banbe = new BANBE();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from "+TABLE_PRODUCT  , null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            if(cursor.getString(0).equals(id)){
                banbe.setMa( cursor.getString(0));
                banbe.setBietdanh( cursor.getString(1));
                banbe.setHo( cursor.getString(2));
                banbe.setTen( cursor.getString(3));
                DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                Date startDate = null;
                try {
                    startDate = df.parse(cursor.getString(4));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                banbe.setNgaysinh(startDate);
                banbe.setPhone(cursor.getString(6));
                banbe.setMail( cursor.getString(5));
                banbe.setPhoto(cursor.getBlob(7));


                Cursor cursor2 = db.rawQuery("SELECT * from "+TABLE_PRODUCT2, null);
                ArrayList<MANGXAHOI> mxh = new ArrayList<>();
                cursor2.moveToFirst();
                while (!cursor2.isAfterLast()) {
                    if(Objects.equals(cursor2.getString(0), id)){
                        mxh.add( new MANGXAHOI(cursor2.getString(1),cursor2.getString(2)));
                    }
                    cursor2.moveToNext();
                }
                cursor2.close();
                banbe.setMxh(mxh);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return banbe   ;
    }

    public void delete(BANBE banbe) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCT2,COLUMN_MADINHDANH2 +" = ?",new String[]{banbe.getMa()});
        db.delete(TABLE_PRODUCT,COLUMN_MADINHDANH +" = ?",new String[]{banbe.getMa()});
        db.close();
    }

    public void update(BANBE banbe) {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(banbe.ngaysinh);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BIETDANH,banbe.getBietdanh()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_HO,banbe.getHo()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_TEN,banbe.getTen()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_MAIL,banbe.getMail()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_PHONE,banbe.getPhone()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_NGAYSINH,strDate);
        cv.put(COLUMN_HINH,banbe.getPhoto()); //These Fields should be your String values of actual column names
        db.update(TABLE_PRODUCT,cv,COLUMN_MADINHDANH +" = ?",new String[]{banbe.getMa()});
        db.close();
    }

    public void insert(BANBE banbe) {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(banbe.ngaysinh);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BIETDANH,banbe.getBietdanh()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_HO,banbe.getHo()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_TEN,banbe.getTen()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_MAIL,banbe.getMail()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_PHONE,banbe.getPhone()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_NGAYSINH,strDate);
        cv.put(COLUMN_HINH,banbe.getPhoto());//These Fields should be your String values of actual column names
        db.insert(TABLE_PRODUCT,null,cv);


        String x=null;
        SQLiteDatabase db2 = getWritableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * from "+TABLE_PRODUCT  , null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(1).equals(banbe.getBietdanh())){
                x = cursor.getString(0);

            }
            cursor.moveToNext();
        }

        cursor.close();


        for(MANGXAHOI mxh :banbe.getMxh()){
            insertsocial(x,mxh);
        }

        db.close();
    }
    public void insertsocial(String ma,MANGXAHOI mxh) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MADINHDANH2,ma); //These Fields should be your String values of actual column names
        cv.put(COLUMN_TENMXH,mxh.getTen()); //These Fields should be your String values of actual column names
        cv.put(COLUMN_LINK,mxh.getLink()); //These Fields should be your String values of actual column names
        db.insert(TABLE_PRODUCT2,null,cv);
        db.close();
    }
    public void deletesocial(String ma,MANGXAHOI mxh) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCT2,COLUMN_MADINHDANH2 +" = ? and "+COLUMN_TENMXH +" = ? and "+COLUMN_LINK + " = ?",new String[]{ma,mxh.getTen(),mxh.getLink()});
        db.close();
    }
    public void updatesoial(String ma,MANGXAHOI mxh) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LINK,mxh.getLink()); //These Fields should be your String values of actual column names
        db.update(TABLE_PRODUCT2,cv,COLUMN_MADINHDANH2 +" = ? and "+COLUMN_TENMXH +" = ?", new String[]{ma,mxh.getTen()});
        db.close();
    }


}
