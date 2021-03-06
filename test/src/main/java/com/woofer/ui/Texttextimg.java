package com.woofer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import woofer.com.test.R;

public class Texttextimg extends RelativeLayout{
    private TextView textView1;
    private TextView textView2;
    private ImageView imageView1;
    public Texttextimg(Context context){
        super(context, null);
    }

    public Texttextimg(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.text_text_img, this, true);
        this.textView1=(TextView)findViewById(R.id.text_text_TV1);
        this.textView2=(TextView)findViewById(R.id.text_text_TV2);
        this.imageView1=(ImageView)findViewById(R.id.text_text_IV1);
        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceID){
        this.imageView1.setImageResource(resourceID);
    }
    public void setText(int id, String text){
        switch (id){
            case 0:
                this.textView1.setText(text);
                break;
            case 1:
                this.textView2.setText(text);
                break;
        }
    }
}
