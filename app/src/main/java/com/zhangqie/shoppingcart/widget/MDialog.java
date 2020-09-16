package com.zhangqie.shoppingcart.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import com.zhangqie.shoppingcart.R;
import com.zhangqie.shoppingcart.model.AreaModel;


public class MDialog extends Dialog {

    public MDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置 Dialog的大小
     *
     * @param x 宽比例
     * @param y 高比例
     */
    public void setDialogWindowAttr(double x, double y, Activity activity) {
        if (x < 0 || x > 1 || y < 0 || y > 1) {
            return;
        }
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (width * x);
        lp.height = (int) (height * y);
        this.getWindow().setAttributes(lp);
    }

    public static class Builder {

        private View layout;
        private Context context;
        private MDialog dialog;

        ImageButton closeBtn;
        private boolean state = false;
        Button save_btn;
        ItemGroup igWidth, igHeight, igP;

        OnSaveClick saveClick;

        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            this.context = context;
            dialog = new MDialog(context, R.style.mDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_view, null);
            closeBtn = (ImageButton) layout.findViewById(R.id.imb_dialog_select_close);
            save_btn = layout.findViewById(R.id.save_btn);
            igWidth = layout.findViewById(R.id.ig_width);
            igHeight = layout.findViewById(R.id.ig_height);
            igP = layout.findViewById(R.id.ig_p);

            igWidth.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
            igHeight.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
            igP.getContentEdt().setInputType(InputType.TYPE_CLASS_NUMBER);


            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != saveClick)
                        saveClick.onSave(dialog, igWidth, igHeight, igP);
                }
            });
        }

        public interface OnSaveClick {
            void onSave(MDialog dialog, ItemGroup igWidth, ItemGroup igHeight, ItemGroup igP);
        }

        public void setSaveClick(OnSaveClick saveClick) {
            this.saveClick = saveClick;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private MDialog create() {
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            dialog.setContentView(layout);
            //用户可以点击手机Back键取消对话框显示
            dialog.setCancelable(true);
            //用户不能通过点击对话框之外的地方取消对话框显示
            dialog.setCanceledOnTouchOutside(false);
            return dialog;

        }

        public void setData(AreaModel areaModel) {

            this.igWidth.setText("" + areaModel.getWidth());
            this.igHeight.setText("" + areaModel.getHeight());
            this.igP.setText("" + areaModel.getP());
        }

        public MDialog show() {
            create();
            dialog.show();
            return dialog;
        }

    }
}
