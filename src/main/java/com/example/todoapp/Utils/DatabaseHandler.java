package com.example.todoapp.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;
import com.example.todoapp.Model.ToDoModel;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int VERSION = 2;
    private static final String NAME= "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE "+TODO_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TASK+" TEXT,"+STATUS+" INTEGER)";
    private SQLiteDatabase db;
    public DatabaseHandler(Context context){
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add the "task" column to the existing table
            db.execSQL("ALTER TABLE " + TODO_TABLE + " ADD COLUMN " + TASK + " TEXT");

        }
    }
    public void openDatabase(){
        db=this.getWritableDatabase();
    }
    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK,task.getTask());
        cv.put(STATUS,0);
        db.insert(TODO_TABLE,null,cv);
    }
    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList =new ArrayList<>();
        db.beginTransaction();
        Cursor cur=null;
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null) ;
            if (cur != null) {
                if (cur.moveToFirst()) {
                    int idIndex = cur.getColumnIndex(ID);
                    int taskIndex = cur.getColumnIndex(TASK);
                    int statusIndex =  cur.getColumnIndex(STATUS);
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(idIndex));
                        task.setTask(cur.getString(taskIndex));
                        task.setStatus(cur.getInt(statusIndex));
                        taskList.add(task);
                    }while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();

            assert cur != null;
            cur.close();
        }
        return taskList;
    }
    public void updateStatus(int id,int status){
        ContentValues cv=new ContentValues();
        cv.put(STATUS,status);
        db.update(TODO_TABLE,cv,ID + "=?",new String[] {String.valueOf(id)});

    }
    public void updateTask (int id , String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK,task);
        db.update(TODO_TABLE, cv , ID + "=?",new String[] {String.valueOf(id)});
    }

    public void deleteTask (int id){
        db.delete(TODO_TABLE, ID + "=?", new String[]{String.valueOf(id)});
    }
}
