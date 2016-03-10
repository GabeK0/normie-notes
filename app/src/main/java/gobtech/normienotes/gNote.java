package gobtech.normienotes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gabrielk on 2/9/16.
 */

public class gNote implements Serializable {

    private String className;
    private String professor;
    private String noteTitle;
    private String author;
    private String noteText;
    private int type;
    private int ID;
    private int up;
    private int down;
    private Date date;
    private long unixDate;

    public gNote(int ID, String className, String professor, String noteTitle, String author, String noteText, int up, int down, long initDate, int type) {
        this.ID = ID;
        this.className = className;
        this.professor = professor;
        this.noteTitle = noteTitle;
        this.author = author;
        this.noteText = noteText;
        this.up = up;
        this.down = down;
        this.unixDate = initDate;
        date = new Date(unixDate*1000);

        this.type = type;

    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getID() {
        return ID;
    }

    public int getType() {
        return type;
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
