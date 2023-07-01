package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TimeUtils;
import android.view.View;
import android.widget.ImageView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    private RecyclerView usersRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        SQLiteDatabaseHelper.instance = new SQLiteDatabaseHelper(this);

        UsersAdapter.instance = new UsersAdapter(
            SQLiteDatabaseHelper.instance.getUsers(),
            this
        );

        this.usersRecyclerView = findViewById(R.id.UsersRecyclerView);

        this.usersRecyclerView.setAdapter(UsersAdapter.instance);

        this.usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}