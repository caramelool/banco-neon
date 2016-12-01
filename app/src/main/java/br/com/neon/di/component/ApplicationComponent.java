package br.com.neon.di.component;

import javax.inject.Singleton;

import br.com.neon.di.module.ApiModule;
import br.com.neon.di.module.MeModule;
import br.com.neon.ui.profile.ProfilePresenter;
import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MeModule.class
})
public interface ApplicationComponent {
    void inject(ProfilePresenter presenter);
}
