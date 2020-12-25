package api;

import android.os.StrictMode;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApi {
    private static BaseApi baseApi;
    protected String directUrl = "https://www.gheyas.com/api/";

    protected String getBaseUrl() {
        return "http://www.google.com/";
    }

    public static BaseApi getInstance() {
        if (baseApi == null)
            baseApi = new BaseApi();
        return baseApi;
    }

    Retrofit createBuilder(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }



    <T> void Enqueue(Call<T> runner, final ApiEnqueueListener apiEnqueueListener) {
       runner.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                T result = null;
                String resultError = null;
                try {
                    result = response.body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (apiEnqueueListener != null)
                    if (response.code() == 200)
                        apiEnqueueListener.onSuccess(result);
                    else if (resultError != null)
                        apiEnqueueListener.failure(handlerFailure(resultError.replace("\"", "")));
                    else
                        apiEnqueueListener.failure(null);
            }

            @Override
            public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                if (apiEnqueueListener != null)
                    apiEnqueueListener.failure(handlerFailure(t.toString()));
            }
        });
    }

    <T> T Execute(Call<T> runner) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Response<T> result = runner.execute();
            if (result.isSuccessful())
                return (result.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String handlerFailure(String errorMessage) {
        String error = "";
        if (errorMessage.startsWith("CustomerID"))
            error = "شناسه مشتری را وارد نکرده اید";
        else if (errorMessage.startsWith("Phone"))
            error = "تلفن همراه را وارد نکرده اید";
        else if (errorMessage.startsWith("Email"))
            error = "پست الکترونیک را اشتباه وارد کرده اید";
        else if (errorMessage.startsWith("Not Valid"))
            error = "اطلاعات برای ذخیره سازی با مشکل مواجه گردید";
        else if (errorMessage.startsWith("Subject"))
            error = "موضوع را وارد نکرده اید";
        else if (errorMessage.startsWith("Descriptions"))
            error = "پیشنهاد را وارد نکرده اید";
        else if (errorMessage.startsWith("Name"))
            error = "عنوان را وارد نکرده اید";
        else if (errorMessage.startsWith("PhoneName"))
            error = "تلفن را وارد نکرده اید";
        else if (errorMessage.startsWith("Description"))
            error = "متن را وارد نکرده اید";
        else if (errorMessage.startsWith("Not Exist"))
            error = "رکوردی وجود ندارد";
        else if (errorMessage.startsWith("Not Customer"))
            error = "رکوردی وجود ندارد";
        else if (errorMessage.equals("java.net.SocketTimeoutException: timeout") || errorMessage.contains("timed out"))
            error = "مشکلی در برقراری ارتباط با سرور وجود دارد";
        else
            error = errorMessage;
        return error;
    }
}






