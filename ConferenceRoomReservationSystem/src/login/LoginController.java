package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import master.ClientMasterController;
import transmission.LoginData;
import transmission.TransmissionData;

public class LoginController implements ActionListener {

	private LoginModel lm;
	private LoginView lv;
	private LoginData loginData;
	private TransmissionData data = new TransmissionData();

	public LoginController(LoginModel m, LoginView v) {
		lm = m;
		lv = v;
		lv.setLoginListener(this);
	}

	public boolean controlModel(TransmissionData data) {

		if (data.getFlags() == 12) {
			// there is same named room
			lm.setMessage(data.getMessage());
			return true;
		} else if (data.getFlags() == 13) {
			// successfully registered
			lm.setMessage(data.getMessage());
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		lm.setId(lv.getIdStr());
		lm.setPw(lv.getPwStr());

		if (lv.isEpu() && lv.isNmu()) {
			lm.setMessage("사용자 하나만을 선택하세요.");
		} else if (lv.isEpu() == false && lv.isNmu() == false) {
			lm.setMessage("사용자를 선택하세요.");
		} else if (lv.isEpu()) {
			loginData = new LoginData(lm.getId(), lm.getPw());
			data.setFlags(11);
			data.setLoginData(loginData);
			System.out.println(loginData.getId() + ", " + loginData.getPw());
			try {
				ClientMasterController.getClient().sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (lv.isNmu()) {

		}
	}

}