package io.writerme.app.data.mapper

import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.viewdata.ComponentViewData
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComponentMapper @Inject constructor() {

    fun map(entity: ComponentEntity): ComponentViewData = ComponentViewData(
        id = entity.id,
        noteId = entity.noteId,
        content = entity.content,
        isChecked = entity.isChecked,
        url = entity.url,
        title = entity.title,
        mediaUrl = entity.mediaUrl,
        time = Date(entity.time),
        isImage = entity.isImage,
        changeTime = entity.changeTime,
        type = ComponentType.fromValue(entity.type)
    )

    fun mapToEntity(viewData: ComponentViewData): ComponentEntity = ComponentEntity(
        id = viewData.id,
        noteId = viewData.noteId,
        content = viewData.content,
        isChecked = viewData.isChecked,
        url = viewData.url,
        title = viewData.title,
        mediaUrl = viewData.mediaUrl,
        time = viewData.time.time,
        isImage = viewData.isImage,
        changeTime = viewData.changeTime,
        type = viewData.type.value
    )
}
