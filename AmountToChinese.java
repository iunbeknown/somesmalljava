package wg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;






/** ����ҵİ��������ֽ�� ת���ɺ���չʾ  */
public class AmountToChinese {

	
	/**����ҵ����ֽ�� ת���ɺ���չʾ(���֧��ǧ���ڼ���)   add by iunbeknown  */
	public String getChineseAmount(String amt){
		if(amt==null || amt.trim().equals("")){
			return "";
		}
		
		Pattern pattern = Pattern.compile("[.0-9]*");
		Matcher isNum = pattern.matcher(amt);
		boolean b = isNum.matches();
		if(!b){
			return "��ת�������ָ�ʽ����";
		}
		
		//ȥ������ǰ������0
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
			return "�������֧���޶ȣ�";
		}
		
		
		int len = amt.length();
		int index = amt.indexOf(".");
		
		String jiaofen="";
		//��ȡ�Ƿ�λ
		if(index>-1){
			int jfnum = len-index+1;  //�Ƿ��м�λ
			if(jfnum == 0){
				jiaofen="��";
			}else if(jfnum == 1){
				String jiao = amt.substring(index+1,index+2);
				String jiaocn = hanZiChg(jiao);
				if(jiao.equals("0")){
					jiaofen="��";
				}else{
					jiaofen = jiaocn+"��";
				}
			}else{
				String jiao = amt.substring(index+1,index+2);
				String jiaocn = hanZiChg(jiao);
				String fen = amt.substring(index+2,index+3);
				String fencn = hanZiChg(fen);
				if(jiao.equals("0") && fen.equals("0")){
					jiaofen="��";
				}else if(fen.equals("0")){
					jiaofen = jiaocn+"��";
				}else if(jiao.equals("0")){
					jiaofen = fencn+"��";
				}else{
					jiaofen = jiaocn+"��"+fencn+"��";
				}
			}
		}else{
			jiaofen="��";
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
		
		String strfinal=""; //��ȡ���յĺ���
		
		if ((index - 1) > -1) { // Ԫ
			yuan = amt.substring(index - 1, index);
			String yuancn = hanZiChg(yuan);
			if (yuan.equals("0")) {
				strfinal = "Ԫ" + jiaofen;
			} else {
				strfinal = yuancn + "Ԫ" + jiaofen;
			}
			if ((index - 1) == 0) {
				return strfinal;
			}
		}
		if ((index - 2) > -1) { // ʮ
			shi = amt.substring(index - 2, index - 1);
			String shicn = hanZiChg(shi);
			if (shi.equals("0")) {
				if (yuan.equals("0")) {
					// ��λλ��ʮλ����0ʱ������
				} else {
					strfinal = shicn + strfinal;
				}
			} else {
				strfinal = shicn + "ʰ" + strfinal;
			}
			if ((index - 2) == 0) {
				return strfinal;
			}
		}
		if ((index - 3) > -1) { // ��
			bai = amt.substring(index - 3, index - 2);
			String baicn = hanZiChg(bai);
			if (bai.equals("0")) {
				if (shi.equals("0")) {
					// �����ʮλͬʱΪ0�����λ���ñ仯
				} else {
					strfinal = baicn + strfinal;
				}
			} else {
				strfinal = baicn + "��" + strfinal;
			}
			if ((index - 3) == 0) {
				return strfinal;
			}
		}
		if ((index - 4) > -1) { // ǧ
			qian = amt.substring(index - 4, index - 3);
			String qiancn = hanZiChg(qian);
			if (qian.equals("0")) {
				if (bai.equals("0")) {
					// ͬ��
				} else {
					strfinal = qiancn + strfinal;
				}
			} else {
				strfinal = qiancn + "Ǫ" + strfinal;
			}
			if ((index - 4) == 0) {
				return strfinal;
			}
		}
		if ((index - 5) > -1) { // ��
			wan = amt.substring(index - 5, index - 4);
			String wancn = hanZiChg(wan);
			if (wan.equals("0")) {
				boolean isallzero = judgeisallwanzero(amt); // �ж����е���λ�Ƿ�Ϊ0
				if (isallzero) {
					if (qian.equals("0")) {
						
					} else {
						strfinal = wancn + strfinal;
					}
				} else {
					strfinal = "��" + strfinal;
				}
			} else {
				strfinal = wancn + "��" + strfinal;
			}
			if ((index - 5) == 0) {
				return strfinal;
			}
		}
		if ((index - 6) > -1) { // ʮ��
			shiwan = amt.substring(index - 6, index - 5);
			String shiwancn = hanZiChg(shiwan);
			if (shiwan.equals("0")) {
				if (wan.equals("0")) {
					// ��λλ��ʮλ����0ʱ������
				} else {
					strfinal = shiwancn + strfinal;
				}
			} else {
				strfinal = shiwancn + "ʰ" + strfinal;
			}
			if ((index - 6) == 0) {
				return strfinal;
			}
		}
		if ((index - 7) > -1) { // ����
			baiwan = amt.substring(index - 7, index - 6);
			String baiwancn = hanZiChg(baiwan);
			if (baiwan.equals("0")) {
				if (shiwan.equals("0")) {
					// ͬ��
				} else {
					strfinal = baiwancn + strfinal;
				}
			} else {
				strfinal = baiwancn + "��" + strfinal;
			}
			if ((index - 7) == 0) {
				return strfinal;
			}
		}
		if ((index - 8) > -1) { // ǧ��
			qianwan = amt.substring(index - 8, index - 7);
			String qianwancn = hanZiChg(qianwan);
			if (qianwan.equals("0")) {
				if (baiwan.equals("0")) {
					// ͬ��
				} else {
					strfinal = qianwancn + strfinal;
				}
			} else {
				strfinal = qianwancn + "Ǫ" + strfinal;
			}
			if ((index - 8) == 0) {
				return strfinal;
			}
		}

		if ((index - 9) > -1) { // ��
			yi = amt.substring(index - 9, index - 8);
			String yicn = hanZiChg(yi);
			if (yi.equals("0")) {
				strfinal = "��" + strfinal;
			} else {
				strfinal = yicn + "��" + strfinal;
			}
			if ((index - 9) == 0) {
				return strfinal;
			}
		}
		if ((index - 10) > -1) { // ʮ��
			shiyi = amt.substring(index - 10, index - 9);
			String shiyicn = hanZiChg(shiyi);
			if (shiyi.equals("0")) {
				if (yi.equals("0")) {
					// ��λλ��ʮλ����0ʱ������
				} else {
					strfinal = shiyicn + strfinal;
				}
			} else {
				strfinal = shiyicn + "ʰ" + strfinal;
			}
			if ((index - 10) == 0) {
				return strfinal;
			}
		}
		if ((index - 11) > -1) { // ����
			baiyi = amt.substring(index - 11, index - 10);
			String baiyicn = hanZiChg(baiyi);
			if (baiyi.equals("0")) {
				if (shiyi.equals("0")) {
					// �����ʮλͬʱΪ0�����λ���ñ仯
				} else {
					strfinal = baiyicn + strfinal;
				}
			} else {
				strfinal = baiyicn + "��" + strfinal;
			}
			if ((index - 11) == 0) {
				return strfinal;
			}
		}
		if ((index - 12) > -1) { // ǧ��
			qianyi = amt.substring(index - 12, index - 11);
			String qianyicn = hanZiChg(qianyi);
			if (qianyi.equals("0")) {
				if (baiyi.equals("0")) {
					// ͬ��
				} else {
					strfinal = qianyicn + strfinal;
				}
			} else {
				strfinal = qianyicn + "Ǫ" + strfinal;
			}
			if ((index - 12) == 0) {
				return strfinal;
			}
		}
		
		if( (index-13) > -1){ //����
			wanyi = amt.substring(index-13,index-12);
			String wanyicn = hanZiChg(wanyi);
			if(wanyi.equals("0")){
				strfinal = "��" + strfinal;
			}else{
				strfinal = wanyicn+"��" + strfinal;
			}
			if( (index-13) == 0){
				return strfinal;
			}
		}
		if ((index - 14) > -1) { // ʮ����
			shiwanyi = amt.substring(index - 14, index - 13);
			String shiwanyicn = hanZiChg(shiwanyi);
			if (shiwanyi.equals("0")) {
				if (wanyi.equals("0")) {
					// ��λλ��ʮλ����0ʱ������
				} else {
					strfinal = shiwanyicn + strfinal;
				}
			} else {
				strfinal = shiwanyicn + "ʰ" + strfinal;
			}
			if ((index - 14) == 0) {
				return strfinal;
			}
		}
		if ((index - 15) > -1) { // ������
			baiwanyi = amt.substring(index - 15, index - 14);
			String baiwanyicn = hanZiChg(baiwanyi);
			if (baiwanyi.equals("0")) {
				if (shiwanyi.equals("0")) {
					// ͬ��
				} else {
					strfinal = baiwanyicn + strfinal;
				}
			} else {
				strfinal = baiwanyicn + "��" + strfinal;
			}
			if ((index - 15) == 0) {
				return strfinal;
			}
		}
		if ((index - 16) > -1) { // ǧ����
			qianwanyi = amt.substring(index - 16, index - 15);
			String qianwanyicn = hanZiChg(qianwanyi);
			if (qianwanyi.equals("0")) {
				if (baiwanyi.equals("0")) {
					// ͬ��
				} else {
					strfinal = qianwanyicn + strfinal;
				}
			} else {
				strfinal = qianwanyicn + "Ǫ" + strfinal;
			}
			if ((index - 16) == 0) {
				return strfinal;
			}
		}
		
		
		return strfinal;
	}
	
	
	
	/**ת����*/
	public String hanZiChg(String num){
		String strtrim = num.trim();
		if(strtrim == null){
			return "";
		}else if(strtrim.equals("0")){
			return "��";
		}else if(strtrim.equals("1")){
			return "Ҽ";
		}else if(strtrim.equals("2")){
			return "��";
		}else if(strtrim.equals("3")){
			return "��";
		}else if(strtrim.equals("4")){
			return "��";
		}else if(strtrim.equals("5")){
			return "��";
		}else if(strtrim.equals("6")){
			return "½";
		}else if(strtrim.equals("7")){
			return "��";
		}else if(strtrim.equals("8")){
			return "��";
		}else if(strtrim.equals("9")){
			return "��";
		}else{
			return "";
		}
	}
	
	
	
	
	
	/**�ж��Ƿ����е���λ��Ϊ0  */
	public boolean judgeisallwanzero(String amt){
		int len = amt.length();
		int index = amt.indexOf(".");
		int intlen;     //���յ�����λ����
		
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




