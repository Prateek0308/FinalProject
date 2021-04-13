package com.project.recipesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class BaseActivity extends AppCompatActivity {


    KProgressHUD kProgressHUD;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kProgressHUD = new KProgressHUD(this);

    }




    public void customProgressDialog(Boolean show, String message) {

        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(message).setCancellable(false).setAnimationSpeed(1).setDimAmount(0.7f);

        if (show) {
            kProgressHUD.show();
        } else {
            kProgressHUD.dismiss();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showAlertMessage(String message){
        new MaterialAlertDialogBuilder(this)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

}
