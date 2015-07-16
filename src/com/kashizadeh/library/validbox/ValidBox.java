package com.kashizadeh.library.validbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ValidBox extends LinearLayout {

    public ValidBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet = attrs;
        initilaizer(context);
    }


    public ValidBox(Context context) {
        super(context);
        initilaizer(context);
    }

    private static Typeface typefaceIconFont;
    public static final int ICON_CHECKMARK  = 1;
    public static final int ICON_CHECKBOX   = 2;
    public static final int ICON_RADIOCHECK = 3;

    private Context         context;
    private AttributeSet    attributeSet;

    private String          pattern;
    private int             textColor       = Color.WHITE;
    private int             textSize        = 14;
    private int             iconType        = ICON_CHECKMARK;

    private boolean         isValid;

    private EditText        validBoxEdt;
    private TextView        validBoxTxtIsValid;


    /**
     * Sets the text color.
     * 
     * @attr ref android.R.styleable#TextView_textColor
     */
    public ValidBox setTextColor(int value) {
        textColor = value;
        return this;
    }


    /**
     * Set the default text size to the given value, interpreted as "scaled
     * dip" units. This size is adjusted based on the current density and
     * user font size preference.
     * 
     * @param size The scaled dip size.
     * 
     */
    public ValidBox setTextSize(int value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
        validBoxEdt.setTextColor(textColor);
        validBoxEdt.setTextSize(textSize);
        return this;
    }


    /**
     * Set type of validation icon
     * 
     * @param {@link #ICON_CHECKMARK} or {@link #ICON_CHECKBOX} or {@link #ICON_RADIOCHECK}
     */
    public ValidBox setIconType(int value) {
        iconType = value;
        return this;
    }


    /**
     * Set the ValidBox Pattern
     * use Regex in pattern
     */
    public ValidBox setPattern(String value) {
        pattern = value;
        return this;
    }


    /**
     * Return the set of isValid.
     * 
     * @return Return the set of isValid.
     */
    public boolean isValid() {
        return isValid;
    }


    private void initilaizer(Context context) {
        this.context = context;
        if (isInEditMode()) {
            setBackgroundColor(Color.GRAY);
            return;
        }

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ValidBoxAttributes);
            pattern = typedArray.getString(R.styleable.ValidBoxAttributes_pattern);
            textSize = typedArray.getInt(R.styleable.ValidBoxAttributes_textSize, 14);
            textColor = typedArray.getInt(R.styleable.ValidBoxAttributes_textColor, Color.WHITE);
            iconType = typedArray.getInt(R.styleable.ValidBoxAttributes_iconType, 1);
        }

        if (typefaceIconFont == null) {
            typefaceIconFont = Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_valid_box, this);

        validBoxEdt = (EditText) layout.findViewById(R.id.validBoxEdt);
        validBoxTxtIsValid = (TextView) layout.findViewById(R.id.validBoxTxtIsValid);

        validBoxEdt.setTextColor(textColor);
        validBoxEdt.setTextSize(textSize);

        validBoxTxtIsValid.setTypeface(typefaceIconFont);

        validBoxEdt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (pattern == null) {
                    return;
                }
                isValid = validation();
                if (start + count < 1) {
                    validBoxTxtIsValid.setText("");
                    return;
                }
                setValidationicon();
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void setValidationicon() {
        if (isValid) {
            validBoxTxtIsValid.setTextColor(Color.GREEN);
            validBoxTxtIsValid.setText(getIcon());
        } else {
            validBoxTxtIsValid.setTextColor(Color.RED);
            validBoxTxtIsValid.setText(getIcon());
        }
    }


    private int getIcon() {
        if (isValid) {
            switch (iconType) {
                case 1:
                    return R.string.ic_checkmark;
                case 2:
                    return R.string.ic_checkbox_checked;
                case 3:
                    return R.string.ic_radio_checked;
            }
        } else {
            switch (iconType) {
                case 1:
                    return R.string.ic_cross;
                case 2:
                    return R.string.ic_checkbox_unchecked;
                case 3:
                    return R.string.ic_radio_unchecked;
            }
        }
        return 0;
    }


    private boolean validation() {
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(validBoxEdt.getText().toString());
        return matcher.matches();
    }
}
