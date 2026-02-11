package ca.uwaterloo.cs446.notes.model.service.module

import ca.uwaterloo.cs446.notes.model.service.AccountService
import ca.uwaterloo.cs446.notes.model.service.StorageService
import ca.uwaterloo.cs446.notes.model.service.impl.AccountServiceImpl
import ca.uwaterloo.cs446.notes.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}
