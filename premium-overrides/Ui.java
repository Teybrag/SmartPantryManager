package com.teybrag.smartpantry;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/** Vibrant visual system: berry, coral, mango, aqua and playful motion. */
public final class Ui {
    public static final int GREEN = Color.rgb(255, 70, 112);
    public static final int GREEN_DARK = Color.rgb(45, 35, 76);
    public static final int MINT = Color.rgb(255, 237, 243);
    public static final int LIME = Color.rgb(45, 211, 178);
    public static final int DARK = Color.rgb(39, 31, 66);
    public static final int MUTED = Color.rgb(121, 112, 143);
    public static final int BG = Color.rgb(255, 249, 246);
    public static final int WHITE = Color.WHITE;
    public static final int CORAL = Color.rgb(255, 111, 97);
    public static final int MANGO = Color.rgb(255, 190, 72);
    public static final int AQUA = Color.rgb(61, 214, 199);
    public static final int LAVENDER = Color.rgb(157, 123, 255);
    private Ui() {}

    public static int dp(android.content.Context a, int value) { return (int)(value * a.getResources().getDisplayMetrics().density + 0.5f); }
    public static LinearLayout vertical(Activity a) {
        LinearLayout l = new LinearLayout(a); l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(a,20), dp(a,18), dp(a,20), dp(a,24)); l.setBackgroundColor(BG); return l;
    }
    public static TextView text(Activity a, String value, int size, int color, boolean bold) {
        TextView t = new TextView(a); t.setText(value); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        t.setGravity(Gravity.CENTER_VERTICAL); t.setIncludeFontPadding(true); return t;
    }
    public static Button button(Activity a, String value, boolean primary) {
        Button b = new Button(a); b.setText(value); b.setAllCaps(false); b.setTextSize(15);
        b.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        b.setTextColor(primary ? WHITE : GREEN_DARK);
        b.setBackground(primary ? gradient(a, GREEN, CORAL, 17) : round(a, WHITE, 16));
        b.setPadding(dp(a,14), dp(a,7), dp(a,14), dp(a,7));
        b.setElevation(dp(a, primary ? 5 : 2));
        b.setOnTouchListener((v,event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) v.animate().scaleX(.95f).scaleY(.95f).alpha(.88f).setDuration(90).start();
            else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(190).start();
            return false;
        });
        return b;
    }
    public static GradientDrawable round(Activity a, int color, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(a,radius)); return d;
    }
    public static GradientDrawable gradient(Activity a, int start, int end, int radius) {
        GradientDrawable d = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{start,end});
        d.setCornerRadius(dp(a,radius)); return d;
    }
    public static LinearLayout.LayoutParams lp(Activity a, int width, int height) { return new LinearLayout.LayoutParams(width < 0 ? width : dp(a,width), height < 0 ? height : dp(a,height)); }
    public static void title(Activity a, LinearLayout root, String title, String subtitle) {
        TextView eyebrow = text(a, "✦  FRESH IDEAS  •  SMARTER KITCHEN", 10, GREEN, true);
        eyebrow.setLetterSpacing(.12f); root.addView(eyebrow, lp(a,-1,-2));
        TextView t = text(a,title,29,DARK,true); t.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));
        LinearLayout.LayoutParams tp=lp(a,-1,-2); tp.topMargin=dp(a,5); root.addView(t,tp);
        TextView s = text(a,subtitle,14,MUTED,false); s.setLineSpacing(dp(a,3),1f); s.setPadding(0,dp(a,6),0,dp(a,18)); root.addView(s,lp(a,-1,-2));
    }
    public static TextView section(Activity a, String label) {
        TextView t=text(a,label,18,DARK,true); t.setPadding(0,dp(a,3),0,dp(a,9)); return t;
    }
    public static LinearLayout card(Activity a, int color) {
        LinearLayout c=new LinearLayout(a); c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(dp(a,16),dp(a,15),dp(a,16),dp(a,15));
        GradientDrawable bg=round(a,color,22);
        bg.setStroke(dp(a,1),0xFFFFD5DF);
        c.setBackground(bg); c.setElevation(dp(a,5));
        c.setClipToOutline(true);
        return c;
    }
    public static void nav(Activity a, LinearLayout root) {
        LinearLayout row = new LinearLayout(a); row.setOrientation(LinearLayout.HORIZONTAL); row.setGravity(Gravity.CENTER);
        row.setPadding(dp(a,5),dp(a,5),dp(a,5),dp(a,5)); row.setElevation(dp(a,4));
        row.setBackground(gradient(a,0xFFFFE8EF,0xFFFFF0D8,18));
        Button pantry = button(a,"⌂ Pantry",false), recipes = button(a,"✿ Recipes",false), settings = button(a,"⚙ Settings",false);
        pantry.setTextSize(12); recipes.setTextSize(12); settings.setTextSize(12);
        pantry.setBackground(gradient(a,0xFFFF4778,0xFFFF9478,14)); pantry.setTextColor(WHITE);
        recipes.setBackground(round(a,0xFFFFFFFF,14)); settings.setBackground(round(a,0xFFFFFFFF,14));
        row.addView(pantry,new LinearLayout.LayoutParams(0,dp(a,42),1)); row.addView(recipes,new LinearLayout.LayoutParams(0,dp(a,42),1)); row.addView(settings,new LinearLayout.LayoutParams(0,dp(a,42),1));
        pantry.setOnClickListener(v -> a.startActivity(new android.content.Intent(a,MainActivity.class)));
        recipes.setOnClickListener(v -> a.startActivity(new android.content.Intent(a,SuggestedRecipesActivity.class)));
        settings.setOnClickListener(v -> a.startActivity(new android.content.Intent(a,SettingsActivity.class)));
        LinearLayout.LayoutParams p=lp(a,-1,52); p.bottomMargin=dp(a,18); root.addView(row,p);
    }
    public static ScrollView scroll(Activity a, View child) {
        ScrollView s = new ScrollView(a); s.setFillViewport(true); s.setClipToPadding(false); s.setVerticalScrollBarEnabled(false); s.addView(child); return s;
    }
    public static void setScreen(Activity a, LinearLayout root) {
        a.setContentView(scroll(a,root));
        animateChildren(root);
    }
    public static void animateChildren(LinearLayout parent) {
        for (int i=0;i<parent.getChildCount();i++) {
            View child=parent.getChildAt(i); child.setAlpha(0f); child.setTranslationY(dp(parent.getContext(),20)); child.setScaleX(.98f);
            child.animate().alpha(1f).translationY(0f).scaleX(1f).setStartDelay(Math.min(i*52,360)).setDuration(420).start();
        }
    }
    public static void animateCard(View view, int position) {
        view.setAlpha(0f); view.setTranslationY(dp(view.getContext(),16)); view.setScaleX(.97f);
        view.animate().alpha(1f).translationY(0f).scaleX(1f).setStartDelay(Math.min(position*48,300)).setDuration(360).start();
    }
}