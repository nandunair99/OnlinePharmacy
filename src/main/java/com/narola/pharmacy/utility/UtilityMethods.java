package com.narola.pharmacy.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Collection;

import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.test.model.TestBean;

public class UtilityMethods {

	public static String getServletName(String url) {
		url = url + " ";
		String[] urls = url.split("/");
		return urls[urls.length - 1].trim();
	}

	public static void writeImagesToFolder(String fileName, String destPath, Collection<Part> parts, Integer medId)
			throws PharmacyServiceException {
		try {
			InputStream filecontent = null;

			OutputStream out = null;

			for (Part part : parts) {

				if (part.getName().equals(Constant.CTRL_IMAGE_CONTROL)) {

					fileName = part.getSubmittedFileName();
					if (fileName != null && !fileName.isEmpty()) {
						out = new FileOutputStream(new File(destPath + medId.toString() + File.separator + fileName));
						filecontent = part.getInputStream();
						final byte[] bytes = new byte[1024];
						int read = 0;
						while ((read = filecontent.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
						out.flush();
						out.close();
					}
				}

			}
		} catch (IOException e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}
	}

	public static String inputStreamToBase64String(TestBean tb) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		final byte[] bytes = new byte[1024];
		int read = 0;
		try {
			while ((read = tb.getPicStream().read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Base64.getEncoder().encodeToString(bos.toByteArray());
	}
}
