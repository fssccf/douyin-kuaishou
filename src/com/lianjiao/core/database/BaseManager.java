package com.lianjiao.core.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * 类名称：BaseManager   
 * 类描述：Manager基类  继承这个类，自动就会有增删改查的方法；也可以直接new出来一个Manager直接使用
 * @创建者：韩创    
 * @创建时间：2014年10月27日   
 * @变更记录：2014年10月27日下午4:51:55 by 记录变更人
 */
public class BaseManager<T> {
	
	private Context mContext;
	private DatabaseHelper dataBaseHelper;
	public Dao<T, Integer> dao;
	
	
	
	public BaseManager(Context context,Class<T> clazz){
		mContext = context;
		dataBaseHelper = DatabaseHelper.getHelper(mContext);
		try {
			dao = dataBaseHelper.getDao(clazz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addOrUpdate(T o){
		int msg = -1;
		try {
			dao.createOrUpdate(o);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public int add(T o){
		int msg = -1;
		try {
			msg = dao.create(o);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public int del(T o){
		int msg = -1;
		try {
			msg = dao.delete(o);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public int delByParams(String columnName, String value){
		int delCount = 0;
    	try {
			DeleteBuilder<T, Integer> deleteBuilder = dao.deleteBuilder();
			deleteBuilder.where().eq(columnName, value);
			delCount = dao.delete(deleteBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return delCount;
	}
	
	public int update(T o){
		int msg = -1;
		try {
			msg = dao.update(o);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public T getObjectById(int id){
		T o = null;
		try {
			o =	dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}
	/**
	 * 方法名：getListByParams
	 * 描述：根据参数获取list：注，参数都必须是变量名同，数据库中名字一直，且这里执行，等于匹配查询
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： List<T>   
	 * @创建时间：  2014年11月18日 
	 * @创建者：韩创
	 * @变更记录：2014年11月18日下午3:07:19 by
	 */
	public List<T> getListByParams(Map<String,Object> map){
		List<T> list = null;
		try {
			list = dao.queryForFieldValues(map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return  list;
	}
	/**
	 * 方法名：getFirst
	 * 描述：ascById 是否为升序排列
	 * @参数：   参数名  参数类型   参数描述
	 * @返回值类型： T   
	 * @创建时间：  2014年11月18日 
	 * @创建者：韩创
	 * @变更记录：2014年11月18日下午3:21:09 by
	 */
	public T getFirst(boolean ascById){
		T t = null;
		try {
			QueryBuilder<T,Integer> builder  = dao.queryBuilder();
			builder.orderBy("id", ascById);
			t = builder.queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public Dao<T, Integer> getDao() {
		return dao;
	}

	public void setDao(Dao<T, Integer> dao) {
		this.dao = dao;
	}
	
	
	
}