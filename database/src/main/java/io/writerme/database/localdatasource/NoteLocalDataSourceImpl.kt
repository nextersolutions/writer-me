package io.writerme.database.localdatasource

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.isManaged
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.Sort
import io.writerme.core.common.FormatUtils.EMPTY
import io.writerme.core.common.FormatUtils.VALUE_1
import io.writerme.core.common.FormatUtils.ZERO
import io.writerme.core.contracts.datasources.local.NoteLocalDataSource
import io.writerme.core.extensions.deleteHistory
import io.writerme.core.extensions.deleteNote
import io.writerme.core.extensions.getLast
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.model.Component
import io.writerme.core.models.model.History
import io.writerme.core.models.model.Note
import io.writerme.database.common.DbFields.changeTime
import io.writerme.database.extensions.findById
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class NoteLocalDataSourceImpl @Inject constructor(
    private val realm: Realm
) : NoteLocalDataSource {
    override suspend fun createNewNote(): Flow<ObjectChange<Note>> {
        return realm.write {
            val note = copyToRealm(Note(), UpdatePolicy.ALL)

            val titleHistory = copyToRealm(History())
            val titleComponent = Component(note, EMPTY)
            titleHistory.push(copyToRealm(titleComponent))

            val coverHistory = copyToRealm(History())

            val textHistory = copyToRealm(History())
            val textComponent = Component(note, EMPTY)
            textHistory.push(
                copyToRealm(textComponent)
            )

            note.title = titleHistory
            note.cover = coverHistory
            note.content.add(textHistory)

            note
        }.asFlow()
    }

    override fun getNotes(): Flow<ResultsChange<Note>> {
        return realm
            .query<Note>()
            .sort(changeTime, Sort.DESCENDING)
            .find()
            .asFlow()
    }

    override suspend fun getNote(noteId: String): Flow<ObjectChange<Note>> {
        return realm.findById(Note::class, noteId)
            ?.asFlow() ?: createNewNote()
    }

    override suspend fun saveComponent(component: Component): Component {
        return realm.write {
            val toWrite = if (component.isManaged()) {
                findLatest(component) ?: component
            } else component

            this.copyToRealm(toWrite, UpdatePolicy.ALL)
        }
    }

    override suspend fun updateHistory(historyId: String, component: Component) {
        realm.write {
            val history = this.findById(History::class, historyId)

            history?.let {
                val saved = this.copyToRealm(component, UpdatePolicy.ALL)

                val toDelete = it.push(saved)
                toDelete?.let { obj -> delete(obj) }

                copyToRealm(it)

                val note = this.findById(Note::class, saved.noteId)

                note?.changeTime = System.currentTimeMillis()
                saved
            }
        }
    }

    override suspend fun updateNoteCoverImage(noteId: String, uri: String?) {
        realm.write {
            val note = this.findById(Note::class, noteId)

            val component = Component().apply {
                this.noteId = noteId
                this.mediaUrl = uri
                this.type = ComponentType.Image
            }
            val image = copyToRealm(component, UpdatePolicy.ALL)

            note?.let {
                if (it.cover == null) {
                    it.cover = copyToRealm(
                        History(),
                        UpdatePolicy.ALL
                    )
                }

                it.cover!!.push(image)
                it.changeTime = System.currentTimeMillis()
            }
        }
    }

    override suspend fun addNewTag(noteId: String, tag: String) {
        realm.write {
            val note = this.findById(Note::class, noteId)

            note?.let {
                if (!note.tags.contains(tag)) {
                    note.tags.add(tag)
                }
                it.changeTime = System.currentTimeMillis()
            }
        }
    }

    override suspend fun deleteTag(noteId: String, tag: String) {
        realm.write {
            val note = this.findById(Note::class, noteId)
            note?.tags?.remove(tag)
            note?.changeTime = System.currentTimeMillis()
        }
    }

    override suspend fun addNewCheckBox(noteId: String, currentPosition: Int) {
        realm.write {
            val component = Component().apply {
                this.noteId = noteId
                this.type = ComponentType.Checkbox
            }

            val checkBox = copyToRealm(component, UpdatePolicy.ALL)

            val note = this.findById(Note::class, noteId)

            deleteTextIfNecessary(this, note)

            val history = this.copyToRealm(History(), UpdatePolicy.ALL)
            history.changes.add(checkBox)

            note?.let {
                if (currentPosition < ZERO || (currentPosition + VALUE_1 == it.content.size)) {
                    it.content.add(history)
                } else {
                    it.content.add(currentPosition + VALUE_1, history)
                }
            }
            note?.changeTime = System.currentTimeMillis()
        }

        addTextIfNecessary(noteId)
    }

    override suspend fun addSection(noteId: String, comp: Component) {
        realm.write {
            val note = this.findById(Note::class, noteId)

            deleteTextIfNecessary(this, note)

            val component = if (comp.isManaged())
                findLatest(comp)!!
            else copyToRealm(comp, UpdatePolicy.ALL)

            val history = copyToRealm(History(), UpdatePolicy.ALL)
            history.push(component)

            note?.content?.add(history)
            note?.changeTime = System.currentTimeMillis()
        }

        addTextIfNecessary(noteId)
    }

    override suspend fun toggleImportance(noteId: String) {
        realm.write {
            val note = this.findById(Note::class, noteId)
            note?.let {
                it.isImportant = !it.isImportant
                it.changeTime = System.currentTimeMillis()
            }
        }
    }

    override suspend fun deleteNote(noteId: String) {
        realm.write {
            val note = this.findById(Note::class, noteId)
            note?.let {
                deleteNote(it)
            }
        }
    }

    override suspend fun toggleCheckbox(component: Component) {
        realm.write {
            findLatest(component)?.let { checkbox ->
                checkbox.isChecked = !checkbox.isChecked

                val note = this.findById(Note::class, checkbox.noteId)
                note?.changeTime = System.currentTimeMillis()
            }
        }
    }

    override suspend fun deleteSection(hist: History) {
        realm.write {
            val h = findLatest(hist)

            h?.let { history ->
                val noteId = history.newest()?.noteId

                this.deleteHistory(history)

                noteId?.let {
                    val note = this.findById(Note::class, it)
                    note?.changeTime = System.currentTimeMillis()
                }
            }
        }
    }

    private suspend fun addTextIfNecessary(noteId: String) {
        realm.write {
            val note = this.findById(Note::class, noteId)

            note?.let {
                if (it.content.isNotEmpty()) {
                    val lastHistory = it.content.getLast()!!

                    val type = lastHistory.getType()

                    if (type == null || type != ComponentType.Text) {
                        val textComponent = Component().apply {
                            this.type = ComponentType.Text
                            this.noteId = noteId
                        }
                        val text = copyToRealm(textComponent, UpdatePolicy.ALL)

                        if (type == null) {
                            lastHistory.push(text)
                        } else {
                            var history = History()
                            history = copyToRealm(history, UpdatePolicy.ALL)
                            history.push(textComponent)

                            note.content.add(history)
                        }
                    }
                }
                it.changeTime = System.currentTimeMillis()
            }
        }
    }

    private fun deleteTextIfNecessary(realm: MutableRealm, note: Note?) {
        val lastHistory = note?.content?.getLast()
        lastHistory?.let { history ->
            history.newest()?.let { last ->
                if (last.type == ComponentType.Text && last.content.isEmpty()) {
                    realm.deleteHistory(history)
                }
            }
        }
    }
}