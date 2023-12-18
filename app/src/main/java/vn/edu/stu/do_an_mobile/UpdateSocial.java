package vn.edu.stu.do_an_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.do_an_mobile.Adapter.ADAPTERMANGXAHOI;
import vn.edu.stu.do_an_mobile.dao.DBHelper;
import vn.edu.stu.do_an_mobile.model.BANBE;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;

public class UpdateSocial extends AppCompatActivity {
    ImageButton back,save,add,sua,cancel;
    TextView title,link;
    DBHelper dbHelper;
    ListView listView;
    ArrayList<MANGXAHOI> arrayList;
    ArrayAdapter adapter;
    Spinner spinner;
    BANBE banbe;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_social);
        dbHelper = new DBHelper(UpdateSocial.this);
        banbe= (BANBE) getIntent().getSerializableExtra("obj");
        arrayList = banbe.getMxh();
        addComponent();
        addData();
        adapter =  new ADAPTERMANGXAHOI(UpdateSocial.this, R.layout.custom_item_updatesocial,arrayList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        addEvent();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if(item.getItemId()==R.id.sua_updateSocial){
            layout.setVisibility(View.VISIBLE);
            add.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            sua.setVisibility(View.VISIBLE);
            MANGXAHOI mxh = arrayList.get(index);
            if(mxh.getTen().equals("facebook")) spinner.setSelection(0);
            if(mxh.getTen().equals("instagram")) spinner.setSelection(1);
            if(mxh.getTen().equals("tiktok")) spinner.setSelection(2);
            if(mxh.getTen().equals("zalo")) spinner.setSelection(3);
            if(mxh.getTen().equals("Youtube")) spinner.setSelection(4);
            link.setText(mxh.getLink());
            sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MANGXAHOI mxh = new MANGXAHOI();
                    mxh.setTen( spinner.getSelectedItem().toString().toLowerCase());
                    mxh.setLink(link.getText().toString());

                    dbHelper.updatesoial(banbe.getMa(),mxh);
                    arrayList.set(index,mxh);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        else if(item.getItemId()==R.id.xoa_updateSocial){
            MANGXAHOI mxh = arrayList.get(index);
            dbHelper.deletesocial(banbe.getMa(),mxh);
            arrayList.remove(mxh);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    public  void addData(){
        title.setText(banbe.getBietdanh());
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

    public void addEvent(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateSocial.this);
//                builder.setTitle("Xác nhận bỏ chỉnh sửa");
//                builder.setMessage("Bạn có chắc là không chỉnh sửa nữa không?");
//                builder.setIcon(R.drawable.jake);
//
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                });
//                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
                Intent intent= new Intent();
                setResult(789,intent);
                finish();
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
                layout.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MANGXAHOI mxh = new MANGXAHOI();
                mxh.setTen( spinner.getSelectedItem().toString().toLowerCase());
                mxh.setLink(link.getText().toString());
                dbHelper.insertsocial(banbe.getMa(),mxh);
                adapter.add(mxh);
                adapter.notifyDataSetChanged();
                layout.setVisibility(View.GONE);
                add.setVisibility(View.VISIBLE);
            }
        });

    }

    public void addComponent(){
        title= findViewById(R.id.tbxTitle_update_social);
        link= findViewById(R.id.tbxLink_update_social);
        listView = findViewById(R.id.listView_update_social);
        spinner = findViewById(R.id.spinner_update_social);
        back =findViewById(R.id.btnBack_update_social);
        save= findViewById(R.id.btnSave_update_social);
        cancel = findViewById(R.id.btnCancel_update_social);
        add = findViewById(R.id.btnThem_update_social);
        layout = findViewById(R.id.them_update_social);
        sua =findViewById(R.id.btnSua_update_social);
    }
}