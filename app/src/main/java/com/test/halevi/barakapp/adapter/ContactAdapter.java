package com.test.halevi.barakapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.halevi.barakapp.R;
import com.test.halevi.barakapp.interfaces.DescriptionInterface;
import com.test.halevi.barakapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barak on 24/08/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> fullContactCellList;
    private DescriptionInterface mLisenner;
    private List<Contact> mFilterData;


    public ContactAdapter(List<Contact> contactList, DescriptionInterface listenner) {
        this.fullContactCellList = contactList;
        mFilterData = new ArrayList<>();
        mFilterData.addAll(fullContactCellList);
        mLisenner = listenner;
    }


    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public Contact getItem(int position) {
        return mFilterData.get(position);
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View horizontalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(horizontalItem);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        final Contact contact = getItem(position);

        if (contact != null) {
            holder.nameView.setText(contact.getName());
            holder.phoneView.setText("0503078333");
            Glide.with(holder.imgView.getContext()).load("http://goo.gl/gEgYUd").into(holder.imgView);


        }
        holder.itemView.setOnClickListener(v -> mLisenner.goToDescription(contact.getId()));
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        public ContactViewHolder(View itemView) {
            super(itemView);
            nameView  =  itemView.findViewById(R.id.name_text);
            phoneView =  itemView.findViewById(R.id.phone_text);
            imgView   =  itemView.findViewById(R.id.img_view);

        }

        private final ImageView imgView;
        TextView nameView;
        TextView phoneView;
    }


    public void search(String searchString) {
        mFilterData.clear();
        if (searchString.length() > 0) {
            if (fullContactCellList != null && fullContactCellList.size() > 0) {
                for (int i = 0; i < fullContactCellList.size(); i++) {
                    if (fullContactCellList.get(i).getName().toLowerCase().contains(searchString)) {
                        mFilterData.add(fullContactCellList.get(i));
                        continue;
                    }
                }
            }
        }
        else {
            mFilterData.addAll(fullContactCellList);
        }
        notifyDataSetChanged();
    }

}
