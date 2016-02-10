package gobtech.normienotes;

import java.util.Date;

/**
 * Created by gabrielk on 2/9/16.
 */

public class gNote {

    private int classNum;
    private String className;
    private String professor;
    private Date date;

    public gNote(int classNum, String className, String professor, Date date) {
        this.classNum = classNum;
        this.className = className;
        this.professor = professor;
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

    public Date getDate() {
        return date;
    }
}
