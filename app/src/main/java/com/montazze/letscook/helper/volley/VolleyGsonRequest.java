package com.montazze.letscook.helper.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by RizaFu on 9/30/14.
 *
 * Volley adapter for JSON requests with POST and GET method that will be parsed into Java objects by Gson.
 */
public class VolleyGsonRequest<T> extends Request<T> {
	private Gson mGson = new Gson();
	private Class<T> clazz;
	private Map<String, String> headers;
	private Map<String, String> params;
	private Listener<T> listener;

	/**
	 * Make a GET request and return a parsed object from JSON.
	 *
	 * @param url URL of the request to make
	 * @param clazz Relevant class object, for Gson's reflection
	 */
	public VolleyGsonRequest(int method,
	                   String url,
	                   Class<T> clazz,
	                   Listener<T> listener,
	                   ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.listener = listener;
		mGson = new Gson();
	}

	/**
	 * Make a POST request and return a parsed object from JSON.
	 *
	 * @param url URL of the request to make
	 * @param clazz Relevant class object, for Gson's reflection
	 */
	public VolleyGsonRequest(int method,
	                   String url,
	                   Class<T> clazz,
	                   Map<String, String> params,
	                   Listener<T> listener,
	                   ErrorListener errorListener) {

		super(method, url, errorListener);
		this.clazz = clazz;
		this.params = params;
		this.listener = listener;
		this.headers = null;
		mGson = new Gson();
	}

	/**
	 * getting headers from parent class.
	 *
	 * @return                  headers value
	 * @throws AuthFailureError
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	/**
	 * getting maping value of params.
	 *
	 * @return                  params
	 * @throws AuthFailureError
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	/**
	 * listener for deliver response.
	 *
	 * @param response
	 */
	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	/**
	 * getting response for network.
	 *
	 * @param response
	 * @return          response success or error
	 */
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(
					response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(
					mGson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

}
