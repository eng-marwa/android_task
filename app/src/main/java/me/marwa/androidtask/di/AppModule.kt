package me.marwa.androidtask.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.marwa.androidtask.BuildConfig
import me.marwa.androidtask.app.MyApp
import me.marwa.androidtask.data.datasource.local.room.CartDao
import me.marwa.androidtask.data.datasource.local.room.MyAppRoomDatabase
import me.marwa.androidtask.data.datasource.remote.api.ApiServices
import me.marwa.androidtask.data.datasource.remote.remote_repository.ProductsRemoteDS
import me.marwa.androidtask.data.datasource.remote.remote_repository.ProductsRemoteDSImp
import me.marwa.androidtask.domain.entity.CartEntity
import me.marwa.androidtask.domain.repository.CartRepository
import me.marwa.androidtask.domain.repository.CartRepositoryImp
import me.marwa.androidtask.domain.repository.ProductsRepository
import me.marwa.androidtask.domain.repository.ProductsRepositoryImp
import me.marwa.androidtask.domain.use_cases.CartUseCases
import me.marwa.androidtask.domain.use_cases.ProductsUseCases
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApp {
        return app as MyApp
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASED_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.cache(cache)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    fun provideContext(application: MyApp): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteProductsDS(api: ApiServices): ProductsRemoteDS {
        return ProductsRemoteDSImp(api)
    }

    @Provides
    @Singleton
    fun provideProductDSRepository(productsRemoteDS: ProductsRemoteDS): ProductsRepository {
        return ProductsRepositoryImp(productsRemoteDS)
    }

    @Provides
    @Singleton
    fun provideProductUseCase(productsRepository: ProductsRepository): ProductsUseCases {
        return ProductsUseCases(productsRepository)
    }

    @Provides
    @Singleton
    fun provideMyAppDatabase(@ApplicationContext context: Context) =
        MyAppRoomDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDao(db: MyAppRoomDatabase) = db.cartDoa()

    @Provides
    fun provideEntity() = CartEntity()

    @Provides
    @Singleton
    fun provideCartDSRepository(cartDao: CartDao): CartRepository {
        return CartRepositoryImp(cartDao)
    }

    @Provides
    @Singleton
    fun provideCartUseCase(cartRepository: CartRepository): CartUseCases {
        return CartUseCases(cartRepository)
    }
}