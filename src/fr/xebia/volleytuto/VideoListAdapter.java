package fr.xebia.volleytuto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class VideoListAdapter extends BaseAdapter {

    static final String JSON_VIDEO_TITLE = "title";
    static final String JSON_VIDEO_THUMBNAIL = "thumbnail_medium";
    static final String JSON_VIDEO_URL = "mobile_url";
    private final Context mContext;
    private final ImageLoader mVolleyImageLoader;
    private JSONArray mVideos;

    public VideoListAdapter(Context context, ImageLoader imageLoader) {
        mContext = context;
        mVolleyImageLoader = imageLoader;
    }

    public void updateVideos(JSONArray videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (mVideos == null) ? 0 : mVideos.length();
    }

    @Override
    public JSONObject getItem(int position) {
        JSONObject item = null;

        if (mVideos != null) {
            try {
                item = mVideos.getJSONObject(position);
            } catch (JSONException e) {
                // log error
            }
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView videoTitle;
        NetworkImageView videoPreview;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            videoTitle = (TextView) convertView.findViewById(R.id.videoTitle);
            videoPreview = (NetworkImageView) convertView.findViewById(R.id.videoPreview);
            convertView.setTag(new ViewHolder(videoTitle, videoPreview));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            videoTitle = viewHolder.mVideoTitle;
            videoPreview = viewHolder.mVideoPreview;
        }

        // Get info from JSONObject
        JSONObject video = getItem(position);
        videoTitle.setText(video.optString(JSON_VIDEO_TITLE));
        videoPreview.setImageUrl(video.optString(JSON_VIDEO_THUMBNAIL), mVolleyImageLoader);
        return convertView;
    }

    static final class ViewHolder {
        final TextView mVideoTitle;
        final NetworkImageView mVideoPreview;

        public ViewHolder(TextView videoTitle, NetworkImageView videoPreview) {
            mVideoTitle = videoTitle;
            mVideoPreview = videoPreview;
        }
    }
}
