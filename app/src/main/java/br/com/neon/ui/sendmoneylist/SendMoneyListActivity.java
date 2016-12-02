package br.com.neon.ui.sendmoneylist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.BaseActivity;
import br.com.neon.ui.BasePresenter;
import br.com.neon.ui.contact.ContactListAdapter;
import br.com.neon.ui.itemdecoration.DividerItemDecoration;
import br.com.neon.ui.sendmoneydetail.OnSendMoneyListener;
import br.com.neon.ui.sendmoneydetail.SendMoneyDetailFragment;
import butterknife.BindView;

public class SendMoneyListActivity extends BaseActivity
        implements SendMoneyListContract.View, ContactListAdapter.OnContactSelectListener,
        OnSendMoneyListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.error_view)
    LinearLayout errorView;
    @BindView(R.id.container)
    FrameLayout container;


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
        SendMoneyDetailFragment.newInstance(contact).show(this);
    }

    @Override
    public void onSendMoney(boolean sent) {
        int message;
        int color;
        int buttonColor;
        if (sent) {
            message = R.string.message_transfer_success;
            color = R.color.persian_green;
            buttonColor = R.color.cello;
        } else {
            message = R.string.message_transfer_fail;
            color = android.R.color.holo_red_dark;
            buttonColor = R.color.bon_jour;
        }
        final Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });

        View view = snackbar.getView();
        view.setBackgroundResource(color);

        TextView text = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        text.setTextColor(ContextCompat.getColor(this, R.color.bon_jour));
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        Button action = (Button) view.findViewById(android.support.design.R.id.snackbar_action);
        action.setTextColor(ContextCompat.getColor(this, buttonColor));
        action.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        snackbar.show();
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
