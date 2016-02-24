package gobtech.normienotes;

import android.util.Log;

import java.util.Date;

/**
 * Created by gabrielk on 2/9/16.
 */

public class gNote {

    private String className;
    private String professor;
    private String noteTitle;
    private String author;
    private String noteText;
    private Date date;
    private long unixDate;

    public gNote(String className, String professor, String noteTitle, String author, String noteText, long initDate) {
        this.className = className;
        this.professor = professor;
        this.noteTitle = noteTitle;
        this.author = author;
        this.noteText = noteText;

        this.unixDate = initDate;

        date = new Date(unixDate*1000);

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

    public String getAuthor() {
        return author;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getDate() {
        return date;
    }
}
