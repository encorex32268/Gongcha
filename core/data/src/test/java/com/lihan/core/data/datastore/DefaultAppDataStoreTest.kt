package com.lihan.core.data.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.lihan.core.data.DefaultAppDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DefaultAppDataStoreTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder()

    @Test
    fun isFirstTime_defaultsToTrue_andUpdatesCorrectly() = runBlocking {
        val testFile = tmpFolder.newFile("test_app_preferences.preferences_pb")
        val scope = CoroutineScope(Dispatchers.IO + Job())
        try {
            val dataStore = PreferenceDataStoreFactory.create(
                scope = scope,
                produceFile = { testFile }
            )
            val appDataStore = DefaultAppDataStore(dataStore)

            assertTrue(appDataStore.isFirstTime.first())

            appDataStore.setIsFirstTime(false)
            assertFalse(appDataStore.isFirstTime.first())
        } finally {
            scope.cancel()
        }
    }
}
