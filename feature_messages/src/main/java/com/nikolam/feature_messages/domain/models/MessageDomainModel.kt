package com.nikolam.feature_messages.domain.models

import com.nikolam.data.db.models.MessageDataModel

data class MessageDomainModel(val senderID: String, val receiverID: String, val content: String, val addedAtMillis: Long, val isMine: Boolean)


fun MessageDataModel.toDomainModel(): MessageDomainModel {
    return MessageDomainModel(receiverID = receiverID ?: "", senderID = senderID
            ?: "", content = contents ?: "", addedAtMillis = addedAtMillis ?: 0, isMine = isMine
            ?: false)
}