package com.multithread.main.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class ThreadEventManager {
	private Collection<ThreadEventListener> listeners = null;

	public void addListener(ThreadEventListener listener) {
		if (listeners == null) {
			listeners = new HashSet<ThreadEventListener>();
		}
		listeners.add(listener);
	}

	public void removeListener(ThreadEventListener listener) {
		if (listeners == null)
			return;

		listeners.remove(listener);
	}
	
	//add
	protected void fireAddAction(){
		if (listeners == null)
			return;
		
		ThreadEvent event = new ThreadEvent(this, "add");
		notifyListeners(event);
	}
	
	//del
	protected void fireDelAction(){
		if (listeners == null)
			return;
		
		ThreadEvent event = new ThreadEvent(this, "del");
		notifyListeners(event);
	}
	
    /**
     * 通知所有的ThreadEventListener
     */
    private void notifyListeners(ThreadEvent event) {
        Iterator<ThreadEventListener> iter = listeners.iterator();
        while (iter.hasNext()) {
        	ThreadEventListener listener = (ThreadEventListener) iter.next();
            listener.threadEvent(event);;
        }
    }
}
