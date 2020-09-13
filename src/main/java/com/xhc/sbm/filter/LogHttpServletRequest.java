package com.xhc.sbm.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @Author: xhc
 * @Date: 2020/4/13 15:57
 * @Description: 为了记录日志自定义ServletRequestWrapper
 * 读取body后提供重新封装的ServletInputStream{@link LoggerServletInputStream}
 */
@Log4j2
public class LogHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] body;
    private HttpServletRequest request;
    private LoggerServletInputStream servletInputStream;


    public LogHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
        servletInputStream = new LoggerServletInputStream();
        try {
            body = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("body解析异常", e);
            body = new byte[]{};
        }
    }

    public void resetServletInputStream() {
        try {
            servletInputStream.inputStream = new ByteArrayInputStream(new String(body).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {

            log.error(e.getMessage());

        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (body == null) {
            try {
                body = IOUtils.toByteArray(request.getInputStream());
            } catch (IOException e) {
                log.error("body解析异常", e);
                body = new byte[]{};
            }
        }
        servletInputStream.inputStream = new ByteArrayInputStream(body);
        return servletInputStream;
    }

    public byte[] getBody() {
        return body;
    }

    private class LoggerServletInputStream extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

    }
}
