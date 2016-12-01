package br.com.neon.ui.profile;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
    public void token_received_fail() {
        presenter.processToken(null);
        verify(view).onTokenErrorReceiver();
        assertEquals(null, user.getToken());
    }

    @Test
    public void token_received_success() {
        final String mockToken = "mock_token";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                doReturn(mockToken).when(user).getToken();
                return null;
            }
        }).when(user).updateToken(anyString());

        presenter.processToken(anyString());

        verify(view).onTokenReceiver(user);

        assertEquals(mockToken, user.getToken());
    }
}
