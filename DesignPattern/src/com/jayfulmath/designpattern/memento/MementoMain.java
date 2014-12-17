package com.jayfulmath.designpattern.memento;

import com.jayfulmath.designpattern.main.BasicExample;

/*最长见于游戏状态保存，该模式不需要知道游戏具体状态，只是保存起来，等待需要的时候恢复。
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
