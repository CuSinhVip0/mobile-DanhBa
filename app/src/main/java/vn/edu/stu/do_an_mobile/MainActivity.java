package vn.edu.stu.do_an_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.edu.stu.do_an_mobile.Adapter.DANHSACHLIENLAC;
import vn.edu.stu.do_an_mobile.dao.DBHelper;
import vn.edu.stu.do_an_mobile.model.BANBE;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;
import vn.edu.stu.do_an_mobile.utils.DBConfigUtil;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ImageButton them;
    ListView listView;
    ArrayList<BANBE> arrayList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBConfigUtil.copyDatabaseFromAssets(MainActivity.this);
        dbHelper = new DBHelper(MainActivity.this);
        addComponent();
        arrayList = dbHelper.getAll();
//        for(BANBE x:arrayList){
//            System.out.println(x);
//        }
        registerForContextMenu(listView);
        addData(arrayList);
        addEvent();
        sinhnhat();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;


        if(item.getItemId()==R.id.xoa_updateSocial){
            BANBE banbe = arrayList.get(index);
            dbHelper.delete(banbe);
            arrayList.remove(banbe);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    public void addEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(MainActivity.this, Details.class);
                intent.putExtra("obj",arrayList.get(position));
                startActivityForResult(intent ,123);
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, CreateInfo.class);
                startActivityForResult(intent ,123);
            }
        });
    }
    public void addData(ArrayList<BANBE> arrayList){
        adapter =  new DANHSACHLIENLAC(MainActivity.this, R.layout.custom_item_main,arrayList);
        listView.setAdapter(adapter);
    }

    public void addComponent(){
        listView = findViewById(R.id.listView_main);
        them  = findViewById(R.id.btnThem_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 &&resultCode ==654 ){
            arrayList = dbHelper.getAll();
            addData(arrayList);

        }
    }

    public void sinhnhat() {
        for(BANBE x:arrayList){
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            String date = dateFormat.format(x.getNgaysinh());
            System.out.println(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar birthDate = Calendar.getInstance();

            try {
                birthDate.setTime(sdf.parse(date)); // Chuyển đổi chuỗi sang Calendar
            } catch (ParseException e) {
                e.printStackTrace();
            }

// Lấy năm hiện tại
            Calendar today = Calendar.getInstance();
            int currentYear = today.get(Calendar.YEAR);

// Đặt năm trong birthDate thành năm hiện tại để kiểm tra xem ngày sinh nhật năm nay đã qua chưa
            birthDate.set(Calendar.YEAR, currentYear);
            if (today.after(birthDate)) {
                // Nếu ngày sinh nhật năm nay đã qua, chúng ta thiết lập báo thức cho năm sau
                birthDate.add(Calendar.YEAR, 1);
            }

// Thiết lập AlarmManager như đã mô tả ở trên
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, BirthdayAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

// Thiết lập báo thức
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, birthDate.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY * 365, pendingIntent);
        }
    }

}