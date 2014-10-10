package com.montazze.letscook.model.Gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RizaFu on 9/30/14.
 *
 * this is json modeling by Gson.
 * gson object as Recipes from parse json data at API food 2 fork.
 */
public class Recipes {
	@SerializedName("publisher")
	public String publisher;
	@SerializedName("f2f_url")
	public String f2fUrl;
	@SerializedName("title")
	public String title;
	@SerializedName("source_url")
	public String sourceUrl;
	@SerializedName("recipe_id")
	public String recipeId;
	@SerializedName("image_url")
	public String imageUrl;
	@SerializedName("social_rank")
	public double socialRank;
	@SerializedName("publisher_url")
	public String publisherUrl;

	@SerializedName("ingredients")
	public String[] ingredients;
}
