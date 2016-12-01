package br.com.neon;

import android.app.Application;

import br.com.neon.di.component.ApplicationComponent;
import br.com.neon.di.component.DaggerApplicationComponent;
import br.com.neon.di.module.ApiModule;
import br.com.neon.di.module.MeModule;

public class NeonApplication extends Application {

    private static NeonApplication sInstance;

    private ApplicationComponent applicationComponent;

    public static NeonApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .apiModule(new ApiModule())
                    .meModule(new MeModule())
                    .build();
        }
        return applicationComponent;
    }
}
