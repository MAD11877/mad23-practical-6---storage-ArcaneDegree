package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper
{
    // Database Info
    private static final String DATABASE_NAME = "MainDatabase";

    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String USERS_TABLE_NAME = "users";

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
            this.getRandomUsers(20)
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

    public void addUsers(ArrayList<User> users)
    {
        SQLiteDatabase db = this.getWritableDatabase();

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

    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();



        return users;
    }
}
