package sg.edu.np.mad.madpractical;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeDatabaseHelper
{
    private DatabaseReference dbReference = null;

    private String correctUsername = "";

    private String correctPassword = "";

    private OnCredentialsCheckCompletedListener onCredentialsCheckCompletedListener = null;

    public static FirebaseRealtimeDatabaseHelper instance = new FirebaseRealtimeDatabaseHelper();

    public FirebaseRealtimeDatabaseHelper()
    {
        this.dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public void isCredentialsCorrect(String username, String password, OnCredentialsCheckCompletedListener onCredentialsCheckCompletedListener)
    {
        this.onCredentialsCheckCompletedListener = onCredentialsCheckCompletedListener;

        this.dbReference.child("Users").child("mad").child("username").get().addOnCompleteListener(
            new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    FirebaseRealtimeDatabaseHelper.this.correctUsername = task.getResult().getValue(String.class);

                    FirebaseRealtimeDatabaseHelper.this.dbReference.child("Users").child("mad").child("password").get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                FirebaseRealtimeDatabaseHelper.this.correctPassword = task.getResult().getValue(String.class);

                                if (FirebaseRealtimeDatabaseHelper.this.onCredentialsCheckCompletedListener != null)
                                {
                                    FirebaseRealtimeDatabaseHelper.this.onCredentialsCheckCompletedListener.onCredentialsCheckCompleted(
                                        (username.equals(FirebaseRealtimeDatabaseHelper.this.correctUsername) && password.equals(FirebaseRealtimeDatabaseHelper.this.correctPassword))
                                    );
                                }
                            }
                        }
                    );
                }
            }
        );
    }

    public interface OnCredentialsCheckCompletedListener
    {
        void onCredentialsCheckCompleted(boolean isCorrectCredentials);
    }
}
