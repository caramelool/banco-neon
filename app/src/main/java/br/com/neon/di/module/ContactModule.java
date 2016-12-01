package br.com.neon.di.module;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.neon.model.Contact;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactModule {

    @Provides
    @Named("json_contact_list")
    String provideContactJson() {
        return "[{\"id\":1,\"name\":\"Maria\",\"phone\":\"(11) 99423-2312\",\"email\":\"maria@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/1.jpg\"},{\"id\":2,\"name\":\"Carlos\",\"phone\":\"(11) 99053-0012\",\"email\":\"carlos@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/2.jpg\"},{\"id\":3,\"name\":\"Pedro\",\"phone\":\"(11) 99252-0521\",\"email\":\"pedro@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/3.jpg\"},{\"id\":4,\"name\":\"Henrique\",\"phone\":\"(11) 2234-5231\",\"email\":\"henrique@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/4.jpg\"},{\"id\":5,\"name\":\"Isabelle\",\"phone\":\"(11) 99302-0502\",\"email\":\"isabelle@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/5.jpg\"},{\"id\":6,\"name\":\"Isabela\",\"phone\":\"(11) 99762-0502\",\"email\":\"isabela@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/6.jpg\"},{\"id\":7,\"name\":\"João Carlos\",\"phone\":\"(11) 99762-0502\",\"email\":\"joao.carlos@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/7.jpg\"},{\"id\":8,\"name\":\"Fatima\",\"phone\":\"(11) 2372-1122\",\"email\":\"fafa123@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/8.jpg\"},{\"id\":9,\"name\":\"Gabriela\",\"phone\":\"(11) 4423-4022\",\"email\":\"gabi@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/9.jpg\"},{\"id\":10,\"name\":\"Fernanda\",\"phone\":\"(11) 4423-4022\",\"email\":\"fe@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/10.jpg\"},{\"id\":11,\"name\":\"Claudio\",\"phone\":\"(11) 9332-9292\",\"email\":\"claudio@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/11.jpg\"},{\"id\":12,\"name\":\"Michele\",\"phone\":\"(11) 9332-9292\",\"email\":\"michele@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/women/12.jpg\"},{\"id\":13,\"name\":\"Luiz Augusto\",\"phone\":\"(11) 9111-1119\",\"email\":\"lugusto@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/13.jpg\"},{\"id\":14,\"name\":\"Bruno\",\"phone\":\"(11) 2311-1123\",\"email\":\"bruno@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/14.jpg\"},{\"id\":15,\"name\":\"Victor\",\"phone\":\"(11) 9494-9002\",\"email\":\"victor@gmail.com\",\"image_url\":\"https://randomuser.me/api/portraits/men/15.jpg\"}]";
    }

    @Singleton
    @Provides
    List<Contact> provideContactList(@Named("json_contact_list") String json) {
        return new Gson().fromJson(json, new TypeToken<List<Contact>>(){}.getType());
    }

}
