package com.xhc.sbm.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author: xhc
 * @Date: 2020/7/5 23:37
 * @Description:
 */

public class LogHttpServletResponse extends HttpServletResponseWrapper {
    Logger log = LoggerFactory.getLogger(LogHttpServletResponse.class);

    private  volatile LoggerServletOutputStream loggerServletOutputStream;

    public LogHttpServletResponse(HttpServletResponse response) {
        super(response);
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(loggerServletOutputStream==null){
            synchronized (this) {
                if(loggerServletOutputStream==null){
                    loggerServletOutputStream = new  LoggerServletOutputStream(super.getOutputStream());
                }
            }
        }
        return loggerServletOutputStream;
    }

    public String getResponseBody(){
        if(loggerServletOutputStream != null){
            return new String( loggerServletOutputStream.getWroteInfo());
        }else{
            return "";
        }
    }



    public static class LoggerServletOutputStream extends ServletOutputStream {

        private ServletOutputStream output;
        private ByteArrayOutputStream copy=new ByteArrayOutputStream();



        public LoggerServletOutputStream(ServletOutputStream output) {
            super();
            this.output = output;
        }

        @Override
        public boolean isReady() {
            return output.isReady();
        }

        @Override
        public void setWriteListener(WriteListener arg0) {
            output.setWriteListener(arg0);
        }

        @Override
        public void write(int b) throws IOException {
            output.write(b);
            copy.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            output.write(b);
            copy.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            output.write(b,off,len);
            copy.write(b,off,len);
        }

        public byte[] getWroteInfo() {
            return copy.toByteArray();
        }

        @Override
        public void flush() throws IOException {
            output.flush();
            copy.flush();
        }

        @Override
        public void close() throws IOException {
            output.close();
            copy.close();
        }


    }
}
