package br.com.neon.ui.sendmoneylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.BaseActivity;
import br.com.neon.ui.BasePresenter;
import br.com.neon.ui.itemdecoration.DividerItemDecoration;
import butterknife.BindView;

public class SendMoneyListActivity extends BaseActivity
        implements SendMoneyListContract.View, ContactListAdapter.OnContactSelectListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.error_view)
    LinearLayout errorView;


    private SendMoneyListContract.Presenter presenter;
    private ContactListAdapter adapter;

    @Override
    protected int layout() {
        return R.layout.activity_send_money_list;
    }

    @Override
    protected BasePresenter presenter() {
        if (presenter == null) {
            presenter = new SendMoneyListPresenter(this);
        }
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initAdapter();

        presenter.requestContactList();
    }

    @Override
    public void onContactListReceiver(List<Contact> contactList) {
        adapter.updateData(contactList);

        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onErrorReceiver() {
        errorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onContactSelect(Contact contact) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.neon_line));
        recyclerView.setAdapter(adapter = new ContactListAdapter());
        adapter.setOnContactSelectListener(this);
    }
}
