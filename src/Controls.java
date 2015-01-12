import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controls implements KeyListener {
  protected boolean left;
  protected boolean right;
  protected boolean forward;
  protected boolean backward;
  protected boolean space;
  protected boolean randomMap;

  public Controls() {
    left = right = forward = space = backward = false;
  }

@Override
public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_A) {
		left = true;
	}
	if(e.getKeyCode() == KeyEvent.VK_D) {
		right = true;
	}
	if(e.getKeyCode() == KeyEvent.VK_W) {
		forward = true;
	}
	if(e.getKeyCode() == KeyEvent.VK_S) {
		backward = true;
	}
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		space = true;
	}
	if(e.getKeyCode() == KeyEvent.VK_R) {
		randomMap = true;
	}
	
}

@Override
public void keyReleased(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_A) {
		left = false;
	}
	if(e.getKeyCode() == KeyEvent.VK_D) {
		right = false;
	}
	if(e.getKeyCode() == KeyEvent.VK_W) {
		forward = false;
	}
	if(e.getKeyCode() == KeyEvent.VK_S) {
		backward = false;
	}
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		space = false;
	}
	if(e.getKeyCode() == KeyEvent.VK_R) {
		randomMap = false;
	}
	
}

@Override
public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub
	
}

}
