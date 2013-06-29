package fr.xebia.volleytuto;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public final class XebiaApplication extends Application {

	private RequestQueue mVolleyRequestQueue;
	private ImageLoader mVolleyImageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
		mVolleyRequestQueue.start();
		mVolleyImageLoader = new ImageLoader(mVolleyRequestQueue, new BitmapLruCache());
	}

	public RequestQueue getVolleyRequestQueue() {
		return mVolleyRequestQueue;
	}

	public ImageLoader getVolleyImageLoader() {
		return mVolleyImageLoader;
	}

	@Override
	public void onTerminate() {
		mVolleyRequestQueue.stop();
		super.onTerminate();
	}
}
