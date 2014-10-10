package com.montazze.letscook.helper.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by RizaFu on 9/30/14.
 *
 * This class is required to handle image cache.
 */
public class VolleyLruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{

	/**
	 * for getting default cache image to memory
	 *
	 * @return maxMemory
	 */
	public static int getDefaultLruCacheSize() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		final int cacheSize = maxMemory / 8;

		return cacheSize;
	}

	/**
	 * standart constructor
	 *
	 */
	public VolleyLruBitmapCache() {
		this(getDefaultLruCacheSize());
	}

	/**
	 * overload constructor
	 *
	 * @param sizeInKiloBytes   get size in kilo bytes.
	 */
	public VolleyLruBitmapCache(int sizeInKiloBytes) {
		super(sizeInKiloBytes);
	}

	/**
	 * getting size of bitmap
	 *
	 * @param key       key for bitmap to set.
	 * @param value     the bitmap variable.
	 * @return          math of size from bitmap
	 */
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight() / 1024;
	}

	/**
	 * getting bitmap URL.
	 *
	 * @param url   String of URL.
	 * @return      String value of URL.
	 */
	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	/**
	 * put url bitmap to bitmap variable.
	 *
	 * @param url       String of URL.
	 * @param bitmap    bitmap variable.
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
}
