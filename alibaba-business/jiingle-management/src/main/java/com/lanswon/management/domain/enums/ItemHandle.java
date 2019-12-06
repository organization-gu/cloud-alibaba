package com.lanswon.management.domain.enums;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 17:00
 */
public enum ItemHandle {

    /**
     * 领用物资
     */
    USE ("01","领用"),
    /**
     * 消耗物资
     */
    CONSUME ("02","消耗"),

    /**
     * 物资进货
     */
    INTO ("03","进库");


    public String code;

    public String desc;

     ItemHandle(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static ItemHandle getcode(String code){
        ItemHandle httpStatus=null;
        for(ItemHandle status:values()){
            if(status.code.equals(code)){
                httpStatus=status;
                break;
            }
        }
        return httpStatus;
    }
}
