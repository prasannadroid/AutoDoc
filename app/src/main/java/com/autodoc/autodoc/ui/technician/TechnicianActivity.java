package com.autodoc.autodoc.ui.technician;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.adapter.TechnicianAdapter;
import com.autodoc.autodoc.api.request.ReportRequest;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.listeners.OnItemClickListener;
import com.autodoc.autodoc.ui.history.HistoryActivity;
import com.autodoc.autodoc.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilabs on 8/19/18.
 */

public class TechnicianActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private ReportRequest reportRequest;
    private TechnicianViewModel technicianViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reportRequest = (ReportRequest) bundle.getSerializable("reportRequest");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.technicians));

        technicianViewModel = ViewModelProviders.of(this).get(TechnicianViewModel.class);
        getAllTechnicians();
    }

    private void getAllTechnicians() {
        technicianViewModel.getAllTechnicians(this).observe(this, technicianResponseList -> {
            if (technicianResponseList != null) {
                TechnicianAdapter technicianAdapter = new TechnicianAdapter(TechnicianActivity.this, technicianResponseList, TechnicianActivity.this);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(technicianAdapter);

            }
        });
    }

    @Override
    public void onItemClick(int position, Object data) {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        AppUtil.confirmAlert(dialog, this, view -> {
            dialog.dismiss();
            TechnicianResponse technicianResponse = (TechnicianResponse) data;
            reportRequest.setStatus(getString(R.string.awaiting));
            reportRequest.setTechnicianId(technicianResponse.getId());

            technicianViewModel.postJob(this, reportRequest).observe(this, isSuccess -> {
                if (isSuccess.booleanValue()) {
                    startActivity(new Intent(TechnicianActivity.this, HistoryActivity.class));


                }
            });
        }, view -> {
            dialog.dismiss();
        });


    }
}
