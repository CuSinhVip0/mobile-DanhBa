package vn.edu.stu.do_an_mobile.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import vn.edu.stu.do_an_mobile.R;
import vn.edu.stu.do_an_mobile.model.MANGXAHOI;

public class ADAPTERMANGXAHOI extends ArrayAdapter<MANGXAHOI> {
    Activity context;
    ArrayList<MANGXAHOI> arrayList;
    int resource;


    public ADAPTERMANGXAHOI(@NonNull Activity context, int resource, ArrayList<MANGXAHOI> arrayList) {
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
        MANGXAHOI mxh = arrayList.get(position);
        TextView t = item.findViewById(R.id.tbxLink_updateSocial_item);
        ImageView v = item.findViewById(R.id.imgLink_updateSocial_item);
        t.setText(mxh.getLink());

        if(mxh.getTen().equals("facebook")){
            v.setImageResource(R.drawable.icons8_facebook);
        }
        else if(mxh.getTen().equals("tiktok")){
            v.setImageResource(R.drawable.icons8_tiktok);
        }else if(mxh.getTen().equals("instagram")){
            v.setImageResource(R.drawable.icons8_ig);
        } else if(mxh.getTen().equals("youtube")){
            v.setImageResource(R.drawable.icons8_youtube);
        }
        else if(mxh.getTen().equals("zalo")){
            v.setImageResource(R.drawable.zalo);
        }



        return  item;
    }
}
