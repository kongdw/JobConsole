package cn.com.cis.service;

import cn.com.cis.domain.Mode;
import cn.com.cis.persistence.ModeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModeService {
    @Autowired
    private ModeMapper mapper;
    @Transactional(readOnly = true)
    public List<Mode> selectAllMode() {
        return mapper.selectAllMode();
    }
    @Transactional(readOnly = true)
    public Mode selectModeById(String code) {
        return mapper.selectModeById(code);
    }
}
