package app.com.example.kirtsahaj.sherlocktv;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import java.io.IOException;


public class SeasonsList extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_list);






        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(SeasonsList.this, EpisodeDescription.class);
                intent.putExtra("episodeNo", childPosition+1);
                intent.putExtra("seasonNo", groupPosition+1);
                startActivity(intent);
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Season 1");
        listDataHeader.add("Season 2");
        listDataHeader.add("Season 3");
        listDataHeader.add("Season 4");

        // Adding child data
        List<String> season1 = new ArrayList<String>();
        season1.add("Episode 1");
        season1.add("Episode 2");
        season1.add("Episode 3");

        List<String> season2 = new ArrayList<String>();
        season2.add("Episode 1");
        season2.add("Episode 2");
        season2.add("Episode 3");

        List<String> season3 = new ArrayList<String>();
        season3.add("Episode 1");
        season3.add("Episode 2");
        season3.add("Episode 3");

        List<String> season4 = new ArrayList<String>();
        season4.add("Episode 1");
        season4.add("Episode 2");
        season4.add("Episode 3");

        listDataChild.put(listDataHeader.get(0), season1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), season2);
        listDataChild.put(listDataHeader.get(2), season3);
        listDataChild.put(listDataHeader.get(3), season4);
    }
}
