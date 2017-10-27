package groep.ch.entertainnet;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by marcvollenweider on 27.10.17.
 */

public class Post {
    public String uid;
    private String title;
    private String content;
    private String userid;
    private ArrayList<String> tags;

    public Post(String title, String content, String userid, ArrayList<String> tags){
        uid = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.userid = userid;
        this.tags = tags;
    }

    public Post(){};

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
