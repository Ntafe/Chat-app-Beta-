package com.unipi.ntaf.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cenkgun.chatbar.ChatBarView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView simpleList;
    List<String> countryList = new ArrayList<String>();
    List<String> user = new ArrayList<String>();
    List<Timestamp> timestampList = new ArrayList<>();
    ChatBarView chatBarView;
    SharedPreferences preferences;
    EditText editText;
    Map<String, Object> data_add = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences result = getSharedPreferences("OtherUser", Context.MODE_PRIVATE);
        final String otherUser = result.getString("otherUser","Data Not Found");
        simpleList = findViewById(R.id.simpleListView);
        FirebaseUser userLog = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = userLog.getUid();
        final String chat = "chat_"+(uid<otherUser ? uid+'_'+otherUser : otherUser+'_'+uid);

        chatBarView = findViewById(R.id.chatBar);
        editText = findViewById(R.id.messageEditText);

        try {
            if (uid != null) {
                chatBarView.setMessageBoxHint("Enter your message");
                chatBarView.setSendClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long millis = new Date().getTime();
                        Timestamp tsTemp = new Timestamp(millis);
                        user.add(uid);
                        data_add.put("id", uid);
                        timestampList.add(tsTemp);
                        data_add.put("text", chatBarView.getMessageText());
                        data_add.put("time", tsTemp);
                        data_add.put("other_id",otherUser);
                        countryList.add(chatBarView.getMessageText());
                        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, user, timestampList, uid);
                        simpleList.setAdapter(customAdapter);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(chat)
                                .add(data_add)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                        editText.setText("");
                    }
                });


            }
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(chat).orderBy("time").whereEqualTo("other_id",uid)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w(TAG, "Listen failed.", e);
                                return;
                            }
                            List<String> countryList = new ArrayList<String>();
                            List<String> user = new ArrayList<String>();
                            for (QueryDocumentSnapshot doc : value) {
                                if (doc.get("text") != null) {
                                    user.add(doc.getString("id"));
                                    countryList.add(doc.getString("text"));
                                    CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, user, timestampList, uid);
                                    simpleList.setAdapter(customAdapter);
                                }
                            }
                        }
                    });
        }
        catch (Exception e) {
            Toast.makeText(MainActivity.this, "Something go wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
