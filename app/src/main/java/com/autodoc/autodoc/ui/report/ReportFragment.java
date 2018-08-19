package com.autodoc.autodoc.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.ui.map.MapsActivity;
import com.autodoc.autodoc.ui.technician.TechnicianActivity;
import com.autodoc.autodoc.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ilabs on 8/19/18.
 */

public class ReportFragment extends Fragment {

    @BindView(R.id.reporterName)
    EditText repairNameNameEditText;

    @BindView(R.id.description)
    EditText descriptionEditText;

    @BindView(R.id.address)
    EditText addressEditText;

    @BindView(R.id.input_layout_reporter_name)
    TextInputLayout repairNameTextInputLayout;

    @BindView(R.id.input_layout_description)
    TextInputLayout descriptionTextInputLayout;

    @BindView(R.id.input_layout_reporter_address)
    TextInputLayout addressTextInputLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.mapButton)
    void openGoogleMap() {
        getActivity().startActivity(new Intent(getActivity(), MapsActivity.class));
    }

    @OnClick(R.id.nextButton)
    void goNext() {

        if (!AppUtil.checkNetwork(getActivity())) {
            return;
        }

        String repairName = repairNameNameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        repairNameTextInputLayout.setError("");
        descriptionTextInputLayout.setError("");
        addressTextInputLayout.setError("");

        if (repairName.isEmpty()) {
            repairNameTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (description.isEmpty()) {
            descriptionTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        if (address.isEmpty()) {
            addressTextInputLayout.setError(getString(R.string.invalid_input));
            return;
        }

        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setRepairName(repairName);
        reportRequest.setJobDescription(description);
        reportRequest.setAddress("test");
        reportRequest.setLat("0");
        reportRequest.setLon("0");

        Bundle bundle = new Bundle();
        bundle.putSerializable("reportRequest", reportRequest);
        Intent intent = new Intent(getActivity(), TechnicianActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
