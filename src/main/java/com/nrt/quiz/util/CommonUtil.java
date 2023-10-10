package com.nrt.quiz.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.nrt.quiz.constants.Constants;

public class CommonUtil {

	public static void saveFile(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		String filePath = Constants.UPLOAD_DIR + File.separator + fileName;

		// Create the directory if it doesn't exist.
		File directory = new File(Constants.UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		// Save the file to the specified location.
		File dest = new File(filePath);
		file.transferTo(dest);

	}

}
