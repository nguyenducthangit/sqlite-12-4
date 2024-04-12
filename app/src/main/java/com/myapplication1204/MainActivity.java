package com.myapplication1204;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import com.myapplication1204.AdapterProduct.ProductAdapter;
import com.myapplication1204.Model.Product;
import com.myapplication1204.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Database db;
    ProductAdapter Adapter;
    ArrayList<Product> product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareDb();
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }
    private void loadData(){
        Adapter = new ProductAdapter(MainActivity.this,R.layout.items_list,getDataFromDb());
        binding.lvProduct.setAdapter(Adapter);
    }
    private List<Product> getDataFromDb(){
        product = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + Database.TBL_NAME);
        while (cursor.moveToNext()){
            product.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)));
        }
        cursor.close();
        return product;
    }
    public void openEditDialog(Product p){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);

        EditText edtName = dialog.findViewById(R.id.edtName);
        edtName.setText(p.getProductName());
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        edtPrice.setText(String.valueOf(p.getProductPrice()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Update data
                String name = edtName.getText().toString();
                double price = Double.parseDouble(edtPrice.getText().toString());
                db.execSql("UPDATE " + Database.TBL_NAME + " SET " + Database.COL_NAME + "='" + name
                        + "',"+ Database.COL_PRICE + "=" + price + "WHERE " + Database.COL_CODE + "=" +p.getProductCode());
                loadData();
                dialog.dismiss();
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    //    ------------MENU------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull  MenuItem item){
        if(item.getItemId() == R.id.btnSave){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add);
            EditText edtName = dialog.findViewById(R.id.edtName);
            EditText edtPrice = dialog.findViewById(R.id.edtPrice);
            Button btnSave = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = edtName.getText().toString();
                    double price = Double.parseDouble(edtPrice.getText().toString());
                    db.execSql("INSERT INTO " + Database.TBL_NAME + " VALUES(null, '"+name+"'," +price+")");
                    loadData();
                    dialog.dismiss();
                }
            });
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}