package com.nikolam.common.messaging

import com.google.gson.Gson
import com.nikolam.common.db.AppDatabase
import com.nikolam.common.db.models.MessageDataModel
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.net.URI
import java.util.*

class MessagingManager(private val db : AppDatabase) {

    private var connected = false

    private lateinit var socket: Socket

    private val uri = URI.create("http://10.0.2.2:3001/")

    private lateinit var userID: String

    private val gson = Gson()

    fun connect(userID: String) {
        if (connected) return
        socket = IO.socket(uri)
        connected = true

        this.userID = userID
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectionError);
        socket.on("onMessageReceived", onMessageReceived);
        socket.connect()
    }

    fun disconnect() {
        socket.emit(
            "onUserDisconnected",
            gson.toJson(UserDisconnectedMessage(userID)),
            object : Ack {
                override fun call(vararg args: Any?) {
                    Timber.d("Emit is okay")
                }
            })
    }

    fun getID() : String {
        return userID
    }

    private val onConnect: Emitter.Listener = Emitter.Listener {
        Timber.d("""Socket id is ${socket.id()}""")
        socket.emit(
            "onUserConnected",
            gson.toJson(UserConnectedMessage(userID, socket.id())),
            object : Ack {
                override fun call(vararg args: Any?) {
                    Timber.d("Emit is okay")
                }
            })
    }

    private val onConnectionError: Emitter.Listener = Emitter.Listener {
        Timber.d("""Connection error is $it""")
    }

    private val onMessageReceived: Emitter.Listener = Emitter.Listener {
        Timber.d("""On Message Received is $it""")
        val data: JSONObject = JSONObject(it[0].toString())
        val contents : String
        val receiverID : String
        val senderID : String
        Timber.d(data.toString())
        try {
            contents = data.getString("contents")
            receiverID = data.getString("receiver_id")
            senderID = data.getString("sender_id")
        } catch (e: JSONException) {
            Timber.d(e.localizedMessage)
            return@Listener
        }
        GlobalScope.launch(Dispatchers.IO) {
            db.chatDao().addMessage(MessageDataModel(contents = contents, receiverID = userID, senderID = senderID, isMine = false, addedAtMillis = Date().time, uid = null))
        }

    }

    fun sendMessage(sendID: String, contents: String) {
        socket.emit("onMessageSent", gson.toJson(SendMessageMessage(userID, sendID, contents)))

        GlobalScope.launch(Dispatchers.IO) {
            db.chatDao().addMessage(MessageDataModel(contents = contents, senderID = userID, receiverID = sendID, isMine = true, addedAtMillis = Date().time, uid = null))
        }
    }

    private data class UserConnectedMessage(val user_id: String, val socket_id: String)
    private data class SendMessageMessage(
        val sender_id: String,
        val receiver_id: String,
        val contents: String
    )
    private data class UserDisconnectedMessage(val user_id: String)

}