package com.zhangqie.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangqie.shoppingcart.R;
import com.zhangqie.shoppingcart.callback.OnClickAddCloseListenter;
import com.zhangqie.shoppingcart.callback.OnClickDeleteListenter;
import com.zhangqie.shoppingcart.callback.OnClickListenterModel;
import com.zhangqie.shoppingcart.callback.OnViewItemClickListener;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.widget.FrontViewToMove;
import com.zhangqie.shoppingcart.widget.ZQRoundOvalImageView;

public class TreeExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ListView listView;
    public SheetHeader sheet;

    public TreeExpandAdapter(Context context, ListView listView, SheetHeader sheet) {
        super();
        this.context = context;
        this.listView = listView;
        this.sheet = sheet;
    }

    @Override
    public Object getChild(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return sheet.getAllData().size();
    }

    @Override
    public long getChildId(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int position,
                             boolean arg2, View convertView, ViewGroup parent) {
        final TreeExpandAdapter.ViewHolder1 viewHolder1;
        convertView = LayoutInflater.from(context).inflate(R.layout.cart_list_child_item, null);
        viewHolder1 = new TreeExpandAdapter.ViewHolder1(convertView, groupPosition, position);

        //关键语句，使用自己写的类来对frontView的ontouch事件复写，实现视图滑动效果
        new FrontViewToMove(viewHolder1.frontView, listView, 480);
//        viewHolder1.textView.setText(list.get(groupPosition).getItems().get(position).getTitle());
        viewHolder1.textView.setText("径阶 " + sheet.getAllData().get(position).getJinJie());
//        viewHolder1.checkBox.setChecked(list.get(groupPosition).getItems().get(position).ischeck());
//        viewHolder1.tvMoney.setText("¥ " + list.get(groupPosition).getItems().get(position).getPrice());
        viewHolder1.btnNum.setText("" + sheet.getAllData().get(position).getNum());
        viewHolder1.zqRoundOvalImageView.setType(ZQRoundOvalImageView.TYPE_ROUND);
        viewHolder1.zqRoundOvalImageView.setRoundRadius(8);

        /*Glide.with(context).load(list.get(groupPosition).getItems().get(position).getImage())
                .placeholder(R.mipmap.wood)
                .error(R.mipmap.wood).into(viewHolder1.zqRoundOvalImageView);*/

        viewHolder1.checkBox.setVisibility(View.GONE);
        viewHolder1.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenterModel)
                    onClickListenterModel.onItemClick(viewHolder1.checkBox.isChecked(), v, groupPosition, position);
            }
        });
        viewHolder1.button.setOnClickListener(new View.OnClickListener() {
            // 为button绑定事件，可以用此按钮来实现删除事件
            @Override
            public void onClick(View v) {
               /* if (null != onClickDeleteListenter)
                    onClickDeleteListenter.onItemClick(v, groupPosition, position);*/
                viewHolder1.changeTagText();
                new FrontViewToMove(viewHolder1.frontView, listView, 480).generateRevealAnimate(viewHolder1.frontView, 0);
            }
        });
        return convertView;
    }

    class ViewHolder1 implements View.OnClickListener {
        private int groupPosition;
        private int position;
        private TextView textView, item_chlid_content1, item_chlid_content2, item_chlid_content3;
        private View frontView;
        private Button button;
        private CheckBox checkBox;
        private ZQRoundOvalImageView zqRoundOvalImageView;
        private TextView tvMoney;
        private Button btnAdd;
        private Button btnNum;
        private Button btnClose;
        private EditText width1, width2, width3, height1, height2, height3;


        public ViewHolder1(View view, int groupPosition, int position) {
            this.groupPosition = groupPosition;
            this.position = position;
            zqRoundOvalImageView = (ZQRoundOvalImageView) view.findViewById(R.id.item_chlid_image);
            textView = (TextView) view.findViewById(R.id.item_chlid_name);
            checkBox = (CheckBox) view.findViewById(R.id.item_chlid_check);
            button = (Button) view.findViewById(R.id.btn_delete);
            frontView = view.findViewById(R.id.id_front);
            tvMoney = (TextView) view.findViewById(R.id.item_chlid_money);
            btnAdd = (Button) view.findViewById(R.id.item_chlid_add);
            btnAdd.setOnClickListener(this);
            btnNum = (Button) view.findViewById(R.id.item_chlid_num);
            btnClose = (Button) view.findViewById(R.id.item_chlid_close);
            item_chlid_content1 = view.findViewById(R.id.item_chlid_content1);
            item_chlid_content2 = view.findViewById(R.id.item_chlid_content2);
            item_chlid_content3 = view.findViewById(R.id.item_chlid_content3);
            width1 = view.findViewById(R.id.width1);
            width2 = view.findViewById(R.id.width2);
            width3 = view.findViewById(R.id.width3);
            height1 = view.findViewById(R.id.height1);
            height2 = view.findViewById(R.id.height2);
            height3 = view.findViewById(R.id.height3);


            btnClose.setOnClickListener(this);
        }

        private void changeTagText() {

            String widthStr1 = width1.getText().toString();
            String widthStr2 = width2.getText().toString();
            String widthStr3 = width3.getText().toString();

            String heightStr1 = height1.getText().toString();
            String heightStr2 = height2.getText().toString();
            String heightStr3 = height3.getText().toString();


            widthStr1="".equals(widthStr1)?"0":widthStr1;
            widthStr2="".equals(widthStr2)?"0":widthStr2;
            widthStr3="".equals(widthStr3)?"0":widthStr3;


            heightStr1="".equals(widthStr3)?"0":widthStr3;





            sheet.getAllData().get(position).setTestHight1(Double.parseDouble(heightStr1));
            sheet.getAllData().get(position).setTestHight2(Double.parseDouble(heightStr2));
            sheet.getAllData().get(position).setTestHight3(Double.parseDouble(heightStr3));

            sheet.getAllData().get(position).setTestWidth1(Double.parseDouble(widthStr1));
            sheet.getAllData().get(position).setTestWidth2(Double.parseDouble(widthStr2));
            sheet.getAllData().get(position).setTestWidth3(Double.parseDouble(widthStr3));

            TreeExpandAdapter.this.notifyDataSetChanged();

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_chlid_add:
                    onClickAddCloseListenter.onItemClick(v, 2, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
                case R.id.item_chlid_close:
                    onClickAddCloseListenter.onItemClick(v, 1, groupPosition, position, Integer.valueOf(btnNum.getText().toString()));
                    break;
            }
        }
    }

    // CheckBox1接口的方法
    private OnViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    // CheckBox2接口的方法
    private OnClickListenterModel onClickListenterModel = null;

    public void setOnClickListenterModel(OnClickListenterModel listener) {
        this.onClickListenterModel = listener;
    }

    // 删除接口的方法
    private OnClickDeleteListenter onClickDeleteListenter = null;

    public void setOnClickDeleteListenter(OnClickDeleteListenter listener) {
        this.onClickDeleteListenter = listener;
    }

    // 数量接口的方法
    private OnClickAddCloseListenter onClickAddCloseListenter = null;

    public void setOnClickAddCloseListenter(OnClickAddCloseListenter listener) {
        this.onClickAddCloseListenter = listener;
    }


    @Override
    public int getChildrenCount(int arg0) {
        // TODO Auto-generated method stub
        return (sheet.getAllData() != null && sheet.getAllData().size() > 0) ? sheet.getAllData().size() : 0;
    }

    @Override
    public Object getGroup(int arg0) {
        // TODO Auto-generated method stub
        return sheet;
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final TreeExpandAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_list_group_item, null);
            viewHolder = new TreeExpandAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TreeExpandAdapter.ViewHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
            viewHolder.textTopBar.setVisibility(View.GONE);
        }
//        CartInfo.DataBean dataBean = (CartInfo.DataBean) getGroup(groupPosition);
        String title = String.format("样带编号:%s GPS:%s", sheet.getSheetNo(), sheet.getGps());
        viewHolder.textView.setText(title);
       /* viewHolder.checkBox.setChecked(dataBean.ischeck());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(viewHolder.checkBox.isChecked(), v, groupPosition);
            }
        });*/
        viewHolder.checkBox.setVisibility(View.GONE);
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击标题", Toast.LENGTH_LONG).show();

            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    class ViewHolder {
        CheckBox checkBox;
        TextView textView;
        TextView textTopBar;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.shop_name);
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
            textTopBar = (TextView) view.findViewById(R.id.item_group_topbar);
        }
    }
}
