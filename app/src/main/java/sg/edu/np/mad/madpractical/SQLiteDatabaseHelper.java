package sg.edu.np.mad.madpractical;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper
{
    // Database Info
    private static final String DATABASE_NAME = "MainDatabase";

    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String USERS_TABLE_NAME = "user";

    // User Table Columns
    private static final String KEY_USER_ID = "id";

    private static final String KEY_USER_NAME = "name";

    private static final String KEY_USER_DESCRIPTION = "description";

    private static final String KEY_FOLLOWED = "followed";

    public static SQLiteDatabaseHelper instance = null;

    private ArrayList<User> getRandomUsers(int numRandomUsers)
    {
        Random random = new Random();

        random.setSeed(System.currentTimeMillis());

        ArrayList<User> users = new ArrayList<>();

        User currentUser = null;

        for (int currentUserIndex = 0; currentUserIndex < numRandomUsers; currentUserIndex++)
        {
            currentUser = new User();

            currentUser.name = "Name" + random.nextInt();

            currentUser.description = "Description " + random.nextInt();

            currentUser.followed = random.nextBoolean();

            users.add(
                currentUser
            );
        }

        return users;
    }

    public SQLiteDatabaseHelper(Context context)
    {
        super(
            context,
            DATABASE_NAME,
            null,
            DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTableCommand = "CREATE TABLE " + USERS_TABLE_NAME +
        "(" +
        KEY_USER_NAME + " TEXT," +
        KEY_USER_DESCRIPTION + " TEXT," +
        KEY_USER_ID + " INTEGER PRIMARY KEY," +
        KEY_FOLLOWED + " INTEGER" +
        ")";

        db.execSQL(createUsersTableCommand);

        this.addUsers(
            this.getRandomUsers(20),
            db
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);

            this.onCreate(db);
        }
    }

    public void addUsers(ArrayList<User> users, SQLiteDatabase db)
    {
        if (db == null)
        {
            db = this.getWritableDatabase();
        }

        User currentUser = null;

        ContentValues currentContentValues = null;

        for (int currentUserIndex = 0; currentUserIndex < users.size(); currentUserIndex++)
        {
            currentUser = users.get(currentUserIndex);

            currentContentValues = new ContentValues();

            currentContentValues.put(KEY_USER_NAME, currentUser.name);

            currentContentValues.put(KEY_USER_DESCRIPTION, currentUser.description);

            if (currentUser.followed == true)
            {
                currentContentValues.put(KEY_FOLLOWED, 1);
            }
            else
            {
                currentContentValues.put(KEY_FOLLOWED, 0);
            }

            db.beginTransaction();

            db.insertOrThrow(
                USERS_TABLE_NAME,
                null,
                currentContentValues
            );

            db.setTransactionSuccessful();

            db.endTransaction();
        }
    }

    public int updateUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (user.followed == true)
        {
            contentValues.put(KEY_FOLLOWED, 1);
        }
        else
        {
            contentValues.put(KEY_FOLLOWED, 0);
        }

        return db.update(
            USERS_TABLE_NAME,
            contentValues,
            KEY_USER_ID + " = ?",
            new String[]
            {
                String.valueOf(user.id)
            }
        );
    }

    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();

        User user = null;

        String usersTableQueryCommand = "SELECT * FROM " + USERS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(usersTableQueryCommand, null);

        int userNameColumnIndex = cursor.getColumnIndex(KEY_USER_NAME);

        int userDescriptionColumnIndex = cursor.getColumnIndex(KEY_USER_DESCRIPTION);

        int userIDColumnIndex = cursor.getColumnIndex(KEY_USER_ID);

        int followedColumnIndex = cursor.getColumnIndex(KEY_FOLLOWED);

        if (cursor.moveToFirst() == true)
        {
            do
            {
                user = new User();

                user.name = cursor.getString(
                    userNameColumnIndex
                );

                user.description = cursor.getString(
                    userDescriptionColumnIndex
                );

                user.id = cursor.getInt(
                    userIDColumnIndex
                );

                int followedNum = cursor.getInt(
                    followedColumnIndex
                );

                if (followedNum == 0)
                {
                    user.followed = false;
                }
                else
                {
                    user.followed = true;
                }

                users.add(user);
            }
            while (cursor.moveToNext() == true);
        }

        return users;
    }
}
