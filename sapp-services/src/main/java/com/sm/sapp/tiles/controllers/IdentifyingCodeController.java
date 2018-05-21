package com.sm.sapp.tiles.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sm.sapp.tiles.interfaces.IIdentifyingCode;

@Controller
@RequestMapping("/icoder")
public class IdentifyingCodeController {
	@Resource
	private IIdentifyingCode iCoder = null;

	final private String format = "image/png";

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<byte[]> getICode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		HttpHeaders responseHeaders = new HttpHeaders();
		MediaType mtype = MediaType.valueOf(format);
		responseHeaders.setContentType(mtype);

		String code = iCoder.generateIdentifyingCode(4);
		session.setAttribute("actualCode", code);
		BufferedImage tileImg = iCoder.drawIdentifyingCode(code);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(tileImg, mtype.getSubtype(), out);
		byte[] tileBytes = out.toByteArray();
		return new ResponseEntity<byte[]>(tileBytes, responseHeaders,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public @ModelAttribute
	Object validate(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String code = request.getParameter("code");
		Object actualCode = session.getAttribute("actualCode");
		boolean result = false;
		if (code != null && actualCode != null
				&& code.equalsIgnoreCase(actualCode.toString())) {
			result = true;
		}
		session.setAttribute("actualCode", null);
		return result;
		
	}

}
