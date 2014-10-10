package com.montazze.letscook.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.montazze.letscook.R;
import com.montazze.letscook.helper.volley.VolleyController;
import com.montazze.letscook.model.Gson.Recipes;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by RizaFu on 9/30/14.
 *
 * this class is to adapted the listview item based on BaseAdapter.
 */
public class AdapterItemRecipes extends BaseAdapter {

	private Context context;
	private List<Recipes> recipesList;
	private LayoutInflater inflater;

	/**
	 * this is constructor
	 *
	 * @param context       from activity
	 * @param recipesList   list object from Gson Model
	 *
	 *                      All param assignment to attribute
	 */
	public AdapterItemRecipes(Context context, List<Recipes> recipesList) {
		this.context = context;
		this.recipesList = recipesList;
		this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @return size of list by object from Gson model
	 */
	@Override
	public int getCount() {
		return recipesList.size();
	}

	/**
	 *
	 * @param location from selected list
	 * @return get Gson model by location
	 */
	@Override
	public Object getItem(int location) {
		return recipesList.get(location);
	}

	/**
	 *
	 * @param position of item id
	 * @return position from param
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * set inflate layout to convert view and set content view to widget view.
	 * image view use network image view from volley library, and use image loader URL too.
	 * that is easy to use and fast to load image from the network in parallel.
	 *
	 * @param position from method getItemId
	 * @param convertview setting layout to use
	 * @param parent get view from parent view
	 * @return view to use
	 */
	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		View v = convertview;
		if (this.inflater.equals(null)) {
			this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		}

		if (convertview == null) {
			v = this.inflater.inflate(R.layout.item_list_recipes, null);
		}

		ViewHolder holder = new ViewHolder(v);
		Recipes recipes = recipesList.get(position);
		ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

		holder.mVolleyimageView.setImageUrl(recipes.imageUrl, imageLoader);
		holder.mTextViewPublisher.setText(recipes.publisher);
		holder.mTextViewTitle.setText(Html.fromHtml(recipes.title));
		holder.mTextViewSocialRank.setText(Integer.toString((int)Math.round(recipes.socialRank)));

		return v;
	}

	/**
	 * This class contains all butterknife-injected Views & Layouts from layout file 'item_list_recipes.xml'
	 * for easy to all layout elements.
	 *
	 * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
	 */
	static class ViewHolder {
		@InjectView(R.id.volleyimageView)
		NetworkImageView mVolleyimageView;
		@InjectView(R.id.textViewTitle)
		TextView mTextViewTitle;
		@InjectView(R.id.textViewPublisher)
		TextView mTextViewPublisher;
		@InjectView(R.id.textViewSocialRank)
		TextView mTextViewSocialRank;

		ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}
}
