package io.writerme.core.models.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.core.common.FormatUtils.ZERO
import org.mongodb.kbson.ObjectId
import java.util.Date

open class Note : RealmObject {
    @Index
    @PrimaryKey
    var id: String = ObjectId().toHexString()

    var title: History? = null

    var cover: History? = null

    var content: RealmList<History> = realmListOf()

    var isImportant: Boolean = false

    private var _created: Long = ZERO.toLong()
    var createdAt: Date
        get() = Date(_created)
        set(value) {
            _created = value.time
        }

    var changeTime: Long = System.currentTimeMillis()

    var tags: RealmList<String> = realmListOf()
}
