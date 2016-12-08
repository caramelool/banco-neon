package br.com.neon.ui.transferhistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.neon.R;
import br.com.neon.model.Contact;
import br.com.neon.ui.custom.ProfileImageView;
import br.com.neon.ui.custom.ResizeAnimation;
import br.com.neon.ui.transform.CircleTransform;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GraphicAdapter extends RecyclerView.Adapter<GraphicAdapter.GraphicViewHolder> {

    private List<Contact> contactList;
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
        Contact contact = contactList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void updateData(List<Contact> contactList, int maxValue) {
        this.maxValue = maxValue + 100;
        this.contactList.clear();
        this.contactList.addAll(contactList);
        notifyDataSetChanged();
    }

    class GraphicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.graphic_line)
        ImageView graphicLine;
        @BindView(R.id.contact_image_view)
        ProfileImageView contactImageView;
        @BindView(R.id.contact_name_text_view)
        TextView contactNameTextView;
        @BindView(R.id.contact_transfer_text_view)
        TextView contactTransferTextView;

        private boolean animated;

        GraphicViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_graphic, parent, false));
            ButterKnife.bind(this, itemView);
        }

        void bind(Contact contact) {
            contactImageView.setContact(contact);
            contactImageView.setTextView(14);
            contactNameTextView.setText(contact.getName());
            contactTransferTextView.setText(contact.getTransferFormatted());

            updateLine();
        }

        private void updateLine() {
            try {
                int position = getAdapterPosition();
                Contact contact = contactList.get(position);

                int height = itemView.getHeight()
                        - contactImageView.getHeight()
                        - contactNameTextView.getHeight()
                        - contactNameTextView.getHeight();

                int percentTransfer = (int) ((contact.getTransfer() / maxValue) * 100);
                int lineHeight = ((height * percentTransfer) / 100);
                if (animated) {
                    graphicLine.getLayoutParams().height = lineHeight;
                } else {
                    animated = lineHeight > 0;
                    ResizeAnimation resizeAnimation = new ResizeAnimation(
                            graphicLine,
                            lineHeight,
                            0
                    );
                    resizeAnimation.setDuration(1200);
                    graphicLine.startAnimation(resizeAnimation);
                }
            } catch (Exception e) {
                graphicLine.getLayoutParams().height = 0;
            }
        }
    }
}
