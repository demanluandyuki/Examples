package com.htc.globalsearch.imagesearch.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonImageItem implements Parcelable {
	
	public int persion_id;
	public int contact_id = -1;
	public int type = -1;
	public int image_id = -1;
	public int imageSize;
	public String Bitmap_Path = "";


	public PersonImageItem(int personId,int contact_id,String path,int imageId, int imageSize,int type)
	{
		this.persion_id = personId;
		this.contact_id = contact_id;
		this.Bitmap_Path = path;
		this.image_id = imageId;
		this.imageSize = imageSize;
		this.type = type;
	}

	public PersonImageItem(Parcel source)
	{
		readFromParcel(source);
	}
	
	
	public PersonImageItem(PersonImageItem person) {
		this.persion_id = person.persion_id;
		this.contact_id = person.contact_id;
		this.Bitmap_Path =person.Bitmap_Path;
		this.image_id = person.image_id;
		this.imageSize = person.imageSize;
		this.type = person.type;
	}

	public PersonImageItem(int persionId, int contact_id,int type)
	{
		this.persion_id = persionId;
		this.contact_id = contact_id;
		this.type = type;
	}
	

	public final static Parcelable.Creator<PersonImageItem> CREATOR = new Parcelable.Creator<PersonImageItem>() {

		@Override
		public PersonImageItem createFromParcel(Parcel source) {
			return new PersonImageItem(source);
		}

		@Override
		public PersonImageItem[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PersonImageItem[size];
		}
		
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(persion_id);
		dest.writeInt(contact_id);
		dest.writeInt(image_id);
		dest.writeInt(imageSize);
		dest.writeInt(type);
		dest.writeString(Bitmap_Path);

	}
	
	public void readFromParcel(Parcel source)
	{
		persion_id = source.readInt();
		contact_id = source.readInt();
		image_id = source.readInt();
		imageSize = source.readInt();
		type = source.readInt();
		Bitmap_Path = source.readString();
	}
}
