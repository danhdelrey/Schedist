package com.brighttorchstudio.schedist.data.notification.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindLocalNotificationRepository(localNotificationRepository: LocalNotificationRepository): NotificationRepository
}