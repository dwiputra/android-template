package com.montazze.letscook.model.Gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RizaFu on 10/7/14.
 *
 * this is json modeling by Gson.
 * gson object as Recipe from parse json data at API food 2 fork.
 */
public class Recipe {

	@SerializedName("recipe")
	public Recipes recipes;

}
