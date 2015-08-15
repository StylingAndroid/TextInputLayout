package com.stylingandroid.textinputlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {

    private static final int MIN_TEXT_LENGTH = 4;
    private static final String EMPTY_STRING = "";

    private TextInputLayout textInputLayout;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout);
        editText = (EditText) findViewById(R.id.edit_text);

        textInputLayout.setHint(getString(R.string.hint));
        editText.setOnEditorActionListener(ActionListener.newInstance(this));
    }

    private boolean shouldShowError() {
        int textLength = editText.getText().length();
        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
    }

    private void showError() {
        textInputLayout.setError(getString(R.string.error));
    }

    private void hideError() {
        textInputLayout.setError(EMPTY_STRING);
    }

    private static final class ActionListener implements TextView.OnEditorActionListener {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        public static ActionListener newInstance(MainActivity mainActivity) {
            WeakReference<MainActivity> mainActivityWeakReference = new WeakReference<>(mainActivity);
            return new ActionListener(mainActivityWeakReference);
        }

        private ActionListener(WeakReference<MainActivity> mainActivityWeakReference) {
            this.mainActivityWeakReference = mainActivityWeakReference;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null) {
                if (actionId == EditorInfo.IME_ACTION_GO && mainActivity.shouldShowError()) {
                    mainActivity.showError();
                } else {
                    mainActivity.hideError();
                }
            }
            return true;
        }
    }
}
