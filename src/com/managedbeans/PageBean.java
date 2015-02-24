package com.managedbeans;

public interface PageBean
{
	public void saveApplicationBean();
	public abstract boolean validate();
	public abstract void save();
	public abstract void afterSave();
}
