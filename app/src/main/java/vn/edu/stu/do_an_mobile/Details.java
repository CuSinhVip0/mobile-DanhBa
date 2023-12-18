package vn.edu.stu.do_an_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import vn.edu.stu.do_an_mobile.dao.DBHelper;
import vn.edu.stu.do_an_mobile.model.BANBE;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;

public class Details extends AppCompatActivity {
    TextView title,ho,ten,mail,phone,birth;
    ImageButton fb,titok,ig,zalo,youtube,back,menu_btn;
    BANBE data;
    DBHelper dbHelper;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        data = (BANBE) getIntent().getSerializableExtra("obj");
        System.out.println(data);
        dbHelper = new DBHelper(Details.this);
        addComponent();
        addData(data);
        addEvent();
    }

    public void addEvent(){
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Details.this, menu_btn);

                popupMenu.getMenuInflater().inflate(R.menu.menu_details, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.sua_menu){
                            Intent intent= new Intent(Details.this, UpdateInfo.class);
                            intent.putExtra("obj",data);
                            startActivityForResult(intent,123);
                        }
                        else if(menuItem.getItemId()==R.id.sua2_menu){
                            Intent intent= new Intent(Details.this, UpdateSocial.class);
                            intent.putExtra("obj",data);
                            startActivityForResult(intent,123);
                        }

                        else if(menuItem.getItemId()==R.id.hinh_menu){
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            try {
                                startActivityForResult(takePictureIntent, 123);
                            } catch (ActivityNotFoundException e) {
                                // display error state to the user
                            }
                        }


                        else if(menuItem.getItemId()==R.id.xoa_menu){

                            AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
                            builder.setTitle("Xác nhận xóa thông tin ");
                            builder.setMessage("Bạn có chắc là muốn xóa thông tin liên lạc?");
                            builder.setIcon(R.drawable.jake);

                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    dbHelper.delete(data);
                                    Intent intent =  new Intent();
                                    setResult(654,intent);
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
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent();
                setResult(654,intent);
                finish();
            }
        });
    }


    public void addData(BANBE banbe){
        fb.setVisibility(View.GONE);
        ig.setVisibility(View.GONE);
        zalo.setVisibility(View.GONE);
        titok.setVisibility(View.GONE);
        youtube.setVisibility(View.GONE);
        title.setText(banbe.bietdanh);
        ho.setText(banbe.ho);
        ten.setText(banbe.ten);
        mail.setText(banbe.mail);
        phone.setText(banbe.phone);
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String strDate = dateFormat.format(banbe.ngaysinh);
        birth.setText(strDate);
        if(banbe.getPhoto()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(banbe.getPhoto(), 0, banbe.getPhoto().length);
            imageView.setImageBitmap(bitmap);
        }
        //xử lý các mạng xã hội
        for(MANGXAHOI x : banbe.mxh){

            if(x.getTen().equals("facebook")){
                fb.setVisibility(View.VISIBLE);
                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(x.getLink()));
                        startActivity(intent);
                    }
                });
            }
            else if(x.getTen().equals("instagram")){
                ig.setVisibility(View.VISIBLE);
                ig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(x.getLink()));
                        startActivity(intent);
                    }
                });
            }
            else if(x.getTen().equals("zalo")){
                zalo.setVisibility(View.VISIBLE);
                zalo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(x.getLink()));
                        startActivity(intent);
                    }
                });
            }
            else if(x.getTen().equals("tiktok")){
                titok.setVisibility(View.VISIBLE);
                titok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(x.getLink()));
                        startActivity(intent);
                    }
                });
            }
            else if(x.getTen().equals("youtube")){
                youtube.setVisibility(View.VISIBLE);
                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(x.getLink()));
                        startActivity(intent);
                    }
                });
            }
        }

    }
    public void addComponent(){
        title= findViewById(R.id.tbxTitle_detail);
        ho = findViewById(R.id.tbxHo_details);
        ten = findViewById(R.id.tbxTen_details);
        birth = findViewById(R.id.tbxDate_details);
        mail = findViewById(R.id.tbxEmail_details);
        phone = findViewById(R.id.tbxSdt_details);
        fb = findViewById(R.id.btnFb_details);
        ig = findViewById(R.id.btnIg_details);
        zalo = findViewById(R.id.btnZalo_details);
        titok = findViewById(R.id.btnTiktok_details);
        youtube = findViewById(R.id.btnYoutube_details);
        back = findViewById(R.id.btnBack_details);
        menu_btn = findViewById(R.id.btnMenu_details);
        imageView= findViewById(R.id.imgView_details);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode ==456){
            this.data = dbHelper.getOne(this.data.getMa());
            addData(this.data);
        }
        else if(requestCode==123 && resultCode ==789){
           this.data = dbHelper.getOne(this.data.getMa());
            addData(this.data);
        }

        else if (requestCode == 123 && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] imageData = outputStream.toByteArray();
                this.data.setPhoto(imageData);
                dbHelper.update(this.data);
                imageView.setImageBitmap(imageBitmap);
            }
    }
}