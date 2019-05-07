package com.autils.framework.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.autils.api.response.model.CompanyImages;
import com.autils.api.response.model.SellerInfo;
import com.autils.api.response.model.Token;
import com.autils.framework.Launcher;
import com.autils.framework.data.db.dao.CacheDBDao;
import com.autils.framework.data.db.dao.DaoMaster;
import com.autils.framework.data.db.dao.DaoSession;
import com.autils.framework.data.model.CacheDB;
import com.google.gson.Gson;

/**
 * Created by fengyulong on 2018/5/11.
 */
public class DbHelper {

    private static final String DB_NAME = "com.autils.db";
    private static DbHelper instance;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context context;

    private DbHelper() {
        this.context = Launcher.getInstance().getApplication();
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster();
        getDaoSession();
    }

    public static DbHelper getInstance() {
        if (null == instance) {
            synchronized (DbHelper.class) {
                if (null == instance) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (null == mDevOpenHelper) {
            getInstance();
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (null == mDevOpenHelper) {
            getInstance();
        }

        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            synchronized (DbHelper.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * 获取DaoSession
     */
    private DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (DbHelper.class) {
                mDaoSession = getDaoMaster().newSession();
            }
        }
        return mDaoSession;
    }

    public Token getToken() {
        CacheDB cacheDB = getDaoSession().getCacheDBDao().queryBuilder()
                .where(CacheDBDao.Properties.Type.eq(CacheDB.TYPE.TOKEN))
                .unique();
        if (cacheDB == null) {
            return null;
        }

        return new Gson().fromJson(cacheDB.getContent(), Token.class);
    }

    public void saveToken(Token token) {
        clearToken();

        CacheDB cacheDB = new CacheDB();
        cacheDB.setType(CacheDB.TYPE.TOKEN);
        cacheDB.setContent(new Gson().toJson(token));

        getDaoSession().getCacheDBDao().insert(cacheDB);
    }

    public void clearToken() {
        String sql = String.format("delete from %s where %s = '%s'",
                CacheDBDao.TABLENAME,
                CacheDBDao.Properties.Type.columnName,
                CacheDB.TYPE.TOKEN
        );
        getDaoSession().getCacheDBDao().getDatabase().execSQL(sql);
    }

    public SellerInfo getSellerInfo() {
        CacheDB cacheDB = getDaoSession().getCacheDBDao().queryBuilder()
                .where(CacheDBDao.Properties.Type.eq(CacheDB.TYPE.USERINFO))
                .unique();
        if (cacheDB == null) {
            return null;
        }

        return new Gson().fromJson(cacheDB.getContent(), SellerInfo.class);
    }

    public void saveSellerInfo(SellerInfo sellerInfo) {
        clearSellerInfo();

        CacheDB cacheDB = new CacheDB();
        cacheDB.setType(CacheDB.TYPE.USERINFO);
        cacheDB.setContent(new Gson().toJson(sellerInfo));

        getDaoSession().getCacheDBDao().insert(cacheDB);
    }

    public void clearSellerInfo() {
        String sql = String.format("delete from %s where %s = '%s'",
                CacheDBDao.TABLENAME,
                CacheDBDao.Properties.Type.columnName,
                CacheDB.TYPE.USERINFO
        );
        getDaoSession().getCacheDBDao().getDatabase().execSQL(sql);
    }

    public CompanyImages getCompanyImages(String customer_id) {
        CacheDB cacheDB = getDaoSession().getCacheDBDao().queryBuilder()
                .where(CacheDBDao.Properties.Type.eq(CacheDB.TYPE.COMPANY_IMAGE))
                .where(CacheDBDao.Properties.User_key.eq(customer_id))
                .unique();

        if (cacheDB == null) {
            return null;
        }

        return new Gson().fromJson(cacheDB.getContent(), CompanyImages.class);
    }

    public void saveCompanyImages(String customer_id, CompanyImages companyImages) {
        clearCompanyImages(customer_id);

        CacheDB cacheDB = new CacheDB();
        cacheDB.setType(CacheDB.TYPE.COMPANY_IMAGE);
        cacheDB.setContent(new Gson().toJson(companyImages));
        cacheDB.setUser_key(customer_id);

        getDaoSession().getCacheDBDao().insert(cacheDB);
    }

    public void clearCompanyImages(String customer_id) {
        String sql = String.format("delete from %s where %s = '%s' and %s = '%s'",
                CacheDBDao.TABLENAME,
                CacheDBDao.Properties.Type.columnName,
                CacheDB.TYPE.COMPANY_IMAGE,
                CacheDBDao.Properties.User_key.columnName,
                customer_id
        );
        getDaoSession().getCacheDBDao().getDatabase().execSQL(sql);
    }
}
