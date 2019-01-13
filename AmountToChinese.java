package wg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;






/** 人民币的阿拉伯数字金额 转换成汉字展示  */
public class AmountToChinese {

	
	/**人民币的数字金额 转换成汉字展示(最大支持千万亿级别)   add by iunbeknown  */
	public String getChineseAmount(String amt){
		if(amt==null || amt.trim().equals("")){
			return "";
		}
		
		Pattern pattern = Pattern.compile("[.0-9]*");
		Matcher isNum = pattern.matcher(amt);
		boolean b = isNum.matches();
		if(!b){
			return "待转换的数字格式有误！";
		}
		
		//去掉数字前面多余的0
		int alllen = amt.trim().length();
		for(int i=0; i<alllen; i++){
			char charAt = amt.charAt(i);
			if(charAt != '0'){
				amt = amt.substring(i);
				break;
			}
		}
		
		
		if(amt==null || amt.trim().equals("")){
			return "";
		}
		
		double amtdou = Double.parseDouble(amt);
		if(amtdou > 9999999999999999.99){
			return "金额超过最大支持限度！";
		}
		
		
		int len = amt.length();
		int index = amt.indexOf(".");
		
		String jiaofen="";
		//获取角分位
		if(index>-1){
			int jfnum = len-index+1;  //角分有几位
			if(jfnum == 0){
				jiaofen="整";
			}else if(jfnum == 1){
				String jiao = amt.substring(index+1,index+2);
				String jiaocn = hanZiChg(jiao);
				if(jiao.equals("0")){
					jiaofen="整";
				}else{
					jiaofen = jiaocn+"角";
				}
			}else{
				String jiao = amt.substring(index+1,index+2);
				String jiaocn = hanZiChg(jiao);
				String fen = amt.substring(index+2,index+3);
				String fencn = hanZiChg(fen);
				if(jiao.equals("0") && fen.equals("0")){
					jiaofen="整";
				}else if(fen.equals("0")){
					jiaofen = jiaocn+"角";
				}else if(jiao.equals("0")){
					jiaofen = fencn+"分";
				}else{
					jiaofen = jiaocn+"角"+fencn+"分";
				}
			}
		}else{
			jiaofen="整";
			index = len;
		}
		
		if(amtdou < 1){
			return jiaofen;
		}
		
		String yuan="";
		String shi="";
		String bai="";
		String qian="";
		String wan="";
		String shiwan="";
		String baiwan="";
		String qianwan="";
		
		String yi = "";
		String shiyi = "";
		String baiyi = "";
		String qianyi = "";
		String wanyi = "";
		String shiwanyi="";
		String baiwanyi="";
		String qianwanyi="";
		
		String strfinal=""; //获取最终的汉字
		
		if ((index - 1) > -1) { // 元
			yuan = amt.substring(index - 1, index);
			String yuancn = hanZiChg(yuan);
			if (yuan.equals("0")) {
				strfinal = "元" + jiaofen;
			} else {
				strfinal = yuancn + "元" + jiaofen;
			}
			if ((index - 1) == 0) {
				return strfinal;
			}
		}
		if ((index - 2) > -1) { // 十
			shi = amt.substring(index - 2, index - 1);
			String shicn = hanZiChg(shi);
			if (shi.equals("0")) {
				if (yuan.equals("0")) {
					// 个位位和十位都是0时，不变
				} else {
					strfinal = shicn + strfinal;
				}
			} else {
				strfinal = shicn + "拾" + strfinal;
			}
			if ((index - 2) == 0) {
				return strfinal;
			}
		}
		if ((index - 3) > -1) { // 百
			bai = amt.substring(index - 3, index - 2);
			String baicn = hanZiChg(bai);
			if (bai.equals("0")) {
				if (shi.equals("0")) {
					// 如果与十位同时为0，则百位不用变化
				} else {
					strfinal = baicn + strfinal;
				}
			} else {
				strfinal = baicn + "佰" + strfinal;
			}
			if ((index - 3) == 0) {
				return strfinal;
			}
		}
		if ((index - 4) > -1) { // 千
			qian = amt.substring(index - 4, index - 3);
			String qiancn = hanZiChg(qian);
			if (qian.equals("0")) {
				if (bai.equals("0")) {
					// 同上
				} else {
					strfinal = qiancn + strfinal;
				}
			} else {
				strfinal = qiancn + "仟" + strfinal;
			}
			if ((index - 4) == 0) {
				return strfinal;
			}
		}
		if ((index - 5) > -1) { // 万
			wan = amt.substring(index - 5, index - 4);
			String wancn = hanZiChg(wan);
			if (wan.equals("0")) {
				boolean isallzero = judgeisallwanzero(amt); // 判断所有的万位是否都为0
				if (isallzero) {
					if (qian.equals("0")) {
						
					} else {
						strfinal = wancn + strfinal;
					}
				} else {
					strfinal = "万" + strfinal;
				}
			} else {
				strfinal = wancn + "万" + strfinal;
			}
			if ((index - 5) == 0) {
				return strfinal;
			}
		}
		if ((index - 6) > -1) { // 十万
			shiwan = amt.substring(index - 6, index - 5);
			String shiwancn = hanZiChg(shiwan);
			if (shiwan.equals("0")) {
				if (wan.equals("0")) {
					// 个位位和十位都是0时，不变
				} else {
					strfinal = shiwancn + strfinal;
				}
			} else {
				strfinal = shiwancn + "拾" + strfinal;
			}
			if ((index - 6) == 0) {
				return strfinal;
			}
		}
		if ((index - 7) > -1) { // 百万
			baiwan = amt.substring(index - 7, index - 6);
			String baiwancn = hanZiChg(baiwan);
			if (baiwan.equals("0")) {
				if (shiwan.equals("0")) {
					// 同上
				} else {
					strfinal = baiwancn + strfinal;
				}
			} else {
				strfinal = baiwancn + "佰" + strfinal;
			}
			if ((index - 7) == 0) {
				return strfinal;
			}
		}
		if ((index - 8) > -1) { // 千万
			qianwan = amt.substring(index - 8, index - 7);
			String qianwancn = hanZiChg(qianwan);
			if (qianwan.equals("0")) {
				if (baiwan.equals("0")) {
					// 同上
				} else {
					strfinal = qianwancn + strfinal;
				}
			} else {
				strfinal = qianwancn + "仟" + strfinal;
			}
			if ((index - 8) == 0) {
				return strfinal;
			}
		}

		if ((index - 9) > -1) { // 亿
			yi = amt.substring(index - 9, index - 8);
			String yicn = hanZiChg(yi);
			if (yi.equals("0")) {
				strfinal = "亿" + strfinal;
			} else {
				strfinal = yicn + "亿" + strfinal;
			}
			if ((index - 9) == 0) {
				return strfinal;
			}
		}
		if ((index - 10) > -1) { // 十亿
			shiyi = amt.substring(index - 10, index - 9);
			String shiyicn = hanZiChg(shiyi);
			if (shiyi.equals("0")) {
				if (yi.equals("0")) {
					// 个位位和十位都是0时，不变
				} else {
					strfinal = shiyicn + strfinal;
				}
			} else {
				strfinal = shiyicn + "拾" + strfinal;
			}
			if ((index - 10) == 0) {
				return strfinal;
			}
		}
		if ((index - 11) > -1) { // 百亿
			baiyi = amt.substring(index - 11, index - 10);
			String baiyicn = hanZiChg(baiyi);
			if (baiyi.equals("0")) {
				if (shiyi.equals("0")) {
					// 如果与十位同时为0，则百位不用变化
				} else {
					strfinal = baiyicn + strfinal;
				}
			} else {
				strfinal = baiyicn + "佰" + strfinal;
			}
			if ((index - 11) == 0) {
				return strfinal;
			}
		}
		if ((index - 12) > -1) { // 千亿
			qianyi = amt.substring(index - 12, index - 11);
			String qianyicn = hanZiChg(qianyi);
			if (qianyi.equals("0")) {
				if (baiyi.equals("0")) {
					// 同上
				} else {
					strfinal = qianyicn + strfinal;
				}
			} else {
				strfinal = qianyicn + "仟" + strfinal;
			}
			if ((index - 12) == 0) {
				return strfinal;
			}
		}
		
		if( (index-13) > -1){ //万亿
			wanyi = amt.substring(index-13,index-12);
			String wanyicn = hanZiChg(wanyi);
			if(wanyi.equals("0")){
				strfinal = "万" + strfinal;
			}else{
				strfinal = wanyicn+"万" + strfinal;
			}
			if( (index-13) == 0){
				return strfinal;
			}
		}
		if ((index - 14) > -1) { // 十万亿
			shiwanyi = amt.substring(index - 14, index - 13);
			String shiwanyicn = hanZiChg(shiwanyi);
			if (shiwanyi.equals("0")) {
				if (wanyi.equals("0")) {
					// 个位位和十位都是0时，不变
				} else {
					strfinal = shiwanyicn + strfinal;
				}
			} else {
				strfinal = shiwanyicn + "拾" + strfinal;
			}
			if ((index - 14) == 0) {
				return strfinal;
			}
		}
		if ((index - 15) > -1) { // 百万亿
			baiwanyi = amt.substring(index - 15, index - 14);
			String baiwanyicn = hanZiChg(baiwanyi);
			if (baiwanyi.equals("0")) {
				if (shiwanyi.equals("0")) {
					// 同上
				} else {
					strfinal = baiwanyicn + strfinal;
				}
			} else {
				strfinal = baiwanyicn + "佰" + strfinal;
			}
			if ((index - 15) == 0) {
				return strfinal;
			}
		}
		if ((index - 16) > -1) { // 千万亿
			qianwanyi = amt.substring(index - 16, index - 15);
			String qianwanyicn = hanZiChg(qianwanyi);
			if (qianwanyi.equals("0")) {
				if (baiwanyi.equals("0")) {
					// 同上
				} else {
					strfinal = qianwanyicn + strfinal;
				}
			} else {
				strfinal = qianwanyicn + "仟" + strfinal;
			}
			if ((index - 16) == 0) {
				return strfinal;
			}
		}
		
		
		return strfinal;
	}
	
	
	
	/**转汉字*/
	public String hanZiChg(String num){
		String strtrim = num.trim();
		if(strtrim == null){
			return "";
		}else if(strtrim.equals("0")){
			return "零";
		}else if(strtrim.equals("1")){
			return "壹";
		}else if(strtrim.equals("2")){
			return "贰";
		}else if(strtrim.equals("3")){
			return "叁";
		}else if(strtrim.equals("4")){
			return "肆";
		}else if(strtrim.equals("5")){
			return "伍";
		}else if(strtrim.equals("6")){
			return "陆";
		}else if(strtrim.equals("7")){
			return "柒";
		}else if(strtrim.equals("8")){
			return "捌";
		}else if(strtrim.equals("9")){
			return "玖";
		}else{
			return "";
		}
	}
	
	
	
	
	
	/**判断是否所有的万位都为0  */
	public boolean judgeisallwanzero(String amt){
		int len = amt.length();
		int index = amt.indexOf(".");
		int intlen;     //最终的整数位长度
		
		if(index>-1){
			intlen = index;
		}else{
			intlen = len;
		}
		
		if( intlen < 9 ){
			return false;
		}
		
		String wanwei = amt.substring(intlen-8,intlen-4);
		if( !wanwei.trim().equals("0000") ){
			return false;
		}
		
		return true;
	}
	
	
	
	
	
}




