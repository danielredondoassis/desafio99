package desafiomobile99.desafio99.view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import desafiomobile99.desafio99.R;
import desafiomobile99.desafio99.model.ProfileModel;
import desafiomobile99.desafio99.view.adapter.ProfileAdapter;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.loadingLayout)
    LinearLayout mLoadingLayout;
    @BindView(R.id.errorLayout)
    LinearLayout mErrorLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ProfileAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title));
        requestProfiles();
    }

    private void setupLoadingUI() {
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.errorLayout})
    protected void reload() {
        mErrorLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        requestProfiles();
    }

    private void setupErrorUI() {
        mErrorLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    public void setupProfilesAdapter(ProfileModel[] response) {
        mLoadingLayout.setVisibility(View.GONE);

        mAdapter = new ProfileAdapter
                .Builder()
                .with(this)
                .setSource(response)
                .build();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mLoadingLayout.setVisibility(View.GONE);
    }

    private void requestProfiles() {
        TaskGetProfiles getProfiles = new TaskGetProfiles();
        getProfiles.execute();
    }

    class TaskGetProfiles extends AsyncTask<String, Void, ProfileModel[]> {

        @Override
        protected ProfileModel[] doInBackground(String... arg0) {

            try {

                InputStream is = getAssets().open("json/desafio99.json");
                Gson gson = new GsonBuilder().create();
                Reader reader = new InputStreamReader(is);

                ProfileModel[] profiles = gson.fromJson(reader, ProfileModel[].class);

                return profiles;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(ProfileModel[] result) {
            if (result != null) {
                setupProfilesAdapter(result);
            } else {
                setupErrorUI();
            }
        }
    }

}
