package kr.co.hsd.ygy;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class YgyService {

	private final YgyRepository ygyRepository;
    
	public YgyService(YgyRepository ygyRepository) {
        this.ygyRepository = ygyRepository;
    }
	
	public List<Map<String, Object>> selectYgyDetail(String correctYn) {
		return ygyRepository.selectYgyDetail(correctYn);
    }
	
	public void insertSale(List<YgyDAO> orderList) {
		for (YgyDAO order : orderList) {
			ygyRepository.insertSale(order);
		}
    }

	public void insertCompleteLog(String cdPartnerOrigin, String targetStartDays) {
		ygyRepository.insertCompleteLog(cdPartnerOrigin, targetStartDays);
	}
	
	public void updateComplite(String cdPartnerOrigin) {
		ygyRepository.updateComplite(cdPartnerOrigin);
	}

	public void updateYgyDetailCorrectYn(String id, String pw, String correctYn) {
		ygyRepository.updateYgyDetailCorrectYn(id, pw, correctYn);
	}
}
