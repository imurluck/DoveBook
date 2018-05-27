package com.example.dovebook.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.model.Friend;
import com.example.dovebook.images.ImageManager;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zyd on 2018/4/13.
 */

public abstract class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder> {

    private static String TAG = "contactAdapter";
    OnMenuClickListener onMenuClickListener;
    public List<Friend> mUserList;

    public contactAdapter(Context context) {
        mUserList = new ArrayList<Friend>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_recycler_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.firstChar.setText(getFirstChar(position));
        if (position == 0) {
            holder.firstChar.setVisibility(View.VISIBLE);
        } else if (!isFirstCharSame(position, position - 1)) {
            holder.firstChar.setVisibility(View.VISIBLE);
        } else {
            holder.firstChar.setVisibility(View.GONE);
        }
        if (position + 1 < getItemCount() && !isFirstCharSame(position, position + 1)) {
            holder.Line_view.setVisibility(View.GONE);
        } else {
            holder.Line_view.setVisibility(View.VISIBLE);
        }
        ImageManager.getInstance().loadImage(getContext(), mUserList.get(position).getUserAvatarPath(), holder.image);
        holder.name.setText(mUserList.get(position).getUserName());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onMenuClickListener.onClick(mUserList.get(position),view);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        RelativeLayout mainContent;
        TextView firstChar;
        CircleImageView image;
        TextView name;
        View Line_view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            firstChar = (TextView) view.findViewById(R.id.head);
            image = (CircleImageView) view.findViewById(R.id.circle_image);
            name = (TextView) view.findViewById(R.id.text_name);
            Line_view = (View) view.findViewById(R.id.line_view);
            mainContent=(RelativeLayout)view.findViewById(R.id.mainContent);
        }
    }

    /**
     * 判断两位置的字符串首字母是否相等
     */
    public boolean isFirstCharSame(int position1, int position2) {
        if (getFirstChar(position1).equals(getFirstChar(position2))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前位置的字符串首字母
     */
    public String getFirstChar(int position) {
        if (mUserList.get(position).getUserName().charAt(0) >= 'a' && mUserList.get(position).getUserName().charAt(0) <= 'z' || mUserList.get(position).getUserName().charAt(0) >= 'A' && mUserList.get(position).getUserName().charAt(0) <= 'Z') {
            return (mUserList.get(position).getUserName().toUpperCase()).substring(0, 1);//如果首字母为'A'--'z'返回该字母大写
        } else {
            return "#";//否则返回'#'
        }
    }


    public void add(List<Friend> list) {
        if (list != null && list.size() > 0) {
            int start=mUserList.size();
            mUserList.addAll(list);
            notifyItemRangeInserted(start, list.size());
        }
    }

    public void clearThenAddAll(List<Friend> friendList){
        for(int i=0;i<friendList.size();i++){
            Log.d(TAG, "clearThenAddAll: "+friendList.get(i).getUserName());
        }
        if(mUserList!=null) {
            mUserList.clear();
            notifyDataSetChanged();
            mUserList.addAll(friendList);
            notifyItemRangeInserted(0, friendList.size());
        }
    }

    abstract public Context getContext();

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener){
        this.onMenuClickListener=onMenuClickListener;
    }

    public interface OnMenuClickListener{
        void onClick(Friend friend,View view);
    }

}
