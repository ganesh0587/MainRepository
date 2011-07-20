package com.mobicule.createcalllog;

import net.rim.device.api.ui.UiApplication;

public class CreateCallLogApp extends UiApplication 
{
	public CreateCallLogApp()
	{
		CreateCallLogMainScreen screen = new CreateCallLogMainScreen();
		pushScreen(screen);
	}
	
	public static void main(String[] args)
	{
		CreateCallLogApp app = new CreateCallLogApp();
		app.enterEventDispatcher();
	}
}
