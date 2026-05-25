package io.writerme.app.data.mapper

import io.writerme.app.data.model.ComponentEntity
import io.writerme.app.data.model.HistoryEntity
import io.writerme.app.data.viewdata.HistoryViewData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoryMapper @Inject constructor(
    private val componentMapper: ComponentMapper
) {
    fun map(entity: HistoryEntity, components: List<ComponentEntity>): HistoryViewData =
        HistoryViewData(
            id = entity.id,
            changes = components.map { componentMapper.map(it) }
        )
}
