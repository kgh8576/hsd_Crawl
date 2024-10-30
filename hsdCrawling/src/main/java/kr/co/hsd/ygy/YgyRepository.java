package kr.co.hsd.ygy;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class YgyRepository {
		
	private final JdbcTemplate jdbcTemplate;

    public YgyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Map<String, Object>> selectYgyDetail(String correctYn) {
        String sql = "SELECT * FROM YGY_M_DTAIL WHERE COMPLITE_YN = 'N' AND CORRECT_YN = ?";
        return jdbcTemplate.queryForList(sql, correctYn);
    }
    
    public List<Map<String, Object>> selectYgyCompLog(String cdPartnerOrigin) {
        String sql = "SELECT TARGET_START_DAYS FROM YGY_COMP_LOG WHERE CD_PARTNER_ORIGIN = ?";
        return jdbcTemplate.queryForList(sql, cdPartnerOrigin);
    }
    
    public void insertSale(YgyDAO order) {
        String sql = "INSERT INTO YGY_SALE\r\n"
        		+ "(CD_PARTNER_ORIGIN, CD_PARTNER, LN_PARTNER, NO_COMPANY,DT_SALE, NOPOS, SLIPNO, SLIPLINE, SALESFG, CANCELYN, FGSYSTEM, AMTOT, ITEMTOT, NUMUSERDEF, DCRMK, QTSALE, ICDUSERDEF1, ICDUSERDEF2, ICDUSERDEF3, ICDUSERDEF4, FGPACK, LCDYSERDEF1, LCDYSERDEF2, SALESTIME, INSERTTIME)\r\n"
        		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyymmddhh24miss'))";
        jdbcTemplate.update(sql,order.getCdPartnerOrigin(),order.getCdPartner(),order.getLnPartner(),order.getNoCompany(),order.getDtSale(),order.getNoPos(),order.getSlipNo(),order.getSlipLine(),order.getSalesFg(),order.getCancelYn(),order.getFgSystem(),order.getAmTot(),order.getItemTot(),order.getNumUserdef(),order.getDcRmk(),order.getQtSale(),order.getiCdUserdef1(),order.getiCdUserdef2(),order.getiCdUserdef3(),order.getiCdUserdef4(),order.getFgPack(),order.getlCdYserdef1(),order.getlCdYserdef2(),order.getSalesTime());
    }

	public void insertCompleteLog(String cdPartnerOrigin, String targetStartDays) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO YGY_COMP_LOG (CD_PARTNER_ORIGIN, TARGET_START_DAYS) VALUES(?,?)";
		jdbcTemplate.update(sql,cdPartnerOrigin, targetStartDays);
	}
	
	public void updateComplite(String cdPartnerOrigin) {
		// TODO Auto-generated method stub
		String sql = "UPDATE YGY_M_DTAIL SET COMPLITE_YN = 'Y' WHERE CD_PARTNER_ORIGIN = ? AND (SELECT COUNT(*) FROM YGY_COMP_LOG WHERE CD_PARTNER_ORIGIN = ?) = 3";
		jdbcTemplate.update(sql,cdPartnerOrigin,cdPartnerOrigin);
	}

	public void updateYgyDetailCorrectYn(String id, String pw, String correctYn) {
		String sql = "UPDATE YGY_M_DTAIL SET CORRECT_YN= ? WHERE ID = ? AND PW = ?";
		jdbcTemplate.update(sql,correctYn,id,pw);
	}

	public int insertBaminG(String formattedDate) {
		String sql = "INSERT INTO SALE_POSRGTB_SUB\r\n"
				+ "SELECT\r\n"
				+ "	CD_COMPANY,\r\n"
				+ "	CD_PARTNER_ORIGIN,\r\n"
				+ "	CD_PARTNER,\r\n"
				+ "	DT_SALE,\r\n"
				+ "	NO_POS,\r\n"
				+ "	SLIP_NO,\r\n"
				+ "	LPAD(ROW_NUMBER() OVER(PARTITION BY ORDER_CODE ORDER BY ORG_DC_RMK, CD_USERDEF2),6,'0') AS SLIP_LINE,\r\n"
				+ "	CD_ITEM_TEMP,\r\n"
				+ "	CD_ITEM,\r\n"
				+ "	FG_SYSTEM,\r\n"
				+ "	AM_SALE,\r\n"
				+ "	AM_PAYMENT,\r\n"
				+ "	AM_VAT,\r\n"
				+ "	AM_DC,\r\n"
				+ "	AM_TOT,\r\n"
				+ "	DC_RMK,\r\n"
				+ "	QT_SALE,\r\n"
				+ "	UM,\r\n"
				+ "	CD_USERDEF1,\r\n"
				+ "	CD_USERDEF2,\r\n"
				+ "	CD_USERDEF3,\r\n"
				+ "	NULL AS CD_USERDEF4,\r\n"
				+ "	CD_USERDEF5,\r\n"
				+ "	NUM_USERDEF1,\r\n"
				+ "	NUM_USERDEF2,\r\n"
				+ "	NUM_USERDEF3,\r\n"
				+ "	NUM_USERDEF4,\r\n"
				+ "	NUM_USERDEF5,\r\n"
				+ "	ID_INSERT,\r\n"
				+ "	DTS_INSERT,\r\n"
				+ "	ID_UPDATE,\r\n"
				+ "	DTS_UPDATE,\r\n"
				+ "	SALES_TIME,\r\n"
				+ "	ORDER_CODE\r\n"
				+ "	FROM (\r\n"
				+ "	SELECT\r\n"
				+ "		1000 AS CD_COMPANY,\r\n"
				+ "		(SELECT URL_PARTNER FROM MA_PARTNER MP WHERE MP.NO_COMPANY = BS.NO_COMPANY AND CD_COMPANY = '1000' AND MP.DT_OPEN = (SELECT MAX(DT_OPEN) FROM MA_PARTNER MP WHERE MP.NO_COMPANY = BS.NO_COMPANY AND CD_COMPANY = '1000')) AS CD_PARTNER_ORIGIN,\r\n"
				+ "		(SELECT CD_PARTNER FROM MA_PARTNER MP WHERE MP.NO_COMPANY = BS.NO_COMPANY AND CD_COMPANY = '1000' AND MP.DT_OPEN = (SELECT MAX(DT_OPEN) FROM MA_PARTNER MP WHERE MP.NO_COMPANY = BS.NO_COMPANY AND CD_COMPANY = '1000')) AS CD_PARTNER,\r\n"
				+ "		DT_SALE AS DT_SALE,\r\n"
				+ "		20 AS NO_POS,\r\n"
				+ "		(SELECT SLIP_NO FROM SALE_POSRHTB_SUB SPS WHERE SPS.CD_USERDEF5 = BS.ORDER_CODE) SLIP_NO,\r\n"
				+ "		DC_RMK AS ORG_DC_RMK,\r\n"
				+ "		(SELECT CD_ITEM FROM BAMIN_ITEM_TEMP BIT WHERE BIT.DC_RMK = CASE WHEN SUB_YN = '가격' THEN BS.DC_RMK ELSE BS.SUB_RMK END) AS CD_ITEM_TEMP,\r\n"
				+ "		'U2400176' AS CD_ITEM,\r\n"
				+ "		'BM_IMPORT' AS FG_SYSTEM,\r\n"
				+ "		TRUNC(SUB_AM_TOT/1.1) AS AM_SALE,\r\n"
				+ "		SUB_AM_TOT AS AM_PAYMENT,\r\n"
				+ "		SUB_AM_TOT-TRUNC(SUB_AM_TOT/1.1) AS AM_VAT,\r\n"
				+ "		0 AS AM_DC,\r\n"
				+ "		SUB_AM_TOT AS AM_TOT,\r\n"
				+ "		CASE WHEN SUB_YN = '가격' THEN BS.DC_RMK ELSE BS.SUB_RMK END AS DC_RMK,\r\n"
				+ "		SUB_QU_SALE AS QT_SALE,\r\n"
				+ "		SUB_AM_PAYMENT AS UM,\r\n"
				+ "		CASE WHEN SUB_YN = '가격' AND (SELECT count(*) FROM BAMIN_SALE BSS WHERE SUB_YN != '가격' AND BSS.ORDER_CODE = BS.ORDER_CODE AND BSS.DC_RMK = BS.DC_RMK AND BSS.AM_PAYMENT = BS.AM_PAYMENT) > 1 THEN 'Y' ELSE 'N' END AS CD_USERDEF1,\r\n"
				+ "		CASE WHEN SUB_YN = '가격' THEN 'N' ELSE 'Y' END AS CD_USERDEF2,\r\n"
				+ "		CASE WHEN SUB_YN = '가격' THEN '0' ELSE (SELECT POS_ITEM FROM BAMIN_ITEM_TEMP BIT WHERE BIT.DC_RMK = BS.DC_RMK) END AS CD_USERDEF3,\r\n"
				+ "		CASE WHEN SALE_TYPE = 'TAKEOUT' THEN 'T' ELSE 'D' END AS CD_USERDEF5,\r\n"
				+ "		NULL AS NUM_USERDEF1,\r\n"
				+ "		NULL AS NUM_USERDEF2,\r\n"
				+ "		NULL AS NUM_USERDEF3,\r\n"
				+ "		NULL AS NUM_USERDEF4,\r\n"
				+ "		NULL AS NUM_USERDEF5,\r\n"
				+ "		'HSD' AS ID_INSERT,\r\n"
				+ "		TO_CHAR(SYSDATE,'YYYYMMDDHH24mmss') AS DTS_INSERT,\r\n"
				+ "		NULL AS ID_UPDATE,\r\n"
				+ "		NULL AS DTS_UPDATE,\r\n"
				+ "		SALE_TIME AS SALES_TIME,\r\n"
				+ "		ORDER_CODE AS ORDER_CODE\r\n"
				+ "	FROM\r\n"
				+ "		BAMIN_SALE BS\r\n"
				+ "	WHERE DT_SALE = ? "
				+ ") A";
		
		int result = jdbcTemplate.update(sql,formattedDate);
		return result;
	}

}
