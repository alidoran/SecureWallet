package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.doran_program.SecureWallet.R;
import models.AccountDetails;

public class AdapterMainList extends RecyclerView.Adapter<AdapterMainListHolder> {

    List<AccountDetails> accountDetailsList;

    public AdapterMainList(List<AccountDetails> accountDetailsList) {
        this.accountDetailsList = accountDetailsList;
    }

    @NonNull
    @Override
    public AdapterMainListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, parent, false);
        return new AdapterMainListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMainListHolder holder, int position) {
        AccountDetails accountDetails = accountDetailsList.get(position);
        holder.imgMore.setTag(accountDetails.getId());
        holder.itemView.setTag(accountDetails.getId());
        holder.viewLineOne.setText(accountDetails.getName());
        holder.viewLineTwo.setText(String.valueOf(accountDetails.getNumber()));
    }

    @Override
    public int getItemCount() {
        return accountDetailsList.size();
    }
}

class AdapterMainListHolder extends RecyclerView.ViewHolder {

    AppCompatImageView imgCircle;
    TextView viewLineOne;
    TextView viewLineTwo;
    AppCompatImageView imgMore;
    AppCompatImageView imgDivider;

    public AdapterMainListHolder(@NonNull View itemView) {
        super(itemView);

        imgCircle = itemView.findViewById(R.id.item_main_list_circle_img);
        viewLineOne = itemView.findViewById(R.id.item_main_list_one_view);
        viewLineTwo = itemView.findViewById(R.id.item_main_list_two_view);
        imgMore = itemView.findViewById(R.id.item_main_list_more_img);
        imgDivider = itemView.findViewById(R.id.item_main_list_divider_img);
    }
}
