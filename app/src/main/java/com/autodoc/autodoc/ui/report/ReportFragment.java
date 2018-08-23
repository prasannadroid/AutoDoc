package com.autodoc.autodoc.ui.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.data.MapData;
import com.autodoc.autodoc.ui.map.MapsActivity;
import com.autodoc.autodoc.ui.technician.TechnicianActivity;
import com.autodoc.autodoc.util.AppUtil;
import com.autodoc.autodoc.util.MyTextWatcher;

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

    @BindView(R.id.typeButton)
    Button typeButton;

    private final static int ACTIVITY_RESULT = 123;
    private ReportRequest reportRequest;
    private String seletedType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, null);
        ButterKnife.bind(this, view);
        reportRequest = new ReportRequest();

        repairNameNameEditText.addTextChangedListener(new MyTextWatcher(repairNameTextInputLayout, getString(R.string.invalid_input)));
        descriptionEditText.addTextChangedListener(new MyTextWatcher(descriptionTextInputLayout, getString(R.string.invalid_input)));
        addressEditText.addTextChangedListener(new MyTextWatcher(addressTextInputLayout, getString(R.string.invalid_input)));

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

        if (TextUtils.isEmpty(seletedType)) {
            final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
            AppUtil.standardAlert(dialog, getActivity().getString(R.string.msg_error), getActivity().getString(R.string.please_select_technician_type), v -> {
                        dialog.dismiss();
                    },
                    getActivity().getResources().getString(R.string.text_ok));
            return;
        }

        reportRequest.setRepairName(repairName);
        reportRequest.setJobDescription(description);
        reportRequest.setType(seletedType);

        Bundle bundle = new Bundle();
        bundle.putSerializable("reportRequest", reportRequest);
        Intent intent = new Intent(getActivity(), TechnicianActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);

        repairNameNameEditText.setText("");
        descriptionEditText.setText("");
        addressEditText.setText("");
        typeButton.setText(getString(R.string.technician_type));
        seletedType = null;

        repairNameTextInputLayout.setErrorEnabled(false);
        descriptionTextInputLayout.setErrorEnabled(false);
        addressTextInputLayout.setErrorEnabled(false);

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

    @OnClick(R.id.typeButton)
    void initTypeDialog() {
        String[] myResArray = getResources().getStringArray(R.array.typeArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.type));
        builder.setItems(myResArray, (dialogInterface, position) -> {
            seletedType = myResArray[position];
            typeButton.setText(seletedType);
            dialogInterface.dismiss();

        });

        builder.show();
    }
}
