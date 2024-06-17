package bitcamp.myapp;

import java.util.ArrayList;

public class TeamCommand {
    static final int MAX_LENGTH = 50;
    static Team[] teams = new Team[MAX_LENGTH];
    static int teamLength = 0;

    static void autoCreateTeam() {
        Team team = new Team();
        team.teamName = "테스트팀";
        team.member = new String[]{"하나", "둘", "셋"};
        team.memberLength = 2;
        teams[0] = team;
        teamLength++;
        System.out.print("team 추가 :" + teams[0].teamName);
    }

    static void createTeam() {
        Team team = new Team();
        User[] users = UserCommand.users;
        int userLength = UserCommand.userLength;

        team.teamName = Prompt.prompt("팀명? ");
        int memberNo = 1;
        while (true) {
            try {
                memberNo = Integer.parseInt(Prompt.prompt("추가할 팀원 번호?(종료: 0) "));
                if (memberNo < 0 || memberNo > userLength) {
                    System.out.println("없는 팀원입니다.");
                } else if (memberNo == 0) {
                    teams[teamLength] = team;
                    break;
                } else {
                    team.member[team.memberLength] = users[memberNo - 1].name;
                    System.out.printf("'%s'을 추가했습니다.\n", users[memberNo - 1].name);
                }
            } catch (NumberFormatException e) {
                System.out.println("회원 번호는 숫자만 입력 가능합니다.");
            }

        }
    }

    static void listTeam() {
        System.out.println("번호 팀명");
        int count = 0;
        for (int i = 0; i < teamLength; i++) {
            System.out.printf("%d %s\n", i + 1, teams[i].teamName);
        }
    }

    static void searchTeam() {
        int teamNo;
        while (true) {
            try {
                teamNo = Integer.parseInt(Prompt.prompt("팀 번호? "));
                if (teamNo < 0 || teamNo > teams.length) {
                    System.out.println("없는 팀입니다.");
                    break;
                } else {
                    System.out.printf("팀명: %s\n", teams[teamNo - 1].teamName);
                    System.out.println("팀원");
                    for (int i = 0; i <= teams[teamNo - 1].memberLength; i++) {
                        System.out.printf("- %s\n", teams[teamNo - 1].member[i]);
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("팀 번호는 숫자만 입력 가능합니다.");
            }
        }
    }

    static void updateTeam() {
        int teamNo;
        while (true) {
            try {
                teamNo = Integer.parseInt(Prompt.prompt("팀 번호? "));
                if (teamNo < 0 || teamNo > teams.length) {
                    System.out.println("없는 팀입니다.");
                    break;
                } else {
                    Team teamsNo = teams[teamNo - 1];
                    teamsNo.teamName = Prompt.prompt(String.format("팀명(%s)? ", teamsNo.teamName));
                }
            } catch (NumberFormatException e) {
                System.out.println("팀 번호는 숫자만 입력 가능합니다.");
            }
        }
    }

    static void deleteTeam(){
        int teamNo;
        while (true){
            try{
                teamNo = Integer.parseInt(Prompt.prompt("팀 번호? "));
                if (teamNo < 0 || teamNo > teams.length) {
                    System.out.println("없는 팀입니다.");
                    break;
                } else {
                    for(int i = teamNo; i < teams.length; i++){
                        teams[teamNo - 1] = teams[teamNo];
                    }
                    teams[teamLength] = null;
                    teamLength--;
                    break;
                }
            }catch (NumberFormatException e){
                System.out.println("팀 번호는 숫자만 입력 가능합니다.");
            }
        }
    }
}
