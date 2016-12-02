package br.com.neon.ui.transferhistory;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.com.neon.NeonApplication;
import br.com.neon.model.Contact;
import br.com.neon.model.TransferHistory;
import br.com.neon.model.User;
import br.com.neon.rest.NeonApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferHistoryPresenter implements TransferHistoryContract.Presenter {

    @Inject
    NeonApi neonApi;
    @Inject
    User me;
    @Inject
    List<Contact> contactList;

    private TransferHistoryContract.View view;

    public TransferHistoryPresenter(TransferHistoryContract.View view) {
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
    public void requestHistory() {
        view.showLoading();
        Call<List<TransferHistory>> call = neonApi.getTransfers(me.getToken());
        call.enqueue(new Callback<List<TransferHistory>>() {
            @Override
            public void onResponse(Call<List<TransferHistory>> call,
                                   Response<List<TransferHistory>> response) {
                if (view != null) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        processHistory(response.body());
                    } else {
                        view.onErrorReceiver();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TransferHistory>> call, Throwable t) {
                if (view != null) {
                    view.onErrorReceiver();
                }
            }
        });
    }

    @Override
    public void processHistory(List<TransferHistory> historyList) {
        if (historyList != null) {
            for (TransferHistory history : historyList) {
                updateContactList(history);
            }
            view.onHistoryReceiver(contactList);
        } else {
            view.onErrorReceiver();
        }
    }

    @Override
    public void updateContactList(TransferHistory history) {
        for (Contact contact : contactList) {
            if (contact.getId().equals(history.getClientId())) {
                contact.setTransfer(history.getValue());
            }
        }
    }

    @Override
    public double maxTransfer() {
        double max = 0;
        for (Contact contact: contactList) {
            if (contact.getTransfer() > max) {
                max = contact.getTransfer();
            }
        }
        return max;
    }

    @Override
    public List<Contact> getListForGraphic() {
        List<Contact> contactList = new ArrayList<>();
        contactList.addAll(this.contactList);
        Collections.sort(contactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                if (contact.getTransfer() > t1.getTransfer()) {
                    return -1;
                } else if (contact.getTransfer() < t1.getTransfer()){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return contactList;
    }
}
