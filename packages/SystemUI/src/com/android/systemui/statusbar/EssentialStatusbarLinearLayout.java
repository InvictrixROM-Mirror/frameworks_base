package com.android.systemui.statusbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.android.keyguard.AlphaOptimizedLinearLayout;

import com.android.systemui.R;

public class EssentialStatusbarLinearLayout extends AlphaOptimizedLinearLayout {
	private EssentialStatusBarIconLayout mStatusIconsLayout;

	public EssentialStatusbarLinearLayout(Context paramContext) {
		super(paramContext);
	}

	public EssentialStatusbarLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public EssentialStatusbarLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public EssentialStatusbarLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
		super(paramContext, paramAttributeSet, paramInt1, paramInt2);
	}

	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		if ((paramBoolean) && (((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getOrientation() == 0)) {
			post(new Runnable() {
				public void run() {
					if (mStatusIconsLayout == null) {
						mStatusIconsLayout = findViewById(R.id.statusIcons);
					}
					mStatusIconsLayout.invalidate();
					mStatusIconsLayout.requestLayout();
				}
			});
		}
	}
}
