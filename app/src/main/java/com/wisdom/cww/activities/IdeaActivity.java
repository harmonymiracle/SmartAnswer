package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class IdeaActivity extends AppCompatActivity {

    public ImageButton searchBar;
    public ImageButton pubBar;
    public ImageButton accountBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.idea_title);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent writeIntent = new Intent(IdeaActivity.this, WriteIdeaActivity.class);
                startActivity(writeIntent);



            }
        });

        searchBar = (ImageButton)findViewById(R.id.searchBar);
        searchBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent searchIntent = new Intent(IdeaActivity.this, MainActivity.class);
                        startActivity(searchIntent);
                    }
                }
        );

        pubBar = (ImageButton)findViewById(R.id.pubBar);
        pubBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );

        accountBar = (ImageButton)findViewById(R.id.accountBar);
        accountBar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent accountIntent = new Intent(IdeaActivity.this, AccountActivity.class);

                        startActivity(accountIntent);
                    }
                }
        );


    }



}
