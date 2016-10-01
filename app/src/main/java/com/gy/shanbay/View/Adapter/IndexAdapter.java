package com.gy.shanbay.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gy.shanbay.Model.Entity.Article;
import com.gy.shanbay.R;

/**
 * Created by gy939 on 2016/9/29.
 */
public class IndexAdapter extends BaseExpandableListAdapter{

    private Context mContext;
    private Article article;
    private Button groupButton;

    public IndexAdapter(Context mContext, Article article) {
        this.mContext = mContext;
        this.article = article;
    }

    @Override
    public int getGroupCount() {
        return article.getUnits().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return article.getUnits().get(groupPosition).getLessons().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return article.getUnits().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return article.getUnits().get(groupPosition).getLessons().get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.groupitem, null);
            groupHolder = new GroupHolder();
            groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_indicator);
            groupHolder.groupText = (TextView) convertView.findViewById(R.id.tv_group_text);
            convertView.setTag(groupHolder);
        }else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.groupImg.setBackgroundResource(R.drawable.downarrow);
        }else {
            groupHolder.groupImg.setBackgroundResource(R.drawable.rightarrow);
        }
        groupHolder.groupText.setText(article.getUnits().get(groupPosition).getTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.childitem, null);
            childHolder = new ChildHolder();
            childHolder.childText = (TextView) convertView.findViewById(R.id.tv_child_text);
            convertView.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.childText.setText(article.getUnits().get(groupPosition).getLessons().get(childPosition).getTitle());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupHolder
    {
        ImageView groupImg;
        TextView groupText;
    }
    private class ChildHolder
    {
        TextView childText;
    }
}
