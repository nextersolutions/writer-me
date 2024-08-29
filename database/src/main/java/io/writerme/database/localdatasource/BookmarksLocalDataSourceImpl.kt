package io.writerme.database.localdatasource

import io.realm.kotlin.Realm
import io.writerme.core.contracts.datasources.local.BookmarksLocalDataSource
import io.writerme.core.extensions.deleteBookmarkFolder
import io.writerme.core.extensions.deleteComponent
import io.writerme.core.models.enums.ComponentType
import io.writerme.core.models.model.BookmarksFolder
import io.writerme.core.models.model.Component
import io.writerme.database.common.DbConst.MAIN_BOOKMARK_ID
import javax.inject.Inject

internal class BookmarksLocalDataSourceImpl @Inject constructor(
    private val realm: Realm
) : BookmarksLocalDataSource {
    override suspend fun getMainFolder(): BookmarksFolder {
        val result = realm
            .query(BookmarksFolder::class, "id == $0", MAIN_BOOKMARK_ID)
            .first()
            .find()

        return result
            ?: realm.write {
                val s = BookmarksFolder().apply {
                    this.id = MAIN_BOOKMARK_ID
                }
                copyToRealm(s)
            }
    }

    override suspend fun getLatest(folder: BookmarksFolder): BookmarksFolder? {
        return realm.write { findLatest(folder) }
    }

    override suspend fun createFolder(name: String, parent: BookmarksFolder?) {
        realm.write {
            val _parent = if (parent != null) {
                findLatest(parent)
            } else realm.query(BookmarksFolder::class, "id == $0", 0).first().find()

            var folder = BookmarksFolder().apply {
                this.name = name
            }

            val latest = if (_parent != null) {
                findLatest(_parent)
            } else null

            folder = copyToRealm(folder)
            folder.parent = latest
            latest?.folders?.add(folder)

            folder
        }
    }

    override suspend fun createBookmark(
        url: String,
        title: String,
        parent: BookmarksFolder?
    ): Component {
        return realm.write {
            val _parent =
                parent ?: realm.query(BookmarksFolder::class, "id == $0", 0).first().find()

            val bookmark = Component().apply {
                this.type = ComponentType.Link
                this.title = title
                this.url = url
            }

            val realmObj = copyToRealm(bookmark)

            _parent?.let {
                findLatest(it)?.bookmarks?.add(realmObj)
            }

            bookmark
        }
    }

    override suspend fun deleteFolder(bookmarksFolder: BookmarksFolder) {
        realm.write {
            val latest = findLatest(bookmarksFolder)

            latest?.let { folder ->
                this.deleteBookmarkFolder(folder)
            }
        }
    }

    override suspend fun deleteBookmark(component: Component) {
        realm.write {
            val latest = findLatest(component)

            latest?.let { bookmark ->
                this.deleteComponent(bookmark)
            }
        }
    }

    override fun close() {
        realm.close()
    }
}
