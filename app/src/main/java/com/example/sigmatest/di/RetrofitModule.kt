package com.example.sigmatest.di

import android.os.Build
import android.util.Log
import com.cendana2000.sim_pbb_sinjai.Interface.RestApi
import com.example.sigmatest.BuildConfig
import com.example.sigmatest.di.helper.Tls12SocketFactory
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun getOkHttpBuilder(): OkHttpClient.Builder {
        var client = OkHttpClient.Builder()
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2") as SSLContext
                sc.init(null, null, null);
                client.sslSocketFactory(Tls12SocketFactory(sc.getSocketFactory()));

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build();

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client
    }

    @Singleton
    @Provides
    internal fun getOkHttpClient(
        client: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return client
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(null)
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    internal fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        var baseUrl = BuildConfig.API_URL
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    internal fun getApiInterface(retroFit: Retrofit): RestApi {
        return retroFit.create(RestApi::class.java!!)
    }

}