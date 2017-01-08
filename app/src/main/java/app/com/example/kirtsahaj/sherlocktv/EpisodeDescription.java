
package app.com.example.kirtsahaj.sherlocktv;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class EpisodeDescription extends AppCompatActivity {

    int[] images = new int[]{R.drawable.s1e1, R.drawable.s1e2, R.drawable.s1e3, R.drawable.s2e1, R.drawable.s2e2, R.drawable.s2e3, R.drawable.s3e1, R.drawable.s3e2, R.drawable.s3e3, R.drawable.s4e1, R.drawable.s4e2,R.drawable.s4e3};
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_description);

        Intent intent = getIntent();
        int seasonNo = intent.getIntExtra("seasonNo", 0);
        int episodeNo = intent.getIntExtra("episodeNo", 0);

        databaseHelper = new DatabaseHelper(getBaseContext());
        try {
            databaseHelper.createDataBase();
        }catch (IOException e){
            throw new Error("Unable to Create Database");
        }

        try {
            databaseHelper.openDataBase();
        }catch (SQLException sql){
            throw sql;
        }

        ArrayList<String> list = databaseHelper.getEpisode(episodeNo, seasonNo);

        TextView name = (TextView) findViewById(R.id.name);
        TextView description = (TextView) findViewById(R.id.description);

        ImageView image = (ImageView) findViewById(R.id.image);

        int no = (seasonNo-1)*3+episodeNo-1;

        image.setImageResource(images[no]);

        name.setText(list.get(0));
        description.setText(list.get(1));




    }
}
