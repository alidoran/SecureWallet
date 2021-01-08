


package views;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import adapter.AdapterMainList;
import interfaces.OnObjectClickListener;
import ir.doran_program.SecureWallet.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.BaseActivity;
import database.AppDatabase;
import models.AccountDetails;

import static constants.IntentKeys.SELECTED_ACCOUNT;
import static constants.SettingManager.DATABASE_NAME;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerMain;
    private static final int REQ_ADD_ACCOUNT = 626;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        saveData();
        createMainRecycler();
    }

    private void initView() {
        recyclerMain = findViewById(R.id.main_list_recycler);
    }

    //region initEvent
    public void main_list_more_click(View view) {
        ArrayList<String> moreList = new ArrayList<>(Arrays.asList(getString(R.string.show), getString(R.string.delete)));
        popUpItemCreate(view, moreList, object -> {
            int selectedPopup = ((int) object);
            moreClicked(view, selectedPopup);
        });
    }

    private void moreClicked(View view, int selectedPopUp) {
        int id = Integer.parseInt(view.getTag().toString());
        switch (selectedPopUp) {
            case 0:
            default:
                showAccount(id);
                break;
            case 1:
                deleteAccount(id);
                break;
        }
    }

    public void define_onclick(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterAccountActivity.class);
        startActivityForResult(intent, REQ_ADD_ACCOUNT);
    }

    private void createMainRecycler() {
        List<AccountDetails> accountDetailsList = AppDatabase.getInstance(this).accountDetailsDao().getAll();
        AdapterMainList adapterAlarm = new AdapterMainList(accountDetailsList);
        recyclerMain.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerMain.setAdapter(adapterAlarm);
    }
    //endregion

    public void main_list_item_view_click(View view) {
        int id = (int) view.getTag();
        showAccount(id);
    }

    private void showAccount(int id) {
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        intent.putExtra(SELECTED_ACCOUNT, id);
        startActivityForResult(intent, REQ_ADD_ACCOUNT);
    }

    private void deleteAccount(long id) {
        int result = AppDatabase.getInstance(this).accountDetailsDao().deleteSelectedModel(id);
        if (result != 0) {
            Toast.makeText(this, getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();
            createMainRecycler();
        }else{
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData() {
        File dbFile = this.getDatabasePath(DATABASE_NAME);
        File sDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SecureWalletDataBases");
        String sfPath = sDir.getPath() + File.separator + "SecureWalletDataBase" + "DBFile.db";
        if (!sDir.exists()) {
            sDir.mkdirs();
        }
        File saveFile = new File(sfPath);
        try {
            saveFile.createNewFile();
            int bufferSize = 8 * 1024;
            byte[] buffer = new byte[bufferSize];
            OutputStream saveDb = new FileOutputStream(sfPath);
            InputStream indb = new FileInputStream(dbFile);
            while ((bufferSize = indb.read(buffer, 0, bufferSize)) > 0) {
                saveDb.write(buffer, 0, bufferSize);
            }
            saveDb.flush();
            indb.close();
            saveDb.close();
            Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case REQ_ADD_ACCOUNT:
                    createMainRecycler();
            }
    }
}