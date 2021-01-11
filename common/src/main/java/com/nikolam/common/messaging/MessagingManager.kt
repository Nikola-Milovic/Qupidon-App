package com.nikolam.common.messaging

import com.google.gson.Gson
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
        socket.connect()
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Timber.d("""Socket id is ${socket.id()}""")
//        socket.emit("onUserConnected", gson.toJson(UserConnectedMessage(userID, socket.id())), object : Ack {
//            override fun call(vararg args: Any?) {
//                Timber.d("Emit is okay")
//            }
//        })
        socket.emit("onUserConnected", "1")
    }

    private val onConnectionError: Emitter.Listener = Emitter.Listener {
        Timber.d("""Connection error is $it""")
    }

    fun SendMessage(contents : String) {
        socket.emit("onMessageSent", contents)
    }

    private data class UserConnectedMessage(val user_id : String, val socket_id : String)

}