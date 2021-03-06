package com.nikolam.feature_messages.presentation.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikolam.feature_messages.data.models.ChatUserModel
import com.nikolam.feature_messages.databinding.ChatListItemBinding
import com.nikolam.feature_messages.domain.models.UserDomainModel

class ChatListAdapter (private val listener : ChatListItemClickListener) :
    RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    override fun onBindViewHolder(holder: ChatListAdapter.ChatViewHolder, position: Int) {

        val data = data[position]
        try {
            holder.bind(data, listener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListAdapter.ChatViewHolder {
        val itemBinding =
            ChatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    private val data = ArrayList<UserDomainModel>()

    fun newData(newData: List<UserDomainModel>) {
        if (data == newData)
            return
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(
        private val itemBinding: ChatListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: UserDomainModel, listener: ChatListItemClickListener) {
            itemBinding.apply {
                name.text = data.name
            }

            Glide.with(itemBinding.profilePicImageView).load(data.profilePicture).into(itemBinding.profilePicImageView)

            //Glide.with(itemBinding.profilePicImageView).load("https://picsum.photos/300/300").into(itemBinding.profilePicImageView)
            itemBinding.root.setOnClickListener {
                listener.onChatItemClicked(data.userID)
            }
        }
    }
}
