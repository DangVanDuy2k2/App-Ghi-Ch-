package com.duydv.vn.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    private ListView lvCongViec;
    private CongViecAdapter adapter;
    private ArrayList<CongViec> listCongViec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = findViewById(R.id.lvCongViec);
        listCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(
                this,
                R.layout.item,
                listCongViec
        );
        lvCongViec.setAdapter(adapter);

        //tạo database
        database = new Database(this,"ghichu.sqlite",null,1);

        //tạo bảng CongViec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec (ID INTEGER PRIMARY KEY AUTOINCREMENT,TenCV VARCHAR(200))");

        //insert data
        //database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập Android')");
        //database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập Java')");

        getData();

    }

    public void getData(){
        //select data
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        listCongViec.clear();
        while(dataCongViec.moveToNext()){
            int id = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            listCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }

    //hàm tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //hàm bắt sự kiện cho menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    //hàm hiển thị dialog them cong viec
    public void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText txtCongViec = dialog.findViewById(R.id.txtThemCongViec);
        Button btnThem = dialog.findViewById(R.id.btnThem);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenCongViec = txtCongViec.getText().toString();

                if(tenCongViec.equals("")){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show();
                }else{
                    //insert data
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+tenCongViec+"')");
                    Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getData();
                }
            }
        });
        dialog.show();
    }

    //hiển thị dialog cập nhât
    public void DialogCapNhat(int id, String tenCV){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cap_nhat_cong_viec);

        EditText txtCapNhat = dialog.findViewById(R.id.txtCapNhat);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = dialog.findViewById(R.id.btnHuyCapNhat);

        txtCapNhat.setText(tenCV);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = txtCapNhat.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+tenMoi+"' WHERE ID = "+id);
                dialog.dismiss();
                getData();
            }
        });
        dialog.show();
    }

    //hàm xóa công việc
    public void DialogXoaCV(int id,String tenCV){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc "+tenCV+" không ?");

        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE ID = "+id);
                Toast.makeText(MainActivity.this, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                getData();
            }
        });

        dialogXoa.show();
    }
}