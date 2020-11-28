package views;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.RelativeLayout;

import ir.doran_program.SecureWallet.R;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import base.BaseActivity;
import database.AppDatabase;
import models.AccountDetails;
import models.ItemListModel;
import tools.EnumManager;

import static constants.IntentKeys.SELECTED_ACCOUNT;
import static constants.SettingManager.DATABASE_NAME;

public class ShowAccountsActivity extends BaseActivity {

    private RecyclerView recyclerAccount;
    private MaterialButton btnDefineAccount;
    private RelativeLayout relList;
    private static final int REQ_ADD_ACCOUNT = 626;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);

        initView();
        readAccountList();
        initEvent();
        saveData();
    }

    private void initEvent() {
        btnDefineAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterAccountActivity.class);
            startActivityForResult(intent , REQ_ADD_ACCOUNT);
        });
    }

    private void readAccountList() {
        List<AccountDetails> accountDetailsList = AppDatabase.getInstance(this).accountDetailsDao().getAll();
        if (accountDetailsList.isEmpty()) {
            showError(EnumManager.ErrorType.NoItem , relList , null);
        } else {
            hideError(relList);
            createTwoLineListRecycler(accountDetailsList, null, recyclerAccount, true, null, null, object -> {
                ItemListModel selectedItem = (ItemListModel) object;
                for (AccountDetails accountDetails : accountDetailsList) {
                    if (accountDetails.getCode() == selectedItem.getCode()) {
                        Intent intent = new Intent(this, RegisterAccountActivity.class);
                        intent.putExtra(SELECTED_ACCOUNT, accountDetails);
                        startActivityForResult(intent , REQ_ADD_ACCOUNT);
                    }
                }
            });
        }
    }

    private void initView() {
        recyclerAccount = findViewById(R.id.show_account_recycler);
        btnDefineAccount = findViewById(R.id.show_account_define_btn);
        relList = findViewById(R.id.show_account_list_rel);
    }

    private void saveData() {
        File dbfile = this.getDatabasePath(DATABASE_NAME);
        File sdir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "DBsaves");
        String sfpath = sdir.getPath() + File.separator + "DBsave" + String.valueOf(System.currentTimeMillis());
        if (!sdir.exists()) {
            sdir.mkdirs();
        }
        File savefile = new File(sfpath);
        try {
            savefile.createNewFile();
            int buffersize = 8 * 1024;
            byte[] buffer = new byte[buffersize];
            int bytes_read = buffersize;
            OutputStream savedb = new FileOutputStream(sfpath);
            InputStream indb = new FileInputStream(dbfile);
            while ((bytes_read = indb.read(buffer, 0, buffersize)) > 0) {
                savedb.write(buffer, 0, bytes_read);
            }
            savedb.flush();
            indb.close();
            savedb.close();
        }catch (Exception e){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            switch (requestCode){
                case REQ_ADD_ACCOUNT:
                    readAccountList();
            }
    }
}