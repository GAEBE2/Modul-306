package groep.ch.entertainnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by P on 23.10.2017.
 */

public class CreatePostActivity extends AppCompatActivity {
    private EditText titleText;
    private EditText contentText;
    private EditText tagText;
    private TextView allTags;
    private ArrayList<String> tags = new ArrayList<>();
    private FirebaseUser user;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("post");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        titleText = findViewById(R.id.create_post_title);
        contentText = findViewById(R.id.create_post_content);
        tagText = findViewById(R.id.create_post_tag);
        allTags = findViewById(R.id.all_tags);

        findViewById(R.id.button_create_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });

        findViewById(R.id.button_add_tag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = tagText.getText().toString();
                if(!text.equals("")) {
                    String tagsText = allTags.getText().toString();
                    tagsText += "#" + text + " ";
                    allTags.setText(tagsText);
                    tags.add(tagText.getText().toString());
                    tagText.setText("");
                }
            }
        });
    }

    public void createPost(){
        if (!titleText.getText().toString().equals("") && !contentText.getText().toString().equals("") && tags.size() != 0){
            Post post = new Post(titleText.getText().toString(), contentText.getText().toString(), user.getUid().toString(), tags);
            myRef.child(post.getUid().toString()).setValue(post);
            try {
                Intent k = new Intent(CreatePostActivity.this, MainActivity.class);
                startActivity(k);
                finish();
            } catch(Exception e) {
            }
        }else{
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
