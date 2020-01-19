package com.dicoding.cinemalog.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.cinemalog.db.FavMovieContract.FavMovieColumns.MOVIE_ID;
import static com.dicoding.cinemalog.db.FavMovieContract.TABLE_NAME;

public class FavMovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavMovieHelper INSTANCE;
    private static SQLiteDatabase database;

    public FavMovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    /**
     * untuk menginisiasi database.
     *
     * @param context
     * @return
     */
    public static FavMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavMovieHelper(context);
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
                MOVIE_ID + " ASC");
    }

    /**
     * GETBYID
     * metode untuk mengambil data dengan id tertentu.
     *
     * @param id
     * @return
     */
    public Cursor queryById(String id) {
        database = dataBaseHelper.getReadableDatabase();
        return database.query(
                DATABASE_TABLE,
                null,
                MOVIE_ID + " = ?",
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
        database = dataBaseHelper.getWritableDatabase();
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
        return database.delete(DATABASE_TABLE, MOVIE_ID + " = ?", new String[]{id});
    }
}
