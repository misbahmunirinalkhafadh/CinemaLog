package com.dicoding.cinemalog.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.dicoding.cinemalog.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton rb;
    Locale locale;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hide action bar
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        Button btnTranslate = findViewById(R.id.ic_translate);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_movie, R.id.navigation_tvshow, R.id.navigation_favorite)
//                .build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        getSupportActionBar();

        btnTranslate.setOnClickListener(this);
    }

    public void onComposeAction(MenuItem mi) {
        // pengecekannya di sini
        showDialogRadio();
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        showDialogRadio();
    }

    /**
     * show dialog locale
     */
    private void showDialogRadio() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog_radio);
        dialog.setCancelable(true);

        RadioGroup radioGroup = dialog.findViewById(R.id.rg_language);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = group.findViewById(checkedId);
                if (rb != null) {
                    switch (checkedId) {
                        case R.id.rb_english:
                            rb.setChecked(true);
                            locale = new Locale("en");
                            SharedPreferences sharedPreferences = getSharedPreferences("languangeKey", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("keyLang", "en-US");
                            editor.apply();
                            setLocale();
                            dialog.dismiss();
                            break;
                        case R.id.rb_indonesian:
                            locale = new Locale("in");
                            SharedPreferences sharedPreferences2 = getSharedPreferences("languangeKey", MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                            editor2.putString("keyLang", "id-IN");
                            editor2.apply();
                            setLocale();
                            dialog.dismiss();
                            break;

                        default:
                            locale = new Locale("in");
                            setLocale();
                    }
                }
            }
        });

        dialog.show();
    }

    /**
     * set locale
     */
    private void setLocale() {
        rb.setChecked(true);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());

        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));

    }
}
