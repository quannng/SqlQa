package com.example.quanganh.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.quanganh.activity.AddNhanVienActivity;
import com.example.quanganh.activity.HomeActivity;
import com.example.quanganh.adapter.NhanVienAdapter;
import com.example.quanganh.model.NhanVien;
import com.example.quanganh.project.Database;
import com.example.quanganh.project.R;

import java.util.ArrayList;

public class AddNhanVienFragment extends BaseFragment {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    private SQLiteDatabase database;

    private ListView listView;
    private ArrayList<NhanVien> list;
    private NhanVienAdapter adapter;
    private Button btnAdd;



    @Override
    protected void initview(View view) {
        btnAdd = view.findViewById(R.id.btnAdd);
        listView = view.findViewById(R.id.listView);
        addControls();
        readData();
    }



    @Override
    protected int getLayoutID() {
        return R.layout.fragment_add_nhanvien;
    }



    private void addControls() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNhanVienActivity.class);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        adapter = new NhanVienAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }



    private void readData() {
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new NhanVien(id, ten, sdt, anh));
        }
        adapter.notifyDataSetChanged();
    }
}
