package sg.edu.np.mad.madpractical;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    private AlertDialog alertDialog = null;

    private Context context = null;

    public static UsersAdapter instance = null;

    public ArrayList<User> users = null;

    public int currentSelectedUserIndex = -1;

    public UsersAdapter(ArrayList<User> userArrayList, Context context)
    {
        this.users = userArrayList;

        this.context = context;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setNegativeButton(
            "CLOSE",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UsersAdapter.this.alertDialog.hide();
                }
            }
        );

        alertDialogBuilder.setPositiveButton(
            "VIEW",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(UsersAdapter.this.context, MainActivity.class);

                    UsersAdapter.this.context.startActivity(intent);
                }
            }
        );

        alertDialogBuilder.setTitle(
                "Profile"
        );

        this.alertDialog = alertDialogBuilder.create();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                R.layout.users_recyclerview_element,
                parent,
                false
            )
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = this.users.get(position);

        holder.userElementNameTextView.setText(user.name);

        holder.userElementDescriptionTextView.setText(user.description);

        if (user.name.charAt(user.name.length() - 1) == '7')
        {
            holder.userElementLargeImageView.setVisibility(
                View.VISIBLE
            );
        }
        else
        {
            holder.userElementLargeImageView.setVisibility(
                View.GONE
            );
        }
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView userElementImageView = null;

        public ImageView userElementLargeImageView = null;

        public TextView userElementNameTextView = null;

        public TextView userElementDescriptionTextView = null;

        public ViewHolder(View itemView)
        {
            super(itemView);

            this.userElementImageView = itemView.findViewById(
                R.id.UserElementImageView
            );

            this.userElementImageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UsersAdapter.this.currentSelectedUserIndex = ViewHolder.this.getAdapterPosition();

                        UsersAdapter.this.alertDialog.setMessage(
                            ViewHolder.this.userElementNameTextView.getText()
                        );

                        UsersAdapter.this.alertDialog.show();
                    }
                }
            );

            this.userElementLargeImageView = itemView.findViewById(
                R.id.UserElementLargeImageView
            );

            this.userElementNameTextView = itemView.findViewById(
                R.id.UserElementNameTextView
            );

            this.userElementDescriptionTextView = itemView.findViewById(
                R.id.UserElementDescriptionTextView
            );
        }
    }
}
