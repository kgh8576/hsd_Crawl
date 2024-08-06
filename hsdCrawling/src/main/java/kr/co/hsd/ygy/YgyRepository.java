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
    
    public List<Map<String, Object>> selectYgyDetail() {
        String sql = "SELECT * FROM YGY_M_DTAIL"
        		   + "WHERE COMPLITE_YN = 'N'";
        return jdbcTemplate.queryForList(sql);
    }
    
    public void insertSale(YgyDAO order) {
        String sql = "INSERT INTO YGY_SALE\r\n"
        		+ "(CD_PARTNER_ORIGIN, CD_PARTNER, LN_PARTNER, NO_COMPANY,DT_SALE, NOPOS, SLIPNO, SLIPLINE, SALESFG, CANCELYN, FGSYSTEM, AMTOT, ITEMTOT, NUMUSERDEF, DCRMK, QTSALE, ICDUSERDEF1, ICDUSERDEF2, ICDUSERDEF3, ICDUSERDEF4, FGPACK, LCDYSERDEF1, LCDYSERDEF2, SALESTIME, INSERTTIME)\r\n"
        		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyymmddhh24miss'))";
        jdbcTemplate.update(sql,order.getCdPartnerOrigin(),order.getCdPartner(),order.getLnPartner(),order.getNoCompany(),order.getDtSale(),order.getNoPos(),order.getSlipNo(),order.getSlipLine(),order.getSalesFg(),order.getCancelYn(),order.getFgSystem(),order.getAmTot(),order.getItemTot(),order.getNumUserdef(),order.getDcRmk(),order.getQtSale(),order.getiCdUserdef1(),order.getiCdUserdef2(),order.getiCdUserdef3(),order.getiCdUserdef4(),order.getFgPack(),order.getlCdYserdef1(),order.getlCdYserdef2(),order.getSalesTime());
    }

	public void insertCompleteLog(String cdPartnerOrigin, String targetStartDays) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO YGY_COMP_LOG\\r\\n"
				+ "(CD_PARTNER_ORIGIN, TARGET_START_DAYS)"
				+ "VALUES(?,?)";
		jdbcTemplate.update(sql,cdPartnerOrigin, targetStartDays);
	}
	
	public void updateComplite(String cdPartnerOrigin) {
		// TODO Auto-generated method stub
		String sql = "UPDATE YGY_M_DTAIL SET COMPLITE_YN = 'Y' WHERE (SELECT COUNT(*) FROM YGY_COMP_LOG WHERE CD_PARTNER_ORIGIN = ?) = 3";
		jdbcTemplate.update(sql,cdPartnerOrigin);
	}

}
