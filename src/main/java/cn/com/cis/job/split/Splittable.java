package cn.com.cis.job.split;


import cn.com.cis.task.entity.TaskInfo;

import java.util.List;

public interface Splittable {

    public static final long ONE_DAY = 24 * 3600 * 1000;

    public List<TaskInfo> split() throws Exception;


}
