package com.nikolam.common.messaging

import com.nikolam.common.BaseChatMessagesApiUrl
import com.nikolam.common.BaseChatUserApiUrl
import com.nikolam.common.db.models.MessageDataModel
import com.nikolam.common.messaging.models.MessageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MessagingService {
    @GET("$BaseChatMessagesApiUrl/unread")
    suspend fun getUnreadMessages(@Query("id") id : String) : List<MessageResponseModel>
}