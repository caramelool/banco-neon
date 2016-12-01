package br.com.neon.ui.profile;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import br.com.neon.model.User;

import static org.mockito.MockitoAnnotations.initMocks;

public class ProfilePresenterTest {

    @Mock
    User user;
    @Mock
    ProfileContract.View view;

    private ProfilePresenter presenter;

    @Before
    public void before() {
        initMocks(this);

        presenter = spy(new ProfilePresenter(view));
        presenter.user = user;
    }

    @Test
    public void token_received_success() {
        presenter.processToken(anyString());
        verify(view).onTokenReceiver(user);
    }

    @Test
    public void token_received_fail() {
        presenter.processToken(null);
        verify(view).onTokenErrorReceiver();
    }
}
