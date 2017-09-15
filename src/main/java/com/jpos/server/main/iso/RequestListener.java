package com.jpos.server.main.iso;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

public class RequestListener implements ISORequestListener {

	public static final String INQUIRY_REQUEST = "0200";
	public static final String INQUIRY_RESPONSE = "0210";
	private static final String NETWORK_REQUEST = "0800";
	private static final String NETWORK_RESPONSE = "0810";
	public static final String REVERSAL_REQUEST = "0400";
	public static final String REVERSAL_REQUEST_REPEAT = "0401";
	public static final String REVERSAL_RESPONSE = "0410";
	
	public static final Integer RESPONSE_CODE = 39;
	
	public static final String INQUIRY_PROCESSING_CODE = "380000"; // bit 3
	public static final String PAYMENT_PROCESSING_CODE = "180000";

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
			
			String processingCode = request.getString(3);
			if (INQUIRY_REQUEST.equals(mti) && processingCode.equals(INQUIRY_PROCESSING_CODE)) {
				/*
				ISOMsg response = (ISOMsg) request.clone();
				response.setMTI(INQUIRY_RESPONSE);
				response.set(RESPONSE_CODE, "00");
				*/
				String isoString = "0210723E40010A41800216000730032307003938000000000000000003230700390000690200390323032303246021060007300000697641120002906123       6322112539507764112 404XVW2VXXZV3ZYWV56789AY12VY2WXXV2W                                PEL POSTPAID 088767           535550212337890     R1  00000130000000000000000120000003602017022502201724022017000000300000C000000000000000000000000000000000000010000000100000002000000020000000300000003002017012501201724022017000000300000C000000000000000000000000000000000000010000000100000002000000020000000300000003002016122512201624022017000000300000C000000000000000000000000000000000000010000000100000002000000020000000300000003002016102510201624022017000000300000C00000000000000000000000000000000000001000000010000000200000002000000030000000300360003214";
				ISOMsg response = new ISOMsg();
				response.setPackager(new MyPackager());
				response.unpack(isoString.getBytes());
				sender.send(response);
				return true;
			}
			
			if (INQUIRY_REQUEST.equals(mti) && processingCode.equals(PAYMENT_PROCESSING_CODE)) {
				ISOMsg response = (ISOMsg) request.clone();
				response.setMTI(INQUIRY_RESPONSE);
				response.set(RESPONSE_CODE, "00");				
				sender.send(response);
				return true;
			}
			
			if (REVERSAL_REQUEST.equals(mti)) {
				ISOMsg response = (ISOMsg) request.clone();
				response.setMTI(REVERSAL_RESPONSE);
				response.set(RESPONSE_CODE, "00");
				sender.send(response);
				return true;
			}
			
			if (REVERSAL_REQUEST_REPEAT.equals(mti)) {
				ISOMsg response = (ISOMsg) request.clone();
				response.setMTI(REVERSAL_RESPONSE);
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
