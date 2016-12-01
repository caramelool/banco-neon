package br.com.neon.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.neon.model.User;
import dagger.Module;
import dagger.Provides;

@Module
public class MeModule {

    @Singleton
    @Provides
    @Named("me_image_url")
    String provideUserImageUrl() {
        return "https://scontent.fcgh3-1.fna.fbcdn.net/v/t1.0-1/p320x320/12743617_981450971929446_8588284176885548315_n.jpg?oh=c02e42501a8c5b003adc1661fdb7e7ee&oe=58B96BB6";
    }

    @Singleton
    @Provides
    User provideUser(@Named("me_image_url") String imageUrl) {
        return new User("Lucas Caramelo", "lucascaramelo@gmail.com", imageUrl, null);
    }
}
