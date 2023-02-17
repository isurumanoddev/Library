package tm;

public class MemberTM {
    private String MemberID;
    private String MemberName;
    private String MemberEmail;
    private String MemberAddress;
    private String MemberMobile;

    public MemberTM() {
    }

    public MemberTM(String memberID, String memberName, String memberEmail, String memberAddress, String memberMobile) {
        MemberID = memberID;
        MemberName = memberName;
        MemberEmail = memberEmail;
        MemberAddress = memberAddress;
        MemberMobile = memberMobile;
    }

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String memberID) {
        MemberID = memberID;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberEmail() {
        return MemberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        MemberEmail = memberEmail;
    }

    public String getMemberAddress() {
        return MemberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        MemberAddress = memberAddress;
    }

    public String getMemberMobile() {
        return MemberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        MemberMobile = memberMobile;
    }
}
