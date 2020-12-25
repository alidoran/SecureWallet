package tools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import ir.doran_program.SecureWallet.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import interfaces.OnObjectClickListener;
import interfaces.OnPopUpClickListener;
import models.ItemListModel;

public class SearchToolbar extends LinearLayoutCompat {

    private TextInputEditText txtSearch;
    private TextInputLayout laySearch;
    private OnObjectClickListener onMainObjectClickListener;
    private OnPopUpClickListener onPopUpClickListener;
    private AppCompatImageView imgBack;
    private MaterialTextView viewTitle;
    private AppCompatImageView imgSearch;
    private RecyclerView recyclerView;
    private List<ItemListModel> itemListModels;
    private List<ItemListModel> filteredItemListModels = new ArrayList<>();
    private RelativeLayout relFull;
    private RelativeLayout relMainToolbar;
    private Drawable defaultIcon;
    private RecyclerView mainRecycler;
    private List<String> moreStringList;
    private boolean isModule;
    private BaseActivity baseActivity;

    public SearchToolbar(Context context) {
        super(context);
        init();
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_search_toolbar, this);

        initView();
        initEvent();
        baseActivity = ((BaseActivity) SearchToolbar.this.getContext());
    }

    private void initView() {
        txtSearch = findViewById(R.id.search_toolbar_search_txt);
        laySearch = findViewById(R.id.search_toolbar_search_lay);
        imgSearch = findViewById(R.id.search_toolbar_search_img);
        imgBack = findViewById(R.id.search_toolbar_back_img);
        relMainToolbar = findViewById(R.id.search_toolbar_main_toolbar_rel);
        viewTitle = findViewById(R.id.search_toolbar_title_view);
        recyclerView = findViewById(R.id.search_toolbar_recycler);
        relFull = findViewById(R.id.search_toolbar_full_rel);
    }

    private void initEvent() {
        imgSearch.setOnClickListener(v -> searchAction());
        laySearch.setStartIconOnClickListener(v -> searchClose());
        imgBack.setOnClickListener(v -> {
            if (isModule)
                ((ViewGroup) (((ViewGroup) this.getParent()).getParent())).removeView(((ViewGroup) this.getParent()));
            else
                baseActivity.onBackPressed();
        });
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredItemListModels.clear();
                if (!s.toString().isEmpty())
                    for (ItemListModel itemListModel : itemListModels) {
                        if (itemListModel.getCode().toString().contains(Common.toNumberLatin(s.toString())) || itemListModel.getName().contains(Common.toNumberLatin(s.toString())))
                            filteredItemListModels.add(itemListModel);
                    }
                createFilterRecycler(s.toString().isEmpty());
            }
        });
    }

    public void initSearchToolbar(List codeNameModels, List<String> moreStringList, RecyclerView mainRecycler, String title, boolean isModule
            , Drawable defaultIcon, boolean clickable,
                                  OnPopUpClickListener onPopUpClickListener, OnObjectClickListener onMainObjectClickListener) {
        this.mainRecycler = mainRecycler;
        this.isModule = isModule;
        imgBack.setImageResource(isModule ? R.drawable.close : R.drawable.arrow_back);
        if (this.mainRecycler != null) {

            baseActivity.createTwoLineListRecycler(codeNameModels, moreStringList, this.mainRecycler, clickable, defaultIcon, onPopUpClickListener, onMainObjectClickListener);
        }
        this.itemListModels = Common.getInstance().listModelMapper(codeNameModels, ItemListModel.class);
        this.defaultIcon = defaultIcon;
        this.onMainObjectClickListener = onMainObjectClickListener;
        this.onPopUpClickListener = onPopUpClickListener;
        this.moreStringList = moreStringList;
        viewTitle.setText(title);
        relFull.setVisibility(GONE);
    }

    private void searchClose() {
        txtSearch.setText("");
        toolbarVisibility(false);
        relFull.setVisibility(GONE);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    private void toolbarVisibility(boolean isSearchMode) {
        if (isSearchMode) {
            imgSearch.setVisibility(View.GONE);
            imgBack.setVisibility(View.GONE);
            viewTitle.setVisibility(View.GONE);
            laySearch.setVisibility(View.VISIBLE);
            relMainToolbar.setBackgroundColor(baseActivity.getResources().getColor(R.color.md_white_1000));
        } else {
            imgSearch.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.VISIBLE);
            viewTitle.setVisibility(View.VISIBLE);
            laySearch.setVisibility(View.GONE);
            relMainToolbar.setBackgroundColor(baseActivity.getResources().getColor(R.color.colorPrimary));
        }
    }

    private void createFilterRecycler(boolean emptySearchField) {
        if (emptySearchField) {
            baseActivity.hideError(relFull);
            recyclerView.setVisibility(GONE);
        } else {
            recyclerView.setVisibility(VISIBLE);
            if (filteredItemListModels.isEmpty()) {
                baseActivity.showError(EnumManager.ErrorType.NotFound, relFull, null);
            } else {
                baseActivity.hideError(relFull);
                baseActivity.createTwoLineListRecycler(filteredItemListModels, moreStringList, recyclerView, true,
                        defaultIcon, onPopUpClickListener,
                        info -> {
                            if (onMainObjectClickListener != null) {
                                searchClose();
                                onMainObjectClickListener.onClick(info);
                            }
                        });
            }
        }
    }

    private void searchAction() {
        toolbarVisibility(true);
        relFull.setVisibility(VISIBLE);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        ((BaseActivity) this.getContext()).focusView(txtSearch);
    }

}