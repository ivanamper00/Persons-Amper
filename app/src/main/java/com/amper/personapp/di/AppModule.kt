package com.amper.personapp.di

import android.content.Context
import androidx.room.Room
import com.amper.personapp.data.AppDatabase
import com.amper.personapp.data.repository.local.LocalRepositoryImp
import com.amper.personapp.data.repository.remote.PersonRepositoryImp
import com.amper.personapp.data.service.PersonService
import com.amper.personapp.domain.repository.local.LocalRepository
import com.amper.personapp.domain.repository.remote.PersonRepository
import com.amper.personapp.util.AppConstants
import com.amper.personapp.util.AppSchedulerProvider
import com.amper.personapp.util.NetworkConnectionInterceptor
import com.amper.personapp.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
         @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppConstants.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun providesClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(NetworkConnectionInterceptor(context))
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun providePersonService(retrofit: Retrofit): PersonService = retrofit.create()

    @Provides
    @Singleton
    fun providePersonRepository(
        service: PersonService,
    ): PersonRepository = PersonRepositoryImp(service)

    @Provides
    @Singleton
    fun provideLocalRepository(
        db: AppDatabase
    ): LocalRepository = LocalRepositoryImp(db.dao)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider(
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }
}