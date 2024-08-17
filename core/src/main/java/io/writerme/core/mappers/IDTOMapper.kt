package io.writerme.core.mappers

interface IDTOMapper<Domain, DTO> {
    fun toDTO(model: Domain): DTO
}
