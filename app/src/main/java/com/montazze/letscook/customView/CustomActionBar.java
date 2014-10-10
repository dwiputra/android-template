package com.montazze.letscook.customView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.montazze.letscook.R;

/**
 * Created by RizaFu on 10/10/14.
 */
public class CustomActionBar {

	private Activity activity;

	/**
	 * this is constructor
	 *
	 * @param activity  get activity and than getting actionbar too.
	 */
	public CustomActionBar(Activity activity){
		this.activity = activity;
	}

	/**
	 * set custom actionbar
	 *
	 * @param title set title bar
	 */
	public void setupActionbar_default(String title){
		final ActionBar actionBar = activity.getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		final LayoutInflater inflater = (LayoutInflater) activity.getActionBar().getThemedContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(R.layout.actionbar_custom, null);

		actionBar.setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM |
				                               ActionBar.DISPLAY_SHOW_HOME |
				                               ActionBar.DISPLAY_SHOW_TITLE);

		actionBar.setCustomView(customActionBarView,
		                        new ActionBar.LayoutParams(
				                        ViewGroup.LayoutParams.MATCH_PARENT,
				                        ViewGroup.LayoutParams.MATCH_PARENT)
		                       );

		ImageButton back=(ImageButton)customActionBarView.findViewById(R.id.buttonBack);
		TextView textTitle=(TextView)customActionBarView.findViewById(R.id.textViewTitleBar);
		textTitle.setText(title);

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				activity.onBackPressed();
			}
		});
	}
}
