package io.instaal.miniupdatechecker;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

public class MiniUpdateChecker {

    public static final String DEFAULT_THEME = "default";
    public static final String MINI_THEME = "mini";
    public static final String SIMPLE_THEME = "simple";
    private static final String DEFAULT_TITLE = "New Update Available";
    private static final int DEFAULT_APP_ICON = R.drawable.update_icon;
    private static final int DEFAULT_CLOSE_ICON = R.drawable.cancel_icon;
    private static final String DEFAULT_DESCRIPTION = "There is a new version available for";
    private static final String DEFAULT_NOT_NOW = "Not Now";
    private static final String DEFAULT_UPDATE = "Update";
    private static final int DEFAULT_COLOR = Color.parseColor("#FFFFFF");


    private final Activity activity;
    OnUpdateEventListener onUpdateEventListener;
    SharedPreferences sharedPreferences;
    private String packageName, oldVersionCode, newVersionCode, changes;
    private String updateText, remindText, negativeText;

    private String THEME = "default";
    private String TITLE = "main";
    private int APP_ICON = 0;
    private int CLOSE_ICON = 0;
    private String DESCRIPTION = "desc";
    private String APP_NAME = "app_name";
    private String NOT_NOW = "not_now";
    private String UPDATE = "update_now";
    private int COLOR = 0;

    public MiniUpdateChecker(Activity activity) {
        this.activity = activity;
        sharedPreferences = this.activity.getApplicationContext().getSharedPreferences("MiniUpdateChecker", Activity.MODE_PRIVATE);
    }


    public void showDialog() {

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // set layout for different theme
        if (THEME.equals(MINI_THEME)) {
            dialog.setContentView(R.layout.minimal_layout);
        } else if (THEME.equals(SIMPLE_THEME)) {
            dialog.setContentView(R.layout.simple_layout);
        } else if (THEME.equals("default")) {
            dialog.setContentView(R.layout.default_layout);
        } else {
            dialog.setContentView(R.layout.default_layout);
        }

        // setting up dialog
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);


        // changes for each theme
        if (THEME.equals(MINI_THEME)) {
            ImageView app_icon = dialog.findViewById(R.id.app_icon);
            ImageView close_icon = dialog.findViewById(R.id.close_icon);
            TextView current_version = dialog.findViewById(R.id.current_version);
            TextView latest_version = dialog.findViewById(R.id.latest_version);

            if (APP_ICON == 0) {
                app_icon.setImageResource(DEFAULT_APP_ICON);
            } else {
                app_icon.setImageResource(APP_ICON);
            }

            if (CLOSE_ICON == 0) {
                close_icon.setImageResource(DEFAULT_CLOSE_ICON);
            } else {
                close_icon.setImageResource(CLOSE_ICON);
            }

            close_icon.setOnClickListener(view -> dialog.dismiss());

        } else if (THEME.equals(SIMPLE_THEME)) {

            // nothing special
            CardView not_now_button = dialog.findViewById(R.id.not_now_button);

        } else {
            ImageView app_icon = dialog.findViewById(R.id.app_icon);
            ImageView close_icon = dialog.findViewById(R.id.close_icon);
            TextView update_description = dialog.findViewById(R.id.update_description);
            TextView app_name = dialog.findViewById(R.id.app_name);
            TextView current_version = dialog.findViewById(R.id.current_version);
            TextView latest_version = dialog.findViewById(R.id.latest_version);

            if (APP_ICON == 0) {
                app_icon.setImageResource(DEFAULT_APP_ICON);
            } else {
                app_icon.setImageResource(APP_ICON);
            }

            if (CLOSE_ICON == 0) {
                close_icon.setImageResource(DEFAULT_CLOSE_ICON);
            } else {
                close_icon.setImageResource(CLOSE_ICON);
            }

            if (DESCRIPTION.equals("desc")) {
                update_description.setText(DEFAULT_DESCRIPTION);
            } else {
                update_description.setText(DESCRIPTION);
            }

            if (APP_NAME.equals("app_name")) {
                String appName = "";
                PackageManager packageManager = activity.getPackageManager();
                try {
                    appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Log.e("TAG", " Can't find AppName ");
                }
                app_name.setText(appName);

            } else {
                app_name.setText(APP_NAME);
            }

            close_icon.setOnClickListener(view -> dialog.dismiss());

        }


        // All Theme
        TextView titleText = dialog.findViewById(R.id.title_text);
        if (TITLE.equals("main")) {
            titleText.setText(DEFAULT_TITLE);
        } else {
            titleText.setText(TITLE);
        }


        CardView wholeView = dialog.findViewById(R.id.main_card);

        if (COLOR==0){
            wholeView.setCardBackgroundColor(DEFAULT_COLOR);
        } else {

            try {
                wholeView.setCardBackgroundColor(ContextCompat.getColor(activity,COLOR));
            } catch (Resources.NotFoundException e){
                wholeView.setCardBackgroundColor(COLOR);
            }


        }




        CardView not_now_button = dialog.findViewById(R.id.not_now_button);
        not_now_button.setOnClickListener(view -> dialog.dismiss());

        CardView update_button = dialog.findViewById(R.id.update_button);

        TextView positive_text = dialog.findViewById(R.id.positive_text);
        if (UPDATE.equals("update_now")) {
            positive_text.setText(DEFAULT_UPDATE);
        } else {
            positive_text.setText(UPDATE);
        }


        TextView negative_text = dialog.findViewById(R.id.negative_text);
        if (NOT_NOW.equals("not_now")) {
            negative_text.setText(DEFAULT_NOT_NOW);
        } else {
            negative_text.setText(NOT_NOW);
        }


        dialog.show();


    }


    public MiniUpdateChecker setTheme(String s) {
        THEME = s;
        return this;

    }

    public MiniUpdateChecker setTitle(String s) {
        TITLE = s;
        return this;
    }

    public MiniUpdateChecker setAppIcon(int i) {
        APP_ICON = i;
        return this;
    }

    public MiniUpdateChecker setCloseIcon(int i) {
        CLOSE_ICON = i;
        return this;
    }


    public MiniUpdateChecker setDescription(String s) {
        DESCRIPTION = s;
        return this;
    }

    public MiniUpdateChecker setAppName(String s) {
        APP_NAME = s;
        return this;
    }

    public MiniUpdateChecker setPositiveLabel(String s) {
        UPDATE = s;
        return this;
    }

    public MiniUpdateChecker setNegativeLabel(String s) {
        NOT_NOW = s;
        return this;
    }

    public MiniUpdateChecker setBackgroundColor(int i) {
        COLOR = i;
        return this;
    }



}
