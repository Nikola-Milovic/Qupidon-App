package com.nikolam.qupidon.di

import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod

object PermissionRequest {
    private const val PERMISSIONS_ENDPOINT = "/me/permissions"
    private const val APP = "app"

    fun makeRevokePermRequest(permission: String, callback: GraphRequest.Callback?) {
        val graphPath: String = if (permission == APP) {
            PERMISSIONS_ENDPOINT
        } else {
            "$PERMISSIONS_ENDPOINT/$permission"
        }
        val request = GraphRequest.newGraphPathRequest(
            AccessToken.getCurrentAccessToken(),
            graphPath,
            callback
        )
        request.httpMethod = HttpMethod.DELETE
        request.executeAsync()
    }
}