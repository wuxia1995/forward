package com.ntech.util;

import java.util.Base64;

public class Base64Encrypt {
	
	public static String  encryptUserName(String userName) {
		byte[] b = userName.getBytes();
		for(int i=0;i<b.length;i++) {
			if(b[i]%2==0) {
				b[i] = (byte) (b[i]+1);
			}else {
				b[i] = (byte) (b[i]-1);
			}
		}
		return new String(Base64.getEncoder().encode(b));
	}
	public static String  decryptUserName(String userName) {
		byte[] b = userName.getBytes();
		b = Base64.getDecoder().decode(b);
		for(int i=0;i<b.length;i++) {
			if(b[i]%2==0) {
				b[i] = (byte) (b[i]+1);
			}else {
				b[i] = (byte) (b[i]-1);
			}
		}
		return new String(b);
	}
}
