package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private User user = null;

    private TextView userNameTextView = null;

    private TextView userDescriptionTextView = null;

    private Button followButton = null;

    private Button messageButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.user = UsersAdapter.instance.users.get(
            UsersAdapter.instance.currentSelectedUserIndex
        );

        this.userNameTextView = findViewById(R.id.UserNameTextView);

        this.userNameTextView.setText(this.user.name);

        this.userDescriptionTextView = findViewById(R.id.UserDescriptionTextView);

        this.userDescriptionTextView.setText(this.user.description);

        this.followButton = findViewById(R.id.FollowButton);

        if (this.user.followed == true)
        {
            this.followButton.setText(R.string.unfollow_button_text);
        }
        else
        {
            this.followButton.setText(R.string.follow_button_text);
        }

        this.followButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.user.followed = !MainActivity.this.user.followed;

                    if (MainActivity.this.user.followed == true)
                    {
                        MainActivity.this.followButton.setText(R.string.unfollow_button_text);

                        Toast.makeText(MainActivity.this, "Followed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        MainActivity.this.followButton.setText(R.string.follow_button_text);

                        Toast.makeText(MainActivity.this, "Unfollowed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );

        this.messageButton = findViewById(R.id.MessageButton);

        this.messageButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MessageGroup.class);

                    startActivity(intent);
                }
            }
        );
    }
}