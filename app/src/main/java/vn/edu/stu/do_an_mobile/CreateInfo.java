package vn.edu.stu.do_an_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.stu.do_an_mobile.Adapter.ADAPTERMANGXAHOI;
import vn.edu.stu.do_an_mobile.dao.DBHelper;
import vn.edu.stu.do_an_mobile.model.BANBE;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;

public class CreateInfo extends AppCompatActivity {
    TextView title,ho,ten,mail,phone,birth,link;
    DBHelper dbHelper;
    ImageButton back,save,save2,add,sua,cancel,hinh,date;
    ListView listView;
    Spinner spinner;
    LinearLayout layout;
    ImageView hinhchinh;
    BANBE banbe = new BANBE();
    ArrayList<MANGXAHOI> mxh;
    ArrayAdapter adapter;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        dbHelper = new DBHelper(CreateInfo.this);
        addComponent();
        addData();
        addEvent();
    }
    public  void addData(){
        calendar = Calendar.getInstance();
        mxh = new ArrayList<>();
        adapter = new ADAPTERMANGXAHOI(CreateInfo.this,R.layout.custom_item_updatesocial,mxh);
        listView.setAdapter(adapter);


        List<String> list = new ArrayList<>();
        list.add("Facebook");
        list.add("Instagram");
        list.add("Tiktok");
        list.add("Zalo");
        list.add("Youtube");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }


    public void addComponent(){
        hinh= findViewById(R.id.btnHinh_create);
        title= findViewById(R.id.tbxTitle_createInfo);
        ho = findViewById(R.id.tbxHo_createInfo);
        ten = findViewById(R.id.tbxTen_createInfo);
        birth = findViewById(R.id.tbxDate_createInfo);
        mail = findViewById(R.id.tbxEmail_createInfo);
        phone = findViewById(R.id.tbxSdt_createInfo);
        back= findViewById(R.id.btnBack_createInfo);
        save2= findViewById(R.id.btnSave2_createInfo);
        save= findViewById(R.id.btnSave_createInfo);
        link= findViewById(R.id.tbxLink_createInfo);
        listView = findViewById(R.id.listView_createInfo);
        spinner = findViewById(R.id.spinner_createInfo);
        cancel = findViewById(R.id.btnCancel_createInfo);
        add = findViewById(R.id.btnThem_createInfo);
        layout = findViewById(R.id.them_createInfo);
        sua =findViewById(R.id.btnSua_createInfo);
        hinhchinh = findViewById(R.id.imgView_create);
        date = findViewById(R.id.btnDate_createInfo);
    }

    public void addEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banbe.setBietdanh(title.getText().toString());
                banbe.setHo(ho.getText().toString());
                banbe.setTen(ten.getText().toString());
                banbe.setMail(mail.getText().toString());
                banbe.setPhone(phone.getText().toString());

                DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                Date startDate = null;
                try {
                    startDate = df.parse(birth.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                banbe.setNgaysinh(startDate);
                banbe.setMxh(mxh);
                for(MANGXAHOI x:banbe.getMxh()){
                    System.out.println(x);
                }

                dbHelper.insert(banbe);
                Intent intent=  new Intent();
                setResult(654,intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MANGXAHOI mxh2 = new MANGXAHOI();
                mxh2.setTen( spinner.getSelectedItem().toString().toLowerCase());
                mxh2.setLink(link.getText().toString());
                mxh.add(mxh2);

                adapter.notifyDataSetChanged();
                layout.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link.setText("");
                layout.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link.setText("");
                layout.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
            }
        });
        hinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, 123);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                DatePickerDialog.OnDateSetListener listener = new
                        DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int
                                    dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DATE, dayOfMonth);
                                birth.setText(sdf.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateInfo.this,
                        listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );
                datePickerDialog.show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] imageData = outputStream.toByteArray();
            banbe.setPhoto(imageData);
            hinhchinh.setImageBitmap(imageBitmap);
        }
    }



}