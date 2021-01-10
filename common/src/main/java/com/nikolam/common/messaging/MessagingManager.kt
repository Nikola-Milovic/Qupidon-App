package com.nikolam.common.messaging

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import timber.log.Timber
import java.net.URI

class MessagingManager {

    private var connected = false

    private lateinit var socket: Socket

    private val uri = URI.create("http://10.0.2.2:3001/")

    fun connect() {
        if (connected) return
        socket = IO.socket(uri)
        connected = true

        socket.connect()
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);


    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Timber.d("""Socket id is ${socket.id()}""")
    }

    private val onConnectionError: Emitter.Listener = Emitter.Listener {
        Timber.d("""Connection error is $it""")
    }



}