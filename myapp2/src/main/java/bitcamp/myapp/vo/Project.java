package bitcamp.myapp.vo;
public class Project {
    private final int MAX_MEMBER = 10;
    private String title;
    private String explanation;
    private String startDate;
    private String endDate;
    private User[] members = new User[MAX_MEMBER];
    private int memberLength;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void addMember(User user){
        members[memberLength++] = user;
    }

    public void deleteMember(int userNo){
        for(int i = userNo; i < memberLength + 1; i++){
            members[i - 1] = members[i];
        }
        members[--memberLength] = null;
    }

    public boolean isMember(User user){
        for(int i = 0; i < memberLength; i++){
            if(user == members[i]){
                return true;
            }
        }
        return false;
    }

    public int getMemberLength() {
        return memberLength;
    }

    public User[] getMembers() {
        return members;
    }

}
