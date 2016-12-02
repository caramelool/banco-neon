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
        ImageView contactImageView;
        @BindView(R.id.contact_name_text_view)
        TextView contactNameTextView;
        @BindView(R.id.contact_transfer_text_view)
        TextView contactTransferTextView;

        private Context context;

        GraphicViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_graphic, parent, false));
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        void bind(Contact contact) {
            if (contact.hasImageUrl()) {
                int resize = R.dimen.contact_circle_graphic_resize;
                Picasso.with(context)
                        .load(contact.getImageUrl())
                        .placeholder(R.drawable.ic_contact)
                        .resizeDimen(resize, resize)
                        .transform(new CircleTransform())
                        .into(contactImageView);
            } else {
                contactImageView.setImageResource(R.drawable.ic_contact);
            }

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
                graphicLine.getLayoutParams().height = lineHeight;
            } catch (Exception e) {
                graphicLine.getLayoutParams().height = 0;
            }
        }
    }
}
