package vn.edu.stu.do_an_mobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import vn.edu.stu.do_an_mobile.R;
import vn.edu.stu.do_an_mobile.model.BANBE;

public class DANHSACHLIENLAC extends ArrayAdapter<BANBE> {

    Activity context;
    ArrayList<BANBE> arrayList;
    int resource;


    public DANHSACHLIENLAC(@NonNull Activity context, int resource, ArrayList<BANBE> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        byte[] hinh = arrayList.get(position).getPhoto();

        ImageView imageView = item.findViewById(R.id.imgView_main);
        if(hinh!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
            imageView.setImageBitmap(bitmap);
        }
        TextView t = item.findViewById(R.id.tbx_ma_custom);
        t.setText(arrayList.get(position).getTen());
        return  item;
    }
}
