package com.example.dovebook.net.process;

/**
 * 进度监听器
 */
public interface ProcessListener {

    /**
     * 出现异常时调用
     * @param e
     */
    void onError(Exception e);

    /**
     * 进度更新时调用
     * @param info {@link ProcessInfo}
     */
    void onProcess(ProcessInfo info);
}
