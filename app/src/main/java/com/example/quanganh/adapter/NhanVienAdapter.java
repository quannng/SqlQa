package com.example.quanganh.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanganh.model.NhanVien;
import com.example.quanganh.project.Database;
import com.example.quanganh.project.R;
import com.example.quanganh.activity.UpdateActivity;

import java.util.ArrayList;

public class NhanVienAdapter extends BaseAdapter {
    Activity context;
    ArrayList<NhanVien> list;

    public NhanVienAdapter(Activity context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_row, null);
        ImageView imgHinhDaiDien = row.findViewById(R.id.imgHinhDaiDien);
        TextView txtId = row.findViewById(R.id.txtId);
        TextView txtTen = row.findViewById(R.id.txtTen);
        TextView txtSdt = row.findViewById(R.id.txtSdt);
        Button btnXoa = row.findViewById(R.id.btnXoa);
        Button btnSua = row.findViewById(R.id.btnSua);

        final NhanVien nhanVien = list.get(position);
        txtId.setText(nhanVien.id + "");
        txtTen.setText(nhanVien.ten);
        txtSdt.setText(nhanVien.sdt);

        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(nhanVien.anh, 0, nhanVien.anh.length);
        imgHinhDaiDien.setImageBitmap(bmHinhDaiDien);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("ID", nhanVien.id);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(nhanVien.id);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(int idNhanVien) {
        SQLiteDatabase database = Database.initDatabase(context,"EmployeeDB.sqlite");
        database.delete("NhanVien", "ID = ?", new String[]{idNhanVien + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            list.add(new NhanVien(id, ten, sdt, anh));
        }
        notifyDataSetChanged();
    }
}
