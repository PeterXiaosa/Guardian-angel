package com.peter.guardianangel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.peter.guardianangel.R;
import com.peter.guardianangel.bean.ChildrenData;
import com.peter.guardianangel.bean.ParentData;
import com.peter.guardianangel.view.IconFont;

import java.util.List;

public class ExPandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ParentData> dataList;
    IExPandableListViewItemClick iExPandableListViewItemClick;

    public interface IExPandableListViewItemClick {
        void onChildItemClick(int groupPosition, int childPosition);

        void onParentItemClick(int groupPosition);
    }

    public ExPandableListViewAdapter(Context context, List<ParentData> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    public void setExPandableListViewItemClick(IExPandableListViewItemClick exPandableListViewItemClick) {
        this.iExPandableListViewItemClick = exPandableListViewItemClick;
    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).getChildrenDataList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getChildrenDataList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.partent_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvName = convertView.findViewById(R.id.tvName);
            groupViewHolder.iconFont = convertView.findViewById(R.id.if_arrow);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        int month = dataList.get(groupPosition).getMonth();
        groupViewHolder.tvName.setText(String.format("第%d月", month));
        // 根据当前父条目的展开状态来设置不同的图片
        if (isExpanded) {
            groupViewHolder.iconFont.setText(mContext.getResources().getString(R.string.arrow_up));
        } else {
            groupViewHolder.iconFont.setText(mContext.getResources().getString(R.string.arrow_down));
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvName = convertView.findViewById(R.id.child_tvName);
            childViewHolder.tvTime = convertView.findViewById(R.id.child_tvTime);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        ChildrenData childrenData = dataList.get(groupPosition).getChildrenDataList().get(childPosition);
        int start = childrenData.getStart();
        int end = childrenData.getEnd();
        int month = childrenData.getMonth();

        childViewHolder.tvName.setText(String.format("第%d次", childrenData.getWeek()));
        String startStr, endStr;
        startStr = String.format("%s.%s", formatData(month), formatData(start));
        endStr = String.format("%s.%s", formatData(month), formatData(end));
        childViewHolder.tvTime.setText(String.format("%s~%s", startStr, endStr));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iExPandableListViewItemClick != null) {
                    iExPandableListViewItemClick.onChildItemClick(groupPosition, childPosition);
                }
            }
        });

        return convertView;
    }

    public String formatData(int num) {
        if (num <10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView tvName, tvMoney;
        IconFont iconFont;
        CheckBox checkbox;
    }

    static class ChildViewHolder {
        TextView tvName, tvTime;
    }
}
