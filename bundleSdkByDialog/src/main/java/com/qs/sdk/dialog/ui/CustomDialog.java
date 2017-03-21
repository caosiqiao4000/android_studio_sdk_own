package com.qs.sdk.dialog.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.sdk.dialog.R;

/**
 * Create custom Dialog windows for your application Custom dialogs rely on custom layouts wich allow you to create and use your own look & feel.
 * <p/>
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * demo
 * builder = new CustomDialog.Builder(setCreateContext());
 * String a = "";
 * if (null != args) {
 * a = args.getString(KEY_STRING);
 * }
 * builder.setTitle("修改备注");
 * dialogView = LayoutInflater.from(setCreateContext()).inflate(
 * R.layout.dialog_editview, null);
 * et_dialog = (EditText) dialogView.findViewById(R.id.et_dialog);
 * et_dialog.setText(null == a ? "" : a);
 * Util.setEditCursorToTextEnd(et_dialog);
 * builder.setContentView(dialogView);
 * builder.setPositiveButton(R.string.submit,setDialogClickListener());
 * builder.setNegativeButton(R.string.cancel, null);
 * return builder.create();
 *
 * @author antoine vianey
 *         这个对话框有按钮
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;

        private DialogInterface.OnClickListener
                positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         *
         * @param title
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param title
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the contentView is not added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context,
                    R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_custom_alert, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                Button negativeBtn = ((Button) layout.findViewById(R.id.negativeButton));
                negativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(
                                    dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                } else {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dismiss(dialog); // 默认关闭对话框
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(
                        R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.FILL_PARENT,
                                        LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

        private void dismiss(CustomDialog myDialog) {
            // 如果对话框不为null 而且正在显示
            if (myDialog != null && myDialog.isShowing()) {
                myDialog.dismiss();
            }
        }
    }

}