package com.montazze.letscook.helper.volley;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by RizaFu on 9/30/14.
 *
 * singleton class which extends Application object is
 * the best way to maintain volley core objects and request queue.
 */
public class VolleyController extends Application {

	public static final String TAG = VolleyController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static VolleyController mInstance;

	/**
	 * call when Application start
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	/**
	 * getting instance for create object of volley Controller
	 *
	 * @return value volley controller.
	 */
	public static synchronized VolleyController getInstance() {
		return mInstance;
	}

	/**
	 * set volley request queue to new request queue.
	 *
	 * @return value request queue
	 */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * set image loader to new image loader with param of request queue and volley lru bitmap cache.
	 *
	 * @return value image loader.
	 */
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new VolleyLruBitmapCache());
		}
		return this.mImageLoader;
	}

	/**
	 * add request queue with object, in this case can use json object, json array, or Gson object.
	 * tag is use for tagging when canceling request.
	 *
	 * @param req   volley request
	 * @param tag   tagging.
	 * @param <T>
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	/**
	 * add request queue with object, in this case can use json object, json array, or Gson object.
	 *
	 * @param req   volley request
	 * @param <T>
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * this is to canceling request by tag.
	 *
	 * @param tag   tagging.
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
