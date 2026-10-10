package uml.class_problems.problem1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Classes & multiplicities:
 *   Examination 1 ---- 1..* Question   (composition: questions belong to the exam)
 *   Student     1 ---- 0..* Attempt
 *   Examination 1 ---- 0..* Attempt    (at most ONE submitted attempt per Student per Examination)
 *   Question <<abstract>> <|-- MultipleChoiceQuestion, TrueFalseQuestion
 *
 * Interaction: Student -> Examination.start(student) creates Attempt (IN_PROGRESS)
 *              Student -> Attempt.answer(q, ans) records answer (only while IN_PROGRESS)
 *              Student -> Attempt.submit() -> state SUBMITTED, Examination.evaluate(attempt)
 *              evaluate loops over questions and calls q.isCorrect(answer) polymorphically.
 */
abstract class Question {
    private final int number;
    private final String text;

    Question(int number, String text) {
        this.number = number;
        this.text = text;
    }

    int getNumber() {
        return number;
    }

    String getText() {
        return text;
    }

    abstract boolean isCorrect(String answer);
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    MultipleChoiceQuestion(int number, String text, String correctOption) {
        super(number, text);
        this.correctOption = correctOption;
    }

    @Override
    boolean isCorrect(String answer) {
        return correctOption.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctValue;

    TrueFalseQuestion(int number, String text, boolean correctValue) {
        super(number, text);
        this.correctValue = correctValue;
    }

    @Override
    boolean isCorrect(String answer) {
        return Boolean.parseBoolean(answer) == correctValue;
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

enum AttemptState {
    IN_PROGRESS, SUBMITTED
}

class Attempt {
    private final Student student;
    private final Examination examination;
    private final Map<Integer, String> answers = new LinkedHashMap<>();
    private AttemptState state = AttemptState.IN_PROGRESS;
    private int score = -1;

    Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    void answer(int questionNumber, String answer) {
        if (state == AttemptState.SUBMITTED) {
            System.out.println("Cannot change answers: attempt already submitted.");
            return;
        }
        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber + " answered with '" + answer + "'.");
    }

    void submit() {
        if (state == AttemptState.SUBMITTED) {
            System.out.println("Attempt already submitted.");
            return;
        }
        state = AttemptState.SUBMITTED;
        examination.registerSubmission(this);
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        score = examination.evaluate(this);
        System.out.println("Result for '" + examination.getTitle() + "' attempt: " + score + "/"
                + examination.getQuestionCount() + " correct");
    }

    String getAnswer(int questionNumber) {
        return answers.get(questionNumber);
    }

    Student getStudent() {
        return student;
    }

    AttemptState getState() {
        return state;
    }
}

class Examination {
    private final String title;
    private final List<Question> questions = new ArrayList<>();
    private final List<Attempt> submittedAttempts = new ArrayList<>();

    Examination(String title) {
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    void addQuestion(Question q) {
        questions.add(q);
    }

    int getQuestionCount() {
        return questions.size();
    }

    Attempt start(Student student) {
        for (Attempt a : submittedAttempts) {
            if (a.getStudent() == student) {
                System.out.println(student.getName() + " has already submitted '" + title + "'.");
                return null;
            }
        }
        System.out.println("Examination '" + title + "' started by " + student.getName() + ".");
        return new Attempt(student, this);
    }

    void registerSubmission(Attempt attempt) {
        submittedAttempts.add(attempt);
    }

    int evaluate(Attempt attempt) {
        int correct = 0;
        for (Question q : questions) {
            String ans = attempt.getAnswer(q.getNumber());
            if (ans != null && q.isCorrect(ans)) {
                correct++;
            }
        }
        return correct;
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion(1, "2 + 2 = ?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion(2, "3 x 3 = ?", "B"));

        Student student = new Student("Student");
        Attempt attempt = exam.start(student);
        attempt.answer(1, "A");
        attempt.answer(2, "C");
        attempt.submit();

        attempt.answer(2, "B");  // rejected: submitted attempts are locked
        exam.start(student);     // rejected: only one submitted attempt per exam
    }
}
