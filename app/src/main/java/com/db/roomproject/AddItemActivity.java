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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddItemActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ItemDao itemDao;
    private EditText titleET;
    private EditText priceET;
    private EditText descET;
    private Button btnAddItem;
    private Button BSelectImage;

    private ImageView IVPreviewImage;
    private Button button;
    private Button deletebtn;

    private static final int SELECT_PICTURE = 200;
    private Uri selectedImageUri;

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
        itemDao = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, DbConfig.ROOM_DB_NAME)
//                .fallbackToDestructiveMigration()
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
                        // добавление через Uri и вывод с помошью Glide в ItemRecyclerView
                        item.setImage(selectedImageUri.toString());
                        itemDao.insert(item);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddItemActivity.this, "Добавлен товар!", Toast.LENGTH_SHORT).show();
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
        deletebtn = findViewById(R.id.taptodelete);
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddItemActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });
    }

    // Метод для выбора изображения из галереи
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Выберите изображение"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
