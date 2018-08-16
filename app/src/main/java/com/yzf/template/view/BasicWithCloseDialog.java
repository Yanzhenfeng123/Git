/**
 * @param
 */
package com.yzf.template.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yzf.template.R;
import com.yzf.template.utils.AppUtil;

;


/**
 * 基本样式 Dialog
 *
 * @author Yzf
 * @date 2018-7-31 11:20:46
 */
public class BasicWithCloseDialog extends Dialog {

    public BasicWithCloseDialog(Context context) {
        super(context);
    }

    public BasicWithCloseDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String message;
        private String title;
        private String phone;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int phone) {
            this.phone = (String) context.getText(phone);
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setPhone(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public BasicWithCloseDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final BasicWithCloseDialog dialog = new BasicWithCloseDialog(context, R.style.my_dialog);

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(50, 50, 50, 50);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);

            View layout = inflater.inflate(R.layout.dialog_custom_with_close, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.popup_content_commit)).setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    (layout.findViewById(R.id.popup_content_commit))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.popup_content_commit).setVisibility(
                        View.GONE);
            }

            // set the cancel button  
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.popup_content_dismiss)).setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    (layout.findViewById(R.id.popup_content_dismiss))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE  
                layout.findViewById(R.id.popup_content_dismiss).setVisibility(
                        View.GONE);
            }

            // set the title
            if (title != null) {
                ((TextView) layout.findViewById(R.id.popup_title)).setText(title);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.popup_content)).setText(message);
                if (phone != null) {
                    AppUtil.setTextViewColor((TextView) layout.findViewById(R.id.popup_content), phone, context.getResources().getColor(R.color.theme_color));
                    ((TextView) layout.findViewById(R.id.popup_content)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                            //启动
                            context.startActivity(phoneIntent);
                        }
                    });
                }
            }

            (layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }
}  