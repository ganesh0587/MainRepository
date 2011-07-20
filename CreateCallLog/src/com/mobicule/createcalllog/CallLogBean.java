package com.mobicule.createcalllog;

import net.rim.blackberry.api.phone.phonelogs.PhoneCallLog;

public class CallLogBean 
{
	private long logFolderId;
	private PhoneCallLog phoneCallLog;

	
	public long getLogFolderId() 
	{
		return logFolderId;
	}
	public void setLogFolderId(long logFolderId)
	{
		this.logFolderId = logFolderId;
	}	
	
	public PhoneCallLog getPhoneCallLog() 
	{
		return phoneCallLog;
	}
	public void setPhoneCallLog(PhoneCallLog phoneCallLog) 
	{
		this.phoneCallLog = phoneCallLog;
	}
}
