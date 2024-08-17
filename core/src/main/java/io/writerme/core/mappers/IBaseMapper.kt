package io.writerme.core.mappers

interface IBaseMapper<Domain, Target> {
    fun toDomain(model: Target): Domain
}
