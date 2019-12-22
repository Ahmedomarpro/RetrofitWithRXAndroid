package com.ao.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ao.adapter.ReposAdapter;
import com.ao.retrofitrxjava.R;
import com.ao.retrofitrxjava.apiservice.BaseApiService;
import com.ao.retrofitrxjava.apiservice.UtilsApi;
import com.ao.retrofitrxjava.databinding.MainActivityBinding;
import com.ao.retrofitrxjava.model.ResponseRepos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ReposAdapter.OnItemClickListenerView {
	//Resource Shrinking
	//protected EditText etUsername;

	BaseApiService mApiService;
	ReposAdapter mRepoAdapter;

	List<ResponseRepos> repoList = new ArrayList<>();

	//ActivityMainBinding binding;
	MainActivityBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//super.setContentView(R.layout.activity_main);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		initView();
	}

	private void initView() {
		//etUsername = findViewById(R.id.etUsername);
		//RecyclerView rvRepos = findViewById(R.id.rvRepos);

		mApiService = UtilsApi.getBaseApiServiceclint();

		mRepoAdapter = new ReposAdapter(repoList);
		binding.rvRepos.setLayoutManager(new LinearLayoutManager(this));


		binding.rvRepos.setItemAnimator(new DefaultItemAnimator());
		binding.rvRepos.setHasFixedSize(true);
		binding.rvRepos.setAdapter(mRepoAdapter);
		mRepoAdapter.setOnItemClickListener(null);

		binding.etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String user = binding.etUsername.getText().toString();
					requestRepos(user);
					return true;
				}
				return false;
			}
		});


	}

	private void requestRepos(String userName) {

		mApiService.requestRepos(userName)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<List<ResponseRepos>>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(List<ResponseRepos> responseRepos) {

/*
						for (int i = 0; i < responseRepos.size(); i++) {
							String name = responseRepos.get(i).getName();
							String description = responseRepos.get(i).getDescription();
							repoList.add(new Repo(name, description));
						}
*/

						repoList.clear();
						repoList.addAll(responseRepos);
						mRepoAdapter.setList(repoList);

					}

					@Override
					public void onError(Throwable e) {
						Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete() {
						Toast.makeText(MainActivity.this, "Got Data", Toast.LENGTH_SHORT).show();

						mRepoAdapter = new ReposAdapter(repoList);
						//binding.rvRepos.setAdapter(mRepoAdapter);
						mRepoAdapter.notifyDataSetChanged();
						mRepoAdapter.setOnItemClickListener(MainActivity.this::onItemClick);
					}
				});


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		binding.unbind();
	}

	@Override
	public void onItemClick(int position, ResponseRepos item) {
		Toast.makeText(MainActivity.this, "New Item" + position, Toast.LENGTH_SHORT).show();

	}
}