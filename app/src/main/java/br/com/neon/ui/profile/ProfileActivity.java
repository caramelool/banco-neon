package br.com.neon.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.neon.R;
import br.com.neon.model.User;
import br.com.neon.ui.BaseActivity;
import br.com.neon.ui.BasePresenter;
import br.com.neon.ui.transform.CircleTransform;
import butterknife.BindView;

public class ProfileActivity extends BaseActivity
        implements ProfileContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_image_view)
    ImageView userImageView;
    @BindView(R.id.user_name_text_view)
    TextView userNameTextView;
    @BindView(R.id.user_email_text_view)
    TextView userEmailTextView;
    @BindView(R.id.profile_container)
    LinearLayout profileContainer;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.button_container)
    LinearLayout buttonContainer;
    @BindView(R.id.error_view)
    LinearLayout errorView;

    private ProfilePresenter presenter;

    @Override
    protected int layout() {
        return R.layout.activity_profile;
    }

    @Override
    protected BasePresenter presenter() {
        if (presenter == null) {
            presenter = new ProfilePresenter(this);
        }
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        presenter.requestToken();
    }

    @Override
    public void showLoading() {
        errorView.setVisibility(View.GONE);
        profileContainer.setVisibility(View.GONE);
        buttonContainer.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTokenReceiver(User user) {
        Picasso.with(this)
                .load(user.getImageUrl())
                .transform(new CircleTransform())
                .into(userImageView);
        userNameTextView.setText(user.getNome());
        userEmailTextView.setText(user.getEmail());

        errorView.setVisibility(View.GONE);
        profileContainer.setVisibility(View.VISIBLE);
        buttonContainer.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onTokenErrorReceiver() {
        errorView.setVisibility(View.VISIBLE);
        profileContainer.setVisibility(View.GONE);
        buttonContainer.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
    }
}
