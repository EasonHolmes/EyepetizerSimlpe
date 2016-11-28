package com.cui.video.http;

import com.cui.video.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * Created by cuiyang on 15/12/22.
 */
public class OkHttpClientSingle {
    private static final Charset UTF8 = Charset.forName("UTF-8");


    private static OkHttpClient.Builder okHttpBuilder;

    private OkHttpClientSingle() {
        okHttpBuilder = new OkHttpClient.Builder();
//        okHttpBuilder.retryOnConnectionFailure(false);//设置出现错误是否进行重新连接。
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//
                /**
                 *制作固定的键值对(以表单的形式) 每次请求都会在最后面加上
                 */
                Request.Builder requestBuilder = request.newBuilder();
                //制作固定的键值对(以添加头部的方式) 每次请求都会在最后面加上
//                requestBuilder.addHeader("key","value").build();
//
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
//                formBodyBuilder.add("token", AppServiceImpl.getInstance().getCurrentUser()._TOKEN);
//                formBodyBuilder.add("lastAppVersion", BuildConfig.VERSION_NAME);
//                formBodyBuilder.add("deviceType", "2");
//                封装固定键值
                RequestBody formBody = formBodyBuilder.build();
                String postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
                requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));//发送的编码格式
                request = requestBuilder.build();
//
//                Logger.e("开始请求:\n" + request.url().uri().toString() + request.method() + postBodyString);
//
//                Logger.w("请求结束:\n" + response.request().url().uri().toString());
//                Logger.json(response.peekBody(100000).string());//复制一个response 数值要可能的大要不会出现不够解析因为是直接设置一个固定缓冲大小
//
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
//            okHttpBuilder.interceptors().add(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            okHttpBuilder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //request Log start
                    Request request = chain.request();
                    RequestBody requestBody = request.body();

                    Connection connection = chain.connection();
                    Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

                    Buffer requestBuffer = new Buffer();
                    requestBody.writeTo(requestBuffer);
                    Charset requestCharset = UTF8;
                    MediaType requestContentType = requestBody.contentType();
                    if (requestContentType != null) {
                        requestCharset = requestContentType.charset(UTF8);
                    }
                    Logger.e("请求开始:\n--> " + request.method() //方法
                            + ' ' + request.url() + "\n"//url
                            + ' ' + requestBuffer.readString(requestCharset) + "\n"//post或get时后面跟的参数
                            + ' ' + protocol(protocol) + "\n"//http1.0orhttp1.1
                            + " (" + requestBody.contentLength() + "-byte body)");//request内容长度
                    //request Log end

                    //respose log start
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();

                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    Charset charset = UTF8;
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }

                    if (responseBody.contentLength() != 0) {
                        Logger.json(buffer.clone().readString(charset));
                    } else {
                        Logger.e("请求结束:\n" + response.request().url().toString() + "没有返回任何数据");
                    }
                    //respose log end
                    return response;
                }
            });
        }
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private static String protocol(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    public static OkHttpClient getInstance() {
        if (okHttpBuilder == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpBuilder == null) {
                    new OkHttpClientSingle();
                }
            }
        }
        return okHttpBuilder.build();
    }
}
