package kr.co.hsd.ygy;

public class YgyDAO {

	String lnPartner;
	String cdPartnerOrigin;
	String cdPartner;
	String noCompany;
	String dtSale;
	String noPos = "20";
	String slipNo;
	String slipLine;
	String salesFg = "Y";
	String cancelYn = "N";
	String fgSystem = "HSD"; // 연동시스템구분
	String amTot; // 주문별총매출액
	String itemTot; // 상품별총매출액
	String numUserdef; // 품목개수
	String dcRmk; // 상품명
	String qtSale; // 매출수량
	String iCdUserdef1; // 부가메뉴 포함여부
	String iCdUserdef2; // 부가메뉴 여부
	String iCdUserdef3; // 부모상품코드
	String iCdUserdef4; // 부모상풐모드 순번(FOREIGN spliLine)
	String fgPack; // 배달(D),포장(T) 구분 item:cdUserdef5
	String lCdYserdef1; // 채널코드 (YOGIYOEXPRESS/YOGIYOINTEGRATION)
	String lCdYserdef2; // 채널번호
	String salesTime;
	
	public YgyDAO() {
	}
	
	
	public YgyDAO(YgyDAO parents) {
		this.lnPartner = parents.getLnPartner();
		this.cdPartnerOrigin = parents.getCdPartnerOrigin();
		this.cdPartner = parents.getCdPartner();
		this.noCompany = parents.getNoCompany();
		this.dtSale = parents.getDtSale();
		this.noPos = parents.getNoPos();
		this.slipNo = parents.getSlipNo();
		this.slipLine = parents.getSlipLine();
		this.salesFg = parents.getSalesFg();
		this.cancelYn = parents.getCancelYn();
		this.fgSystem = parents.getFgSystem();
		this.amTot = parents.getAmTot();
		this.numUserdef = parents.getNumUserdef();
		this.iCdUserdef1 = "N";
		this.iCdUserdef2 = "Y";
		this.iCdUserdef3 = parents.getDcRmk();
		this.fgPack = parents.getFgPack();
		this.lCdYserdef1 = parents.getlCdYserdef1();
		this.lCdYserdef2 = parents.getlCdYserdef2();
		this.salesTime = parents.getSalesTime();
	}
	
	
	public String getLnPartner() {
		return lnPartner;
	}
	public void setLnPartner(String lnPartner) {
		this.lnPartner = lnPartner;
	}
	public String getCdPartnerOrigin() {
		return cdPartnerOrigin;
	}
	public void setCdPartnerOrigin(String cdPartnerOrigin) {
		this.cdPartnerOrigin = cdPartnerOrigin;
	}
	public String getCdPartner() {
		return cdPartner;
	}
	public void setCdPartner(String cdPartner) {
		this.cdPartner = cdPartner;
	}
	public String getDtSale() {
		return dtSale;
	}
	public void setDtSale(String dtSale) {
		this.dtSale = dtSale;
	}
	public String getNoPos() {
		return noPos;
	}
	public void setNoPos(String noPos) {
		this.noPos = noPos;
	}
	public String getSlipNo() {
		return slipNo;
	}
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}
	public String getSlipLine() {
		return slipLine;
	}
	public void setSlipLine(String slipLine) {
		this.slipLine = slipLine;
	}
	public String getSalesFg() {
		return salesFg;
	}
	public void setSalesFg(String salesFg) {
		this.salesFg = salesFg;
	}
	public String getCancelYn() {
		return cancelYn;
	}
	public void setCancelYn(String cancelYn) {
		this.cancelYn = cancelYn;
	}
	public String getFgSystem() {
		return fgSystem;
	}
	public void setFgSystem(String fgSystem) {
		this.fgSystem = fgSystem;
	}
	public String getAmTot() {
		return amTot;
	}
	public void setAmTot(String amTot) {
		this.amTot = amTot;
	}
	public String getNumUserdef() {
		return numUserdef;
	}
	public void setNumUserdef(String numUserdef) {
		this.numUserdef = numUserdef;
	}
	public String getDcRmk() {
		return dcRmk;
	}
	public void setDcRmk(String dcRmk) {
		this.dcRmk = dcRmk;
	}
	public String getQtSale() {
		return qtSale;
	}
	public void setQtSale(String qtSale) {
		this.qtSale = qtSale;
	}
	public String getiCdUserdef1() {
		return iCdUserdef1;
	}
	public void setiCdUserdef1(String iCdUserdef1) {
		this.iCdUserdef1 = iCdUserdef1;
	}
	public String getiCdUserdef2() {
		return iCdUserdef2;
	}
	public void setiCdUserdef2(String iCdUserdef2) {
		this.iCdUserdef2 = iCdUserdef2;
	}
	public String getiCdUserdef3() {
		return iCdUserdef3;
	}
	public void setiCdUserdef3(String iCdUserdef3) {
		this.iCdUserdef3 = iCdUserdef3;
	}
	public String getiCdUserdef4() {
		return iCdUserdef4;
	}
	public void setiCdUserdef4(String iCdUserdef4) {
		this.iCdUserdef4 = iCdUserdef4;
	}
	public String getFgPack() {
		return fgPack;
	}
	public void setFgPack(String fgPack) {
		this.fgPack = fgPack;
	}
	public String getlCdYserdef1() {
		return lCdYserdef1;
	}
	public void setlCdYserdef1(String lCdYserdef1) {
		this.lCdYserdef1 = lCdYserdef1;
	}
	public String getlCdYserdef2() {
		return lCdYserdef2;
	}
	public void setlCdYserdef2(String lCdYserdef2) {
		this.lCdYserdef2 = lCdYserdef2;
	}
	public String getSalesTime() {
		return salesTime;
	}
	public void setSalesTime(String salesTime) {
		this.salesTime = salesTime;
	}
	public String getItemTot() {
		return itemTot;
	}
	public void setItemTot(String itemTot) {
		this.itemTot = itemTot;
	}

	public String getNoCompany() {
		return noCompany;
	}

	public void setNoCompany(String noCompany) {
		this.noCompany = noCompany;
	}

	@Override
	public String toString() {
		return "YgyDAO [lnPartner=" + lnPartner + ", cdPartnerOrigin=" + cdPartnerOrigin + ", cdPartner=" + cdPartner
				+ ", noCompany=" + noCompany + ", dtSale=" + dtSale + ", noPos=" + noPos + ", slipNo=" + slipNo
				+ ", slipLine=" + slipLine + ", salesFg=" + salesFg + ", cancelYn=" + cancelYn + ", fgSystem="
				+ fgSystem + ", amTot=" + amTot + ", itemTot=" + itemTot + ", numUserdef=" + numUserdef + ", dcRmk="
				+ dcRmk + ", qtSale=" + qtSale + ", iCdUserdef1=" + iCdUserdef1 + ", iCdUserdef2=" + iCdUserdef2
				+ ", iCdUserdef3=" + iCdUserdef3 + ", iCdUserdef4=" + iCdUserdef4 + ", fgPack=" + fgPack
				+ ", lCdYserdef1=" + lCdYserdef1 + ", lCdYserdef2=" + lCdYserdef2 + ", salesTime=" + salesTime + "]";
	}
	
}
