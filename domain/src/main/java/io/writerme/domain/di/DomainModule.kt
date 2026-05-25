package io.writerme.domain.di

import android.content.Context
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.writerme.core.contracts.validators.IFieldValidator
import io.writerme.core.contracts.validators.IPhoneValidator
import io.writerme.core.helpers.LocalImagesLoader
import io.writerme.domain.validators.FieldValidatorImpl
import io.writerme.domain.validators.PhoneValidatorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
data object DomainModule {
    @Provides
    fun provideIPhoneValidator(): IPhoneValidator {
        return PhoneValidatorImpl(PhoneNumberUtil.getInstance())
    }

    @Provides
    fun provideIFieldValidator(
        phoneValidator: IPhoneValidator
    ): IFieldValidator {
        return FieldValidatorImpl(phoneValidator)
    }

    @Provides
    fun providesImageLoader(
        @ApplicationContext context: Context
    ): LocalImagesLoader {
        return LocalImagesLoader(context)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager =
        WorkManager.getInstance(appContext)
}
