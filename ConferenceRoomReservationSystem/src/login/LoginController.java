package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import master.ClientMasterController;
import transmission.LoginData;
import transmission.TransmissionData;

/*
 * This class is about to login
 * MVC controller
 */
public class LoginController implements ActionListener {

	private LoginModel lm;
	private LoginView lv;
	private TransmissionData data;

	public LoginController(LoginModel m, LoginView v) {
		lm = m;
		lv = v;
		lv.setLoginListener(this);
	}

	public boolean controlModel(TransmissionData data) {

		if (data.getFlags() == 13) {
			// enterprise user login success
			lm.setMessage(data.getMessage());
			return true;
		} else if (data.getFlags() == 14) {
			// login failed
			lm.setMessage(data.getMessage());
		}

		return false;
	}

	/*
	 * when logout, reset id field and password field
	 */
	public void logout() {
		lm.setId("아이디");
		lm.setPw("비밀번호");
		lv.logout();
		lm.setMessage("정상적으로 로그아웃되었습니다.");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getActionCommand().equals("회원가입")) {
			data = new TransmissionData();
			data.setFlags(0);
			try {
				ClientMasterController.getClient().sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		lm.setId(lv.getIdStr());
		lm.setPw(lv.getPwStr());

		/*
		 * validation and send to server
		 */
		if (lv.isEpu() && lv.isNmu()) {
			lm.setMessage("사용자 하나만을 선택하세요.");
		} else if (lv.isEpu() == false && lv.isNmu() == false) {
			lm.setMessage("사용자를 선택하세요.");
		} else if (lv.isEpu()) {
			data = new TransmissionData();
			data.setFlags(11);
			data.setLoginData(new LoginData(lm.getId(), lm.getPw()));
			try {
				ClientMasterController.getClient().sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (lv.isNmu()) {
			data = new TransmissionData();
			data.setFlags(10);
			data.setLoginData(new LoginData(lm.getId(), lm.getPw()));
			try {
				ClientMasterController.getClient().sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}