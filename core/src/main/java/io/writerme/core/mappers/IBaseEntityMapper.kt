package io.writerme.core.mappers

interface IBaseEntityMapper<Entity, Target> {
    fun toEntity(model: Target): Entity
}
