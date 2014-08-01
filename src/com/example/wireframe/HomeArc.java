package com.example.wireframe;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * 圆弧计分
 * 
 * @author Administrator
 * 
 */
public class HomeArc extends View {

	private Paint paint_black, paint_white;
	private RectF rectf;
	private float tb;
	private int blackColor = 0x70000000; // 底黑色
	private int whiteColor = 0xddffffff; // 白色
	private int score;
	private float arc_y = 0f;
	private int score_text;

	public HomeArc(Context context, int score) {
		super(context);
		init(score);
	}

	public void init(int score) {
		this.score = score;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);

		paint_black = new Paint();
		// 抗锯齿
		paint_black.setAntiAlias(true);
		paint_black.setColor(blackColor);
		paint_black.setStrokeWidth(tb * 0.2f);
		// 实心
		paint_black.setStyle(Style.STROKE);

		paint_white = new Paint();
		paint_white.setAntiAlias(true);
		paint_white.setColor(whiteColor);
		paint_white.setTextSize(tb * 6.0f);
		paint_white.setStrokeWidth(tb * 0.2f);
		paint_white.setTextAlign(Align.CENTER);
		paint_white.setStyle(Style.STROKE);

		// 矩形
		rectf = new RectF();
		rectf.set(tb * 0.5f, tb * 0.5f, tb * 18.5f, tb * 18.5f);

		setLayoutParams(new LayoutParams((int) (tb * 19.5f), (int) (tb * 19.5f)));
		// 视图树将要绘制时，回调函数的接口类
		this.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						new thread();
						getViewTreeObserver().removeOnPreDrawListener(this);
						return false;
					}
				});
	}

	protected void onDraw(Canvas c) {
		super.onDraw(c);
		c.drawArc(rectf, -90, 360, false, paint_black);
		c.drawArc(rectf, -90, arc_y, false, paint_white);
		c.drawText("" + score_text, tb * 9.7f, tb * 11.0f, paint_white);
	}

	class thread implements Runnable {
		private Thread thread;
		int count;

		public thread() {
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(150);
					arc_y += 3.6f;
					score_text++;
					count++;
					postInvalidate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count >= score)
					break;
			}
		}
	}
}
