package kh.nobita.hang.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.TypeUtil;


@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {
    private String fontFamily;

    public CustomButton(Context context){
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0);
        try {
            fontFamily = typedArray.getString(R.styleable.CustomButton_fontFamily);
        } finally {
            typedArray.recycle();
        }

        setFont();
    }

    private void setFont() {
        try {
            setTypeface(TypeUtil.getFont(getContext(), fontFamily));
        } catch (Exception e){

        }
    }
}
