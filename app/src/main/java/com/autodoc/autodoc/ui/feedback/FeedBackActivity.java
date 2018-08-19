package com.autodoc.autodoc.ui.feedback;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.FeedBackRequest;
import com.autodoc.autodoc.ui.login.LoginActivity;
import com.autodoc.autodoc.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends AppCompatActivity {

    @BindView(R.id.feedback)
    EditText feedbackEditText;

    @BindView(R.id.input_layout_feedback)
    TextInputLayout feedbackTextInputLayout;

    private FeedBackViewModel feedBackViewModel;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    private String technicianId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.feedback));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            technicianId = bundle.getString("technicianId");
        }

        feedBackViewModel = ViewModelProviders.of(this).get(FeedBackViewModel.class);

    }

    @OnClick(R.id.sendFeedback)
    void sendFeedBack() {
        if (!AppUtil.checkNetwork(this)) {
            return;
        }

        String feedBack = feedbackEditText.getText().toString().trim();

        if (feedBack.isEmpty()) {
            feedbackTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }
        FeedBackRequest feedBackRequest = new FeedBackRequest();
        feedBackViewModel.sendFeedBack(this, feedBackRequest, technicianId).observe(this, success -> {
            if (success.booleanValue()) {
                finish();
            }
        });
    }
}
