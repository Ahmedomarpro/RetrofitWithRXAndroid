package com.ao.retrofitrxjava.apiservice;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
	private static final String TAG = RetrofitClient.class.getSimpleName();

	private static int REQUEST_TIMEOUT = 60;
	private static OkHttpClient okHttpClient;

	public static Retrofit retrofit = null;

	public static  Retrofit getClientRetrofit(String  baseUrl){

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.level(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
				.readTimeout(10, TimeUnit.SECONDS).connectTimeout(30,TimeUnit.SECONDS)
				.build();

		if (retrofit == null){
			retrofit = new Retrofit
					.Builder()
					.baseUrl(baseUrl)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.client(client)
					.build();
		}
		

		return retrofit;
	}




//******************************//An other way //*************

	public static Retrofit getClient() {
		if (okHttpClient == null)
			initHttpClient();

		if (retrofit == null) {
			retrofit = initRetrofit();
		}

		return retrofit;

	}
	

	private static Retrofit initRetrofit() {
		return new Retrofit.Builder().baseUrl(Const.BASE_URL).client(okHttpClient)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
	}



	private static void initHttpClient() {
		OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
		httpBuilder.connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
		httpBuilder.readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
		httpBuilder.writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.level(HttpLoggingInterceptor.Level.BODY);

		httpBuilder.addInterceptor(interceptor);

		httpBuilder.addInterceptor(new Interceptor() {
			@NotNull
			@Override
			public Response intercept(@NotNull Chain chain) throws IOException {
				Request original = chain.request();
				Request.Builder requestBuilder = original.newBuilder();
				/*
				requestBuilder.addHeader("Accept", "application/json");
				requestBuilder.addHeader("Request-Type", "Android");
				requestBuilder.addHeader("Content-Type", "application/json");
*/
				Request request = requestBuilder.build();


				return chain.proceed(request);
			}
		});

		okHttpClient = httpBuilder.build();
	}



}
