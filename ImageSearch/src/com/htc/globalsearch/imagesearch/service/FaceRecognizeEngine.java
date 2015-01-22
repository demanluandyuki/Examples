package com.htc.globalsearch.imagesearch.service;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.qualcomm.snapdragon.sdk.face.FaceData;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing.FEATURE_LIST;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing.FP_MODES;
import com.qualcomm.snapdragon.sdk.face.FacialProcessingConstants;

/*
 * supply addperson and findperson ,other api should transparent 
 * */
public class FaceRecognizeEngine {

	private final static String TAG = "ImageSearch.FaceRecognizeEngine";
	private static FacialProcessing faceProcess = null;
	private final static int CONFIDENCE_VALUE = 50;
	private final static String ALBUM_NAME_TABLE = "album_table";
	private final static int ALBUM_TABLE_SIZE = 10;
	
	private final static int FIND_FACE_OK = 0;
	private final static int FIND_NO_FACE = -1;
	private final static int FIND_NOT_IN_ALBUM = -2;
	private final static int FIND_SRC_BITMAP_ERROR = -3;
	private final static int FIND_HAS_TOO_MUCH = -4;
	
	
	private static boolean initDone = false;
	private static Context mContext = null;
	private static ArrayList<AlbumBlockContent> mAlbumArray = null;
	private static ArrayList<FaceResult> nFindPersons = null;
		
	private static void initRecognizeEngine(){
		initDone = false;
		if (checkDeviceSupport()) {
			if (faceProcess == null) {
				faceProcess = FacialProcessing.getInstance();
			} else {
				faceProcess.release();
				faceProcess = FacialProcessing.getInstance();
			}
			if (faceProcess != null) {
				faceProcess.setRecognitionConfidence(CONFIDENCE_VALUE);
				faceProcess.setProcessingMode(FP_MODES.FP_MODE_STILL);
				faceProcess.resetAlbum();
			}
			mAlbumArray = new ArrayList<AlbumBlockContent>(ALBUM_TABLE_SIZE);
			mAlbumArray.clear();
			initDone = true;
			Log.i(TAG, "[initRecognizeEngine] done");
		} else {
			initDone = false;
			throw new IllegalArgumentException(
					"It can support this app with this phone!");
		}
	}
	
	public static void Release()
	{
		if(faceProcess!=null)
		{
			Log.i(TAG, "Release");
			faceProcess.release();
			faceProcess = null;
			mAlbumArray = null;
		}
		initDone = false;
	}
	
	public static boolean checkDeviceSupport()
	{
		boolean isSupported = FacialProcessing
				.isFeatureSupported(FEATURE_LIST.FEATURE_FACIAL_RECOGNITION);
		return isSupported;
	}
	
	public static ArrayList<Integer> AddorFindPerson(Bitmap bitmap,Context context)
	{
		if(!checkDeviceSupport())
		{
			throw new IllegalArgumentException(
					"It can support this app with this phone!"); 
		}
		mContext = context;
		ArrayList<Integer> mPersons = null;
		try{
			if(!initDone || faceProcess==null)
			{
				initRecognizeEngine();
			}
			
			faceProcess.resetAlbum();
			//person may apper in some album
			if(mAlbumArray.size() == 0)
			{
				loadAlbumArray();
			}
			
			if(mAlbumArray.size()>0)
			{
				int findresult = findPersonInAlbum(mAlbumArray,bitmap,true);
				switch(findresult)
				{
				case FIND_FACE_OK:
					if(!nFindPersons.isEmpty())
					{
						//add these person
						mPersons = FaceResult.toPersonIdArray(nFindPersons);
					}
					
					break;
				case FIND_NO_FACE:
				case FIND_SRC_BITMAP_ERROR:
				case FIND_HAS_TOO_MUCH:
					//no face in bitmap,source error
					mPersons = new ArrayList<Integer>(1);
					mPersons.clear();
					mPersons.add(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
					break;
				case FIND_NOT_IN_ALBUM:
					//add person in last album
					mPersons = addPersonInAlbum(bitmap);
					if(!nFindPersons.isEmpty())
					{
						//add these person
						mPersons.addAll(FaceResult.toPersonIdArray(nFindPersons));
					}
					Log.i(TAG, "[AddorFindPerson] FIND_NOT_IN_ALBUM mPersons:"+mPersons.size());
					break;
					
				}
			}
			else
			{
				nFindPersons = new ArrayList<FaceResult>(AlbumBlockContent.MAX_PERSON_IN_BITMAP);
				nFindPersons.clear();
				mPersons = addPersonInAlbum(bitmap);
			}
		
		}catch (Exception e) {
			mPersons = new ArrayList<Integer>(1);
			mPersons.clear();
			mPersons.add(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
			e.printStackTrace();
		}
		
		return mPersons;
		
	}
	
	private static ArrayList<Integer> addPersonInAlbum(Bitmap bitmap) {
		// TODO Auto-generated method stub
		Log.i(TAG, "[addPersonInAlbum] ");

		//load last album
		ArrayList<Integer> mPersons = new ArrayList<Integer>(AlbumBlockContent.MAX_PERSON_IN_BITMAP);
		mPersons.clear();
		try{
			faceProcess.resetAlbum();
			int length = mAlbumArray.size();
			if(length == 0)
			{
				//nothing in album,just create one
				AlbumBlockContent album = AlbumBlockContent.GenerateNewAlbum(0);
				mPersons = addPersonWithBlock(album,bitmap);				
			}
			else
			{
				AlbumBlockContent lastAlbum = mAlbumArray.get(length-1);
				if(lastAlbum.personCount == AlbumBlockContent.MAX_PERSON_IN_ALBUM)
				{
					AlbumBlockContent album = AlbumBlockContent.GenerateNewAlbum(length);
					mPersons = addPersonWithBlock(album,bitmap);
				}
				else
				{
					loadAlbum(mContext, lastAlbum.albumName);
					mPersons = addPersonWithBlock(lastAlbum,bitmap);
				}
			}
		}catch (IllegalArgumentException e) {
			mPersons.clear();
			mPersons.add(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
			Log.i(TAG, "addPersonInAlbum "+e.getMessage());
		}catch (Exception e) {
			mPersons.clear();
			mPersons.add(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
			Log.i(TAG, "addPersonInAlbum no face in bitmap");
		}
		
		Log.i(TAG, "[addPersonInAlbum] mPersons size"+mPersons.size());
		
		return mPersons;

	}

	private static ArrayList<Integer> addPersonWithBlock(
			AlbumBlockContent album, Bitmap bitmap) {
		ArrayList<Integer> mPersons = new ArrayList<Integer>(
				AlbumBlockContent.MAX_PERSON_IN_BITMAP);

		try {
			boolean result = faceProcess.setBitmap(bitmap);//exception no face
			if (result) {
				FaceData[] faceArray = faceProcess.getFaceData();
				if (faceArray.length > AlbumBlockContent.MAX_PERSON_IN_BITMAP) {
					throw new IllegalArgumentException(
							"src bitmap face too much " + faceArray.length);
				} else {
					//album.count+new one
					if(faceArray.length-nFindPersons.size()+album.personCount<=AlbumBlockContent.MAX_PERSON_IN_ALBUM)
					{
						//current album can store person enouht
						ArrayList<Integer> faceIndexArray = FaceResult.toIndexArray(nFindPersons);
						int personId = -111;
						int faceIndex = 0;
						for (FaceData data : faceArray) {
							if(faceIndexArray.contains(faceIndex))
							{
								faceIndex++;
								continue;
							}
							
							personId = data.getPersonId();
							if (personId >= 0) {
								Log.w(TAG,"addPersonWithBlock person Id:"+ personId);
							} else {
								personId = faceProcess.addPerson(faceIndex)+album.blockIndex*AlbumBlockContent.MAX_PERSON_IN_ALBUM;
								mPersons.add(personId);
							}
							faceIndex++;
						}
						album.setperson(album.personCount + mPersons.size());
					}
					else
					{
						//store new person in new album
						AlbumBlockContent newAlbum = AlbumBlockContent.GenerateNewAlbum(mAlbumArray.size());//create new one
						faceProcess.resetAlbum();
						ArrayList<Integer> mPerson2 = addPersonWithBlock(newAlbum,bitmap);
						System.arraycopy(mPerson2, 0, mPersons, 0, mPerson2.size());
					}
	
				}
			} else {
				// src bit map error
				throw new IllegalArgumentException("src bitmap wrong");
			}

			// save album and albumarray
			if (album.personCount > 0) {
				saveAlbum(mContext, album.albumName);
				if(!mAlbumArray.contains(album))
				{
					mAlbumArray.add(album);
				}
				saveAlbumArray();
			}
		}catch (IllegalArgumentException e) {
			throw e;
		}catch (Exception e) {
			throw new IllegalArgumentException("[addPersonWithBlock]bitmap has no face");
		}

		return mPersons;
	}

	private static int findPersonInAlbum(ArrayList<AlbumBlockContent> albumArray, Bitmap bitmap,boolean multifaceFlag) {
		Log.i(TAG, "[findPersonInAlbum] multifaceFlag "+multifaceFlag);
		int resultFlag  = FIND_NOT_IN_ALBUM;
		int personId = -111;
		int index = 0;
		int personCount = AlbumBlockContent.MAX_PERSON_IN_BITMAP+1;
		nFindPersons = new ArrayList<FaceResult>(AlbumBlockContent.MAX_PERSON_IN_BITMAP);
		nFindPersons.clear();
		//load album with name
		try{		
			for(AlbumBlockContent album:albumArray)
			{
				faceProcess.resetAlbum();
				loadAlbum(mContext, album.albumName);
				boolean result = faceProcess.setBitmap(bitmap);
				if(result)
				{
					FaceData[] faceArray = faceProcess.getFaceData();
					personCount = faceArray.length;
					Log.i(TAG, "findPersonInAlbum personCount:"+personCount);
					if(multifaceFlag)
					{
						if(personCount> AlbumBlockContent.MAX_PERSON_IN_BITMAP)//too many people to check
						{
							throw new IllegalArgumentException("too many face personCount "+personCount);
						}

						index = 0;
						for(FaceData face:faceArray)
						{
							personId = face.getPersonId();
							if(personId>=0)
							{
								personId = personId+album.blockIndex*AlbumBlockContent.MAX_PERSON_IN_ALBUM;
								FaceResult faceResult = new FaceResult(index, personId);
								nFindPersons.add(faceResult);
							}
							index++;
						}
						
					}
					else
					{
						if(personCount>=2)
						{
							throw new IllegalArgumentException("not support multiface");
						}
						else
						{
							//person is one
							personId = faceArray[0].getPersonId();
							if(personId>=0)
							{
								personId = personId+album.blockIndex*AlbumBlockContent.MAX_PERSON_IN_ALBUM;
								FaceResult faceResult = new FaceResult(0, personId);
								nFindPersons.add(faceResult);
							}
						}
					}
					

				}
				if(nFindPersons.size() == personCount)
				{
					//all person has been find
					resultFlag = FIND_FACE_OK;
					break;
				}
				else if(nFindPersons.size() < personCount)
				{
					resultFlag = FIND_NOT_IN_ALBUM;
				}
				else
				{
					throw new IllegalArgumentException("face find error:nFindPersons.size():"+nFindPersons.size()+" personCount:"+personCount);
				}
				
			}
		}catch(IllegalArgumentException e)
		{
			resultFlag = FIND_HAS_TOO_MUCH;
			Log.w(TAG, "[findPersonInAlbum] e:"+e.getMessage());
		}		
		catch (Exception e) {
			resultFlag = FIND_NO_FACE;
			Log.w(TAG, "[findPersonInAlbum] e: no face in bitmap");
		}
		Log.i(TAG, "[findPersonInAlbum] resultFlag "+resultFlag);

		return resultFlag;
	}

	public static int FindPerson(Bitmap bitmap,Context context)
	{
		int personId = FacialProcessingConstants.FP_PERSON_NOT_REGISTERED;
		
		if(!checkDeviceSupport())
		{
			throw new IllegalArgumentException(
					"It can support this app with this phone!"); 
		}
		
		if(mAlbumArray.size() == 0)
		{
			throw new IllegalArgumentException(
					"db has not been built");
		}
		mContext = context;

		int findresult = findPersonInAlbum(mAlbumArray, bitmap, false);
		
		if(findresult == FIND_FACE_OK)
		{
			personId = nFindPersons.get(0).personId;
			Log.i(TAG, "[FindPerson]nFindPersons size"+nFindPersons.size());
		}
		
		return personId;
	}
	
	private static void loadAlbumArray() throws IllegalArgumentException
	{
		SharedPreferences settings = mContext.getSharedPreferences(ALBUM_NAME_TABLE, 0);
		//count ----5. item1---12/item2--13
		int count = settings.getInt("count", 0);
		int personCount = -1;
		if(count > 0)
		{
			for(int i = 0;i<count;i++)
			{
				personCount = settings.getInt(AlbumBlockContent.generateAlbumName(i), 0);
				AlbumBlockContent album = AlbumBlockContent.GenerateNewAlbum(i);
				album.setperson(personCount);
				mAlbumArray.add(album);
			}
		}
		
	}
	
	private static void saveAlbumArray() throws IllegalArgumentException
	{
		if(mAlbumArray.size() == 0)
		{
			throw new IllegalArgumentException("saveAlbumArray albumarray is empty");
		}
		SharedPreferences settings = mContext.getSharedPreferences(ALBUM_NAME_TABLE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("count", mAlbumArray.size());
		for(AlbumBlockContent album:mAlbumArray)
		{
			editor.putInt(album.albumName, album.personCount);
		}
		editor.apply();
	}
	
	
	private static void saveAlbum(Context context,String name)
	{	
		if(initDone && faceProcess!=null&& faceProcess.getAlbumPersonCount()>0)
		{
			byte[] albumBuffer = faceProcess.serializeRecogntionAlbum();
			Log.i(TAG, "saveAlbum Size of byte Array =" + albumBuffer.length);
			SharedPreferences settings = context.getSharedPreferences(name, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("albumArray", Arrays.toString(albumBuffer));
			editor.apply();
//			editor.commit();//this will call in thread not main
		}
		else
		{
			throw new IllegalArgumentException("saveAlbum wrong initDone "+
					initDone+" persion count "+faceProcess.getAlbumPersonCount());
		}
		Log.i(TAG, "saveAlbum name "+name+" person count "+faceProcess.getAlbumPersonCount());
	}
	
	private static void loadAlbum(Context context,String name) {
		if(!initDone && faceProcess == null)
		{
			throw new IllegalArgumentException("loadAlbum please init engine first");
		}
		
		SharedPreferences settings = context.getSharedPreferences(name, 0);
		String arrayOfString = settings.getString("albumArray", null);
		
		byte[] albumArray = null;
		if (arrayOfString != null) {
			String[] splitStringArray = arrayOfString.substring(1,
					arrayOfString.length() - 1).split(", ");
			
			albumArray = new byte[splitStringArray.length];
			for (int i = 0; i < splitStringArray.length; i++) {
				albumArray[i] = Byte.parseByte(splitStringArray[i]);
			}
			faceProcess.deserializeRecognitionAlbum(albumArray);
			Log.i(TAG, "loadalbum De-Serialized my album name "+name);
		}
		else
		{
			Log.w(TAG, "loadalbum wrong arrayOfString is null "+name);
		}
		
		Log.i(TAG, "loadalbum persion count "+faceProcess.getAlbumPersonCount());
	}
	
	
	public static class AlbumBlockContent{
		/*when person in album is 15, next time add person ,it should add in a new album
		 * 
		 * */
		public  final static int MAX_PERSON_IN_ALBUM = 15;
		public  final static int MAX_PERSON_IN_BITMAP = 5;
		private final static String ALBUM_NAME = "serialize_deserialize";
		public String albumName = null;
		private int personCount = 0;
		public int blockIndex = 0;
		
		public AlbumBlockContent(int index)
		{
			blockIndex = index;
			albumName = generateAlbumName(index);
			personCount = 0;
		}
		
		public void  setperson(int person) throws IllegalArgumentException
		{
			personCount = person;
			if(personCount>MAX_PERSON_IN_ALBUM)
			{
				throw new IllegalArgumentException("[addperson] person is too much personCount "+personCount);
			}
		}
				
		public static String generateAlbumName(int index)
		{
			String name = ALBUM_NAME+"_"+index;
			return name;
		}
		
		public static AlbumBlockContent GenerateNewAlbum(int index)
		{
			AlbumBlockContent block = new AlbumBlockContent(index);
			return block;
		}
	}
	
	public static class FaceResult{
		public int face_id = -1;
		public int personId = FacialProcessingConstants.FP_PERSON_NOT_REGISTERED;
		public FaceResult(int index, int personId)
		{
			this.face_id = index;
			this.personId = personId;
		}
		
		public static ArrayList<Integer> toIndexArray(ArrayList<FaceResult> mFaceresults)
		{
			ArrayList<Integer> indexArray = new ArrayList<Integer>(mFaceresults.size());
			for(FaceResult result:mFaceresults)
			{
				indexArray.add(result.face_id);
			}
			return indexArray;
		}
		
		public static ArrayList<Integer> toPersonIdArray(ArrayList<FaceResult> mFaceresults)
		{
			ArrayList<Integer> indexArray = new ArrayList<Integer>(mFaceresults.size());
			for(FaceResult result:mFaceresults)
			{
				indexArray.add(result.personId);
			}
			return indexArray;
		}
	}
}
