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
import com.zhangqie.shoppingcart.dao.SheetHeaderDao;
import com.zhangqie.shoppingcart.dao.TreeDao;
import com.zhangqie.shoppingcart.model.SheetHeader;
import com.zhangqie.shoppingcart.model.TreeModel;

public class TreeChildExpandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ListView listView;
    public SheetHeader sheet;

    SheetHeaderDao sheetHeaderDao;
    TreeDao treeDao;

    public TreeChildExpandAdapter(Context context, ListView listView, SheetHeader sheet) {
        super();
        treeDao = new TreeDao();
        sheetHeaderDao = new SheetHeaderDao();
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
        final TreeChildExpandAdapter.ViewHolder1 viewHolder1;
        final TreeModel curr = sheet.getAllData().get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tree_list_child_item, null);
            viewHolder1 = new TreeChildExpandAdapter.ViewHolder1(convertView, groupPosition, position);
            convertView.setTag(viewHolder1);
        } else {
            viewHolder1 = (ViewHolder1) convertView.getTag();
        }


        //关键语句，使用自己写的类来对frontView的ontouch事件复写，实现视图滑动效果


        viewHolder1.jinJie.setText("径阶 " + curr.getJinJie());
        viewHolder1.btnNum.setText("" + curr.getNum());
        viewHolder1.width1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestWidth1(String.valueOf(viewHolder1.width1.getText()));
                treeDao.save(curr);

            }
        });
        viewHolder1.width2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestWidth2(String.valueOf(viewHolder1.width2.getText()));
                treeDao.save(curr);
            }
        });
        viewHolder1.width3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestWidth3(String.valueOf(viewHolder1.width3.getText()));
                treeDao.save(curr);

            }
        });
        viewHolder1.height1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestHight1(String.valueOf(viewHolder1.height1.getText()));
                treeDao.save(curr);
            }
        });
        viewHolder1.height2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestHight2(String.valueOf(viewHolder1.height2.getText()));
                treeDao.save(curr);
            }
        });
        viewHolder1.height3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                curr.setTestHight3(String.valueOf(viewHolder1.height3.getText()));
                treeDao.save(curr);
            }
        });

        viewHolder1.width1.setText(curr.getTestWidth1());
        viewHolder1.width2.setText(curr.getTestWidth2());
        viewHolder1.width3.setText(curr.getTestWidth3());

        viewHolder1.height1.setText(curr.getTestHight1());
        viewHolder1.height2.setText(curr.getTestHight2());
        viewHolder1.height3.setText(curr.getTestHight3());

        return convertView;
    }

    class ViewHolder1 implements View.OnClickListener {
        private int groupPosition;
        private int position;
        private TextView jinJie;

        private Button btnAdd;
        private Button btnNum;
        private Button btnClose;
        private EditText width1, width2, width3, height1, height2, height3;


        public ViewHolder1(View view, int groupPosition, int position) {
            this.groupPosition = groupPosition;
            this.position = position;
            btnAdd = (Button) view.findViewById(R.id.item_chlid_add);
            btnAdd.setOnClickListener(this);
            btnNum = (Button) view.findViewById(R.id.item_chlid_num);
            btnClose = (Button) view.findViewById(R.id.item_chlid_close);
            btnClose.setOnClickListener(this);

            jinJie = view.findViewById(R.id.jinJie);
            width1 = view.findViewById(R.id.width1);
            width2 = view.findViewById(R.id.width2);
            width3 = view.findViewById(R.id.width3);
            height1 = view.findViewById(R.id.height1);
            height2 = view.findViewById(R.id.height2);
            height3 = view.findViewById(R.id.height3);
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

            TreeChildExpandAdapter.this.notifyDataSetChanged();

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
                default:
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
        final TreeChildExpandAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_list_group_item, null);
            viewHolder = new TreeChildExpandAdapter.ViewHolder(convertView, groupPosition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TreeChildExpandAdapter.ViewHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
            viewHolder.textTopBar.setVisibility(View.GONE);
        }
//        CartInfo.DataBean dataBean = (CartInfo.DataBean) getGroup(groupPosition);
        String title = String.format("样带编号:%s GPS:%s", sheet.getSheetNo(), sheet.getGps());
        viewHolder.textView.setText(title);
        viewHolder.btnNum.setText(sheet.getYdmnum());
        viewHolder.checkBox.setVisibility(View.GONE);

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

    class ViewHolder implements View.OnClickListener {
        CheckBox checkBox;
        TextView textView;
        TextView textTopBar;
        private int groupPosition, position;
        private Button btnAdd;
        private Button btnNum;
        private Button btnClose;

        public ViewHolder(View view, int groupPosition) {
            this.groupPosition = groupPosition;
            textView = (TextView) view.findViewById(R.id.shop_name);
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
            textTopBar = (TextView) view.findViewById(R.id.item_group_topbar);
            btnAdd = (Button) view.findViewById(R.id.item_chlid_add);
            btnAdd.setOnClickListener(this);
            btnNum = (Button) view.findViewById(R.id.item_chlid_num);
            btnClose = (Button) view.findViewById(R.id.item_chlid_close);
            btnClose.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mVal = Integer.valueOf(btnNum.getText().toString());
            switch (v.getId()) {
                case R.id.item_chlid_add:
                    try {
                        btnNum.setText((mVal + 1) + "");
                        sheet.setYdmnum(btnNum.getText().toString());
                        sheetHeaderDao.save(sheet);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), "Save Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.item_chlid_close:
                    if (mVal >= 1) {
                        try {
                            btnNum.setText((mVal - 1) + "");
                            sheet.setYdmnum(btnNum.getText().toString());
                            sheetHeaderDao.save(sheet);
                        } catch (Exception e) {
                            Toast.makeText(v.getContext(), "Save Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    break;
                default:
                    break;
            }
        }

    }
}
