package groep.ch.entertainnet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
            fab_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent k = new Intent(MainActivity.this, CreatePostActivity.class);
                        startActivity(k);
                    } catch(Exception e) {
                    }
                }
            });
        } else {
            try {
                Intent k = new Intent(MainActivity.this, StartActivity.class);
                startActivity(k);
                finish();
            } catch(Exception e) {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_acc:
                try {
                    Intent k = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(k);
                } catch(Exception e) {
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
