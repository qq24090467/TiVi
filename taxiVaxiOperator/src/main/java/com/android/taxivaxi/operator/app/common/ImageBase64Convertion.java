package com.android.taxivaxi.operator.app.common;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class ImageBase64Convertion {

	@SuppressWarnings("deprecation")
	public static String ImagePathToString(String imgPath) {

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		options.inPurgeable = true;
		File imageFile = new File(imgPath);
		String imgString = null;
		System.out.println("imageFile.getAbsolutePath() " + imageFile.getAbsolutePath());
		try {

			Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

//			int w = bm.getWidth(), h = bm.getHeight();
//		 	RoundedCornerImage roundImage = new RoundedCornerImage();
//			Bitmap b = roundImage.getCroppedBitmap(bm, w);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.JPEG, 75, baos);
			byte[] imgByte = baos.toByteArray();

			imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);

		} catch (NullPointerException e) {
			Log.i("File ", "File Not Found at: " + imageFile);
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			Log.i("File ", "File Not Found at: " + imageFile);
			e.printStackTrace();
		} catch (Exception e) {
			Log.i("File ", "File Not Found at: " + imageFile);
			e.printStackTrace();
		}

		return imgString;
	}//

}
