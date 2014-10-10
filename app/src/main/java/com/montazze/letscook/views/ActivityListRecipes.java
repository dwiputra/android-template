package com.montazze.letscook.views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.montazze.letscook.R;
import com.montazze.letscook.adapter.AdapterItemRecipes;
import com.montazze.letscook.customView.CustomActionBar;
import com.montazze.letscook.helper.volley.VolleyController;
import com.montazze.letscook.helper.volley.VolleyGsonRequest;
import com.montazze.letscook.model.Gson.Recipes;
import com.montazze.letscook.model.Gson.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by RizaFu on 9/30/14.
 * <p/>
 * activity for listing recipe.
 * data fetched form pars json file form api http://food2fork.com/
 */
public class ActivityListRecipes extends Activity{

	/** injectview with butterknife and auto generate use plugin ButterKnifeZelezny */
	@InjectView(R.id.progressBarLoading)
	ProgressBar mProgressBarLoading;
	@InjectView(R.id.relativeLayoutLoadingError)
	RelativeLayout mRelativeLayoutLoadingError;
	@InjectView(R.id.listView)
	ListView mListView;
	@InjectView(R.id.buttonTryAgain)
	Button mButtonTryAgain;
	@InjectView(R.id.linearLayoutError)
	LinearLayout mLinearLayoutError;

	private List<Recipes> recipesList;
	private AdapterItemRecipes adapterItemRecipes;
	private String key;

	/**
	 * call onCreate activity.
	 * set adapter for listview.
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_recipes);
		ButterKnife.inject(this);

		new CustomActionBar(this).setupActionbar_default("List Recipes");

		recipesList = new ArrayList<Recipes>();
		adapterItemRecipes = new AdapterItemRecipes(this, recipesList);
		mListView.setAdapter(adapterItemRecipes);

		Intent intent = getIntent();
		key = intent.getStringExtra("keyWord");

		requestSearchRecipes(key);
	}

	/**
	 * event clicking to go to another activity and sending recipe id.
	 *
	 * @param i    index of list item selected
	 * @param view view of layout adapter
	 */
	@OnItemClick(R.id.listView)
	void onItemClickListView(int i, View view){
		String id = recipesList.get(i).recipeId;
		Intent intent = new Intent(getApplicationContext(), ActivityIngredients.class);
		intent.putExtra("rId", id);
		startActivity(intent);
	}

	/**
	 * event clicking to request HTTP again.
	 */
	@OnClick(R.id.buttonTryAgain)
	void onClickTryAgain(){
		requestSearchRecipes(key);
	}

	/**
	 * this is use to request HTTP.
	 * in this using volley library for networking.
	 * volly is easy and fast networking from google.
	 * this use Gson and volley together.
	 *
	 * @param q that is keyword for searching to the api food 2 fork
	 */
	private void requestSearchRecipes(String q){
		viewLoadingError(true, false);
		String url = "http://food2fork.com/api/search?key=6fd077e1f98d884fe7e6fa07d4abed9d&q=";
		String jsonUrl = url + q;

		VolleyGsonRequest<SearchResponse> Grequest = new VolleyGsonRequest<SearchResponse>(
				Request.Method.GET, jsonUrl, SearchResponse.class, myResponseListener(),
				myErrorListener());

		VolleyController.getInstance().addToRequestQueue(Grequest);
	}

	/**
	 * this listener use SearchResponse as Gson model.
	 * that is simple to parse json object.
	 * set the list recipes data from response
	 *
	 * @return response listener for http request
	 */
	private Response.Listener<SearchResponse> myResponseListener(){
		return new Response.Listener<SearchResponse>(){
			@Override
			public void onResponse(SearchResponse response){
				viewLoadingError(false, false);
				recipesList.addAll(response.recipes);
				adapterItemRecipes.notifyDataSetChanged();
			}
		};
	}

	/**
	 * get error message when error request.
	 *
	 * @return response error listener for http request
	 */
	private Response.ErrorListener myErrorListener(){
		return new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				viewLoadingError(true, true);
			}
		};
	}

	/**
	 * this is to show loading or error message
	 *
	 * @param visible   show or not show view
	 * @param error     loading view or error message
	 */
	public void viewLoadingError(boolean visible, boolean error){

		if (error){
			mLinearLayoutError.setVisibility(View.VISIBLE);
			mProgressBarLoading.setVisibility(View.GONE);
		} else {
			mLinearLayoutError.setVisibility(View.GONE);
			mProgressBarLoading.setVisibility(View.VISIBLE);
		}

		if (!visible){
			mRelativeLayoutLoadingError.setVisibility(View.GONE);
		}
	}
}