package iumw.fyp.alertdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
{
    private Button showDialogButton;
    private CharSequence[] nAlertItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nAlertItems = new CharSequence[]
                {
                    "Movies", "Photos", "Music", "All"
                };

        showDialogButton = (Button) findViewById(R.id.showDialogButton);

        showDialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final MaterialAlertDialogBuilder builder =
                        new MaterialAlertDialogBuilder(MainActivity.this);

                builder.setTitle("View Options");
                //builder.setMessage("This s a simple alert dialog");

                builder.setSingleChoiceItems(nAlertItems, 0, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Snackbar.make(showDialogButton, "You have selected : " + nAlertItems[which],
                                Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

                builder.setIcon(R.drawable.ic_cloud_upload_black_24dp);
                builder.setBackground(getResources().getDrawable(R.drawable.alert_dialog_bg, null));

                builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // do something here
                    }
                });

                builder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // do something here
                        return;
                    }
                });

                builder.show();

            }
        });
    }
}
