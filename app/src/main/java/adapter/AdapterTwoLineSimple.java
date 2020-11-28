package adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import ir.doran_program.SecureWallet.R;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import base.BaseActivity;
import interfaces.OnObjectClickListener;
import interfaces.OnPopUpClickListener;
import models.ItemListModel;
import tools.CircleImageView;
import tools.Common;

public class AdapterTwoLineSimple extends RecyclerView.Adapter<AdapterTwoLineSimpleViewHolder> {

    private List<ItemListModel> itemListModels;
    private OnObjectClickListener onMainObjectClickListener;
    private OnPopUpClickListener onPopUpClickListener;
    private List<String> moreStringList;
    private Context context;
    private Drawable defaultIcon;
    private boolean clickable;

    public AdapterTwoLineSimple(List importList, List<String> moreStringList, Drawable defaultIcon, boolean clickable ,
                                OnPopUpClickListener onPopUpClickListener, OnObjectClickListener onMainObjectClickListener) {
        itemListModels = Common.getInstance().listModelMapper(importList, ItemListModel.class);
        this.onMainObjectClickListener = onMainObjectClickListener;
        this.moreStringList = moreStringList;
        this.defaultIcon = defaultIcon;
        this.clickable = clickable;
        this.onPopUpClickListener = onPopUpClickListener;
    }

    @NonNull
    @Override
    public AdapterTwoLineSimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_two_line, parent, false);
        return new AdapterTwoLineSimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTwoLineSimpleViewHolder holder, int position) {
        ItemListModel itemListModel = itemListModels.get(position);
        String codeString = "";
        if (itemListModel.getId() == null || itemListModel.getId().isEmpty())
            codeString = String.valueOf(itemListModel.getCode());
        else {
            codeString = itemListModel.getId();
        }
        holder.lineOne.setText(itemListModel.getName());
        holder.lineTwo.setText(codeString );
        if (moreStringList == null|| moreStringList.size()==0)
            holder.imgMore.setVisibility(View.GONE);
        else{
            holder.imgMore.setVisibility(View.VISIBLE);
            holder.imgMore.setOnClickListener(view -> {
                ((BaseActivity) context).popUpItemCreate(view, moreStringList, new OnObjectClickListener() {
                    @Override
                    public void onClick(Object object) {
                        onPopUpClickListener.onClick(((int) object) , itemListModel);
                    }
                });
            });
        }
        holder.itemView.setClickable(clickable);
        holder.itemView.setFocusable(clickable);
        holder.itemView.setOnClickListener(v -> {
            if (onMainObjectClickListener != null)
                onMainObjectClickListener.onClick(itemListModel);
        });
        if (defaultIcon != null) {
            if (itemListModel.getImgAddress() == null) {
                holder.imgCircle.setImageDrawable(defaultIcon);
            } else
                Picasso.get()
                        .load(itemListModel.getImgAddress())
                        .fit().centerCrop()
                        .error(defaultIcon)
                        .into(holder.imgCircle);
        } else
            holder.linCircle.setVisibility(View.GONE);
        if (defaultIcon != null) {
            LinearLayoutCompat.LayoutParams param = new LinearLayoutCompat.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, ((int) context.getResources().getDimension(R.dimen.elevation_card)));
            param.setMarginStart((int) context.getResources().getDimension(R.dimen.divider_picture_margin));
            holder.imgDivider.setLayoutParams(param);
        }
    }

    @Override
    public int getItemCount() {
        return itemListModels.size();
    }
}

class AdapterTwoLineSimpleViewHolder extends RecyclerView.ViewHolder {

    MaterialTextView lineOne;
    MaterialTextView lineTwo;
    AppCompatImageView imgMore;
    AppCompatImageView imgDivider;
    CircleImageView imgCircle;
    LinearLayout linCircle;

    AdapterTwoLineSimpleViewHolder(@NonNull View itemView) {
        super(itemView);
        lineOne = itemView.findViewById(R.id.txt_line_one_view);
        lineTwo = itemView.findViewById(R.id.txt_line_two_view);
        imgMore = itemView.findViewById(R.id.img_more);
        imgDivider = itemView.findViewById(R.id.item_two_line_divider_img);
        imgCircle = itemView.findViewById(R.id.txt_line_circle_img);
        linCircle = itemView.findViewById(R.id.txt_line_circle_lin);

    }
}
