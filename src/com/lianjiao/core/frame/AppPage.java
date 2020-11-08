package com.lianjiao.core.frame;

import com.lianjiao.core.fragment.BaseFragment;

public class AppPage {
	/**
	 * 页面名称
	 */
	private String name;
	/**
	 * 页面Fragment对象
	 */
	private BaseFragment frag;
	/**
	 * tag
	 */
	private Object tag;
	
	public AppPage(String name,BaseFragment frag,Object tag){
		this.name = name;
		this.frag = frag;
		this.tag = tag;
	}
	
	public AppPage(String name,BaseFragment frag){
		this.name = name;
		this.frag = frag;
	}
	
	public AppPage(BaseFragment frag){
		this.frag = frag;
	}
	
	private AppPage(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BaseFragment getFrag() {
		return frag;
	}
	public void setFrag(BaseFragment frag) {
		this.frag = frag;
	}
	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}

}
