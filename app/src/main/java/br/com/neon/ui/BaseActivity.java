package br.com.neon.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int layout();
    protected abstract BasePresenter presenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());
        unbinder = ButterKnife.bind(this);

        if (presenter() != null) {
            presenter().onCreate();
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter() != null) {
            presenter().onDestroy();
        }
        unbinder.unbind();
        super.onDestroy();
    }
}
