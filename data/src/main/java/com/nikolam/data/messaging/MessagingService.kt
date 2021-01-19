package com.nikolam.data.messaging

import com.nikolam.common.BaseChatMessagesApiUrl
import com.nikolam.data.messaging.models.MessageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MessagingService {
    @GET("$BaseChatMessagesApiUrl/unread")
    suspend fun getUnreadMessages(@Query("id") id : String) : List<MessageResponseModel>
}