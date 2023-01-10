package test.springboard.domain;

import java.util.Date;

public class Board {

    private  Long BoardNo;
    private  String UserId;
    private  String Title;
    private  String Contents;
    private  String TypeCode;
    private Date RegisterDate;
    private  Date ModifyDate;


    public Long getBoardNo() { return BoardNo; }
    public void setBoardNo(Long boardNo) { BoardNo = boardNo; }

    public String getUserId() { return UserId; }
    public void setUserId(String userId) { UserId = userId; }

    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }

    public String getContents() { return Contents; }
    public void setContents(String contents) { Contents = contents; }

    public String getTypeCode() { return TypeCode; }
    public void setTypeCode(String typeCode) { TypeCode = typeCode; }

    public Date getRegisterDate() { return RegisterDate; }
    public void setRegisterDate(Date registerDate) { RegisterDate = registerDate; }

    public Date getModifyDate() { return ModifyDate; }
    public void setModifyDate(Date modifyDate) { ModifyDate = modifyDate; }
}
