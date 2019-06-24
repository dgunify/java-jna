package com.dgunify.jna;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.PointerByReference;

/**
 * JNA框架DLL动态库读取调用示例类
 */
public class JNADllCall {
	/**
	 * DLL动态库调用方法
	 */
	public interface CLibrary extends Library {
		String urls = "F:\\SdkWrapper";// dll所在路径
		CLibrary INSTANCE = (CLibrary) Native.loadLibrary(urls, CLibrary.class);
		//调用方法
		WString CommitBussiness(int interfacecode, String xmldata);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		//hxsdk.conf 放项目更目录
		//参数
		String xmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><crhmsBusinessQueryBean><businessTime>2019-4-13</businessTime><clientOper>1</clientOper><storeNo>6262001000000056</storeNo><businessType>104</businessType></crhmsBusinessQueryBean>";
		//调用返回
		WString strLicense = CLibrary.INSTANCE.CommitBussiness(104, xmlstr);
		String toString = strLicense.toString();
		String newString = toString.substring(5, toString.length());
		try {
			Document doc = DocumentHelper.parseText("<xml>"+newString); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			String rescode = rootElt.elementTextTrim("rescode");
			String resmsg = rootElt.elementTextTrim("resmsg");
			if("200".equals(rescode)) {
				Element data = rootElt.element("data");
				String businessWaterNo = data.elementText("businessWaterNo");
				String accountMoney = data.elementText("accountMoney");
				String businessCode = data.elementText("businessCode");
				String mallId = data.elementText("mallId");
				System.out.println(rescode);
				System.out.println(resmsg);
				System.out.println(businessWaterNo);
				System.out.println(accountMoney);
				System.out.println(businessCode);
				System.out.println(mallId);
			}else {
				System.out.println(rescode);
				System.out.println(resmsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}