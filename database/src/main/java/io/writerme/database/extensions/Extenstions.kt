package io.writerme.database.extensions

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.writerme.core.common.GlobalConstants
import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.model.Component
import io.writerme.core.models.model.History
import io.writerme.core.models.model.Note
import io.writerme.core.models.model.Settings

fun RealmConfiguration.Companion.default(): RealmConfiguration {
    return RealmConfiguration.Builder(
        schema = setOf(
            BookmarksFolder::class,
            Component::class,
            History::class,
            Note::class,
            Settings::class
        )
    )
        .name(GlobalConstants.DB_NAME)
        .schemaVersion(GlobalConstants.DB_SCHEMA_VERSION)
        .build()
}

fun Realm.Companion.getDefaultInstance(): Realm {
    return open(RealmConfiguration.default())
}
