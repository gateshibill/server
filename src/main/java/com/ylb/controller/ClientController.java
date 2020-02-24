package com.ylb.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xmlpull.v1.XmlPullParser;

import com.ylb.util.BaseUtil;
import com.ylb.util.JsonUtil;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

@RestController
@RequestMapping("/client")
public class ClientController extends BaseUtil {
	private final static Logger log = LoggerFactory.getLogger(ClientController.class);
	private final static String APK_URL = "/usr/java/apache-tomcat-8.5.40/webapps/apk/yinlibao.apk";
	//private final static String APK_URL = "D:\\yinlibao.apk";
	
	/**
	 * 搜索用戶
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getApkLatestVersion", method = RequestMethod.GET)
	public void getApkLatestVersion(HttpServletRequest request, HttpServletResponse response, String keyword, Integer page,
			Integer limit) {
		log.debug("getApkLatestVersion()");
		try {
			Map<String, Object> mapApk = ClientController.readAPK(APK_URL);
			String version = (String) mapApk.get("versionName");
			output(response, JsonUtil.buildObjectJson(0, "success", version));
		} catch (Exception e) {
			log.error(e.getMessage());
			output(response, JsonUtil.buildObjectJson("1", "failure"));
		}
	}

	/**
	 * 读取apk
	 * 
	 * @param apkUrl
	 * @return
	 */
	public static Map<String, Object> readAPK(String apkUrl) {
		ZipFile zipFile;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			zipFile = new ZipFile(apkUrl);
			Enumeration<?> enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (zipEntry.isDirectory()) {

				} else {
					if ("androidmanifest.xml".equals(zipEntry.getName().toLowerCase())) {
						AXmlResourceParser parser = new AXmlResourceParser();
						parser.open(zipFile.getInputStream(zipEntry));
						while (true) {
							int type = parser.next();
							if (type == XmlPullParser.END_DOCUMENT) {
								break;
							}
							String name = parser.getName();
							if (null != name && name.toLowerCase().equals("manifest")) {
								for (int i = 0; i != parser.getAttributeCount(); i++) {
									if ("versionName".equals(parser.getAttributeName(i))) {
										String versionName = getAttributeValue(parser, i);
										if (null == versionName) {
											versionName = "";
										}
										map.put("versionName", versionName);
									} else if ("package".equals(parser.getAttributeName(i))) {
										String packageName = getAttributeValue(parser, i);
										if (null == packageName) {
											packageName = "";
										}
										map.put("package", packageName);
									} else if ("versionCode".equals(parser.getAttributeName(i))) {
										String versionCode = getAttributeValue(parser, i);
										if (null == versionCode) {
											versionCode = "";
										}
										map.put("versionCode", versionCode);
									}
								}
								break;
							}
						}
					}

				}
			}
			zipFile.close();
		} catch (Exception e) {
			map.put("code", "fail");
			map.put("error", "读取apk失败");
			log.error(e.getMessage());
		}
		return map;
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == TypedValue.TYPE_STRING) {
			return parser.getAttributeValue(index);
		}
		if (type == TypedValue.TYPE_ATTRIBUTE) {
			return String.format("?%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_REFERENCE) {
			return String.format("@%s%08X", getPackage(data), data);
		}
		if (type == TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == TypedValue.TYPE_INT_HEX) {
			return String.format("0x%08X", data);
		}
		if (type == TypedValue.TYPE_INT_BOOLEAN) {
			return data != 0 ? "true" : "false";
		}
		if (type == TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type == TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format("#%08X", data);
		}
		if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>", data, type);
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1) {
			return "android:";
		}
		return "";
	}

// ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
	public static float complexToFloat(int complex) {
		return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
	}

	private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };

	@RequestMapping("apk")
//public ResponseParameterApp apk(@RequestBody RequestParameter<VersionNumber> parameter){
//    try {
//        VersionNumber sa = parameter.getReqparam();
//        String url=sa.getUrl();
//        System.out.println("======apk=========");
//        //String apkUrl = "D:\\app_V3.0.0_yingyongbao_release_2019-03-22_2019-03-23_00-09-53_legu_aligned_signed.apk";
//        String apkUrl =url;
//        //获取MD5方法2
//        //String aa=DigestUtils.md5Hex(new FileInputStream(apkUrl));
//        //获取版本号
//        Map<String,Object> mapApk = VersionNumberController.readAPK(apkUrl);
//        Object versionCode=mapApk.get("versionCode");
//        String version = (String)versionCode;
//        //获取MD5方法1
//        String md5 = VersionNumberController.getMD5Three(apkUrl);
//        VersionNumber list = new VersionNumber();
//        list.setVersion(version);
//        list.setMd5(md5);
//        setResponseApp(ResultConstant.SUCCESS, ResultConstant.SUCCESS_DESC, ResultConstant.SUCCESS_MSG, list);
//        return getResponseObjApp();
//    }catch (Exception e) {
//        e.printStackTrace();
//        setResponseApp(ResultConstant.SYSTEM_ERROR, String.format(ResultConstant.SYSTEM_ERROR_DESC, "系统错误，请联系管理员")
//                , ResultConstant.SYSTEM_ERROR_MSG, null);
//        return getResponseObjApp();
//    }
//}
//
//public static String getMD5Three(String path) {
//    BigInteger bi = null;
//    try {
//        byte[] buffer = new byte[8192];
//        int len = 0;
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        File f = new File(path);
//        FileInputStream fis = new FileInputStream(f);
//        while ((len = fis.read(buffer)) != -1) {
//            md.update(buffer, 0, len);
//        }
//        fis.close();
//        byte[] b = md.digest();
//        bi = new BigInteger(1, b);
//    } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//    return bi.toString(16);
//}

	public static void main(String[] args) {
		System.out.println("======apk=========");
		String apkUrl = "D:\\yinlibao.apk";
		Map<String, Object> mapApk = ClientController.readAPK(apkUrl);
		for (String key : mapApk.keySet()) {
			System.out.println(key + ":" + mapApk.get(key));
		}
//		System.out.println("======ipa==========");
//		String ipaUrl = "src/IM.ipa";
//		Map<String, Object> mapIpa = VersionNumberController.readIPA(ipaUrl);
//		for (String key : mapIpa.keySet()) {
//			System.out.println(key + ":" + mapIpa.get(key));
//		}
//		String path = "D:\\app_V3.0.0_yingyongbao_release_2019-03-22_2019-03-23_00-09-53_legu_aligned_signed.apk";
//		String MD5 = VersionNumberController.getMD5Three(path);
//		System.out.println(mapApk);
	}

}