package br.com.neon.di.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.neon.rest.NeonApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Named("api_base_url")
    String provideBaseUrl() {
        return "http://processoseletivoneon.azurewebsites.net/";
    }

    @Provides
    Retrofit provideRetrofit(@Named("api_base_url") String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    NeonApi provideApi(Retrofit retrofit) {
        return retrofit.create(NeonApi.class);
    }
}
