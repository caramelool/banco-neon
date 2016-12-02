package br.com.neon.ui.transferhistory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.model.TransferHistory;
import br.com.neon.ui.BaseActivity;
import br.com.neon.ui.BasePresenter;
import br.com.neon.ui.itemdecoration.DividerItemDecoration;
import br.com.neon.ui.contact.ContactListAdapter;
import butterknife.BindView;
import butterknife.OnClick;

public class TransferHistoryActivity extends BaseActivity
        implements TransferHistoryContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.history_graphic_recycler_view)
    RecyclerView historyGraphicRecyclerView;
    @BindView(R.id.history_recycler_view)
    RecyclerView historyRecyclerView;
    @BindView(R.id.recycler_container)
    NestedScrollView recyclerContainer;
    @BindView(R.id.loading_progress)
    ProgressBar loadingProgress;
    @BindView(R.id.error_view)
    LinearLayout errorView;


    private TransferHistoryContract.Presenter presenter;
    private ContactListAdapter adapter;
    private GraphicAdapter graphicAdapter;

    @Override
    protected int layout() {
        return R.layout.activity_transfer_history;
    }

    @Override
    protected BasePresenter presenter() {
        if (presenter == null) {
            presenter = new TransferHistoryPresenter(this);
        }
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initGraphic();
        initHistory();

        showLoading();
        presenter.processHistory(new ArrayList<TransferHistory>());

//TODO always returning 400
//        presenter.requestHistory();
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

    private void initGraphic() {
        historyGraphicRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        historyGraphicRecyclerView.setHasFixedSize(true);
        historyGraphicRecyclerView.setAdapter(graphicAdapter = new GraphicAdapter());
        historyGraphicRecyclerView.setNestedScrollingEnabled(false);
        historyGraphicRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                historyGraphicRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                graphicAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initHistory() {
        historyRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.neon_line));
        historyRecyclerView.setAdapter(adapter = new ContactListAdapter(true));
        historyRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void showLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        recyclerContainer.setVisibility(View.GONE);
    }

    @Override
    public void onHistoryReceiver(List<Contact> contactList) {

        adapter.updateData(contactList);

        graphicAdapter.updateData(
                presenter.getListForGraphic(),
                (int) presenter.maxTransfer());

        loadingProgress.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        recyclerContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorReceiver() {
        loadingProgress.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        recyclerContainer.setVisibility(View.GONE);
    }


    @OnClick({
            R.id.try_again_button
    })
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.try_again_button: {
                presenter.requestHistory();
            }
        }
    }
}
