package cn.com.cis.persistence;

import cn.com.cis.domain.Mode;

import java.util.List;

public interface ModeMapper {

    List<Mode> selectAllMode();

    Mode selectModeById(String code);

}
