package com.multithread.main.event;

import java.util.EventListener;

public interface ThreadEventListener extends EventListener {
	public void threadEvent(ThreadEvent event);
}
