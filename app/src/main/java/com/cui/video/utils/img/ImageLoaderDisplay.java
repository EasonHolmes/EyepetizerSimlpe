package com.cui.video.utils.img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by cuiyang on 16/6/3.
 */
public class ImageLoaderDisplay {

    /**
     * 通用基础设置
     */
    public static DrawableRequestBuilder getGlide(Context mContext, String imgUrl) {
        return Glide.with(mContext)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT);
//                .skipMemoryCache(true);//跳过内存缓存使用磁盘缓存默认false

    }

    /**
     * 直接加载图片
     *
     * @param mContext
     * @param img
     * @param defResourceId
     * @param imgUrl
     */
    public static void imageLoader(Context mContext, ImageView img, int defResourceId, String imgUrl) {
        getGlide(mContext, imgUrl)
                .centerCrop()
                .crossFade()
                .placeholder(defResourceId)
                .into(img);
    }

    /**
     * 直接加载图片
     *
     * @param mContext
     * @param img
     * @param imgUrl
     */
    public static void imageLoaderOverride800(Context mContext, ImageView img, String imgUrl) {
        getGlide(mContext, imgUrl)
                .centerCrop()
                .override(800,400)
                .crossFade()
                .into(img);
    }
    /**
     * 直接加载图片
     *
     * @param mContext
     * @param img
     * @param imgUrl
     */
    public static void imageLoader(Context mContext, ImageView img, String imgUrl) {
        getGlide(mContext, imgUrl)
                .centerCrop()
                .crossFade()
                .into(img);
    }

    public static void imageLoader(Context mContext, ImageView img, int defResourceId, String imgUrl, BitmapTransformation... transformations) {
        getGlide(mContext, imgUrl)
                .crossFade()
                .placeholder(defResourceId)
                .bitmapTransform(transformations)
                .into(img);

    }

    /**
     * 加载圆角矩形
     * diskCacheStrategy() 磁盘缓存策略，Glide支持很多种图片缓存策略。
     * <p>
     * DiskCacheStrategy.RESOURCE 只缓存原始文件
     * <p>
     * DiskCacheStrategy.ALL 缓存所有size的图片和源文件
     * <p>
     * DiskCacheStrategy.RESULT 缓存最后的结果文件
     * <p>
     * DiskCacheStrategy.NONE 撒都不缓存
     * <p>
     * 在V3的版本默认是DiskCacheStrategy.RESULT的策略。
     */
    public static void imageLoaderRound(Context mContext, ImageView image, int default_list_image, String imgUrl) {
        getGlide(mContext, imgUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)//跳过内存缓存使用磁盘缓存默认false
                .placeholder(default_list_image)
//                .thumbnail(0.5f)//缩略图0.5f即50%原图的大小
                .bitmapTransform(new CenterCrop(mContext), new RoundedCornersTransformation(mContext, 30, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(image);
    }


    /**
     * 加载本地图片并不需要预置图片
     *
     * @param mContext
     * @param image
     * @param imgUrl
     */
    public static void imageloadLocalImg(Context mContext, ImageView image, File imgUrl) {
        getGlide(mContext, imgUrl.getAbsolutePath())
                .crossFade()
                .bitmapTransform(new CenterCrop(mContext), new RoundedCornersTransformation(mContext, 30, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .into(image)
        ;
    }

    //优先级主要用在大小图片同时展示上
    //我们分配Priority.HIGH给它。理论上，那就够了。
    // 但为了这个例子更有趣，我们通过调用.priority(Priority.LOW)分配给底部的图片低优先级权限：
//    不管是多大的图片，几乎所有情况下主要的图片都会被优先显示。（图片越大，需要更多的处理时间）
//    private void loadImageWithHighPriority() {
//        Glide
//                .with( context )
//                .load( UsageExampleListViewAdapter.eatFoodyImages[0] )
//                .priority( Priority.HIGH )
//                .into( imageViewHero );
//    }
//
//    private void loadImagesWithLowPriority() {
//        Glide
//                .with(context)
//                .load(UsageExampleListViewAdapter.eatFoodyImages[1])
//                .priority(Priority.LOW)
//                .into(imageViewLowPrioLeft);
//
//        Glide
//                .with(context)
//                .load(UsageExampleListViewAdapter.eatFoodyImages[2])
//                .priority(Priority.LOW)
//                .into(imageViewLowPrioRight);
//
//        Glide.with(mContext)
////                .load("http://puzzle.captian.me/Public/Test/2016-08-04/thumb_crop1470306015.jpg")
//                .load(R.mipmap.pic)
//                .asBitmap().into(new SimpleTarget<Bitmap>() {
//            //成功回调
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Log.e(getClass().getName(), "resource==" + resource);
//                //拿到bitmap进行处理
//                Bitmap mBitmap = resource;
//            }
//
//            //失败回调
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//            }
//        });
//    }

}
