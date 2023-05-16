import com.randomx.travel.network.api.CategoriesApi
import com.randomx.travel.network.api.DestinationsApi
import com.randomx.travel.network.api.ProductsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.travel.udg.pruebas.dev/"

    private const val isInterceptorEnabled = true

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (isInterceptorEnabled) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val destinationsApi: DestinationsApi by lazy {
        retrofit.create(DestinationsApi::class.java)
    }

    val categoriesApi: CategoriesApi by lazy {
        retrofit.create(CategoriesApi::class.java)
    }

    val productsApi: ProductsApi by lazy {
        retrofit.create(ProductsApi::class.java)
    }
}
