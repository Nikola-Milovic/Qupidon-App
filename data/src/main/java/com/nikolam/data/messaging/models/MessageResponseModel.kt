package com.nikolam.data.messaging.models

data class MessageResponseModel (val receiver_id : String, val sender_id : String, val content : String, val sent_at_milis : Long)