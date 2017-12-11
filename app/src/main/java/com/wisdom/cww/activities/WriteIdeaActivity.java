package com.wisdom.cww.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class WriteIdeaActivity extends AppCompatActivity {

    public int userid;
    public EditText ideaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_idea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.write_idea_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_publish:
                                if (!ideaText.getText().toString().equals("")){
                                    String publishSuccess = "新问题已发布";
                                    Toast.makeText(getApplicationContext(), publishSuccess, Toast.LENGTH_SHORT).show();

                                    Intent ideaIntent = new Intent(WriteIdeaActivity.this, IdeaActivity.class);
                                    Bundle bund = new Bundle();
                                    //bund.putSerializable();
                                    ideaIntent.putExtra("userInfo",bund);
                                    startActivity(ideaIntent);
                                }
                                break;
                        }
                        return true;
                    }
                }
        );

        ideaText = (EditText) findViewById(R.id.ideaText);

        Init();

        Intent intent = getIntent();
        Bundle bund = intent.getBundleExtra("user");
        if (bund != null) {
            //User user = (User) bund.getSerializable("user");
            // userid = user.getUserid();
        }





    }

    private void Init () {
        userid = -1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_write_idea,menu);
        return true;
    }

}
