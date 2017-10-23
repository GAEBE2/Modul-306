package groep.ch.entertainnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by P on 23.10.2017.
 */

public class CreatePostActivity extends AppCompatActivity {
    private EditText titleText;
    private EditText contentText;
    private EditText tagText;
    private TextView allTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        titleText = findViewById(R.id.create_post_title);
        contentText = findViewById(R.id.create_post_content);
        tagText = findViewById(R.id.create_post_tag);
        allTags = findViewById(R.id.all_tags);

        findViewById(R.id.button_create_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hio");
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
                    tagText.setText("");
                }
            }
        });
    }
}
