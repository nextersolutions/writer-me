package io.writerme.database.extensions

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.model.Component
import io.writerme.core.models.model.History
import io.writerme.core.models.model.Note
import io.writerme.core.models.model.Settings
import io.writerme.database.common.DbConst
import io.writerme.database.common.DbConst.DB_NAME
import io.writerme.database.common.DbConst.DB_SCHEMA_VERSION
import kotlin.reflect.KClass

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
        .name(DB_NAME)
        .schemaVersion(DB_SCHEMA_VERSION)
        .build()
}

fun Realm.Companion.getDefaultInstance(): Realm {
    return open(RealmConfiguration.default())
}

fun <T : TypedRealmObject> Realm.findById(
    clazz: KClass<T>, id: String
): T? {
    return this
        .query(clazz, DbConst.WHERE_ID_EQUALS, id)
        .first()
        .find()
}

fun <T : TypedRealmObject> MutableRealm.findById(
    clazz: KClass<T>, id: String
): T? {
    return this
        .query(clazz, DbConst.WHERE_ID_EQUALS, id)
        .first()
        .find()
}
