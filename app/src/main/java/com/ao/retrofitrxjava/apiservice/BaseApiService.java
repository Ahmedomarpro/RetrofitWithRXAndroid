package com.ao.retrofitrxjava.apiservice;

import com.ao.retrofitrxjava.model.ResponseRepos;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

	@GET ("users/{username}/repos")
	Observable <List<ResponseRepos>> requestRepos (@Path("username") String username);
}
