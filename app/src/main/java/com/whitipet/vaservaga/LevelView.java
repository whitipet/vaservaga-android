package com.whitipet.vaservaga;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

final class LevelView extends View {

	private final Paint paintOutline;
	private final Paint paintBubble;
	private final Path outlinePath = new Path();

	private float centerX;
	private float centerY;
	private float bubbleRadius;
	private float bubbleStepMultiplier;

	private float x;
	private float y;

	public LevelView(Context context) {
		super(context);

		final int flags = Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG;

		paintOutline = new Paint(flags);
		paintOutline.setStyle(Paint.Style.STROKE);
		paintOutline.setColor(0xFFFFFFFF);
		paintOutline.setStrokeWidth(2);

		paintBubble = new Paint(flags);
		paintBubble.setColor(0xFFFF0000);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		float halfMinSize = (Math.min(w, h) * 0.9f) * 0.5f;
		centerX = w * 0.5f;
		centerY = h * 0.5f;
		bubbleStepMultiplier = halfMinSize;
		bubbleRadius = halfMinSize * 0.1f;

		float left = centerX - halfMinSize;
		float top = centerY - halfMinSize;
		float right = centerX + halfMinSize;
		float bottom = centerY + halfMinSize;
		float bubbleOutlineSize = bubbleRadius * 1.2f;
		float halfOutline = bubbleOutlineSize * 0.5f;

		outlinePath.reset();
		outlinePath.addRect(left, top, right, bottom, Path.Direction.CW);
		outlinePath.addCircle(centerX, centerY, bubbleOutlineSize, Path.Direction.CW);

		outlinePath.moveTo(left, centerY - bubbleOutlineSize);
		outlinePath.lineTo(left + halfOutline, centerY - bubbleOutlineSize);
		outlinePath.moveTo(left, centerY + bubbleOutlineSize);
		outlinePath.lineTo(left + halfOutline, centerY + bubbleOutlineSize);

		outlinePath.moveTo(centerX - bubbleOutlineSize, top);
		outlinePath.lineTo(centerX - bubbleOutlineSize, top + halfOutline);
		outlinePath.moveTo(centerX + bubbleOutlineSize, top);
		outlinePath.lineTo(centerX + bubbleOutlineSize, top + halfOutline);

		outlinePath.moveTo(right, centerY - bubbleOutlineSize);
		outlinePath.lineTo(right - halfOutline, centerY - bubbleOutlineSize);
		outlinePath.moveTo(right, centerY + bubbleOutlineSize);
		outlinePath.lineTo(right - halfOutline, centerY + bubbleOutlineSize);

		outlinePath.moveTo(centerX - bubbleOutlineSize, bottom);
		outlinePath.lineTo(centerX - bubbleOutlineSize, bottom - halfOutline);
		outlinePath.moveTo(centerX + bubbleOutlineSize, bottom);
		outlinePath.lineTo(centerX + bubbleOutlineSize, bottom - halfOutline);
	}

	void setData(float x, float y) {
		this.x = x;
		this.y = y;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(
				centerX + (x * bubbleStepMultiplier),
				centerY + (y * bubbleStepMultiplier),
				bubbleRadius, paintBubble
		);
		canvas.drawPath(outlinePath, paintOutline);
	}
}