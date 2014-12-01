package com.jayfulmath.designpattern.facade;

class VideoPlayer {
	
	public void init()
	{
		System.out.println("VideoPlayer prepare init done");
	}
	
	public void openVideo()
	{
		System.out.println("VideoPlayer open");
	}
	
	public void closedVideo()
	{
		System.out.println("VideoPlayer clsoed");
	}
}
