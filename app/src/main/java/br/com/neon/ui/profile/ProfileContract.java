package br.com.neon.ui.profile;

import br.com.neon.model.User;
import br.com.neon.ui.BasePresenter;

interface ProfileContract {
    interface Presenter extends BasePresenter{
        void onResume();
        void requestToken();
        void processToken(String token);
        boolean hasToken();
    }
    interface View {
        void showLoading();
        void onTokenReceiver(User user);
        void onTokenErrorReceiver();
    }
}
