package br.com.neon.ui.sendmoneydetail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import br.com.neon.model.Contact;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class SendMoneyDetailPresenterTest {

    @Mock
    SendMoneyDetailContract.View view;
    @Mock
    Contact contact;

    private SendMoneyDetailContract.Presenter presenter;

    @Before
    public void before() {
        initMocks(this);

        presenter = spy(new SendMoneyDetailPresenter(view));
    }

    @Test
    public void test_send_money_success() {
        presenter.processSendMoney(true);

        verify(view).onSendMoneySuccess();
        verify(view, never()).onSendMoneyError();
    }

    @Test
    public void test_send_money_fail() {
        presenter.processSendMoney(false);

        verify(view).onSendMoneyError();
        verify(view, never()).onSendMoneySuccess();
    }

    @Test
    public void test_send_money_invalid_params() {
        presenter.sendMoney(null, "");

        verify(view).onSendMoneyError();
        verify(view, never()).onSendMoneySuccess();
    }
}
