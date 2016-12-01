package br.com.neon.ui.sendmoneydetail;

import javax.inject.Inject;

import br.com.neon.NeonApplication;
import br.com.neon.model.Contact;
import br.com.neon.model.User;
import br.com.neon.rest.NeonApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMoneyDetailPresenter
    implements SendMoneyDetailContract.Presenter {

    @Inject
    NeonApi neonApi;

    @Inject
    User me;

    private SendMoneyDetailContract.View view;

    public SendMoneyDetailPresenter(SendMoneyDetailContract.View view) {
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
    public void sendMoney(Contact contact, String value) {
        view.showLoading();
        try {
            double money = Double.valueOf(value);
            Call<Boolean> call = neonApi.sendMoney(contact.getId(), me.getToken(), money);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    processSendMoney(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    processSendMoney(false);
                }
            });
        } catch (Exception e) {
            processSendMoney(false);
        }
    }

    @Override
    public void processSendMoney(boolean sent) {
        if (view != null) {
            if (sent) {
                view.onSendMoneySuccess();
            } else {
                view.onSendMoneyError();
            }
        }
    }
}
