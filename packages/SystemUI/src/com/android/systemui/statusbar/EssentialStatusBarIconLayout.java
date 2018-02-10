package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import java.util.ArrayList;

import com.android.systemui.R;

public class EssentialStatusBarIconLayout extends AlphaOptimizedLinearLayout {
	private Rect mCameraNotchRect;
	private Context mContext;

	public EssentialStatusBarIconLayout(Context context) {
		super(context);
		this.mContext = context;
		initialize();
	}

	public EssentialStatusBarIconLayout(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.mContext = context;
		initialize();
	}

	public EssentialStatusBarIconLayout(Context context, AttributeSet attributeSet, int paramInt) {
		super(context, attributeSet, paramInt);
		this.mContext = context;
		initialize();
	}

	public EssentialStatusBarIconLayout(Context context, AttributeSet attributeSet, int paramInt1, int paramInt2) {
		super(context, attributeSet, paramInt1, paramInt2);
		this.mContext = context;
		initialize();
	}

	private void initialize() {
		Resources resources = mContext.getResources();
		this.mCameraNotchRect = new Rect((int) resources.getDimension(R.dimen.essential_camera_notch_position_x), 0, (int) (resources.getDimension(R.dimen.essential_camera_notch_position_x) + resources.getDimension(R.dimen.essential_camera_notch_width)), (int) resources.getDimension(R.dimen.essential_camera_notch_height));
		System.out.println("BLUG: init: " + this.mCameraNotchRect.left + " " + this.mCameraNotchRect.top + " " + this.mCameraNotchRect.right + " " + this.mCameraNotchRect.bottom);
	}

	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		System.out.println("BLUG: layout called");
		if (((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getRotation() == Surface.ROTATION_0) {
			int children = getChildCount();
			System.out.println("BLUG: number of children: " + children);
			ArrayList<View> childrenNeedingRelayout = new ArrayList<>();
			for(int i = 0; i < children ; i++) {
				View child = getChildAt(i);
				if (child.getVisibility() == View.VISIBLE) {
					Rect childRect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
					if(Rect.intersects(childRect, mCameraNotchRect) || mCameraNotchRect.contains(childRect)) {
						//Icon is in notch
						childrenNeedingRelayout.add(child);
						System.out.println("BLUG: icon " + i + "needs layout");
					}
				}
			}
			int startLeft = mCameraNotchRect.right;
			for(View v : childrenNeedingRelayout) {
				int currentTop = v.getTop();
				int currentBottom = v.getBottom();
				int currentRight = v.getRight();
				int currentLeft = v.getLeft();
				int iconWidth = currentRight - currentLeft;
				if(startLeft != mCameraNotchRect.right)
					startLeft += iconWidth;
				System.out.println("BLUG: calling layout: " + startLeft + " " + currentTop + " " + (currentRight+iconWidth) + " " + currentBottom);
				v.layout(startLeft, currentTop, currentRight+iconWidth, currentBottom);
			}
		}
	}
}

