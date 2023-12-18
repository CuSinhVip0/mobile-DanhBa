package vn.edu.stu.do_an_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.edu.stu.do_an_mobile.dao.DBHelper;
import vn.edu.stu.do_an_mobile.model.BANBE;

public class UpdateInfo extends AppCompatActivity {
    TextView title,ho,ten,mail,phone,birth;
    ImageButton back,save,date;
    Calendar calendar;
    BANBE banbe;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        dbHelper = new DBHelper(UpdateInfo.this);
        banbe = (BANBE) getIntent().getSerializableExtra("obj");
        addComponent();
        addData();
        addEvent();
    }
    public void addComponent(){
        title= findViewById(R.id.tbxTitle_update_info);
        ho = findViewById(R.id.tbxHo_update_info);
        ten = findViewById(R.id.tbxTen_update_info);
        birth = findViewById(R.id.tbxDate_update_info);
        mail = findViewById(R.id.tbxEmail_update_info);
        phone = findViewById(R.id.tbxSdt_update_info);
        back= findViewById(R.id.btnBack_update_info);
        save= findViewById(R.id.btnSave2_update_info);
        date = findViewById(R.id.btnDate_updateInfo);
    }
    public  void addData(){
        calendar = Calendar.getInstance();
        title.setText(banbe.bietdanh);
        ho.setText(banbe.ho);
        ten.setText(banbe.ten);
        mail.setText(banbe.mail);
        phone.setText(banbe.phone);
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(banbe.ngaysinh);
        birth.setText(strDate);
    }

    public void addEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfo.this);
                builder.setTitle("Xác nhận bỏ chỉnh sửa");
                builder.setMessage("Bạn có chắc là không chỉnh sửa nữa không?");
                builder.setIcon(R.drawable.jake);

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfo.this);
                builder.setTitle("Xác nhận thay đổi sau chỉnh sửa");
                builder.setMessage("Bạn có chắc là muốn thay đổi thông tin?");
                builder.setIcon(R.drawable.jake);

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                        Date startDate = null;
                        try {
                            startDate = df.parse(birth.getText().toString());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        BANBE tam = new BANBE(banbe.getMa(),title.getText().toString(),ho.getText().toString(),ten.getText().toString(),mail.getText().toString(),startDate,phone.getText().toString(),banbe.mxh,null);
                        dbHelper.update(tam);
                        Intent intent  = new Intent();
                        setResult(456,intent);

                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
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
                        UpdateInfo.this,
                        listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );
                datePickerDialog.show();
            }
        });
    }

}