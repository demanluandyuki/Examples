/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: H:\\SVN_Project\\ImageSearch\\ImageSearch\\src\\com\\htc\\globalsearch\\imagesearch\\service\\aidl\\IBuildService.aidl
 */
package com.htc.globalsearch.imagesearch.service.aidl;
public interface IBuildService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.htc.globalsearch.imagesearch.service.aidl.IBuildService
{
private static final java.lang.String DESCRIPTOR = "com.htc.globalsearch.imagesearch.service.aidl.IBuildService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.htc.globalsearch.imagesearch.service.aidl.IBuildService interface,
 * generating a proxy if needed.
 */
public static com.htc.globalsearch.imagesearch.service.aidl.IBuildService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.htc.globalsearch.imagesearch.service.aidl.IBuildService))) {
return ((com.htc.globalsearch.imagesearch.service.aidl.IBuildService)iin);
}
return new com.htc.globalsearch.imagesearch.service.aidl.IBuildService.Stub.Proxy(obj);
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
case TRANSACTION_getServiceStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getServiceStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_findPerson:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.findPerson(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.htc.globalsearch.imagesearch.service.aidl.ICallBack _arg0;
_arg0 = com.htc.globalsearch.imagesearch.service.aidl.ICallBack.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.htc.globalsearch.imagesearch.service.aidl.ICallBack _arg0;
_arg0 = com.htc.globalsearch.imagesearch.service.aidl.ICallBack.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.htc.globalsearch.imagesearch.service.aidl.IBuildService
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
@Override public int getServiceStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int findPerson(java.lang.String path, int filter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(filter);
mRemote.transact(Stub.TRANSACTION_findPerson, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void registerCallback(com.htc.globalsearch.imagesearch.service.aidl.ICallBack cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterCallback(com.htc.globalsearch.imagesearch.service.aidl.ICallBack cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getServiceStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_findPerson = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public int getServiceStatus() throws android.os.RemoteException;
public int findPerson(java.lang.String path, int filter) throws android.os.RemoteException;
public void registerCallback(com.htc.globalsearch.imagesearch.service.aidl.ICallBack cb) throws android.os.RemoteException;
public void unregisterCallback(com.htc.globalsearch.imagesearch.service.aidl.ICallBack cb) throws android.os.RemoteException;
}
