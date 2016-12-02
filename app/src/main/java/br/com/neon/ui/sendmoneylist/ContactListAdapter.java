package br.com.neon.ui.sendmoneylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.transform.CircleTransform;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private boolean showTransfer;
    private OnContactSelectListener onContactSelectListener;

    public ContactListAdapter() {
        this(false);
    }

    public ContactListAdapter(boolean showTransfer) {
        this.showTransfer = showTransfer;
        contactList = new ArrayList<>();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void updateData(List<Contact> contactList) {
        this.contactList.clear();
        this.contactList.addAll(contactList);
        notifyDataSetChanged();
    }

    public void setOnContactSelectListener(OnContactSelectListener onContactSelectListener) {
        this.onContactSelectListener = onContactSelectListener;
    }

    public interface OnContactSelectListener {
        void onContactSelect(Contact contact);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        @BindView(R.id.contact_image_view)
        ImageView contactImageView;
        @BindView(R.id.contact_name_text_view)
        TextView contactNameTextView;
        @BindView(R.id.contact_phone_text_view)
        TextView contactPhoneTextView;
        @BindView(R.id.contact_transfer_text_view)
        TextView contactTransferTextView;

        private Context context;

        ContactViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_contact, parent, false));
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        void bind(Contact contact) {
            if (contact.hasImageUrl()) {
                Picasso.with(context)
                        .load(contact.getImageUrl())
                        .placeholder(R.drawable.ic_contact)
                        .resizeDimen(R.dimen.contact_circle_resize, R.dimen.contact_circle_resize)
                        .transform(new CircleTransform())
                        .into(contactImageView);
            } else {
                contactImageView.setImageResource(R.drawable.ic_contact);
            }

            contactNameTextView.setText(contact.getName());
            contactPhoneTextView.setText(contact.getPhone());

            if (showTransfer) {
                contactTransferTextView.setText(contact.getTransferFormatted());
                contactTransferTextView.setVisibility(View.VISIBLE);
            } else {
                contactTransferTextView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }

            Contact contact = contactList.get(getAdapterPosition());
            if (onContactSelectListener != null) {
                onContactSelectListener.onContactSelect(contact);
            }
        }
    }
}
