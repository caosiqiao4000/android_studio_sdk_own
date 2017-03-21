package com.qs.sdk.common.other.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.XmlResourceParser;

import com.sq.sdk.common.R;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 本类主要实现加载 使用本SDK的主工程的包名中的R文件
 * <p/>
 * http://www.myexception.cn/android/1211634.html
 *
 * @author Administrator
 *         <p/>
 *         由于本SDK是只提供JAR包 不包括资源文件 所以打包JAR时 必须将GEN目录下的R。class文件加入进去
 */
public class RResouceByWebUtil {

    public static final String SH_R_STYLE = "style";
    public static final String SH_R_LAYOUT = "layout";
    public static final String SH_R_ID = "id";
    public static final String SH_R_STRING = "string";
    public static final String SH_R_DRAWABLE = "drawable";
    public static final String SH_R_ANIM = "anim";
    public static final String SH_R_ATTR = "attr";
    public static final String SH_R_COLOR = "color";
    public static final String SH_R_DIMEN = "dimen";
    public static final String SH_R_MENU = "menu";
    public static final String SH_R_RAW = "raw";

    /**
     * 远程主题包的Context引用
     */
    private static Context remoteContext;

    /**
     * 远程主题包的包名引用
     */
    private static String remkotePackageName;

    /**
     * 本Application的Context引用
     */
    // private static Context appContext;

    /**
     * 本工具类实例
     */
    private static RResouceByWebUtil instance;

    public static AssetResources assetResources;

    /**
     * 构造函数
     *
     * @param context            本Application的Context
     * @param remkotePackageName 远程主题包的包名
     * @throws NameNotFoundException
     */
    private RResouceByWebUtil(Context context, String remkotePackageName)
            throws NameNotFoundException {
        // 创建远程主题包的Context引用
        remoteContext = context.createPackageContext(remkotePackageName,
                Context.CONTEXT_INCLUDE_CODE);
        RResouceByWebUtil.remkotePackageName = remkotePackageName;
        // RResouceUtil.appContext = context.getApplicationContext();
        assetResources = new AssetResources();
    }

    /**
     * 获得工具类实例
     *
     * @param context
     * @param remkotePackageName
     * @return
     * @throws NameNotFoundException
     */
//	public static RResouceByWebUtil getInstance(Context context,
//			String remkotePackageName) throws NameNotFoundException {
//		if (instance == null) {
//			instance = new RResouceByWebUtil(context, remkotePackageName);
//		}
//		return instance;
//	}

    /**
     * 更新工具类实例
     *
     * @param context
     * @param remkotePackageName
     * @return
     * @throws NameNotFoundException
     */
    // public static RResouceByWebUtil refresh(Context context, String
    // remkotePackageName) throws NameNotFoundException {
    // instance = new RResouceByWebUtil(context, remkotePackageName);
    // return instance;
    // }

    /**
     * 根据resId获得Drawable对象
     *
     * @param resId
     * @return
     */
    // public Drawable getDrawable(int resId) {
    // return remoteContext.getResources().getDrawable(
    // remoteContext.getResources().getIdentifier(appContext.getResources().getResourceEntryName(resId),
    // appContext.getResources().getResourceTypeName(resId),
    // remkotePackageName));
    // }

    /**
     * 根据resId获得String对象
     *
     * @param resId
     * @return
     */
    // public String getString(int resId) {
    // return remoteContext.getResources().getString(
    // remoteContext.getResources().getIdentifier(appContext.getResources().getResourceEntryName(resId),
    // appContext.getResources().getResourceTypeName(resId),
    // remkotePackageName));
    // }

    /**
     * R.id.class
     *
     * @param resID
     * @return
     */
//	public static int getIdFromR_IdClass(int resID) {
//		return getIdByOtherPackageName(resID, R.id.class, SH_R_ID);
//	}

//	public static int getIdFromR_AnimClass(int resID) {
//		return getIdByOtherPackageName(resID, R.anim.class, SH_R_ANIM);
//	}

    // public static int getIdFromR_AttrClass(int resID) {
    // return getIdByOtherPackageName(resID, R.attr.class, SH_R_ATTR);
    // }

//	public static int getIdFromR_ColorClass(int resID) {
//		return getIdByOtherPackageName(resID, R.color.class, SH_R_COLOR);
//	}
//
//	public static int getIdFromR_DimenClass(int resID) {
//		return getIdByOtherPackageName(resID, R.dimen.class, SH_R_DIMEN);
//	}
    public static int getIdFromR_DrawableClass(int resID) {
        return getIdByOtherPackageName(resID, R.drawable.class, SH_R_DRAWABLE);
    }

    // public static int getIdFromR_RawClass(int resID) {
    // return getIdByOtherPackageName(resID, R.raw.class, SH_R_RAW);
    // }

    public static int getIdFromR_StringClass(int resID) {
        return getIdByOtherPackageName(resID, R.string.class, SH_R_STRING);
    }

    public static int getIdFromR_StyleClass(int resID) {
        return getIdByOtherPackageName(resID, R.style.class, SH_R_STYLE);
    }

    public static int getIdFromR_LayoutClass(int resID) {
        return getIdByOtherPackageName(resID, R.layout.class, SH_R_LAYOUT);
    }

    // public static int getIdFromR_MenuClass(int resID) {
    // return getIdByOtherPackageName(resID, R.menu.class, SH_R_MENU);
    // }

    /**
     * 返回在其他应用于中，同名称的资源名称的值
     *
     * @param resId R.style.MyDialog
     * @param clazz R.style.class
     * @param sdkR  "style"
     * @return
     */
    private static int getIdByOtherPackageName(int resId, Class<?> clazz,
                                               String sdkR) {
        Field[] fields = clazz.getFields();
        if (fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                try {
                    int b = fields[i].getInt(null);
                    if (b == resId) {
                        // String name = fields[i].getName();
                        // System.out.println("name is " + name);
                        // System.out.println("R.xxx is " + sdkR);
                        // return remoteContext.getResources().getIdentifier(
                        // appContext.getResources().getResourceEntryName(resId),
                        // appContext.getResources().getResourceTypeName(resId),
                        // remkotePackageName);
                        return remoteContext.getResources().getIdentifier(
                                fields[i].getName(), sdkR, remkotePackageName);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public class AssetResources {
        //asset的子目录
        private final String ASSET_FOLDER = "";

        /*
         * get URI of the asset filename
         */
        public String getAssetUrl(String filename) {
            return "file:///android_asset/" + ASSET_FOLDER + filename;
        }

        /*
         * get layout xml Inflator from assets dir
         */
        public XmlResourceParser getXMLFromAsset(Context mContext, String fileName) {
            try {
                return mContext.getAssets().openXmlResourceParser("assets/" + ASSET_FOLDER + fileName);
            } catch (IOException e) {
                // e.printStackTrace();
            }
            return null;
        }
    }
}
