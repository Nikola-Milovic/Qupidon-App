package com.nikolam.common.messaging

import com.google.gson.Gson
import com.nikolam.common.db.models.MessageDataModel
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import timber.log.Timber
import java.net.URI

class MessagingManager {

    private var connected = false

    private lateinit var socket: Socket

    private val uri = URI.create("http://10.0.2.2:3001/")

    private lateinit var userID: String

    private val gson = Gson()

    fun connect(userID : String) {
        if (connected) return
        socket = IO.socket(uri)
        connected = true

        this.userID = userID
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);
        socket.connect()
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Timber.d("""Socket id is ${socket.id()}""")
        socket.emit("onUserConnected", gson.toJson(UserConnectedMessage(userID, socket.id())), object : Ack {
            override fun call(vararg args: Any?) {
                Timber.d("Emit is okay")
            }
        })
    }

    private val onConnectionError: Emitter.Listener = Emitter.Listener {
        Timber.d("""Connection error is $it""")
    }

    fun sendMessage(sendID : String, contents : String) {
        socket.emit("onMessageSent", gson.toJson(SendMessageMessage(userID, sendID, contents)))
    }

    fun disconnect() {
        socket.emit("onUserDisconnected", gson.toJson(UserDisconnectedMessage(userID)), object : Ack {
            override fun call(vararg args: Any?) {
                Timber.d("Emit is okay")
            }
        })
    }

    private data class UserConnectedMessage(val user_id : String, val socket_id : String)
    private data class SendMessageMessage(val sender_id : String, val receiver_id : String, val contents: String)
    private data class UserDisconnectedMessage(val user_id : String)

}