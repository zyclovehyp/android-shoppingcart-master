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
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;
import com.zhangqie.shoppingcart.widget.FrontViewToMove;
import com.zhangqie.shoppingcart.widget.ZQRoundOvalImageView;

public class TreeExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ListView listView;
    public SheetHeader sheet;

    TreeDao treeDao;

    public TreeExpandAdapter(Context context, ListView listView, SheetHeader sheet) {
        super();
        treeDao = new TreeDao();
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


        viewHolder1.textView.setText("径阶 " + sheet.getAllData().get(position).getJinJie());
        viewHolder1.btnNum.setText("" + sheet.getAllData().get(position).getNum());
        viewHolder1.zqRoundOvalImageView.setType(ZQRoundOvalImageView.TYPE_ROUND);
        viewHolder1.zqRoundOvalImageView.setRoundRadius(8);
        viewHolder1.item_chlid_content1.setText(
                String.format("%s ：%s", sheet.getAllData().get(position).getTestWidth1()
                        , sheet.getAllData().get(position).getTestHight1()));

        viewHolder1.item_chlid_content2.setText(
                String.format("%s ：%s", sheet.getAllData().get(position).getTestWidth2()
                        , sheet.getAllData().get(position).getTestHight2()));

        viewHolder1.item_chlid_content3.setText(
                String.format("%s ：%s", sheet.getAllData().get(position).getTestWidth3()
                        , sheet.getAllData().get(position).getTestHight3()));
        viewHolder1.checkBox.setVisibility(View.GONE);
        viewHolder1.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenterModel)
                    onClickListenterModel.onItemClick(viewHolder1.checkBox.isChecked(), v, groupPosition, position);
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
        private FrontViewToMove frontViewToMove;


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
            frontViewToMove = new FrontViewToMove(frontView, listView, 200, sheet.getAllData().get(position));
            if (sheet.getAllData().get(position).isHasMoved()) {
                frontViewToMove.setDownX(sheet.getAllData().get(position).getDownX());
                frontViewToMove.setHasMoved(sheet.getAllData().get(position).isHasMoved());
                frontViewToMove.generateRevealAnimate(frontView, -200);
            }
            btnClose.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        private void changeTagText() {

            String widthStr1 = width1.getText().toString();
            String widthStr2 = width2.getText().toString();
            String widthStr3 = width3.getText().toString();

            String heightStr1 = height1.getText().toString();
            String heightStr2 = height2.getText().toString();
            String heightStr3 = height3.getText().toString();


            widthStr1 = "".equals(widthStr1) ? "0" : widthStr1;
            widthStr2 = "".equals(widthStr2) ? "0" : widthStr2;
            widthStr3 = "".equals(widthStr3) ? "0" : widthStr3;


            heightStr1 = "".equals(heightStr1) ? "0" : heightStr1;
            heightStr2 = "".equals(heightStr2) ? "0" : heightStr2;
            heightStr3 = "".equals(heightStr3) ? "0" : heightStr3;


            sheet.getAllData().get(position).setTestHight1(heightStr1);
            sheet.getAllData().get(position).setTestHight2(heightStr2);
            sheet.getAllData().get(position).setTestHight3(heightStr3);

            sheet.getAllData().get(position).setTestWidth1(widthStr1);
            sheet.getAllData().get(position).setTestWidth2(widthStr2);
            sheet.getAllData().get(position).setTestWidth3(widthStr3);

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
                case R.id.btn_delete:
                    changeTagText();
                    frontViewToMove.generateRevealAnimate(frontView, 0);
                    sheet.getAllData().get(position).setDownX(frontViewToMove.getDownX());
                    sheet.getAllData().get(position).setHasMoved(false);
                    treeDao.save(sheet.getAllData().get(position));
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
