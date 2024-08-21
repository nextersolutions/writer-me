package io.writerme.core.mappers.model

import io.writerme.core.mappers.IBaseEntityMapper
import io.writerme.core.mappers.IBaseMapper
import io.writerme.core.models.model.Component
import io.writerme.core.models.viewdata.ComponentViewData

class ComponentMapper :
    IBaseEntityMapper<Component, ComponentViewData>,
    IBaseMapper<ComponentViewData, Component> {
    override fun toEntity(model: ComponentViewData): Component {
        return Component().apply {
            id = model.id
            noteId = model.noteId
            content = model.content
            isChecked = model.isChecked
            url = model.url
            title = model.title
            mediaUrl = model.mediaUrl
            time = model.time
            isImage = model.isImage
            changeTime = model.changeTime
            type = model.type
        }
    }

    override fun toDomain(model: Component): ComponentViewData {
        return ComponentViewData(
            id = model.id,
            noteId = model.noteId,
            content = model.content,
            isChecked = model.isChecked,
            url = model.url,
            title = model.title,
            mediaUrl = model.mediaUrl,
            time = model.time,
            isImage = model.isImage,
            changeTime = model.changeTime,
            type = model.type
        )
    }
}
