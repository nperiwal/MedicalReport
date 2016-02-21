package medical_report.narayan.com.medicalreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class GenerateReportActivity extends AppCompatActivity {

    private ImageView imageView1;
    private String imageUri1;
    private Button saveButton;
    private EditText recordName;
    private EditText date;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        Intent intent = getIntent();
        ArrayList<String> arrayList = getIntent().getStringArrayListExtra("reportKey");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recordName = (EditText)findViewById(R.id.record_name);
        date = (EditText)findViewById(R.id.date);
        description = (EditText)findViewById(R.id.description);


        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openImage1();
            }
        });

        saveButton = (Button)findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report report = new Report();
                if (recordName.getText() != null) {
                    report.recordName = recordName.getText().toString();
                }
                if (date.getText() != null) {
                    report.date = date.getText().toString();
                }
                if (description.getText() != null) {
                    report.description = description.getText().toString();
                }
                if (imageUri1 != null) {
                    report.url = imageUri1;
                }
                addReporttoList(report);
            }
        });

        if (arrayList != null) {
            if (arrayList.get(0) != null) {
                recordName.setText(arrayList.get(0));
            }
            if (arrayList.get(1) != null) {
                date.setText(arrayList.get(1));
            }
            if (arrayList.get(2) != null) {
                description.setText(arrayList.get(2));
            }
            if (arrayList.get(3) != null) {
                Uri selectedImage = Uri.parse(arrayList.get(3));
                System.out.println("ImageUri: " + selectedImage);
                imageView1.setImageURI(selectedImage);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addReporttoList(Report report) {
        ViewReportActivity.listItems.add(report);
    }

    public void openImage1() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(imageUri1), "image*//*");
        startActivity(intent);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(GenerateReportActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    imageUri1 = Uri.fromFile(f).toString();
                    System.out.println("ImageUri: " + imageUri1);
                    //pic = f;
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                if (f.listFiles()!=null) {
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            //pic = photo;
                            break;
                        }
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    //f.delete();
                    imageView1.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    imageUri1 = selectedImage.toString();
                    System.out.println("ImageUri: " + imageUri1);
                    imageView1.setImageURI(selectedImage);
                }
            }
        }
    }
}
