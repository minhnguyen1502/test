package com.example.test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class CrackScreenView extends View {
    private Bitmap crackBitmap;

    public CrackScreenView(Context context) {
        super(context);
        // Đợi cho view được tạo và kích thước đã được xác định
        this.post(new Runnable() {
            @Override
            public void run() {
                crackBitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.crack_image, getWidth(), getHeight());
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Vẽ bitmap với kích thước đã được nén
        if (crackBitmap != null) {
            canvas.drawBitmap(crackBitmap, 0, 0, null);
        }
    }

    // Hàm để nén bitmap dựa trên kích thước màn hình
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // Tạo tuỳ chọn chỉ tải kích thước hình ảnh mà không tải toàn bộ bitmap
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Tính toán tỷ lệ nén (sample size)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Tải bitmap với kích thước đã nén
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // Hàm tính toán tỷ lệ nén
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Kiểm tra để tránh chia cho 0
        if (reqWidth <= 0 || reqHeight <= 0) {
            return 1; // Trả về 1 nếu kích thước yêu cầu không hợp lệ
        }

        // Kích thước gốc của hình ảnh
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Tính toán tỷ lệ nén phù hợp
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
