package com.montazze.letscook.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.montazze.letscook.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ActivitySearch extends Activity implements SearchView.OnQueryTextListener{

	/** injectview with butterknife and auto generate use plugin ButterKnifeZelezny */
	@InjectView(R.id.searchView)
	SearchView mSearchView;

	private long mLastPress = 0;

	/**
	 * call onCreate activity.
	 * set search submit listener on activity.
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		ButterKnife.inject(this);

		mSearchView.setOnQueryTextListener(this);
	}

	/**
	 * onBackPressed,
	 * this case can to back pressed when pressed two time
	 */
	@Override
	public void onBackPressed(){
		Toast onBackPressedToast = Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT);
		long currentTime = System.currentTimeMillis();
		if (currentTime - mLastPress > 2000) {
			onBackPressedToast.show();
			mLastPress = currentTime;
		} else {
			onBackPressedToast.cancel();  //Difference with previous answer. Prevent continuing showing toast after application exit
			super.onBackPressed();
		}
	}

	/**
	 * on submit the search view and go to list recipes class
	 *
	 * @param keyWord string from search view.
	 * @return boolean value
	 */
	@Override
	public boolean onQueryTextSubmit(String keyWord){
		Intent intent = new Intent(ActivitySearch.this, ActivityListRecipes.class);
		intent.putExtra("keyWord", keyWord);
		startActivity(intent);
		return false;
	}

	/**
	 * on text search view change
	 *
	 * @param s string form search view.
	 * @return  boolean value
	 */
	@Override
	public boolean onQueryTextChange(String s){
		return false;
	}
}