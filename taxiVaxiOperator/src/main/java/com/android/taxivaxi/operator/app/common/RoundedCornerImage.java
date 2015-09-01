package com.android.taxivaxi.operator.app.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;

public class RoundedCornerImage {

	@SuppressWarnings("unused")
	public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;
		if (bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffa19774;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
		/*
		 * canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
		 * sbmp.getWidth() / 2 - 5, paint);
		 */
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

	public Bitmap addWhiteBorder(Bitmap bitmap, int borderWidth, String b_color) {
		final int width = bitmap.getWidth() + borderWidth;
		final int height = bitmap.getHeight() + borderWidth;

		Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		Canvas canvas = new Canvas(canvasBitmap);
		float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
		canvas.drawCircle(width / 2, height / 2, radius, paint);
		paint.setShader(null);
		paint.setStyle(Paint.Style.STROKE);
		if (b_color.equals("white"))
			paint.setColor(Color.WHITE);
		else
			paint.setColor(Color.GRAY);
		paint.setStrokeWidth(borderWidth);

		canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
		return canvasBitmap;
	}

}
