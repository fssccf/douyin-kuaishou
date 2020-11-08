package com.lianjiao.core.frame;

public class AppMenu {
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单默认图标
	 */
	private int imgnormal;
	/**
	 * 菜单选中图片
	 */
	private int imgselected;
	/**
	 * tag
	 */
	private Object tag;
	/**
	* 构造器名: 菜单构造器
	* 描述: 
	* @参数：<br>
	* name:菜单名称；<br>
	* imgnormal:菜单logo<br>
	* imgselected:菜单选中logo<br>
	* tag:tag<br>
	 */
	public AppMenu(String name,int imgnormal,int imgselected,Object tag){
		this.name = name;
		this.imgnormal = imgnormal;
		this.imgselected = imgselected;
		this.tag = tag;
	}
	/**
	* 构造器名: 菜单构造器
	* 描述: 
	* @参数：<br>
	* name:菜单名称；<br>
	* imgnormal:菜单logo<br>
	* imgselected:菜单选中logo<br>
	 */
	public AppMenu(String name,int imgnormal,int imgselected){
		this.name = name;
		this.imgnormal = imgnormal;
		this.imgselected = imgselected;
	}
	
	private AppMenu(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getImgnormal() {
		return imgnormal;
	}

	public void setImgnormal(int imgnormal) {
		this.imgnormal = imgnormal;
	}

	public int getImgselected() {
		return imgselected;
	}

	public void setImgselected(int imgselected) {
		this.imgselected = imgselected;
	}

	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}

	
}
