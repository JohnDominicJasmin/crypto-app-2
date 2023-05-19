package com.dominic.coin_search.core.util.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
fun <T> DataStore<Preferences>.getData(
    key: Preferences.Key<T>,
    defaultValue: T
): Flow<T> {
    return data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key]?:defaultValue
    }
}

suspend fun <T> DataStore<Preferences>.editData(
    key: Preferences.Key<T>,
    value: T
) {
    edit { preferences ->
        preferences[key] = value
    }
}
