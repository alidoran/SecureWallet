package views;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.SecureWallet.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.List;
import base.BaseActivity;
import database.AppDatabase;
import models.AccountDetail;
import models.ItemListModel;

import static constants.IntentKeys.SelectedAccount;

public class ShowAccountsActivity extends BaseActivity {

    private RecyclerView recyclerAccount;
    private MaterialButton btnDefineAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);

        initView();
        readAccountList();
        initEvent();
    }

    private void initEvent() {
        btnDefineAccount.setOnClickListener(v->{
            Intent intent = new Intent(this , RegisterAccountActivity.class);
            startActivity(intent);
        });
    }

    private void readAccountList() {
        List<AccountDetail> accountDetailList = AppDatabase.getInstance(this).accountDetailsDao().getAll();
        createTwoLineListRecycler(accountDetailList, null, recyclerAccount, true, null, null, object -> {
            ItemListModel selectedItem = (ItemListModel) object;
            for (AccountDetail accountDetail : accountDetailList){
                if (accountDetail.getCode() == selectedItem.getCode()){
                    Intent intent = new Intent(this , RegisterAccountActivity.class);
                    intent.putExtra(SelectedAccount, accountDetail);
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        recyclerAccount = findViewById(R.id.show_account_recycler);
        btnDefineAccount = findViewById(R.id.show_account_define_btn);
    }


}