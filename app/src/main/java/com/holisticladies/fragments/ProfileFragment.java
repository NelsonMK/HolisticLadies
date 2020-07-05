package com.holisticladies.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.holisticladies.PrefManager;
import com.holisticladies.R;
import com.holisticladies.database.DatabaseHandler;
import com.holisticladies.model.User;
import com.holisticladies.utils.RequestHandler;
import com.holisticladies.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private Context context;
    TextView full_names, email, orders_history, feedback, share, log_out;
    ImageView profile_picture;
    Dialog dialog;

    DatabaseHandler db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        profile_picture = view.findViewById(R.id.profile_picture);
        full_names = view.findViewById(R.id.full_names);
        email = view.findViewById(R.id.email);
        orders_history = view.findViewById(R.id.orders_history);
        feedback = view.findViewById(R.id.feedback);
        share = view.findViewById(R.id.share);
        log_out = view.findViewById(R.id.log_out);

        setUpProfile();

        clicked();
    }

    void setUpProfile() {
        db = new DatabaseHandler(context);
        User user = db.getUser(PrefManager.getInstance(context).userId());

        String names = user.getFirst_name() + " " + user.getLast_name();
        full_names.setText(names);
        email.setText(user.getEmail());
    }

    void clicked () {
        orders_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void feedback() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.feedback, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Feedback");
        dialogBuilder.setCancelable(false);

        final EditText title = dialogView.findViewById(R.id.message_title);
        final EditText message = dialogView.findViewById(R.id.message);
        final PrefManager prefManager = PrefManager.getInstance(context);

        dialogBuilder.setPositiveButton("Send",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // empty
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(message.getText().length() > 0){
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setEnabled(false);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = db.getUser(prefManager.userId());
                        String send_title = title.getText().toString();
                        String send_message = message.getText().toString();
                        String email = user.getEmail();

                        if (TextUtils.isEmpty(send_title)){
                            title.setError("Please enter message title!");
                            title.requestFocus();
                            return;
                        }
                        if (TextUtils.isEmpty(send_message)){
                            message.setError("Please enter the message!");
                            message.requestFocus();
                            return;
                        }

                        SendFeedback sendFeedback = new SendFeedback(email, send_title, send_message);
                        sendFeedback.execute();
                        dialog.dismiss();

                    }
                });
            }
        });

        alertDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class SendFeedback extends AsyncTask<Void, Void, String> {
        private String email, send_title, send_message;
        SendFeedback(String email, String send_title, String send_message){
            this.email = email;
            this.send_title = send_title;
            this.send_message = send_message;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("send_title", send_title);
            params.put("send_message", send_message);

            return requestHandler.sendPostRequest(URLS.URL_FEEDBACK, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Feedback","feedback : "+s);

            try {
                JSONObject obj = new JSONObject(s);
                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void share() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT,
                "\nFind more about Holistic Ladies in their official app available in play store");
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Share To"));
    }

    private void logOut() {
        Button back, yes;
        TextView title, message;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.log_out);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAimation;

        title = dialog.findViewById(R.id.title);
        message = dialog.findViewById(R.id.message);
        back = dialog.findViewById(R.id.back);
        yes = dialog.findViewById(R.id.yes);

        title.setText(R.string.app_name);
        message.setText(R.string.log_out_message);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setCancelable(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}
