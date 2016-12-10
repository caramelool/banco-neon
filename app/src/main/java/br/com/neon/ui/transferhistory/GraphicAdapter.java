package br.com.neon.ui.transferhistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.custom.ProfileImageView;
import br.com.neon.ui.custom.ResizeAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GraphicAdapter extends RecyclerView.Adapter<GraphicAdapter.GraphicViewHolder> {

    private List<ContactView> contactList;
    private int maxValue;

    public GraphicAdapter() {
        contactList = new ArrayList<>();
    }

    @Override
    public GraphicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GraphicViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(GraphicViewHolder holder, int position) {
        ContactView contactView = contactList.get(position);
        holder.bind(contactView, maxValue);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void updateData(List<Contact> contactList, int maxValue) {
        this.maxValue = maxValue;
        this.contactList.clear();
        this.contactList.addAll(ContactView.fromContactList(contactList));
        notifyDataSetChanged();
    }

    private static class ContactView {
        private Contact contact;
        private boolean animated;

        ContactView(Contact contact) {
            this.contact = contact;
            this.animated = false;
        }

        public Contact getContact() {
            return contact;
        }

        boolean isAnimated() {
            return animated;
        }

        void setAnimated(boolean animated) {
            this.animated = animated;
        }

        static List<ContactView> fromContactList(List<Contact> list) {
            List<ContactView> viewList = new ArrayList<>();
            for (Contact contact : list) {
                viewList.add(new ContactView(contact));
            }
            return viewList;
        }
    }

    static class GraphicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.graphic_line)
        ImageView graphicLine;
        @BindView(R.id.contact_image_view)
        ProfileImageView contactImageView;
        @BindView(R.id.contact_name_text_view)
        TextView contactNameTextView;
        @BindView(R.id.contact_transfer_text_view)
        TextView contactTransferTextView;

        private ContactView contactView;
        private int maxValue;

        GraphicViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_graphic, parent, false));
            ButterKnife.bind(this, itemView);
        }

        void bind(ContactView contactView, int maxValue) {
            this.contactView = contactView;
            this.maxValue = maxValue;
            contactImageView.setContact(contactView.getContact());
            contactImageView.setTextView(14);
            contactNameTextView.setText(contactView.getContact().getName());
            contactTransferTextView.setText(contactView.getContact().getTransferFormatted());

            updateLine();
        }

        private void updateLine() {
            try {
                Contact contact = contactView.getContact();
                int height = itemView.getHeight()
                        - contactImageView.getHeight()
                        - contactNameTextView.getHeight()
                        - contactNameTextView.getHeight();

                int percentTransfer = (int) ((contact.getTransfer() / maxValue) * 100);
                int lineHeight = ((height * percentTransfer) / 100);
                if (contactView.isAnimated()) {
                    graphicLine.getLayoutParams().height = lineHeight;
                } else {
                    ResizeAnimation resizeAnimation = new ResizeAnimation(
                            graphicLine,
                            lineHeight,
                            0
                    );
                    resizeAnimation.setDuration(1000);
                    graphicLine.startAnimation(resizeAnimation);
                    contactView.setAnimated(lineHeight > 0);
                }
            } catch (Exception e) {
                graphicLine.getLayoutParams().height = 0;
            }
        }
    }
}
