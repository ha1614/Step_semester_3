package uml.assigment_problems.problem1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Hackathon 1 ---- 0..* Team ; Team 1 ---- 2..4 Student ; Student 0..1 ---- Team (per hackathon)
 * Team 1 ---- 0..1 Project ; Project 1 ---- 0..1 Score ; Judge 1 ---- 0..* Score
 * Team ---> ScoringRule <<interface>> (InnovationTrack, OpenTrack) - new tracks = new class only
 * Hackathon state: OPEN -> JUDGING -> PUBLISHED (published results are locked)
 *
 * Interactions: Organiser -> Hackathon.registerTeam() validates size + one-team-per-student;
 * Team -> Hackathon.submitProject(); Judge -> Hackathon.recordScore() -> Score;
 * finalScore = team.getTrack().finalScore(score) (polymorphic); Organiser -> publishResults() locks.
 */
interface ScoringRule {
    String trackName();

    double finalScore(int idea, int execution, int presentation);
}

class InnovationTrack implements ScoringRule {
    public String trackName() {
        return "Innovation";
    }

    public double finalScore(int idea, int execution, int presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
}

class OpenTrack implements ScoringRule {
    public String trackName() {
        return "Open";
    }

    public double finalScore(int idea, int execution, int presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private final String name;

    Student(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class Judge {
    private final String name;

    Judge(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class Score {
    private int idea;
    private int execution;
    private int presentation;

    Score(int idea, int execution, int presentation) {
        set(idea, execution, presentation);
    }

    void set(int idea, int execution, int presentation) {
        if (!valid(idea) || !valid(execution) || !valid(presentation)) {
            throw new IllegalArgumentException("Ratings must be between 0 and 10");
        }
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    private static boolean valid(int r) {
        return r >= 0 && r <= 10;
    }

    int getIdea() {
        return idea;
    }

    int getExecution() {
        return execution;
    }

    int getPresentation() {
        return presentation;
    }
}

class Project {
    private final String title;
    private final Team team;
    private Score score;

    Project(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    String getTitle() {
        return title;
    }

    Team getTeam() {
        return team;
    }

    Score getScore() {
        return score;
    }

    void setScore(Score score) {
        this.score = score;
    }

    double finalScore() {
        return team.getTrack().finalScore(score.getIdea(), score.getExecution(), score.getPresentation());
    }
}

class Team {
    private final String name;
    private final List<Student> members;
    private final ScoringRule track;
    private Project project;

    Team(String name, List<Student> members, ScoringRule track) {
        this.name = name;
        this.members = new ArrayList<>(members);
        this.track = track;
    }

    String getName() {
        return name;
    }

    List<Student> getMembers() {
        return members;
    }

    ScoringRule getTrack() {
        return track;
    }

    Project getProject() {
        return project;
    }

    void setProject(Project project) {
        this.project = project;
    }
}

enum HackathonState {
    OPEN, JUDGING, PUBLISHED
}

class Hackathon {
    private final String name;
    private final List<Team> teams = new ArrayList<>();
    private final Set<Student> registeredStudents = new HashSet<>();
    private HackathonState state = HackathonState.OPEN;

    Hackathon(String name) {
        this.name = name;
    }

    Team registerTeam(String teamName, List<Student> members, ScoringRule track) {
        if (state != HackathonState.OPEN) {
            System.out.println("Registration failed: Registration is closed.");
            return null;
        }
        if (members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return null;
        }
        for (Student s : members) {
            if (registeredStudents.contains(s)) {
                System.out.println("Registration failed: " + s.getName() + " already belongs to a team.");
                return null;
            }
        }
        Team team = new Team(teamName, members, track);
        teams.add(team);
        registeredStudents.addAll(members);
        System.out.println("Team " + teamName + " registered (" + members.size() + " members, "
                + track.trackName() + " track).");
        return team;
    }

    Project submitProject(Team team, String title) {
        if (team.getProject() != null) {
            System.out.println("Submission failed: " + team.getName() + " has already submitted a project.");
            return null;
        }
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Submission failed: Results have already been published.");
            return null;
        }
        Project p = new Project(title, team);
        team.setProject(p);
        System.out.println("Project '" + title + "' submitted by " + team.getName() + ".");
        return p;
    }

    void recordScore(Judge judge, Project project, int idea, int execution, int presentation) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println((project.getScore() == null ? "Scoring" : "Rescore")
                    + " rejected: Results have already been published.");
            return;
        }
        state = HackathonState.JUDGING;
        if (project.getScore() == null) {
            project.setScore(new Score(idea, execution, presentation));
        } else {
            project.getScore().set(idea, execution, presentation);
        }
        System.out.printf("Score recorded for '%s'. Final score: %.2f.%n", project.getTitle(), project.finalScore());
    }

    void changeIdeaScore(Judge judge, Project project, int newIdea) {
        Score s = project.getScore();
        recordScore(judge, project, newIdea, s.getExecution(), s.getPresentation());
    }

    void publishResults() {
        state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");
        Judge judge = new Judge("Dr. Rao");

        List<Student> byteMembers = new ArrayList<>();
        byteMembers.add(asha);
        byteMembers.add(ravi);
        byteMembers.add(neha);
        Team byteBusters = hackathon.registerTeam("ByteBusters", byteMembers, new InnovationTrack());

        List<Student> soloMembers = new ArrayList<>();
        soloMembers.add(kiran);
        hackathon.registerTeam("SoloCoder", soloMembers, new OpenTrack());

        Project smartAttend = hackathon.submitProject(byteBusters, "SmartAttend");
        hackathon.recordScore(judge, smartAttend, 8, 7, 9);
        hackathon.publishResults();
        hackathon.changeIdeaScore(judge, smartAttend, 10);
    }
}
