package com.zhangqie.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangqie.shoppingcart.R;
import com.zhangqie.shoppingcart.callback.OnViewItemClickListener;
import com.zhangqie.shoppingcart.model.SheetHeader;

import java.util.List;

public class SheetHeaderAdapter extends BaseAdapter {

    private List<SheetHeader> list;

    public List<SheetHeader> getList() {
        return list;
    }

    public void setList(List<SheetHeader> list) {
        this.list = list;
    }

    private LayoutInflater mInflater;

    private Context context;

    private OnViewItemClickListener onViewItemClickListener;

    public SheetHeaderAdapter(Context context, List<SheetHeader> list) {

        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void setOnViewItemClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getSheetId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        SheetHeader sheet = list.get(position);


        String showText = String.format("%s   %s", sheet.getSheetNo()
                 , sheet.getGps());
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView =
                    mInflater.inflate(R.layout.cart_list_group_item, null);


            viewHolder = new ViewHolder(convertView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(showText);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.checkBox.setChecked(list.get(position).isCheck());
        final View finalConvertView = convertView;
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onViewItemClickListener)
                    onViewItemClickListener.onItemClick(list.get(position).isCheck(), finalConvertView, position);
            }
        });
        if (position == 0) {
            viewHolder.item_group_topbar.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onViewItemClickListener)
                    onViewItemClickListener.onItemClick(list.get(position).isCheck(), finalConvertView, position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        CheckBox checkBox;
        TextView textView;
        TextView item_group_topbar;

        public ViewHolder(View view) {
            this.checkBox = view.findViewById(R.id.check_box);
            this.textView = view.findViewById(R.id.shop_name);
            this.item_group_topbar = view.findViewById(R.id.item_group_topbar);
        }
    }
}
