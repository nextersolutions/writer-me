package io.writerme.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.writerme.app.usecase.io.ExportDataUseCase
import io.writerme.app.usecase.io.ExportDataUseCaseImpl
import io.writerme.app.usecase.io.ImportDataUseCase
import io.writerme.app.usecase.io.ImportDataUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataIoUseCaseModule {

    @Binds abstract fun bindExportData(impl: ExportDataUseCaseImpl): ExportDataUseCase
    @Binds abstract fun bindImportData(impl: ImportDataUseCaseImpl): ImportDataUseCase
}
