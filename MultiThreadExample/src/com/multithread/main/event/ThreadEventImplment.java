package com.multithread.main.event;

public class ThreadEventImplment implements ThreadEventListener {

	private Person person = null;

	public ThreadEventImplment(Person p) {
		person = p;
	}

	@Override
	public void threadEvent(ThreadEvent event) {
		// TODO Auto-generated method stub
		if (person == null) {
			return;
		}

		if (event.getOperator() != null && event.getOperator() == "add") {
			person.addId();
			System.out.println("operator:"+event.getOperator()+"\t"+person.getId());
		} else {
			person.reduceId();
			System.out.println("operator:"+event.getOperator()+"\t"+person.getId());
		}
	}

}
