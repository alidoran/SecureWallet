
 package views;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ir.doran_program.SecureWallet.R;

import com.example.fullmodulelist.FullModuleFragment;
import com.example.fullmodulelist.FullModuleItemListModel;
import com.example.fullmodulelist.OnItemListClickListenerFullModule;
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
    private ImageView imgSync;
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
            startActivityForResult(intent, REQ_ADD_ACCOUNT);
        });
        imgSync.setOnClickListener(v->{

        });
    }

    private void readAccountList() {
        List<AccountDetails> accountDetailsList = AppDatabase.getInstance(this).accountDetailsDao().getAll();
        if (accountDetailsList.isEmpty()) {
            showError(EnumManager.ErrorType.NotFound, relList, null);
        } else {
            hideError(relList);
            new FullModuleFragment(accountDetailsList)
                    .setOnMainItemListClickListener(fullModuleItemListModel -> {
                        for (AccountDetails accountDetails : accountDetailsList) {
                            if (String.valueOf(accountDetails.getCode()).equals(fullModuleItemListModel.getCode())) {
                                Intent intent = new Intent(this, RegisterAccountActivity.class);
                                intent.putExtra(SELECTED_ACCOUNT, accountDetails);
                                startActivityForResult(intent, REQ_ADD_ACCOUNT);
                            }
                        }
                    })
                    .show(getSupportFragmentManager() , this.getLocalClassName());
        }
    }

    private void initView() {
        recyclerAccount = findViewById(R.id.show_account_recycler);
        btnDefineAccount = findViewById(R.id.show_account_define_btn);
        relList = findViewById(R.id.show_account_list_rel);
        imgSync = findViewById(R.id.show_account_sync_img);
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
            int bytesRead = bufferSize;
            OutputStream saveDb = new FileOutputStream(sfPath);
            InputStream indb = new FileInputStream(dbFile);
            while ((bytesRead = indb.read(buffer, 0, bufferSize)) > 0) {
                saveDb.write(buffer, 0, bytesRead);
            }
            saveDb.flush();
            indb.close();
            saveDb.close();
            Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ShowAccountsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData2(){

    }

//    public boolean restoreBackup(String fileName, Bll bll, Context context) {
//        File sDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SecureWalletDataBases");
//        String sfPath = sDir.getPath() + File.separator + "SecureWalletDataBase" ;
//        File folder = new File(sfPath);
//        if (!folder.exists())
//            return false;
//        try {
//            InputStream myInput = new FileInputStream(sfPath + fileName);
//            OutputStream myOutput = new FileOutputStream(Bll.DATABASE_PATH() + "DBFile.db");
//            CipherInputStream cis = null;
//            if (!debugMode) {
//                SecretKeySpec sks = new SecretKeySpec(Settings.passwordFile.getBytes(), "AES");
//                Cipher cipher = Cipher.getInstance("AES");
//                cipher.init(Cipher.DECRYPT_MODE, sks);
//                cis = new CipherInputStream(myInput, cipher);
//            }
//
//
//            byte[] buffer = new byte[1024];
//            int length;
//            if (debugMode) {
//                while ((length = myInput.read(buffer)) != -1) {
//                    myOutput.write(buffer, 0, length);
//                }
//            } else {
//                while ((length = cis.read(buffer)) !=-1) {
//                    myOutput.write(buffer, 0, length);
//                }
//            }
//            if (debugMode) {
//                myOutput.flush();
//            } else {
//                cis.close();
//            }
//            myOutput.close();
//            myInput.close();
//
//            bll.updateDataBase(bll.getWritableDatabase(), 1, Events.getVersionCode(context));
//            Bll.sInstance = null;
//            return true;
//        } catch (Exception e) {
//            MessageBox.Show(context,"خطا",e.getMessage());
//            return false;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case REQ_ADD_ACCOUNT:
                    readAccountList();
            }
    }
}