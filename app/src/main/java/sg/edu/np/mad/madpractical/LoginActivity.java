package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText = null;

    private EditText passwordEditText = null;

    private Button loginButton = null;

    private Toast incorrectCredentialsToast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this.incorrectCredentialsToast = Toast.makeText(
            this,
            "Incorrect username and/or password entered.",
            Toast.LENGTH_SHORT
        );

        this.usernameEditText = this.findViewById(R.id.UsernameEditText);

        this.passwordEditText = this.findViewById(R.id.PasswordTextEdit);

        this.loginButton = this.findViewById(R.id.LoginButton);

        this.loginButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseRealtimeDatabaseHelper.instance.isCredentialsCorrect(
                        LoginActivity.this.usernameEditText.getText().toString(),
                        LoginActivity.this.passwordEditText.getText().toString(),
                        new FirebaseRealtimeDatabaseHelper.OnCredentialsCheckCompletedListener() {
                            @Override
                            public void onCredentialsCheckCompleted(boolean isCorrectCredentials) {
                                if (isCorrectCredentials)
                                {
                                    Intent intent = new Intent(
                                            LoginActivity.this,
                                            ListActivity.class
                                    );

                                    LoginActivity.this.startActivity(intent);
                                }
                                else
                                {
                                    LoginActivity.this.incorrectCredentialsToast.show();
                                }
                            }
                        }
                    );
                }
            }
        );
    }
}