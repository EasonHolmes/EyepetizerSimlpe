package com.cui.video.utils.img;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by cuiyang on 16/8/26.
 * 在AndroidManfaster中配置Glide会自动找到
 */

public class CustomCachingGlideModule implements GlideModule {


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //3、根据运行内存情况，自定义对图像的编码格式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ActivityManager activityManager =
//                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            builder.setDecodeFormat(activityManager.isLowRamDevice() ?
//                    DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
//        }
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
        setMemoryBigSize(context,builder);
//        setDiskCacheDirectory(builder);
    }


    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    /**
     * 设置自定义目录外面可访问
     */
    private void setDiskCacheDirectory(GlideBuilder builder) {
        int cacheSize100MegaBytes = 104857600;//磁盘缓存最大100M大小

// or any other path
        String downloadDirectoryPath = Environment.getDownloadCacheDirectory().getPath();
//        builder.setDiskCache(
//                new DiskLruCacheFactory(downloadDirectoryPath, cacheSize100MegaBytes)
//        );
        builder.setDiskCache(
                new DiskLruCacheFactory(downloadDirectoryPath, "glidecache", cacheSize100MegaBytes)
        );
    }

    /**
     * 设置磁盘缓存到sd卡上但非指定路径
     *
     * @param context
     * @param builder
     */
    private void setDiskCacheOfSdcard(Context context, GlideBuilder builder) {
        // set size & external vs. internal
        int cacheSize100MegaBytes = 104857600;//磁盘缓存最大100M大小

        builder.setDiskCache(
                new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
    }

    /**
     * 设置磁盘缓存到app内部目录下但非指定路径
     *
     * @param context
     * @param builder
     */
    private void setDisCacheOfApp(Context context, GlideBuilder builder) {
        // set size & external vs. internal
        int cacheSize100MegaBytes = 104857600;//磁盘缓存最大100M大小

        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
        );
    }

    /**
     * 设置内存缓存使用的内存最大缓存数
     * 每个手机可能分配的不同 先获取默认的值再*百分再设置
     *
     * @param context
     * @param builder
     */
    private void setMemoryBigSize(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (0.5 * defaultMemoryCacheSize);//默认的再加一半
        int customBitmapPoolSize = (int) (0.5 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));//设置新的内存缓存大小
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }
}
