package com.jayfulmath.designpattern.memento;

import com.jayfulmath.designpattern.main.BasicExample;

/*�������Ϸ״̬���棬��ģʽ����Ҫ֪����Ϸ����״̬��ֻ�Ǳ����������ȴ���Ҫ��ʱ��ָ���
 * 
 * 
 * */
public class MementoMain extends BasicExample {

	@Override
	public void startDemo() {
		// TODO Auto-generated method stub
		Player mPlayer = new Player();
		mPlayer.setStatus("Before attack Boss");
		
		System.out.println("Plaeyr status:"+mPlayer.getStatus());
		
		StatusTaker taker = new StatusTaker();
		taker.set_mMemonto(mPlayer.CreateMemontoStatus());
		
		
		mPlayer.setStatus("GameOver");
		System.out.println("Plaeyr status:"+mPlayer.getStatus());
		
		mPlayer.setMemonto(taker.get_mMemonto());
		System.out.println("Plaeyr status after restore:"+mPlayer.getStatus());
		
	}

}
