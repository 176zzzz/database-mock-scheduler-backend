package com.zp.model.enums;

/**
 * LogLevel
 *
 * @author ZP
 * @since 2024/5/28 8:55
 */
public enum LogLevel {

    /**
     *
     * 错误日志等级
     */
    INFO("info"),

    ERROR("error");

    private final String level;

    LogLevel(String level) {
        this.level=level;
    }

    public String getLevel() {
        return level;
    }
}
