package com.autodoc.autodoc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodoc.autodoc.R;
import com.autodoc.autodoc.api.response.TechnicianResponse;
import com.autodoc.autodoc.listeners.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilabs on 8/19/18.
 */

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.MyViewHolder> {

    private List<TechnicianResponse> technicianResponseList;
    private OnItemClickListener<TechnicianResponse> mListener;
    private Context context;

    public TechnicianAdapter(Context context, List<TechnicianResponse> technicianResponseList, OnItemClickListener<TechnicianResponse> mListener) {
        this.technicianResponseList = technicianResponseList;
        this.mListener = mListener;
        this.context = context;
    }

    @Override
    public TechnicianAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_technician, parent, false);

        return new TechnicianAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TechnicianAdapter.MyViewHolder holder, int position) {
        TechnicianResponse technicianResponse = technicianResponseList.get(position);

        holder.name.setText(technicianResponse.getName());
        holder.phone.setText(technicianResponse.getPhone());
        holder.type.setText(technicianResponse.getType());
        holder.address.setText(technicianResponse.getAddress());

        /*if (technicianResponse.getJobResponseList() != null && technicianResponse.getJobResponseList().size() > 0) {
            JobResponse jobResponse = technicianResponse.getJobResponseList().get(0);

            if (jobResponse.getStatus().equals(context.getString(R.string.done))) {
                holder.status.setBackground(context.getDrawable(R.drawable.shape_status_done));
            } else if (jobResponse.getStatus().equals(context.getString(R.string.awaiting))) {
                holder.status.setBackground(context.getDrawable(R.drawable.shape_status_awaiting));
            } else if (jobResponse.getStatus().equals(context.getString(R.string.in_progress))) {
                holder.status.setBackground(context.getDrawable(R.drawable.shape_status_inprogress));
            }

            holder.status.setText(jobResponse.getStatus());
        } else {*/
            holder.status.setVisibility(View.GONE);
        //}
    }

    @Override
    public int getItemCount() {
        return technicianResponseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.phone)
        TextView phone;

        @BindView(R.id.type)
        TextView type;

        @BindView(R.id.status)
        TextView status;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(v -> {
                final int position = MyViewHolder.this.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position, technicianResponseList.get(position));

                }
            });
        }
    }

}
