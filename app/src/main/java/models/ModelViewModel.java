package models;

import android.view.View;

public class ModelViewModel {
    private View view;
    private String Tag;
    private String text;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
