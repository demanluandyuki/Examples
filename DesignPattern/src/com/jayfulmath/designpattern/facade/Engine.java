package com.jayfulmath.designpattern.facade;

public class Engine {
	
	private Flashlight _mFlashlight = null;
	private VideoPlayer _mVideoPlayer = null;
	
	public Engine()
	{
		_mFlashlight = new Flashlight();
		_mFlashlight.init();
		
		_mVideoPlayer = new VideoPlayer();
		_mVideoPlayer.init();
	}
	
	public void openFlashlight()
	{
		_mFlashlight.openflashlight();
	}
	
	public void closeFlashlight()
	{
		_mFlashlight.closeflashlight();
	}
	
	public void openVideo()
	{
		_mVideoPlayer.openVideo();
	}
	
	public void closeVideo()
	{
		_mVideoPlayer.closedVideo();
	}
	
	
	private class Flashlight {
		
		boolean bStatus = false;
		
		public void init()
		{
			bStatus = false;
			System.out.println("flashlight prepare init done");
		}
		
		public boolean openflashlight()
		{
			if(!bStatus)
			{
				System.out.println("flashlight open");
				bStatus = true;
			}
			return true;
		}
		
		public void closeflashlight()
		{
			if(bStatus)
			{
				System.out.println("flashlight close");
				bStatus = false;
			}
		}
	}
}
