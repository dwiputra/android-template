package com.montazze.letscook.views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.montazze.letscook.R;
import com.montazze.letscook.customView.CustomActionBar;
import com.montazze.letscook.helper.volley.VolleyController;
import com.montazze.letscook.helper.volley.VolleyGsonRequest;
import com.montazze.letscook.model.Gson.Recipe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by RizaFu on 10/7/14.
 */
public class ActivityIngredients extends Activity {

	/** injectview with butterknife and auto generate use plugin ButterKnifeZelezny */
	@InjectView(R.id.volleyimageView)
	NetworkImageView mVolleyimageView;
	@InjectView(R.id.textViewTitle)
	TextView mTextViewTitle;
	@InjectView(R.id.textViewPublisher)
	TextView mTextViewPublisher;
	@InjectView(R.id.textViewSocialRank)
	TextView mTextViewSocialRank;
	@InjectView(R.id.layoutIngredients)
	LinearLayout mLayoutIngredients;
	@InjectView(R.id.buttonViewOn)
	Button mButtonViewOn;
	@InjectView(R.id.progressBarLoading)
	ProgressBar mProgressBarLoading;
	@InjectView(R.id.buttonTryAgain)
	Button mButtonTryAgain;
	@InjectView(R.id.linearLayoutError)
	LinearLayout mLinearLayoutError;
	@InjectView(R.id.relativeLayoutLoadingError)
	RelativeLayout mRelativeLayoutLoadingError;

	private String urlPublisher, rId;

	/**
	 * call onCreate activity.
	 * get recipe id from intent. this case is selected item from list recipes.
	 * and then calling method requestIngredient.
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredients);
		ButterKnife.inject(this);

		new CustomActionBar(this).setupActionbar_default("Ingredient");

		Intent intent = getIntent();
		rId = intent.getStringExtra("rId");
		requestIngredients(rId);
	}

	/**
	 * event when ViewOn source ingredient click.
	 * and go to the URL publisher to view on web browser.
	 */
	@OnClick(R.id.buttonViewOn)
	void onClickViewOn(){
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPublisher));
		startActivity(intent);
	}

	/**
	 * event clicking to request HTTP again.
	 */
	@OnClick(R.id.buttonTryAgain)
	void onClickTryAgain(){
		requestIngredients(rId);
	}


	/**
	 * this is use to request HTTP.
	 * in this using volley library for networking.
	 * volly is easy and fast networking from google.
	 * this use Gson and volley together.
	 *
	 * @param rId that is recipe id from list recipes.
	 */
	private void requestIngredients(String rId){
		viewLoadingError(true, false);

		String url = "http://food2fork.com/api/get?key=6fd077e1f98d884fe7e6fa07d4abed9d&rId=";
		String jsonUrl = url + rId;

		VolleyGsonRequest<Recipe> Grequest = new VolleyGsonRequest<Recipe>(Request.Method.GET,
		                                                                   jsonUrl, Recipe.class,
		                                                                   myResponseListener(),
		                                                                   myErrorListener());
		VolleyController.getInstance().addToRequestQueue(Grequest);
	}

	/**
	 * this listener use Recipe as Gson model.
	 * that is simple to parse json object.
	 * set content widget data from response.
	 * <p/>
	 * image view use network image view from volley library, and use image loader URL too.
	 * that is easy to use and fast to load image from the network in parallel.
	 * <p/>
	 * addview on mLayoutIngredients with new linear layout.
	 *
	 * @return response listener for http request
	 */
	private Response.Listener<Recipe> myResponseListener(){
		return new Response.Listener<Recipe>(){
			@Override
			public void onResponse(Recipe recipe){
				viewLoadingError(false, false);

				urlPublisher = recipe.recipes.sourceUrl;

				ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

				mVolleyimageView.setImageUrl(recipe.recipes.imageUrl, imageLoader);
				mTextViewPublisher.setText(recipe.recipes.publisher);
				mTextViewTitle.setText(Html.fromHtml(recipe.recipes.title));
				mTextViewSocialRank
						.setText(Integer.toString((int) Math.round(recipe.recipes.socialRank)));
				mButtonViewOn.setText("View On " + recipe.recipes.publisher);

				String[] ingredient = recipe.recipes.ingredients;
				for (String s : ingredient){
					mLayoutIngredients.addView(ingredient(s));
				}

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
	 * this is method to create new linearLayout for ingredient item.
	 *
	 * @param text set text for textview in linearLayout
	 *
	 * @return linearLayout to adding that to another layout.
	 */
	private LinearLayout ingredient(String text){
		LinearLayout linearLayout = new LinearLayout(this);
		TextView textIngredient = new TextView(this);
		TextView textBullet = new TextView(this);
		TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
		                                                         TableRow.LayoutParams.WRAP_CONTENT);

		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setLayoutParams(params);

		String BULLET_SYMBOL = "&#8226";
		textBullet.setText(Html.fromHtml(BULLET_SYMBOL));
		textBullet.setLayoutParams(params);

		params.setMargins(5, 0, 10, 2);
		textIngredient.setTextSize(15);
		textIngredient.setText(text);
		textIngredient.setLayoutParams(params);

		linearLayout.addView(textBullet);
		linearLayout.addView(textIngredient);

		return linearLayout;
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