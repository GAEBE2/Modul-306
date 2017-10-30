package groep.ch.entertainnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView tv_name;
    private TextView tv_mail;
    private Button btn_logout;
    private FirebaseUser user;
    private CustomAdapter adapter;
    private ListView lv_userpost;
    private ArrayList<Post> posts = new ArrayList<>();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mPostRef = mRootRef.child("post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            tv_name = (TextView) findViewById(R.id.tv_name);
            tv_mail = (TextView) findViewById(R.id.tv_mail);
            btn_logout = (Button) findViewById(R.id.btn_logout);
            lv_userpost = (ListView) findViewById(R.id.lv_userpost);

            tv_name.setText(user.getDisplayName().toString());
            tv_mail.setText(user.getEmail().toString());

            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent k = new Intent(UserActivity.this, StartActivity.class);
                    startActivity(k);
                    finish();
                }
            });
        } else {
            try {
                Intent k = new Intent(UserActivity.this, StartActivity.class);
                startActivity(k);
                finish();
            } catch(Exception e) {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_back:
                try {
                    Intent k = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(k);
                    finish();
                } catch(Exception e) {
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onStart() {
        super.onStart();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Post>> gti = new GenericTypeIndicator<HashMap<String, Post>>() {};
                HashMap<String, Post> postHashMap = dataSnapshot.getValue(gti);
                if (postHashMap != null && posts.size() == 0) {
                    Iterator postIterator = postHashMap.keySet().iterator();
                    while (postIterator.hasNext()) {
                        String key = (String) postIterator.next();
                        Post value = (Post) postHashMap.get(key);
                        if (value.getUserid().equals(user.getUid().toString())){
                            addPost(value);
                        }
                    }
                    showPost();
                }else if (postHashMap != null){
                    posts.clear();
                    Iterator postIterator = postHashMap.keySet().iterator();
                    while (postIterator.hasNext()) {
                        String key = (String) postIterator.next();
                        Post value = (Post) postHashMap.get(key);
                        if (value.getUserid().equals(user.getUid().toString())){
                            addPost(value);
                        }
                    }
                    showPost();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };

        mPostRef.addValueEventListener(postListener);
    }

    private void addPost(Post post){posts.add(post);
    }

    private void showPost(){
        if (posts.size() != 0) {
            Post[] os = new Post[]{};
            os = posts.toArray(os);

            adapter = new
                    CustomAdapter(this, os);
            lv_userpost.setAdapter(adapter);
        }
    }
}
