package groep.ch.entertainnet;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by marcvollenweider on 02.10.17.
 */

public class CustomAdapter extends ArrayAdapter<Post> {
    private final Activity context;
    private final Post[] posts;

    public CustomAdapter(Activity context,
                         Post[] posts) {
        super(context, R.layout.list_item, posts);
        this.context = context;
        this.posts = posts;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item, null, true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView tv_title = (TextView) rowView.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) rowView.findViewById(R.id.tv_content);
        LinearLayout space_tags = (LinearLayout) rowView.findViewById(R.id.space_tags);

        Drawable d = ContextCompat.getDrawable(context, R.drawable.text_roundcorner);

        tv_title.setText(posts[position].getTitle());
        tv_content.setText(posts[position].getContent());
        for (String tag : posts[position].getTags()){
            TextView tv_tag = new TextView(getContext());
            tv_tag.setBackground(d);
            tv_tag.setTextColor(ContextCompat.getColor(context, R.color.textColor1));
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 0, 20, 0); // llp.setMargins(left, top, right, bottom);
            tv_tag.setLayoutParams(llp);
            tv_tag.setPadding(5, 0, 5, 0);
            space_tags.addView(tv_tag);
            tv_tag.setText(tag);
        }

        return rowView;
    }
}
