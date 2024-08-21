package io.writerme.core.models.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.core.common.FormatUtils.EMPTY
import org.mongodb.kbson.ObjectId

open class BookmarksFolder : RealmObject {
    @Index
    @PrimaryKey
    var id: String = ObjectId().toHexString()

    var name: String = EMPTY

    var folders: RealmList<BookmarksFolder> = realmListOf()
    var bookmarks: RealmList<Component> = realmListOf()

    var parent: BookmarksFolder? = null

    var changeTime: Long = System.currentTimeMillis()
}
