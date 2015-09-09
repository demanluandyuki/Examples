package com.htc.globalsearch.imagesearch.service.aidl;
import com.htc.globalsearch.imagesearch.service.aidl.PersonImageItem;
import com.htc.globalsearch.imagesearch.service.aidl.ICallBack;

interface IBuildService{
    int getServiceStatus();
    int findPerson(String path,int filter);
    void registerCallback(ICallBack cb);     
    void unregisterCallback(ICallBack cb);  
}