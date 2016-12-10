package br.com.neon.ui.sendmoneydetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.custom.ProfileImageView;
import br.com.neon.ui.sendmoneylist.SendMoneyListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SendMoneyDetailFragment extends DialogFragment
        implements SendMoneyDetailContract.View {

    private static final String KEY_CONTACT = "key_contact";

    @BindView(R.id.contact_image_view)
    ProfileImageView contactImageView;
    @BindView(R.id.contact_name_text_view)
    TextView contactNameTextView;
    @BindView(R.id.contact_phone_text_view)
    TextView contactPhoneTextView;
    @BindView(R.id.value_edit_text)
    EditText valueEditText;
    @BindView(R.id.container_loading)
    FrameLayout containerLoading;

    private Unbinder unbinder;
    private SendMoneyDetailContract.Presenter presenter;
    private Contact contact;
    private OnSendMoneyListener onSendMoneyListener;

    public static SendMoneyDetailFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_CONTACT, contact);
        SendMoneyDetailFragment fragment = new SendMoneyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSendMoneyListener = (OnSendMoneyListener) context;
        } catch (ClassCastException e) {
            onSendMoneyListener = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_money_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SendMoneyDetailPresenter(this);
        presenter.onCreate();

        initContract();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        onSendMoneyListener = null;
        super.onDetach();
    }

    @Override
    public void onSendMoneySuccess() {
        if (onSendMoneyListener != null) {
            onSendMoneyListener.onSendMoney(true);
        }
        dismiss();
    }

    @Override
    public void onSendMoneyError() {
        if (onSendMoneyListener != null) {
            onSendMoneyListener.onSendMoney(false);
        }
        dismiss();
    }

    @Override
    public void showLoading() {
        setCancelable(false);
        containerLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        setCancelable(true);
        containerLoading.setVisibility(View.GONE);
    }

    @OnClick({
            R.id.close_image_button,
            R.id.send_button
    })
    public void onClick(View view) {
        int itemId = view.getId();
        switch (itemId) {
            case R.id.close_image_button:
                dismiss();
                break;
            case R.id.send_button:
                presenter.sendMoney(contact, valueEditText.getText().toString());
                break;
        }
    }

    private void initContract() {
        try {
            hideLoading();
            contact = getArguments().getParcelable(KEY_CONTACT);

            if (contact == null) {
                onSendMoneyError();
                return;
            }

            contactImageView.setContact(contact);
            contactNameTextView.setText(contact.getName());
            contactPhoneTextView.setText(contact.getPhone());
        } catch (Exception e) {
            onSendMoneyError();
        }
    }

    public void show(SendMoneyListActivity activity) {
        show(activity.getSupportFragmentManager(), getClass().getSimpleName());
    }
}
