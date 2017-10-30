package groep.ch.entertainnet;

import android.content.Intent;
import android.nfc.Tag;
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

public class TagActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CustomAdapter adapter;
    private ListView lv_tagpost;
    private ArrayList<Post> posts = new ArrayList<>();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mPostRef = mRootRef.child("post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            lv_tagpost = (ListView) findViewById(R.id.lv_tagpost);
        } else {
            try {
                Intent k = new Intent(TagActivity.this, StartActivity.class);
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
                    Intent k = new Intent(TagActivity.this, MainActivity.class);
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
                        for (String tag : value.getTags()){
                            if (tag.equals(MainActivity.searchTag)){
                                addPost(value);
                            }
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
            lv_tagpost.setAdapter(adapter);
        }
    }
}
