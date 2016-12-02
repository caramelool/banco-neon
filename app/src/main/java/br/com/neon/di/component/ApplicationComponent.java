package br.com.neon.di.component;

import java.util.List;

import javax.inject.Singleton;

import br.com.neon.di.module.ApiModule;
import br.com.neon.di.module.ContactModule;
import br.com.neon.di.module.MeModule;
import br.com.neon.model.Contact;
import br.com.neon.ui.profile.ProfilePresenter;
import br.com.neon.ui.sendmoneydetail.SendMoneyDetailPresenter;
import br.com.neon.ui.sendmoneylist.SendMoneyListPresenter;
import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MeModule.class,
        ContactModule.class
})
public interface ApplicationComponent {
    List<Contact> provideContactList();

    void inject(ProfilePresenter presenter);

    void inject(SendMoneyListPresenter presenter);

    void inject(SendMoneyDetailPresenter sendMoneyDetailPresenter);


}
