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
import com.autodoc.autodoc.data.MapData;
import com.autodoc.autodoc.ui.map.MapsActivity;
import com.autodoc.autodoc.ui.technician.TechnicianActivity;
import com.autodoc.autodoc.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

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

    private final static int ACTIVITY_RESULT = 123;
    private ReportRequest reportRequest;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, null);
        ButterKnife.bind(this, view);
        reportRequest = new ReportRequest();
        return view;
    }

    @OnClick(R.id.mapButton)
    void openGoogleMap() {
        getActivity().startActivityForResult(new Intent(getActivity(), MapsActivity.class), ACTIVITY_RESULT);
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

        reportRequest.setRepairName(repairName);
        reportRequest.setJobDescription(description);

        repairNameNameEditText.setText("");
        descriptionEditText.setText("");
        addressEditText.setText("");

        Bundle bundle = new Bundle();
        bundle.putSerializable("reportRequest", reportRequest);
        Intent intent = new Intent(getActivity(), TechnicianActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    public void updateReport(MapData mapData) {

        if (mapData != null) {
            reportRequest.setAddress(mapData.getAddress());
            reportRequest.setLat("" + mapData.getLatitude());
            reportRequest.setLon("" + mapData.getLongitude());
            addressEditText.setText(mapData.getAddress());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_RESULT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    MapData mapData = (MapData) bundle.getSerializable("mapData");
                    reportRequest.setAddress(mapData.getAddress());
                    reportRequest.setLat("" + mapData.getLatitude());
                    reportRequest.setLon("" + mapData.getLongitude());
                    addressEditText.setText(mapData.getAddress());
                }
            }
        }
    }
}
