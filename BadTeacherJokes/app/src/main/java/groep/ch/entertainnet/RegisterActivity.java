package groep.ch.entertainnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    insertUser();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Passwords don't match", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    protected void onResume(@Nullable Bundle savedInstanceState){
        FirebaseUser currUser = mAuth.getCurrentUser();
        if (currUser != null){
            updateUI();
        }
    }

    private void updateUI(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        RegisterActivity.this.startActivity(intent);
        finish();
    }

    private void insertUser(){
        String  email = ((EditText) findViewById(R.id.email)).getText().toString(),
                password = ((EditText) findViewById(R.id.password)).getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(((EditText) findViewById(R.id.userName)).getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                updateUI();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm(){
        String  password = ((EditText) findViewById(R.id.password)).getText().toString(),
                pwRepeat = ((EditText) findViewById(R.id.password_repeat)).getText().toString();

        if (password.equals(pwRepeat) && !(password.equals(""))){
            return true;
        } else {
            return false;
        }

    }
}
