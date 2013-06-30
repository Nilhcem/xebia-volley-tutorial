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

public final class GithubListAdapter extends BaseAdapter {

    static final String JSON_MEMBER_LOGIN = "login";
    static final String JSON_MEMBER_AVATAR = "avatar_url";
    static final String JSON_MEMBER_URL = "html_url";
    private final Context mContext;
    private final ImageLoader mVolleyImageLoader;
    private JSONArray mMembers;

    public GithubListAdapter(Context context, ImageLoader imageLoader) {
        mContext = context;
        mVolleyImageLoader = imageLoader;
    }

    public void updateMembers(JSONArray members) {
        mMembers = members;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (mMembers == null) ? 0 : mMembers.length();
    }

    @Override
    public JSONObject getItem(int position) {
        JSONObject item = null;

        if (mMembers != null) {
            try {
                item = mMembers.getJSONObject(position);
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
        TextView login;
        NetworkImageView avatar;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            login = (TextView) convertView.findViewById(R.id.login);
            avatar = (NetworkImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(new ViewHolder(login, avatar));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            login = viewHolder.mLogin;
            avatar = viewHolder.mAvatar;
        }

        // On récupère les informations depuis le JSONObject et on les relie aux vues
        JSONObject json = getItem(position);
        login.setText(json.optString(JSON_MEMBER_LOGIN));
        avatar.setImageUrl(json.optString(JSON_MEMBER_AVATAR), mVolleyImageLoader);
        return convertView;
    }

    static final class ViewHolder {
        final TextView mLogin;
        final NetworkImageView mAvatar;

        public ViewHolder(TextView login, NetworkImageView avatar) {
            mLogin = login;
            mAvatar = avatar;
        }
    }
}
