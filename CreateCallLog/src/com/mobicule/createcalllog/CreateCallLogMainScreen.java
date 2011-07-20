package com.mobicule.createcalllog;

import net.rim.blackberry.api.phone.phonelogs.CallLog;
import net.rim.blackberry.api.phone.phonelogs.PhoneCallLog;
import net.rim.blackberry.api.phone.phonelogs.PhoneCallLogID;
import net.rim.blackberry.api.phone.phonelogs.PhoneLogListener;
import net.rim.blackberry.api.phone.phonelogs.PhoneLogs;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class CreateCallLogMainScreen extends MainScreen implements PhoneLogListener
{
	final String PHONE_NUMBER = "+912242153104";
	String callerName = "";
	String callerNumber = "";
	PhoneCallLogID phoneCallLogID1;
	PhoneCallLog phoneCallLog = null;
	CallLogBean lastCallLogBean = new CallLogBean();
	CallLogBean switchCallLogBean = new CallLogBean();
	
	public CreateCallLogMainScreen()
	{
		PhoneLogs.addListener(this);
		add(new LabelField("Welcome in the world of BlackBerry...."));
	}
	
	public void callLogAdded(CallLog cl) 
	{	
		if(cl instanceof PhoneCallLog)
		{
			PhoneCallLog pcl = (PhoneCallLog) cl;
			phoneCallLogID1 = pcl.getParticipant();
			
			System.out.println(phoneCallLogID1.getName());
			System.out.println(phoneCallLogID1.getNumber());
			
			if(phoneCallLogID1.getNumber().equalsIgnoreCase(PHONE_NUMBER))
			{
				switchCallLogBean.setPhoneCallLog(pcl);
				if(PhoneCallLog.TYPE_MISSED_CALL_OPENED==pcl.getType()
						|| PhoneCallLog.TYPE_MISSED_CALL_UNOPENED==pcl.getType())
				{
					switchCallLogBean.setLogFolderId(PhoneLogs.FOLDER_MISSED_CALLS);
				}
				else
				{
					switchCallLogBean.setLogFolderId(PhoneLogs.FOLDER_NORMAL_CALLS);
				}
				
				
				deleteLastCallLog();
				deleteSwitchCall();
				addNewCallLog();	
				//swapCallLog();
			}
			else
			{
				lastCallLogBean.setPhoneCallLog(pcl);
				
				if(PhoneCallLog.TYPE_MISSED_CALL_OPENED==pcl.getType()
						|| PhoneCallLog.TYPE_MISSED_CALL_UNOPENED==pcl.getType())
				{
					lastCallLogBean.setLogFolderId(PhoneLogs.FOLDER_MISSED_CALLS);
				}
				else
				{
					lastCallLogBean.setLogFolderId(PhoneLogs.FOLDER_NORMAL_CALLS);
				}
			}
		}
	}

	public void callLogRemoved(CallLog cl) 
	{
		
	}

	public void callLogUpdated(CallLog cl, CallLog oldCl)
	{
			
	}

	public void reset()
	{
		
	}	
	
/*	private void swapCallLog()
	{
		//PhoneCallLog phoneCallLog = new PhoneCallLog(pcl.getDate(), pcl.getType(), pcl.getDuration(), pcl.getStatus(), phoneCallLogID2, pcl.getNotes());
		phoneCallLog = new PhoneCallLog
		(	
			switchCallLogBean.getPhoneCallLog().getDate(),
			switchCallLogBean.getPhoneCallLog().getType(),
			switchCallLogBean.getPhoneCallLog().getDuration(),
			switchCallLogBean.getPhoneCallLog().getStatus(),
			lastCallLogBean.getPhoneCallLog().getParticipant(),
			lastCallLogBean.getPhoneCallLog().getNotes()		
		);	
		PhoneLogs phoneLogs = PhoneLogs.getInstance();
		int indexForSwap = getMatchingCallIndex(switchCallLogBean);
		phoneLogs.swapCall(phoneCallLog, indexForSwap, switchCallLogBean.getLogFolderId());
		
	}*/
	
	private void deleteLastCallLog()
	{
		PhoneLogs phoneLogs=PhoneLogs.getInstance();
		if(getMatchingCallIndex(lastCallLogBean)!=-1)
		{
			phoneLogs.deleteCall(getMatchingCallIndex(lastCallLogBean),lastCallLogBean.getLogFolderId());
		}	
	}
	
	private void addNewCallLog()
	{
		phoneCallLog = new PhoneCallLog
		(	
			switchCallLogBean.getPhoneCallLog().getDate(),
			switchCallLogBean.getPhoneCallLog().getType(),
			switchCallLogBean.getPhoneCallLog().getDuration(),
			switchCallLogBean.getPhoneCallLog().getStatus(),
			lastCallLogBean.getPhoneCallLog().getParticipant(),
			lastCallLogBean.getPhoneCallLog().getNotes()		
		);	
		
		PhoneLogs phoneLogs = PhoneLogs.getInstance();
		phoneLogs.addCall(phoneCallLog);
	}
	
	private void deleteSwitchCall()
	{
		PhoneLogs phoneLogs=PhoneLogs.getInstance();
			
		if(getMatchingCallIndex(switchCallLogBean)!=-1)
		{
			phoneLogs.deleteCall(getMatchingCallIndex(switchCallLogBean),switchCallLogBean.getLogFolderId());
		}			
	}
	
	
	private int getMatchingCallIndex(CallLogBean phoneLogBean)
	{
		PhoneLogs phoneLogs=PhoneLogs.getInstance();
		
		int numberOfCalls=phoneLogs.numberOfCalls(phoneLogBean.getLogFolderId());
		
		int swapIndex=-1;
		
		for (int logIndex = numberOfCalls-1; logIndex >= 0; logIndex--)
		{
			PhoneCallLog phoneCallLog=(PhoneCallLog)phoneLogs.callAt(logIndex, phoneLogBean.getLogFolderId());
			
			if(areLogItemsEqual(phoneLogBean.getPhoneCallLog(), phoneCallLog))
			{
				swapIndex=logIndex;
				break;
			}
		}		
		return swapIndex;
	}
	
	
	public static boolean areLogItemsEqual(CallLog l1, CallLog l2) 
	{
		try {
			if (!l1.getDate().equals(l2.getDate())) 
			{
				return false;
			}
			if (l1.getStatus() != l2.getStatus()) 
			{
				return false;
			}

			if (!PhoneCallLog.class.isInstance(l1)
					|| !PhoneCallLog.class.isInstance(l2)) 
			{
				return false;
			}
			PhoneCallLog pl1 = (PhoneCallLog) l1;
			PhoneCallLog pl2 = (PhoneCallLog) l2;

			if (!pl1.getParticipant().getNumber().equals(
					pl2.getParticipant().getNumber())) 
			{
				return false;
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return true;
	}
		
}
