package com.autodoc.autodoc.ui.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.autodoc.App;
import com.autodoc.autodoc.R;
import com.autodoc.autodoc.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.telephone)
    TextView telephone;

    @BindView(R.id.address)
    TextView addressTextView;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.profile));
        showData();
    }

    private void showData() {
        User user = App.getInstance().getUserSession().getUser();
        if (user != null) {
            userName.setText(user.getUserName());
            telephone.setText(user.getPhone());
            addressTextView.setText(getString(R.string.address) + ": " + user.getAddress());
        }
    }
}
