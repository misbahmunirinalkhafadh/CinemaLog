package com.dicoding.cinemalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.cinemalog.db.FavTvShowContract.FavTvShowColumns.TABLE_NAME;

public class FavTvShowHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavTvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavTvShowHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    /**
     * untuk menginisiasi database.
     *
     * @param context
     * @return
     */
    public static FavTvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavTvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * CONFIGURATION
     * metode untuk membuka dan menutup koneksi ke database
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    /**
     * GET
     * Metode untuk untuk mengambil data.
     *
     * @return
     */
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    /**
     * GETBYID
     * metode untuk mengambil data dengan id tertentu.
     *
     * @param id
     * @return
     */
    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    /**
     * INSERT
     * metode untuk menyimpan data.
     *
     * @param values
     * @return
     */
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    /**
     * UPDATE
     * metode untuk memperbaharui data.
     *
     * @param id
     * @param values
     * @return
     */
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    /**
     * DELETE
     * metode untuk menghapus data
     *
     * @param id
     * @return
     */
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
