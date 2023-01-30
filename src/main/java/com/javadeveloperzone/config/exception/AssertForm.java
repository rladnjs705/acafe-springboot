package com.javadeveloperzone.config.exception;

import com.javadeveloperzone.config.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class AssertForm {

	/**
	 * NotNull의 메세지 커스톰 타입
	 */
	public static void notNullEx(Object object, String message) throws ParamException {

		if (object == null) {
			message = getNullMsg(message);
			throw new ParamException(message);
		}

		if (object instanceof String) {
			String value = (String) object;
			if (StringUtils.isEmpty(value)) {
				throw new ParamException(message);
			}
		}
	}

	public static void notNullEx(Map<String,Object> map, String key, String message) throws ParamException {

		if (map == null || map.get(key) == null) {
			message = getNullMsg(message);
			throw new ParamException(message);
		}
		if (StringUtils.isEmpty(map.get(key).toString())) {
			throw new ParamException(message);
		}
	}

	/**
	 * NotNull의 메세지 커스톰 타입
	 */
	public static void notNullEx(String[] object, String message) throws ParamException {

		if (object == null) {
			message = getNullMsg(message);
			throw new ParamException(message);
		}

		int len = object.length;
		if (len == 0) {
			throw new ParamException(message);
		}

		for (String value : object) {
			if (StringUtils.isEmpty(value)) {
				throw new ParamException(message);
			}
		}
	}

	/**
	 * 파라피미터값으로 정형화된 notNull 조건에 대한 메세지를 가져온다
	 */
	public static String getNullMsg(String msg) {

		return msg = "[Assertion failed] - " + msg + " is required; it must not be null";
	}

	/**
	 * 파일확장자 체크
	 */
	public static void notImageExt(List<MultipartFile> fileList) throws ParamException {

		if(null != fileList) {
			for(int i=0; i < fileList.size(); i++) {
				for(int j=0; j < fileList.size();j++) {
					String ext = null;
					int dot = fileList.get(i).getOriginalFilename().lastIndexOf('.');
					ext = fileList.get(i).getOriginalFilename().substring(dot + 1);

					if(null != ext) {
						if("jpg".equals(ext.toLowerCase(Locale.ENGLISH)) ||
							"jpeg".equals(ext.toLowerCase(Locale.ENGLISH)) ||
							"png".equals(ext.toLowerCase(Locale.ENGLISH))) {
							return;
						}else {
							throw new ParamException("file extension not match");
						}
					}
				}
			}
		}
	}
}
