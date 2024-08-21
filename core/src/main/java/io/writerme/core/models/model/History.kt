package io.writerme.core.models.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.core.extensions.getLast
import io.writerme.core.extensions.push
import io.writerme.core.models.enums.ComponentType
import org.mongodb.kbson.ObjectId

open class History : RealmObject {

    @PrimaryKey
    var id: String = ObjectId().toHexString()

    var changes: RealmList<Component> = realmListOf()

    constructor() : super()

    constructor(component: Component) : this() {
        changes.add(component)
    }

    fun push(component: Component): Component? {
        return changes.push(component)
    }

    fun newest(): Component? = changes.getLast()

    fun getType(): ComponentType? {
        return if (changes.isNotEmpty()) changes.first().type else null
    }

    fun isNotEmpty(): Boolean = changes.isNotEmpty()
}
