package br.com.neon.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.neon.R;
import br.com.neon.model.User;
import br.com.neon.ui.BaseActivity;
import br.com.neon.ui.BasePresenter;
import br.com.neon.ui.custom.ProfileImageView;
import br.com.neon.ui.sendmoneylist.SendMoneyListActivity;
import br.com.neon.ui.transferhistory.TransferHistoryActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseActivity
        implements ProfileContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_image_view)
    ProfileImageView userImageView;
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
    protected void onResume() {
        super.onResume();
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
        userImageView.setUser(user);

        userNameTextView.setText(user.getName());
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

    @OnClick({
            R.id.send_history_button,
            R.id.send_money_button,
            R.id.try_again_button
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_money_button:
                startActivity(new Intent(this, SendMoneyListActivity.class));
                break;
            case R.id.send_history_button:
                startActivity(new Intent(this, TransferHistoryActivity.class));
                break;
            case R.id.try_again_button: {
                presenter.requestToken();
            }
        }
    }
}
