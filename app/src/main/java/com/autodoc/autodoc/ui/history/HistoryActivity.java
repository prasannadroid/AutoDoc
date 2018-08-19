package com.autodoc.autodoc.ui.history;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.adapter.HistoryAdapter;
import com.autodoc.autodoc.api.response.JobResponse;
import com.autodoc.autodoc.listeners.OnItemClickListener;
import com.autodoc.autodoc.ui.feedback.FeedBackActivity;
import com.autodoc.autodoc.ui.technician.TechnicianActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private HistoryViewModel historyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.history));

        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        getHistory();
    }

    private void getHistory() {
        historyViewModel.getJobHistory(this).observe(this, JobResponse -> {
            if (JobResponse != null) {
                HistoryAdapter technicianAdapter = new HistoryAdapter(HistoryActivity.this, JobResponse, HistoryActivity.this);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(technicianAdapter);
            }
        });
    }

    @Override
    public void onItemClick(int position, Object data) {
        JobResponse jobResponse = (JobResponse) data;
        //if (jobResponse.getStatus().equals(getString(R.string.done))) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("technicianId", jobResponse.getId());
        Intent intent = new Intent(this, FeedBackActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        // }

    }

}
