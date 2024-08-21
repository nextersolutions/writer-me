package io.writerme.core.mappers.model

import io.writerme.core.extensions.toRealmList
import io.writerme.core.mappers.IBaseEntityMapper
import io.writerme.core.mappers.IBaseMapper
import io.writerme.core.models.model.History
import io.writerme.core.models.viewdata.HistoryViewData
import javax.inject.Inject

class HistoryMapper @Inject constructor(
    private val componentMapper: ComponentMapper
) : IBaseEntityMapper<History, HistoryViewData>, IBaseMapper<HistoryViewData, History> {
    override fun toEntity(model: HistoryViewData): History {
        return History().apply {
            id = model.id
            changes = model.changes.map { componentMapper.toEntity(it) }.toRealmList()
        }
    }

    override fun toDomain(model: History): HistoryViewData {
        return HistoryViewData(
            id = model.id,
            changes = model.changes.toList().map { componentMapper.toDomain(it) }
        )
    }
}
