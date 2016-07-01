package me.drozdzynski.library.sample.steppers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));
                MainActivity.this.finish();
            }
        });

        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));
                MainActivity.this.finish();
            }
        });

        steppersViewConfig.setFragmentManager(getSupportFragmentManager());
        ArrayList<SteppersItem> steps = new ArrayList<>();

        int i = 0;
        while (i <= 10) {

            final SteppersItem item = new SteppersItem();
            item.setLabel("Step nr " + i);
            item.setPositiveButtonEnable(i % 2 != 0);

            if(i % 2 == 0) {
                BlankFragment blankFragment = new BlankFragment();
                blankFragment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setPositiveButtonEnable(true);
                    }
                });
                item.setSubLabel("Fragment: " + blankFragment.getClass().getSimpleName());
                item.setFragment(blankFragment);
            } else {
                BlankSecondFragment blankSecondFragment = new BlankSecondFragment();
                item.setSubLabel("Fragment: " + blankSecondFragment.getClass().getSimpleName());
                item.setFragment(blankSecondFragment);
            }

            steps.add(item);
            i++;
        }

        SteppersView steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();
        View v = null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
