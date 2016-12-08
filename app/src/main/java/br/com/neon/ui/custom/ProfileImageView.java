package br.com.neon.ui.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.model.User;
import br.com.neon.ui.transform.CircleTransform;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileImageView extends FrameLayout {

    @BindView(R.id.profile_text_view)
    TextView profileTextView;

    @BindView(R.id.profile_image_view)
    ImageView profileImageView;

    public ProfileImageView(Context context) {
        this(context, null);
    }

    public ProfileImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            View view = inflate(context, R.layout.view_profile, this);
            ButterKnife.bind(this, view);
        }
    }

    public void setUser(User user) {
        process(user.getImageUrl(), user.getName());
    }

    public void setContact(Contact contact) {
        process(contact.getImageUrl(), contact.getName());
    }

    public void setTextView(float size) {
        profileTextView.setTextSize(size);
    }

    private void process(String url, String name) {
        processName(name);
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(getContext())
                    .load(url)
                    .transform(new CircleTransform())
                    .into(profileImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            profileImageView.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onError() {
                            profileImageView.setVisibility(GONE);
                        }
                    });
        } else {
            profileImageView.setVisibility(GONE);
        }
    }


    private void processName(String name) {
        String[] split = name.split(" ");
        String newName = split[0].substring(0,1);
        if (split.length > 1) {
            newName += split[1].substring(0,1);
        }
        profileTextView.setText(newName);
    }

}
