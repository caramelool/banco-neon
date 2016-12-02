package br.com.neon.ui.transferhistory;

import java.util.List;

import br.com.neon.model.Contact;
import br.com.neon.model.TransferHistory;
import br.com.neon.ui.BasePresenter;

public interface TransferHistoryContract {
    interface Presenter extends BasePresenter {
        void requestHistory();
        void processHistory(List<TransferHistory> historyList);
        void updateContactList(TransferHistory history);
        double maxTransfer();
        List<Contact> getListForGraphic();
    }
    interface View {
        void showLoading();
        void onHistoryReceiver(List<Contact> contactList);
        void onErrorReceiver();
    }
}
