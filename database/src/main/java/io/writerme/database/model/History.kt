package io.writerme.database.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.database.extensions.push
import io.writerme.database.extensions.getLast

open class History: RealmObject {

    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var changes: RealmList<Component> = realmListOf()

    constructor(): super()

    constructor(component: Component): this() {
        changes.add(component)
    }

    fun push(component: Component): Component? {
        return changes.push(component)
    }

    fun newest(): Component? = changes.getLast()

    fun getType(): ComponentType? {
        return if (changes.isNotEmpty()) changes[0].type else null
    }

    fun isNotEmpty(): Boolean = changes.isNotEmpty()
}