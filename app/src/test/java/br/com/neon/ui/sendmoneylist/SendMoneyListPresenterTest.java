package br.com.neon.ui.sendmoneylist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import br.com.neon.model.Contact;

import static org.mockito.Mockito.*;

import static org.mockito.MockitoAnnotations.*;

public class SendMoneyListPresenterTest {

    @Mock
    SendMoneyListContract.View view;
    @Mock
    List<Contact> contactList;

    private SendMoneyListContract.Presenter presenter;

    @Before
    public void before() {
        initMocks(this);

        presenter = spy(new SendMoneyListPresenter(view));
    }

    @Test
    public void test_if_receiver_contact_list() {
        doReturn(contactList).when(presenter).getContactList();
        presenter.requestContactList();
        verify(presenter).hasContactList();
        verify(view).onContactListReceiver(contactList);
    }

    @Test
    public void test_if_not_receiver_contact_list() {
        doReturn(null).when(presenter).getContactList();
        presenter.requestContactList();
        verify(presenter).hasContactList();
        verify(view).onErrorReceiver();
    }
}
