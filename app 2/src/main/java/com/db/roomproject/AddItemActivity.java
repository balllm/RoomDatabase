package com.db.roomproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.CharArrayWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddItemActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    ItemDao itemDao;
    EditText titleET;
    EditText priceET;
    EditText descET;
    Button btnAddItem;
    Button BSelectImage;

    ImageView IVPreviewImage;
    Button button;

    int SELECT_PICTURE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        titleET = findViewById(R.id.etTitle);
        priceET = findViewById(R.id.etPrice);
        descET = findViewById(R.id.etDesc);

        BSelectImage = findViewById(R.id.SelectImage);
        IVPreviewImage = findViewById(R.id.showImg);

        btnAddItem = findViewById(R.id.AddItem);
        button = findViewById(R.id.taptomain);
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });


        itemDao = Room.databaseBuilder(this,
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
                .fallbackToDestructiveMigration()
                .build().itemDao();
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        Item item = new Item();
                        item.setName(titleET.getText().toString());
                        item.setDescription(descET.getText().toString());
                        item.setPrice(Integer.parseInt(priceET.getText().toString()));
                        item.setImage(IVPreviewImage.getImageAlpha());
                        itemDao.insert(item);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddItemActivity.this, "Added item!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}