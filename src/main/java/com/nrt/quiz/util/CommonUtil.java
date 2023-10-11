package com.nrt.quiz.util;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nrt.quiz.constants.Constants;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CommonUtil {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY = "1234567801234578"; // Replace with your secret key

	// encrypt the data
	public static String encrypt(String data) {
		try {
			Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedData;

			encryptedData = cipher.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}

	// Decryptes the encrypted data
	public static String decrypt(String encryptedData) {
		try {
			Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] rawData = Base64.getDecoder().decode(encryptedData);
			byte[] decryptedData;

			decryptedData = cipher.doFinal(rawData);
			return new String(decryptedData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;

	}

	// saves the files into upload directory
	public static void saveFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String filePath = Constants.UPLOAD_DIR + File.separator + fileName;

		try {
			// Create the directory if it doesn't exist.
			File directory = new File(Constants.UPLOAD_DIR);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			// Save the file to the specified location.
			File dest = new File(filePath);
			file.transferTo(dest);
			log.info("file save to its destination :" + Constants.UPLOAD_DIR);
		} catch (IllegalStateException e) {
			log.error("IllegalStateException is occoured :" + e.getMessage());
		} catch (IOException e) {
			log.error("IOException its occoured :" + e.getMessage());
		}

	}

}
