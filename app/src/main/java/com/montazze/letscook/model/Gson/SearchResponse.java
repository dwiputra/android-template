package com.montazze.letscook.model.Gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RizaFu on 9/30/14.
 *
 * this is json modeling by Gson.
 * gson object as Search Response from parse json data at API food 2 fork.
 */
public class SearchResponse {

	@SerializedName("count")
	public int count;

	public List<Recipes> recipes;

}
