package com.example.charleskim.sortlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CharlesKim on 2015/10/20.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> list=null;
    private Context mContext;

    public SortAdapter(Context context,List<SortModel> list){
        this.mContext=context;
        this.list=list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<SortModel> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public int getCount(){
        return this.list.size();
    }

    public Object getItem(int position){
        return list.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position,View view,ViewGroup viewGroup){
        ViewHolder viewHolder=null;
        final SortModel mContent=list.get(position);

        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(mContext).inflate(R.layout.item,null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section=getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position==getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        }else {
            viewHolder.tvLetter.setVisibility(View.INVISIBLE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());

        return view;
    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section){
        for (int i=0;i<getCount();i++){
            String sortStr=list.get(i).getSortLetters();
            char fistChaar=sortStr.toUpperCase().charAt(0);
            if (fistChaar==section){
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
