package com.example.acookbookapp.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.acookbookapp.R;

import java.io.ByteArrayOutputStream;

public class SQLiteHelper extends SQLiteOpenHelper {
    //2 tables
    //user and recipe
    public Context context;
    static String DATABASE_NAME="UserDataBase";

    public static final String User="UserTable";

    public static final String User_Column_ID="id";

    public static final String User_Column_1_Name="name";

    public static final String User_Column_2_Email="email";

    public static final String User_Column_3_Password="password";

    public static final String Recipe="Recipe";

    public static final String Recipe_Column_ID="id";

    public static final String Recipe_Column_1_Name="name";

    public static final String Recipe_Column_2_Ingredients="ingredient";

    public static final String Recipe_Column_3_Instructions="instruct";

    public static final String Recipe_Column_4_Time="time";

    public static final String Recipe_Column_4_Diff="difficulty";

    public static final String Recipe_Column_4_Cat="category";
    public  String Recipe_Column_4_Img="image";
    public  String Recipe_User_Id="userId";

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 6);
        // Application Context
        this.context = context.getApplicationContext();

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
    //table 1 is for users table
        //table 2 is for recipes with a foreign key to user that create it
        String CREATE_TABLE1="CREATE TABLE IF NOT EXISTS "+User+" ("+User_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+User_Column_1_Name+" VARCHAR, "+User_Column_2_Email+" VARCHAR, "+User_Column_3_Password+" VARCHAR)";
        String CREATE_TABLE2="CREATE TABLE IF NOT EXISTS "+Recipe+" ("+Recipe_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Recipe_Column_1_Name+" VARCHAR, "+Recipe_Column_2_Ingredients+" VARCHAR, "+Recipe_Column_3_Instructions+" VARCHAR, "+Recipe_Column_4_Time+" VARCHAR, "+Recipe_Column_4_Diff+" VARCHAR, "+Recipe_Column_4_Cat+" VARCHAR, "+Recipe_Column_4_Img+" BLOB, "+Recipe_User_Id+" VARCHAR,"+"FOREIGN KEY(userID) REFERENCES UserTable(id) )";
        database.execSQL(CREATE_TABLE1);
        database.execSQL(CREATE_TABLE2);
        String addUser = "INSERT INTO UserTable VALUES(1, 'ethan', 'ebriffa', '911')";

        database.execSQL(addUser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+User);
        db.execSQL("DROP TABLE IF EXISTS "+Recipe);
        onCreate(db);



    }


//method to seed data in recipes if no recipes are found in db
    public void seed(){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap myLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.pancake);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myLogo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ContentValues val1 = this.seedvalues("Pancake", "ingredient1,ingredient2,ingredient3,ingredient4,ingredient5,ingredient6","First crack ingredient 1 and mix into bowl until \nThen place ingredient2 and continue mixing with ingreident1\nThen wait until..", "30min", "Medium", "Desert", byteArray, "1");

       Bitmap myLogo2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pizza2);
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        myLogo2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] byteArray2 = stream2.toByteArray();
        ContentValues val2 = this.seedvalues("Pizza", "ingredient1,ingredient2,ingredient3,ingredient4,ingredient5,ingredient6","these\ninst\nructions", "30min", "Medium", "Pizza", byteArray2, "1");

        Bitmap myLogo3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pasta2);
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        myLogo3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        byte[] byteArray3 = stream3.toByteArray();
        ContentValues val3 = this.seedvalues("Pasta", "ingredient1,ingredient2,ingredient3,ingredient4,ingredient5,ingredient6","these\ninst\nructions", "30min", "Medium", "Pasta", byteArray3, "1");
        db.insert(Recipe,null ,val1);
        db.insert(Recipe,null ,val2);
        db.insert(Recipe,null ,val3);
    }

    public ContentValues seedvalues(String name,String ing,String instr,String time,String diff, String cat, byte[] image, String userId)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recipe_Column_1_Name,name);
        contentValues.put(Recipe_Column_2_Ingredients,ing);
        contentValues.put(Recipe_Column_3_Instructions,instr);
        contentValues.put(Recipe_Column_4_Time,time);
        contentValues.put(Recipe_Column_4_Diff,diff);
        contentValues.put(Recipe_Column_4_Cat,cat);
        contentValues.put(Recipe_Column_4_Img, image);
        contentValues.put(Recipe_User_Id, userId);

        return contentValues;
    }

        //method to insert data for recipe
    public boolean insertDataRecipe(String name,String ing,String instr,String time,String diff, String cat, byte[] image, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";
        ContentValues contentValues = new ContentValues();
        contentValues.put(Recipe_Column_1_Name,name);
        contentValues.put(Recipe_Column_2_Ingredients,ing);
        contentValues.put(Recipe_Column_3_Instructions,instr);
        contentValues.put(Recipe_Column_4_Time,time);
        contentValues.put(Recipe_Column_4_Diff,diff);
        contentValues.put(Recipe_Column_4_Cat,cat);
        contentValues.put(Recipe_Column_4_Img, image);
        contentValues.put(Recipe_User_Id, userId);


//        SQLiteStatement statement = db.compileStatement(sql);
//        statement.clearBindings();
//
//        statement.bindString(1, name);
//        statement.bindString(2, price);
//        statement.bindBlob(3, image);
//
//        statement.executeInsert();

        long result = db.insert(Recipe,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Recipe, "id = ?",new String[] {id});
    }
        //get all recipes
    public Cursor getAllDataRecipe() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Recipe,null);
        return res;
    }
    //get recipes by category
    public Cursor getAllRecipesByCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = { category};
        Cursor res = db.rawQuery("select * from "+Recipe+ " where category = ?",args);
        return res;
    }

    //get recipes by userId
    public Cursor getAllRecipesByUserId(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = { userId};
        Cursor res = db.rawQuery("select * from "+Recipe+ " where userId = ?",args);
        return res;
    }


        //get single recipe by id
    public Cursor getSingleRecipe(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = { id};
        Cursor res = db.rawQuery("select * from "+Recipe + " where id = ?",args);


        return res;
    }

}
