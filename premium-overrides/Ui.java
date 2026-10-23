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

/** Shared visual system: midnight plum, electric violet, aqua highlights and lively motion. */
public final class Ui {
    public static final int GREEN = Color.rgb(108, 77, 255);
    public static final int GREEN_DARK = Color.rgb(23, 21, 47);
    public static final int MINT = Color.rgb(231, 226, 255);
    public static final int LIME = Color.rgb(108, 240, 210);
    public static final int DARK = Color.rgb(32, 28, 61);
    public static final int MUTED = Color.rgb(119, 116, 142);
    public static final int BG = Color.rgb(247, 246, 252);
    public static final int WHITE = Color.WHITE;
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
        b.setBackground(primary ? gradient(a, GREEN, 0xFF22C9C3, 16) : round(a, WHITE, 16));
        b.setPadding(dp(a,14), dp(a,7), dp(a,14), dp(a,7));
        b.setElevation(dp(a, primary ? 4 : 2));
        b.setOnTouchListener((v,event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) v.animate().scaleX(.965f).scaleY(.965f).alpha(.92f).setDuration(90).start();
            else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(180).start();
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
        TextView eyebrow = text(a, "✦  FRESH IDEAS  /  SMARTER KITCHEN", 10, GREEN, true);
        eyebrow.setLetterSpacing(.14f); root.addView(eyebrow, lp(a,-1,-2));
        TextView t = text(a,title,29,DARK,true); t.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));
        LinearLayout.LayoutParams tp=lp(a,-1,-2); tp.topMargin=dp(a,5); root.addView(t,tp);
        TextView s = text(a,subtitle,14,MUTED,false); s.setLineSpacing(dp(a,3),1f); s.setPadding(0,dp(a,6),0,dp(a,18)); root.addView(s,lp(a,-1,-2));
    }
    public static TextView section(Activity a, String label) {
        TextView t=text(a,label,18,DARK,true); t.setPadding(0,dp(a,3),0,dp(a,9)); return t;
    }
    public static LinearLayout card(Activity a, int color) {
        LinearLayout c=new LinearLayout(a); c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(dp(a,16),dp(a,15),dp(a,16),dp(a,15)); c.setBackground(round(a,color,22));
        c.setElevation(dp(a,5)); return c;
    }
    public static void nav(Activity a, LinearLayout root) {
        LinearLayout row = new LinearLayout(a); row.setOrientation(LinearLayout.HORIZONTAL); row.setGravity(Gravity.CENTER);
        row.setPadding(dp(a,5),dp(a,5),dp(a,5),dp(a,5)); row.setElevation(dp(a,3)); row.setBackground(round(a,0xFFEAE7F8,18));
        Button pantry = button(a,"⌂ Pantry",false), recipes = button(a,"♨ Recipes",false), settings = button(a,"⚙ Settings",false);
        pantry.setTextSize(12); recipes.setTextSize(12); settings.setTextSize(12);
        pantry.setBackground(round(a,WHITE,14)); recipes.setBackground(round(a,0xFFEAE7F8,14)); settings.setBackground(round(a,0xFFEAE7F8,14));
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
            View child=parent.getChildAt(i); child.setAlpha(0f); child.setTranslationY(dp(parent.getContext(),18)); child.setScaleX(.985f);
            child.animate().alpha(1f).translationY(0f).scaleX(1f).setStartDelay(Math.min(i*48,340)).setDuration(380).start();
        }
    }
    public static void animateCard(View view, int position) {
        view.setAlpha(0f); view.setTranslationY(dp(view.getContext(),14)); view.setScaleX(.97f);
        view.animate().alpha(1f).translationY(0f).scaleX(1f).setStartDelay(Math.min(position*44,280)).setDuration(340).start();
    }
}