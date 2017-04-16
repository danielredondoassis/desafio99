package desafiomobile99.desafio99.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import desafiomobile99.desafio99.R;
import desafiomobile99.desafio99.model.ProfileModel;
import desafiomobile99.desafio99.view.adapter.holder.ProfileHolder;

public class ProfileAdapter extends BaseAdapter {

    private static final String TAG = ProfileAdapter.class.getSimpleName();

    private ArrayList<ProfileModel> mSource;
    private static final int HOLDER_POSTER = 0;


    private Context context;

    private ProfileAdapter(Builder builder) {
        this.mSource = builder.mSource;
        this.context = builder.context;
    }

    public static class Builder {
        private Context context;
        private ArrayList<ProfileModel> mSource;

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder setSource(ProfileModel[] profiles) {
            this.mSource = new ArrayList<>();
            if(profiles != null) {
                for(ProfileModel profile : profiles){
                    if(!mSource.contains(profile)) mSource.add(profile);
                }
            }
            return this;
        }

        public ProfileAdapter build() {
            return new ProfileAdapter(this);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = null;
        ProfileHolder holder = null;

        v = inflater.inflate(R.layout.holder_profile, parent, false);
        holder = new ProfileHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfileHolder profileHolder = (ProfileHolder) holder;
        ProfileModel profileModel = mSource.get(position);
        profileHolder.setup(context, profileModel);
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof ProfileHolder) ((ProfileHolder) holder).imageView.setImageDrawable(null);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        return mSource != null ? mSource.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return HOLDER_POSTER;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
