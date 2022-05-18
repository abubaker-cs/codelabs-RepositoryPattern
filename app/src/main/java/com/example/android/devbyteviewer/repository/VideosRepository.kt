/*
 * Copyright (C) 2019 Google Inc.
 *gi
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewer.repository

import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.network.DevByteNetwork
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
// TODO: Implement the VideosRepository class

// Pass in a VideosDatabase object as the class's constructor parameter to access the DAO methods
class VideosRepository(private val database: VideosDatabase) {

    // Coroutine: This method will be the API used to refresh the offline cache.
    suspend fun refreshVideos() {

        // Disk I/O, or reading and writing to disk, is slow and always blocks the current thread until
        // the operation is complete. Because of this, you have to run the disk I/O in the I/O dispatcher.
        // This dispatcher is designed to offload blocking I/O tasks to a shared pool of threads
        // using withContext(Dispatchers.IO)
        withContext(Dispatchers.IO) {

            // Fetch the DevByte video playlist from the network using the Retrofit service instance, DevByteNetwork
            val playlist = DevByteNetwork.devbytes.getPlaylist()

            // After fetching the playlist from the network, store the playlist in the Room database
            // 1. Call the insertAll() DAO method, passing in the playlist retrieved from the network.
            // 2. Use the asDatabaseModel() extension function to map the playlist to the database object.
            database.videoDao.insertAll(playlist.asDatabaseModel())


        }

    }

}
