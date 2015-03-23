package cn.com.cis.job.split;


import cn.com.cis.domain.JobInfo;

public class SplitFactory {

    public static Splittable getSplittableInstance(JobInfo jobEntity) {
        Splittable instance;
        switch (jobEntity.getModel()) {
            case TIME_PERIOD:
                instance =  new SplitByTimePeriod(jobEntity);
                break;
            case NORMAL:
                instance = new DefaultSplit(jobEntity);
                break;
            default:
                instance =  new DefaultSplit(jobEntity);
                break;
        }
        return instance;
    }
}
