package com.jpos.server.main.iso;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

public class RequestListener implements ISORequestListener {

	private static final String NETWORK_REQUEST = "0800";
	private static final String NETWORK_RESPONSE = "0810";
	public static final Integer RESPONSE_CODE = 39;

	@Override
	public boolean process(ISOSource sender, ISOMsg request) {
		// TODO Auto-generated method stub
		try {
			String mti = request.getMTI();
			if (NETWORK_REQUEST.equals(mti)) {
				ISOMsg response = (ISOMsg) request.clone();
				response.setMTI(NETWORK_RESPONSE);
				response.set(RESPONSE_CODE, "00");
				sender.send(response);
				return true;
			}

			return false;
		} catch (Exception ex) {
			Logger.getLogger(RequestListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

}
