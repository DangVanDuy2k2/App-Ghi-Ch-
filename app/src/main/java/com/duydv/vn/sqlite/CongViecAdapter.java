package com.duydv.vn.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> listCongViec;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> listCongViec) {
        this.context = context;
        this.layout = layout;
        this.listCongViec = listCongViec;
    }

    @Override
    public int getCount() {
        return listCongViec.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        TextView txtCongViec;
        ImageButton btnDelete,btnEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtCongViec = view.findViewById(R.id.txtCongViec);
            holder.btnDelete = view.findViewById(R.id.btnDelete);
            holder.btnEdit = view.findViewById(R.id.btnEdit);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final CongViec congViec = listCongViec.get(i);
        holder.txtCongViec.setText(congViec.getTenCV());

        //bắt sự kiện cho nút delete và nút edit

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogCapNhat(congViec.getId(),congViec.getTenCV());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoaCV(congViec.getId(),congViec.getTenCV());
            }
        });

        return view;
    }
}
