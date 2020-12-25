package base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duolingo.open.rtlviewpager.RtlViewPager;
import ir.doran_program.SecureWallet.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.AdapterTwoLineSimple;
import interfaces.OnClickListenerNoObject;
import interfaces.OnObjectClickListener;
import interfaces.OnPopUpClickListener;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import models.ItemListModel;
import models.ModelViewModel;
import tools.Common;
import tools.EnumManager;
import tools.MySettings;
import tools.SearchToolbar;

import static tools.Common.getCustomTitle;

public class BaseActivity extends AppCompatActivity {

    private List<ModelViewModel> modelViewModels = new ArrayList<>();
    private LinearLayout layout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public static void slideShowViewPagerAutoShow(final RtlViewPager viewPager, int delay, int period ) {
        final Handler mHandler = new Handler();
        final Runnable mUpdateResults = new Runnable() {
            int page = 0;

            public void run() {
                int numPages = viewPager.getAdapter().getCount();
                page = (page + 1) % numPages;
                viewPager.setCurrentItem(page);
            }
        };
        Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    mHandler.post(mUpdateResults);
                }
            }, delay, period);
    }

    //region SetView
    public void setViewModelText(View view, String modelViewName) {
        setViewModel(view, modelViewName, null);
    }

    public void setViewModelTag(View view, String modelViewTag) {
        setViewModel(view, null, modelViewTag);
    }

    private List<ModelViewModel> setViewModel(View view, String modelViewName, String modelViewTag) {
        ModelViewModel modelViewModel = new ModelViewModel();
        modelViewModel.setView(view);
        if (modelViewName != null)
            modelViewModel.setText(modelViewName);
        if (modelViewTag != null)
            modelViewModel.setTag(modelViewTag);
        view.setTag(R.string.view_tag, modelViewTag);
        modelViewModels.add(modelViewModel);
        return modelViewModels;
    }

    public <T> T setViewToModel(Class<T> type) {
        T model = null;
        try {
            model = type.newInstance();
            for (ModelViewModel modelView : modelViewModels) {
                View view = modelView.getView();
                for (Field field : model.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if ((view instanceof AppCompatEditText ||
                            view instanceof AppCompatTextView) &&
                            !((TextView) view).getText().toString().isEmpty()) {
                        if (field.getName().equals(modelView.getText()))
                            try {
                                field.set(model, getValue(field.getType().toString(), ((TextView) view).getText().toString()));
                                Log.d("ViewToModel", ((TextView) view).getText().toString());
                            } catch (Exception e) {
                                Log.d("TAG", e.getMessage());
                            }
                        if (field.getName().equals(modelView.getTag()))
                            try {
                                field.set(model, getValue(field.getType().toString(), view.getTag().toString()));
                                Log.d("ViewToModel", ((TextView) view).getText().toString());
                            } catch (Exception e) {
                                Log.d("TAG", e.getMessage());
                            }
                    } else if (view instanceof CheckBox)
                        if (field.getName().equals(modelView.getText()))
                            try {
                                field.set(model, ((CheckBox) view).isChecked());
                            } catch (Exception e) {
                                Log.d("TAG", e.getMessage());
                            }
                }
            }
        } catch (
                Exception e) {
            Log.d("TAG", e.getMessage());
        }
        return model;
    }

    public void hideError(RelativeLayout relativeLayout) {
        if (layout != null) {
            relativeLayout.removeView(layout);
            layout = null;

        }
    }

    public void showError(EnumManager.ErrorType errorType, RelativeLayout relativeLayout, OnClickListenerNoObject onClickListener) {
        if (layout == null) {
            layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams paramText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams paramButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


            AppCompatImageView imageView = new AppCompatImageView(this);

            int errorDrawable = 0;
            if (errorType.equals(EnumManager.ErrorType.DataError))
                errorDrawable = R.drawable.error_outline;
            if (errorType.equals(EnumManager.ErrorType.NotFound))
                errorDrawable = R.drawable.search_off;
            if (errorType.equals(EnumManager.ErrorType.NotConnect))
                errorDrawable = R.drawable.signal_cellular_connected_no_internet;
            imageView.setImageDrawable(getResources().getDrawable(errorDrawable));

            TextView txtError = new TextView(this);
            txtError.setTextColor(getResources().getColor(R.color.md_black_1000));
            paramText.setMargins(0, (int) getResources().getDimension(R.dimen.normal_margin), 0, 0);
            if (errorType == EnumManager.ErrorType.DataError)
                txtError.setText(getResources().getString(R.string.error_read_data));
            else if (errorType == EnumManager.ErrorType.NotConnect)
                txtError.setText(R.string.no_connect_server);
            else if (errorType == EnumManager.ErrorType.NotFound)
                txtError.setText(R.string.not_found_item);
            changeFont(txtError);

            Button button = new Button(this);
            paramButton.setMargins(0, (int) getResources().getDimension(R.dimen.search_margin), 0, 0);
            button.setPadding((int) getResources().getDimension(R.dimen.normal_margin), 0, (int) getResources().getDimension(R.dimen.normal_margin), 0);
            Drawable drawable = getResources().getDrawable(R.drawable.button_corner);
            drawable.setTint(getResources().getColor(R.color.colorPrimary));
            button.setBackground(drawable);
            button.setTextColor(getResources().getColor(R.color.md_white_1000));
            button.setText(R.string.retry);
            changeFont(button);

            layout.setClickable(true);
            layout.setBackgroundColor(getResources().getColor(R.color.md_white_1000));
            layout.setGravity(Gravity.CENTER);
            relativeLayout.addView(layout);
            layout.addView(imageView, paramsImage);
            layout.addView(txtError, paramText);
            if (errorType == EnumManager.ErrorType.DataError || errorType == EnumManager.ErrorType.NotConnect)
                layout.addView(button, paramButton);

            button.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
            });
        }
    }

    public <Model> void setModelToView(Model model) {
        for (ModelViewModel modelView : modelViewModels) {
            View view = modelView.getView();
            for (Field field : model.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (view instanceof AppCompatTextView || view instanceof AppCompatEditText) {
                    try {
                        if (field.getName().equals(modelView.getText()) && !field.get(model).toString().isEmpty()) {
                            ((TextView) view).setText(field.get(model).toString());
                            Log.d("ModelToView", field.get(model).toString());
                        }
                        if (field.getName().equals(modelView.getTag()) && modelView.getTag() != null) {
                            ((TextView) view).setTag(getValue(field.getType().toString(), field.get(model).toString()));
                            Log.d("ModelToView", field.get(model).toString());
                        }
                    } catch (Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                } else if (view instanceof CheckBox)
                    if (field.getName().equals(modelView.getText()))
                        try {
                            ((CheckBox) view).setChecked(Boolean.parseBoolean(field.get(model).toString()));
                        } catch (Exception e) {
                            Log.d("TAG", e.getMessage());
                        }
            }
        }
    }

    private Object getValue(String type, String value) {
        try {
            if (type.contains("long") || type.contains("Long")) {
                return (value.isEmpty() ? -1 : Long.parseLong(value.replace(",", "")));
            } else if (type.contains("int")) {
                return Integer.parseInt(value);
            } else if (type.contains("float")) {
                return Long.parseLong(value);
            } else if (type.contains("boolean")) {
                return value.equals("true");
            }
        } catch (Exception e) {
            Log.e("getValue", e.getMessage());
        }
        return value;
    }
    //endregion

    //region ChangeFont

    private void changeTabLayoutFont(TabLayout tabLayout, Integer fontSize) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    changeFont(tabViewChild, fontSize == null ? 10 : fontSize);
                }
            }
        }
    }

    public void changeFont(View v) {
        changeFont(v, MySettings.fontFamily, null);
    }

    public void changeFont(View v, int size, boolean change) {
        changeFont(v, MySettings.fontFamily, size, false);
    }

    public void changeFont(View v, int size) {
        changeFont(v,  MySettings.fontFamily ,size);
    }

    public void changeFont(View v, String fontFamily) {
        changeFont(v, fontFamily, 13);
    }

    public void changeFont(View v, String fontFamily, Integer size) {
        changeFont(v, fontFamily, size, true);
    }

    private void changeFont(View v, String fontFamily, Integer size, boolean change) {
        Context context = BaseApplication.getContext();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontFamily);
        try {
            if (v instanceof AppCompatEditText) {
                if (fontFamily != null)
                    ((AppCompatEditText) v).setTypeface(typeface);
                if (size != null)
                    ((AppCompatEditText) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof EditText) {
                if (fontFamily != null)
                    ((EditText) v).setTypeface(typeface);
                if (size != null)
                    ((EditText) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof MaterialTextView) {
                if (fontFamily != null)
                    ((MaterialTextView) v).setTypeface(typeface);
                if (size != null)
                    ((MaterialTextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof TextView) {
                if (fontFamily != null)
                    ((TextView) v).setTypeface(typeface);
                if (size != null)
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof RadioButton) {
                if (fontFamily != null)
                    ((RadioButton) v).setTypeface(typeface);
                if (size != null)
                    ((RadioButton) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof Button) {
                if (fontFamily != null)
                    ((Button) v).setTypeface(typeface);
                if (size != null)
                    ((Button) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof SwitchCompat) {
                if (fontFamily != null)
                    ((SwitchCompat) v).setTypeface(typeface);
                if (size != null)
                    ((SwitchCompat) v).setTextSize(TypedValue.COMPLEX_UNIT_SP,
                            size);
            } else if (v instanceof Switch) {
                if (fontFamily != null)
                    ((Switch) v).setTypeface(typeface);
                if (size != null)
                    ((Switch) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            } else if (v instanceof TabLayout) {
                changeTabLayoutFont(((TabLayout) v), size);
            } else if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    changeFont(child, fontFamily, size);
                }
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    //endregion

    //region CheckField
    public Boolean checkField(EditText editText, ScrollView scrollView) {
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(editText);
        return checkField(editTextList, scrollView, null);
    }

    public Boolean checkField(EditText editText, ScrollView scrollView, String error) {
        List<EditText> editTextList = new ArrayList<>();
        editTextList.add(editText);
        return checkField(editTextList, scrollView, error);
    }

    public Boolean checkField(List<EditText> editTextList, ScrollView scrollView) {
        return checkField(editTextList, scrollView, null);
    }

    private Boolean checkField(List<EditText> editTextList, ScrollView scrollView, String error) {
        boolean result = true;
        boolean preResult = true;
        TextInputLayout firstErrorLayout = null;
        for (EditText editText : editTextList) {
            TextInputLayout inputLayout = ((TextInputLayout) (editText.getParent().getParent()));
            if (editText instanceof AutoCompleteTextView) {
                result = editText.getTag() != null;
            } else if (editText instanceof TextInputEditText) {
                result = !editText.getText().toString().isEmpty();
            }
            if (result) {
                result = preResult;
                (inputLayout).setErrorEnabled(false);
                (inputLayout).setError("");
            } else {
                if (firstErrorLayout == null)
                    firstErrorLayout = inputLayout;
                (inputLayout).setErrorEnabled(true);
                (inputLayout).setError(error == null ? getString(R.string.field_error) : error);
            }
            preResult = result;
        }
        if (!result && scrollView != null)
            firstErrorLayout.requestFocus();
        return result;
    }
    //endregion

    //region OpenDialog
    private void fullModuleList(String title, List codeNameModels, Drawable defaultIcon, View clickedView,
                                List<String> moreStringList, OnPopUpClickListener onPopUpClickListener, OnObjectClickListener mainObjectClickListener) {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(this);
        SearchToolbar searchToolbar = new SearchToolbar(this);
        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutCompat.LayoutParams linearParam = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams searchParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RecyclerView.LayoutParams recyclerParam = new RecyclerView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        linearLayoutCompat.setBackgroundColor(Color.WHITE);
        linearLayoutCompat.addView(searchToolbar, searchParam);
        linearLayoutCompat.addView(recyclerView, recyclerParam);
        this.addContentView(linearLayoutCompat, linearParam);
        searchToolbar.initSearchToolbar(codeNameModels, moreStringList, recyclerView, title, true, defaultIcon, true, onPopUpClickListener, object -> {
            ((ViewGroup) linearLayoutCompat.getParent()).removeView(linearLayoutCompat);
            if (clickedView != null) {
                if (clickedView instanceof TextView) {
                    ItemListModel itemListModel = ((ItemListModel) object);
                    ((TextView) clickedView).setText(itemListModel.getName());
                    clickedView.setTag(itemListModel.getCode());
                }
            }
            if (mainObjectClickListener != null)
                mainObjectClickListener.onClick(object);
        });

    }

    public void openDialog(String title, List itemListModelList, View clickedView) {
        openDialog(title, itemListModelList, null, clickedView, null, null, null);
    }

    public void openDialog(String title, List itemListModelList, View clickedView, OnObjectClickListener mainObjectClickListener) {
        openDialog(title, itemListModelList, null, clickedView, null, null, mainObjectClickListener);
    }

    public void openDialog(String title, List itemListModelList, Drawable defaultIcon, View clickedView, List<String> moreStringList,
                           OnPopUpClickListener onPopUpClickListener, OnObjectClickListener mainObjectClickListener) {
        if (itemListModelList.size() == 0) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.error)
                    .setMessage(R.string.not_found_item)
                    .show();
        } else if (itemListModelList.size() > 10)
            fullModuleList(title, itemListModelList, defaultIcon, clickedView, moreStringList, onPopUpClickListener, mainObjectClickListener);
        else
            openSelectDialog(itemListModelList, clickedView, title, mainObjectClickListener);
    }
    //endregion

    //region OpenSelectDialog
    public void openSelectDialog(List dialogList, EditText editText, String title) {
        openSelectDialog(dialogList, editText, title, null);
    }

    public void openSelectDialog(List dialogList, View textView, String title, OnObjectClickListener onObjectClickListener) {
        List<ItemListModel> itemListModelList;
        if (!dialogList.isEmpty()) {
            itemListModelList = Common.getInstance().listModelMapper(dialogList, ItemListModel.class);
            if (!itemListModelList.isEmpty() &&
                    (textView == null || textView instanceof MaterialTextView || textView instanceof TextInputEditText)) {
                int listSize = itemListModelList.size();
                CharSequence[] list = new String[listSize];
                for (Integer i = 0; i < listSize; i++)
                    list[i] = itemListModelList.get(i).getName();

                new MaterialAlertDialogBuilder(this)
                        .setTitle(title)
                        .setItems(list, (dialog, which) -> {
                            if (textView != null) {
                                if (textView instanceof MaterialTextView)
                                    ((MaterialTextView) textView).setText(itemListModelList.get(which).getName());
                                if (textView instanceof TextInputEditText)
                                    ((TextInputEditText) textView).setText(itemListModelList.get(which).getName());
                                Object tag = null;
                                if (itemListModelList.get(which).getCode() == null) {
                                    if (itemListModelList.get(which).getId() != null)
                                        tag = itemListModelList.get(which).getId();
                                } else
                                    tag = itemListModelList.get(which).getCode();
                                textView.setTag(tag);
                            }
                            if (onObjectClickListener != null)
                                onObjectClickListener.onClick(dialogList.get(which));
                        }).show();
            }
        }
    }
    //endregion

    public void createTwoLineListRecycler(List itemListModelList, RecyclerView recyclerView, boolean clickable) {
        createTwoLineListRecycler(itemListModelList, null, recyclerView, clickable, null, null, null);
    }

    public void createTwoLineListRecycler(List itemListModelList, List<String> moreStringList, RecyclerView recyclerView, boolean clickable, Drawable defaultIcon,
                                          OnPopUpClickListener onPopUpClickListener, OnObjectClickListener objectMainClickListener) {
        Common.getInstance().listModelMapper(itemListModelList, ItemListModel.class);
        AdapterTwoLineSimple twoLineListAdapter = new AdapterTwoLineSimple(itemListModelList, moreStringList, defaultIcon, clickable, onPopUpClickListener, objectMainClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(twoLineListAdapter);
        twoLineListAdapter.notifyDataSetChanged();
    }
    public void popUpItemCreate(View view, List<String> stringList, OnObjectClickListener onObjectClickListener) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(item -> {
            onObjectClickListener.onClick(item.getItemId());
            return true;
        });

        for (int i = 0; i < stringList.size(); i++) {
            popup.getMenu().add(1, i, i, getCustomTitle(stringList.get(i)));
        }
        popup.show();
    }

    public Activity getActivity() {
        return this;
    }

    public void focusView(TextInputEditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void verifyStoragePermissions(Activity activity) {

        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {

                //Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permission = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
