package com.ao.retrofitrxjava.apiservice;

public class UtilsApi {

	private static final String BASE_URL_API = "https://api.github.com/";
	//RetrofitClient retrofitClient;

	public static BaseApiService getBaseApiServiceclint(){
		return RetrofitClient.getClientRetrofit(BASE_URL_API).create(BaseApiService.class);

	}
}
