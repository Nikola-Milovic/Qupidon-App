package com.nikolam.feature_messages.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikolam.feature_messages.databinding.LeftMessageItemBinding
import com.nikolam.feature_messages.databinding.RightMessageItemBinding
import com.nikolam.feature_messages.domain.models.MessageDomainModel

class ChatAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = data[position]
        if (data.isMine) {
            try {
                (holder as MessageRightViewHolder).bind(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                (holder as MessageLeftViewHolder).bind(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].isMine) {
            return 0 // right message
        } else {
            return 1 // left message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> { //right
                val itemBinding =
                        RightMessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MessageRightViewHolder(itemBinding)
            }
            else -> {
                val itemBinding = LeftMessageItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
                MessageLeftViewHolder(itemBinding)
            }
        }
    }

    override fun getItemCount() = data.size

    private val data = ArrayList<MessageDomainModel>()

    fun newData(newData: List<MessageDomainModel>) {
        if (data == newData)
            return
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class MessageRightViewHolder(
            private val itemBinding: RightMessageItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: MessageDomainModel) {
            itemBinding.apply {
                senderTextViewRight.text = data.userID
                messageTextViewRight.text = data.content
            }
        }
    }

    inner class MessageLeftViewHolder(
            private val itemBinding: LeftMessageItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: MessageDomainModel) {
            itemBinding.apply {
                senderTextView.text = data.userID
                messageTextView.text = data.content
            }
        }
    }
}
