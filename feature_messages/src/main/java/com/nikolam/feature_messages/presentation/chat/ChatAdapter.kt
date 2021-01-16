package com.nikolam.feature_messages.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikolam.feature_messages.data.models.ChatUserModel
import com.nikolam.feature_messages.databinding.ChatListItemBinding
import com.nikolam.feature_messages.domain.models.UserDomainModel

class ChatAdapter () :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onBindViewHolder(holder: ChatAdapter.ChatViewHolder, position: Int) {

        val data = data[position]
        try {
            holder.bind(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {
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

    inner class MineMessageViewHolder(
        private val itemBinding: ChatListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: UserDomainModel) {
            itemBinding.apply {
                name.text = data.name
            }
        }
    }

    inner class MessageViewHolder(
            private val itemBinding: ChatListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: UserDomainModel) {
            itemBinding.apply {
                name.text = data.name
            }
        }
    }
}