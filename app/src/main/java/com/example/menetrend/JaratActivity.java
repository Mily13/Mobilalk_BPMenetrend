package com.example.menetrend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class JaratActivity extends AppCompatActivity {
    private static final String LOG_TAG = JaratActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<ShopingItem> mItemsData;
    private ShopingItemAdapter mAdapter;
    private int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jarat);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridNumber));
        mItemsData = new ArrayList<>();
        mAdapter = new ShopingItemAdapter(this, mItemsData);
        mRecyclerView.setAdapter(mAdapter);


        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Jaratok");

        queryData();

        mNotificationHandler = new NotificationHandler(this);
    }

    private void queryData() { //READ DATA FROM DB
        mItemsData.clear();
        mItems.limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ShopingItem item = document.toObject(ShopingItem.class);
                item.setId(document.getId());
                mItemsData.add(item);
            }

            if (mItemsData.size() == 0) {
                initializeData();
                queryData();
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData() {  //CREATE (INSERT) TO DB
        String[] itemsList = getResources().getStringArray(R.array.shopping_item_jaratszam);
        String[] itemsInfo = getResources().getStringArray(R.array.shopping_item_megallok);
        String[] itemsPrice = getResources().getStringArray(R.array.shopping_item_indulasok);

        for (int i = 0; i < itemsList.length; i++) {
            mItems.add(new ShopingItem(itemsList[i], itemsInfo[i], itemsPrice[i]));
        }
    }

    public void deleteItem(ShopingItem item){ //DELETE DATA FROM DB
        DocumentReference ref = mItems.document(item._getId());

        ref.delete().addOnSuccessListener(success ->{
            Log.d(LOG_TAG, "Item deleted successfully, deleted: " + item._getId());
        })
        .addOnFailureListener(faliure ->{
            Toast.makeText(this, "Item " + item._getId() + " cannot be deleted.", Toast.LENGTH_SHORT).show();
        });

        mNotificationHandler.send("Route " + item.getJaratszam() + " has been deleted.");

        queryData();
    }

}