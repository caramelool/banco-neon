package br.com.neon.ui.profile;

import javax.inject.Inject;

import br.com.neon.NeonApplication;
import br.com.neon.model.User;
import br.com.neon.rest.NeonApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    @Inject
    NeonApi neonApi;
    @Inject
    User user;

    private ProfileContract.View view;

    ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        NeonApplication.getInstance().getComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestToken() {
        view.showLoading();
        Call<String> call = neonApi.generateToken(user.getNome(), user.getEmail());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String token = response.body();
                processToken(token);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (view != null) {
                    processToken(null);
                }
            }
        });
    }

    @Override
    public void processToken(String token) {
        if (token != null) {
            user.updateToken(token);
            view.onTokenReceiver(user);
        } else {
            view.onTokenErrorReceiver();
        }
    }
}
