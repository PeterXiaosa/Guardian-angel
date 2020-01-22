package com.peter.guardianangel.activity;

import android.content.Intent;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.peter.guardianangel.BaseActivity;
import com.peter.guardianangel.R;
import com.peter.guardianangel.adapter.ExPandableListViewAdapter;
import com.peter.guardianangel.bean.ChildrenData;
import com.peter.guardianangel.bean.ParentData;
import com.peter.guardianangel.view.MyToolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluateListActivity extends BaseActivity implements ExPandableListViewAdapter.IExPandableListViewItemClick{

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.activity_evalute_list_elv_list)
    ExpandableListView expandableListView;
    @BindView(R.id.activity_evalute_list_toolbar)
    MyToolbar toolbar;

    private List<ParentData> dataList = new ArrayList<>();

    ExPandableListViewAdapter exPandableListViewAdapter;
    private int mMonth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_list;
    }

    @Override
    protected void initData() {
        super.initData();

        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dataList.clear();
        for (int i =0; i < mMonth; i++) {
            ParentData parentData = new ParentData();
            List<ChildrenData> list = new ArrayList<>();
            int week = (day -1) / 7;
            for (int j = 0; j < week + 1;j++) {
                ChildrenData childrenData = new ChildrenData();
                childrenData.setMonth(i + 1);
                childrenData.setWeek(j + 1);
                childrenData.setStart(1 + j*7);
                if (j != 3) {
                    childrenData.setEnd(7*(j+1));
                } else {
                    childrenData.setEnd(getCurrentMonthLastDay());
                }
                list.add(childrenData);
            }

            parentData.setMonth(i + 1);
            parentData.setChildrenDataList(list);
            dataList.add(parentData);
        }
    }

    @Override
    protected void initView() {
        super.initView();

        exPandableListViewAdapter = new ExPandableListViewAdapter(this, dataList);
        exPandableListViewAdapter.setExPandableListViewItemClick(this);
        expandableListView.setAdapter(exPandableListViewAdapter);

        expandableListView.expandGroup(mMonth - 1);

        toolbar.setToolbarClick(new MyToolbar.IToolbarClick() {
            @Override
            public void clickIcon(boolean isLeft, int position) {
                if (isLeft && position == 1) {
                    finish();
                }
            }
        });
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    @Override
    public void onChildItemClick(int groupPosition, int childPosition) {
        Intent intent = new Intent(EvaluateListActivity.this, EvaluateActivity.class);
        ChildrenData childrenData = dataList.get(groupPosition).getChildrenDataList().get(childPosition);
        intent.putExtra("data", childrenData);
        startActivity(intent);
    }

    @Override
    public void onParentItemClick(int groupPosition) {

    }
}
