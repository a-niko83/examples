package org.grabber.lg.service.lj;

public interface LJClient {
    String load(int year, int month, int day) throws Exception;
}
