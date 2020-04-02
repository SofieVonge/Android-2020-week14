package com.example.recycler.storage;

import androidx.annotation.NonNull;

import com.example.recycler.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class NoteStorage {

    public static List<String> list = new ArrayList<>();
    private static FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static MainActivity mainActivity;
    private static DocumentSnapshot lastVisible;
    private static int queryLimit = 5;
    private final static String path = "notes";

    public static void init(MainActivity activity) {
        //start one-time query
        mainActivity = activity;
        getInitialNotes();
    }

    private static void getInitialNotes() {
        list.clear(); //because this is asking for the "fresh" list
        addOneTimeQuery(db.collection(path)
                        .orderBy("head")
                        .limit(queryLimit));
    }

    private static void addOneTimeQuery(Query query) {
        //will only fetch data ONCE
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot snap : task.getResult()) {
                        list.add(snap.get("head").toString());
                    }
                    mainActivity.notifyAdapter();
                    if (task.getResult().size() > 0) {
                        lastVisible = task.getResult().getDocuments().get(task.getResult().size() -1);
                    }
                    if (task.getResult().size() < queryLimit) {
                        mainActivity.setIsLastItemReached(true);
                    }

                }
            }
        });
    }

    public static void getNextNotes() {
        addOneTimeQuery(db.collection(path)
                .orderBy("head")
                .startAfter(lastVisible)
                .limit(queryLimit));
    }
}


