package br.com.neon.ui.sendmoneylist;

import java.util.List;

import javax.inject.Inject;

import br.com.neon.NeonApplication;
import br.com.neon.model.Contact;

public class SendMoneyListPresenter implements SendMoneyListContract.Presenter {

    @Inject
    List<Contact> contactList;

    private SendMoneyListContract.View view;

    public SendMoneyListPresenter(SendMoneyListContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        NeonApplication.getInstance().getComponent().inject(this);
    }

    @Override
    public boolean hasContactList() {
        return getContactList() != null;
    }

    @Override
    public List<Contact> getContactList() {
        return contactList;
    }

    @Override
    public void requestContactList() {
        if (hasContactList()) {
            view.onContactListReceiver(getContactList());
        } else {
            view.onErrorReceiver();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
