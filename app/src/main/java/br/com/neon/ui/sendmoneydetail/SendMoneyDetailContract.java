package br.com.neon.ui.sendmoneydetail;

import br.com.neon.model.Contact;
import br.com.neon.ui.BasePresenter;

public interface SendMoneyDetailContract {
    interface Presenter extends BasePresenter {
        void sendMoney(Contact contact, String value);
        void processSendMoney(boolean sent);
    }
    interface View {
        void onSendMoneyError();
        void onSendMoneySuccess();
        void showLoading();
    }
}
