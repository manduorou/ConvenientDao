package com.oldtree.ptydbhelper.exception;

/**
 * 详细介绍类的情况.
 *
 * @ClassName PoJoException
 * @Author oldTree
 * @Date 2022/4/23
 * @Version 1.0
 */
public class PoJoException extends Exception{
    private Class<?> cls;

    public PoJoException(String message, Class<?> cls) {
        super(message);
        this.cls = cls;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return cls.getName()+message;
    }
}

