package com.ppd.mubler.model.entity.log4j;

import org.apache.log4j.Logger;

public class MublerLogger {
    private Logger logger = Logger.getLogger(MublerLogger.class);

    private static final MublerLogger INSTANCE = new MublerLogger();

    public void logInfo(String toLog){
        this.logger.info(toLog);
    }

    public static MublerLogger getInstance(){
        return INSTANCE;
    }
}
