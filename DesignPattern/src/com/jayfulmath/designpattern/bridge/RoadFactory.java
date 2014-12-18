package com.jayfulmath.designpattern.bridge;

public class RoadFactory {
	public static Road CreateRoad(int roadType, int pathLength)
	{
		Road _mRoad = null;
		switch(roadType)
		{
		case Road.TYPE_CITY:
			_mRoad = new CityRoad(pathLength);
			break;
		case Road.TYPE_HIGHWAY:
			_mRoad = new HighWay(pathLength);
			break;
		case Road.TYPE_SUBURBAN:
			_mRoad = new Suburban(pathLength);
			break;		
		}
		return _mRoad;
	}
}
