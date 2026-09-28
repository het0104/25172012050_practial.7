package com.example.a24012011122_mad_pr7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contactList: ArrayList<Contact>,
    private val onDeleteClick: ((Contact) -> Unit)? = null
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)

        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {

        val contact = contactList[position]

        holder.tvContactName.text = contact.name
        holder.tvContactPhone.text = contact.phoneNo
        holder.tvContactEmail.text = contact.emailId
        holder.tvContactAddress.text = contact.address

        holder.deleteIcon.setOnClickListener {
            onDeleteClick?.invoke(contact)
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun removeContact(contact: Contact) {

        val position = contactList.indexOf(contact)

        if (position != -1) {
            contactList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ContactViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val tvContactName: TextView =
            itemView.findViewById(R.id.textview1)

        val tvContactPhone: TextView =
            itemView.findViewById(R.id.textview2)

        val tvContactEmail: TextView =
            itemView.findViewById(R.id.textview3)

        val tvContactAddress: TextView =
            itemView.findViewById(R.id.textview4)

        val deleteIcon: ImageView =
            itemView.findViewById(R.id.del_icon)
    }
}