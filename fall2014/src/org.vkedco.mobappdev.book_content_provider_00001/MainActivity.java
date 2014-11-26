package org.vkedco.mobappdev.book_content_provider_00001;

/**
 ****************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 ****************************************************
 */

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CPBookDbAdptr.createBookInfoDbOpenHelper(this);
        SQLiteDatabase writeDb = CPBookDbAdptr.getWriteDb();
        CPBookDbPopulator.populateBookInfoDb(this, writeDb);
        writeDb.close();
        writeDb = null;
        TextView tv = (TextView) findViewById(R.id.tvMsg);
        tv.setText(getResources().getString(R.string.db_populated_msg));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
