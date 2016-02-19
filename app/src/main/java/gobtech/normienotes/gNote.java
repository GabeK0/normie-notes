package gobtech.normienotes;

import java.util.Date;

/**
 * Created by gabrielk on 2/9/16.
 */

public class gNote {

    private int classNum;
    private String className;
    private String professor;
    private String noteTitle;
    private String noteText;
    private Date date;

    public gNote(int classNum, String className, String professor, String noteTitle, String noteText, Date date) {
        this.classNum = classNum;
        this.className = className;
        this.professor = professor;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.date = date;
    }


    public int getClassNum() {
        return classNum;
    }

    public String getClassName() {
        return className;
    }

    public String getProfessor() {
        return professor;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getDate() {
        return date;
    }
}
