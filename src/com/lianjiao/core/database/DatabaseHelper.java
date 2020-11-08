package com.lianjiao.core.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;
import com.lianjiao.core.CoreConfig;
import com.lianjiao.core.R;
import com.lianjiao.core.utils.LogUtils;

/**
 * 类名称：DatabaseHelper   
 * 类描述：数据库帮助类   
 * @创建者：韩创    
 * @创建时间：2015年12月23日   
 * @变更记录：2015年12月23日上午10:45:55 by 记录变更人
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String DATABASE_NAME = CoreConfig.DBName;
    private static final int DATABASE_VERSION = CoreConfig.DBVersion;
    private static DatabaseHelper databaseHelper;
    public DataBaseUpgradeListener upgradeListener;
    public interface DataBaseUpgradeListener{
    	public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int oldVersion, int newVersion);
    }

    public static DatabaseHelper getHelper(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper ;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
    	//开始创建数据库文件
    }

    public void createTable(List<Class<?>> listClazz){
        for(Class<?> clazz:listClazz){
            try {
				TableUtils.createTableIfNotExists(connectionSource, clazz);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void createTable(Class<?> clazz) {
        try {
			TableUtils.createTableIfNotExists(connectionSource, clazz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int oldVersion, int newVersion) {
        /*LogUtils.i("oldVersion = " + oldVersion + " newVersion = " + newVersion);
        String sql = "";
        switch(oldVersion){
            case 1:
                //db.execSQL(sql);

            case 2:
        }*/
    	if(upgradeListener!=null){
    		upgradeListener.onUpgrade(db, arg1, oldVersion, newVersion);
    	}
    }


    /**
     * 清除数据库中所有表结构
     * @throws SQLException
     */
    public void deleteTable(List<Class<?>> listClazz) throws SQLException {
        for(Class<?> clazz:listClazz){
            TableUtils.dropTable(connectionSource, clazz, true);
        }

    }


    /**
     * 清除所有数据库中所有数据
     * @throws SQLException
     */
    public void deleteData(List<Class<?>> listClazz) throws SQLException {
        for(Class<?> clazz:listClazz){
            TableUtils.clearTable(connectionSource, clazz);
        }

    }

    public void deleteData(Class<?> clazz) throws SQLException{
        TableUtils.clearTable(connectionSource, clazz);
    }

    /**
     * 方法名：getTableName
     * 描述：根据类获取它的数据库表名
     * @参数：   参数名  参数类型   参数描述
     * @返回值类型： String
     * @创建时间：  2015年3月12日
     * @创建者：韩创
     * @变更记录：2015年3月12日下午3:30:58 by
     */
    public String getTableName(Class<?> clazz){
        String tableName = "";
        try {
            DatabaseTableConfig<?> dataTableConfig = DatabaseTableConfig.fromClass(connectionSource, clazz);
            tableName = dataTableConfig.getTableName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableName;
    }

    public static void clearHelper(){
        if(databaseHelper != null){
            databaseHelper.close();
            databaseHelper = null;
        }
    }

    public void close() {
        super.close();
        databaseHelper = null;
    }

	public DataBaseUpgradeListener getUpgradeListener() {
		return upgradeListener;
	}

	/**
	 * 方法名：setUpgradeListener
	 * 描述：添加数据库更新监听
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： void   
	 * @创建时间：  2015年12月23日 
	 * @创建者：韩创
	 * @变更记录：2015年12月23日上午10:50:12 by
	 */
	public void setUpgradeListener(DataBaseUpgradeListener upgradeListener) {
		this.upgradeListener = upgradeListener;
	}
    
}
