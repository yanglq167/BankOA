package com.meishipintu.bankoa.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.meishipintu.bankoa.R;
import com.meishipintu.bankoa.models.entity.Task;
import com.meishipintu.bankoa.models.entity.UpClassRemind;
import com.meishipintu.bankoa.views.activities.TaskDetailActivity;
import com.meishipintu.bankoa.views.adapter.viewHolder.NoticeViewHolder;
import com.meishipintu.library.util.StringUtils;

import java.util.List;


/**
 * Created by Administrator on 2017/3/27.
 * <p>
 * 主要功能：
 */

public class RemindListAdapter extends RecyclerView.Adapter<NoticeViewHolder> {

    private Context context;
    private List<UpClassRemind> remindList;
    private RequestManager requestManager;
    private String supervisorId;
    private String supervisorLevel;

    public RemindListAdapter(Context context, List<UpClassRemind> remindList,String supervisorId,String supervisorLevel) {
        this.context = context;
        this.remindList = remindList;
        requestManager = Glide.with(context);
        this.supervisorId = supervisorId;
        this.supervisorLevel = supervisorLevel;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notic, parent, false));
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        final UpClassRemind remind = remindList.get(position);
        if ("1".equals(remind.getTask_id())) {
            requestManager.load(R.drawable.icon_process_notice).into(holder.icon);
        } else if (!StringUtils.isNullOrEmpty(remind.getUrl())) {
            requestManager.load(remind.getUrl()).into(holder.icon);
        } else {
            requestManager.load(R.drawable.icon_avatar).into(holder.icon);
        }
        holder.tvTitle.setText(remind.getNotice_title());
        holder.tvSubTitle.setText(remind.getNotice_content());
        holder.btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("task", new Task(remind.getTask_id(), remind.getUser_id()));
                if (supervisorId != null) {
                    intent.putExtra("supervisor_id", supervisorId);
                    intent.putExtra("supervisor_level", supervisorLevel);
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return remindList.size();
    }
}
