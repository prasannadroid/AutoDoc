package com.autodoc.autodoc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.response.JobResponse;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.listeners.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilabs on 8/19/18.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<JobResponse> historyResponseList;
    private OnItemClickListener<JobResponse> mListener;
    private Context context;

    public HistoryAdapter(Context context, List<JobResponse> historyResponseList, OnItemClickListener<JobResponse> mListener) {
        this.historyResponseList = historyResponseList;
        this.mListener = mListener;
        this.context = context;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_history, parent, false);

        return new HistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        JobResponse jobResponse = historyResponseList.get(position);

        holder.name.setText(jobResponse.getName());
        holder.createdDate.setText(jobResponse.getCreatedDate());
        holder.jobDescription.setText(jobResponse.getJobDescription());
        holder.address.setText(jobResponse.getAddress());

        if (jobResponse.getStatus().equals(context.getString(R.string.done))) {
            holder.status.setBackground(context.getDrawable(R.drawable.shape_status_done));
        } else if (jobResponse.getStatus().equals(context.getString(R.string.awaiting))) {
            holder.status.setBackground(context.getDrawable(R.drawable.shape_status_awaiting));
        } else if (jobResponse.getStatus().equals(context.getString(R.string.in_progress))) {
            holder.status.setBackground(context.getDrawable(R.drawable.shape_status_inprogress));
        }

        holder.status.setText(jobResponse.getStatus());

    }

    @Override
    public int getItemCount() {
        return historyResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.createdDate)
        TextView createdDate;

        @BindView(R.id.jobDescription)
        TextView jobDescription;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.status)
        TextView status;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(v -> {
                final int position = HistoryAdapter.MyViewHolder.this.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position, historyResponseList.get(position));

                }
            });
        }
    }
}