package br.com.neon.ui.sendmoneylist;

import java.util.List;

import br.com.neon.model.Contact;
import br.com.neon.ui.BasePresenter;

public interface SendMoneyListContract {
    interface Presenter extends BasePresenter {
        boolean hasContactList();
        List<Contact> getContactList();
        void requestContactList();
    }
    interface View {
        void onContactListReceiver(List<Contact> contactList);
        void onErrorReceiver();
        void showLoading();
    }
}
