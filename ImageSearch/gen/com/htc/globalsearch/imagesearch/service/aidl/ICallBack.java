/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: H:\\SVN_Project\\ImageSearch\\ImageSearch\\src\\com\\htc\\globalsearch\\imagesearch\\service\\aidl\\ICallBack.aidl
 */
package com.htc.globalsearch.imagesearch.service.aidl;
public interface ICallBack extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.htc.globalsearch.imagesearch.service.aidl.ICallBack
{
private static final java.lang.String DESCRIPTOR = "com.htc.globalsearch.imagesearch.service.aidl.ICallBack";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.htc.globalsearch.imagesearch.service.aidl.ICallBack interface,
 * generating a proxy if needed.
 */
public static com.htc.globalsearch.imagesearch.service.aidl.ICallBack asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.htc.globalsearch.imagesearch.service.aidl.ICallBack))) {
return ((com.htc.globalsearch.imagesearch.service.aidl.ICallBack)iin);
}
return new com.htc.globalsearch.imagesearch.service.aidl.ICallBack.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onQueryResult:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem> _arg0;
_arg0 = data.createTypedArrayList(com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem.CREATOR);
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.onQueryResult(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onServiceStatusChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onServiceStatusChanged(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.htc.globalsearch.imagesearch.service.aidl.ICallBack
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void onQueryResult(java.util.List<com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem> items, java.lang.String path, int filter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(items);
_data.writeString(path);
_data.writeInt(filter);
mRemote.transact(Stub.TRANSACTION_onQueryResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onServiceStatusChanged(int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onServiceStatusChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onQueryResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onServiceStatusChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void onQueryResult(java.util.List<com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem> items, java.lang.String path, int filter) throws android.os.RemoteException;
public void onServiceStatusChanged(int status) throws android.os.RemoteException;
}
